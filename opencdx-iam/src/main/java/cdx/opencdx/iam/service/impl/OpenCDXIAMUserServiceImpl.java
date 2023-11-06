/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXCommunicationClient;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXUnauthorized;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.*;
import cdx.opencdx.grpc.communication.Notification;
import cdx.opencdx.grpc.iam.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMUserServiceImpl implements OpenCDXIAMUserService {

    private static final String USER_RECORD_ACCESSED = "User record Accessed";
    private static final String DOMAIN = "OpenCDXIAMUserServiceImpl";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL = "Failed to convert OpenCDXIAMUserModel";
    private static final String IAM_USER = "IAM_USER: ";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    private static final String FAILED_TO_FIND_USER = "Failed to find user: ";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final OpenCDXCommunicationClient openCDXCommunicationClient;
    private final AppProperties appProperties;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param objectMapper             Object Mapper for converting to JSON
     * @param openCDXAuditService      Audit service for tracking FDA requirements
     * @param openCDXIAMUserRepository Repository for saving users.
     * @param passwordEncoder Password Encoder to use for encrypting and testing passwords.
     * @param appProperties App Properties is used to set the common variables to supply from config.
     * @param passwordEncoder          Password Encoder to use for encrypting and testing passwords.
     * @param openCDXCurrentUser       Current User Service
     * @param authenticationManager    AuthenticationManager for the service
     * @param jwtTokenUtil              Utility class for JWT Tokens
     */
    @Autowired
    public OpenCDXIAMUserServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            OpenCDXCurrentUser openCDXCurrentUser,
            AppProperties appProperties,
            OpenCDXCommunicationClient openCDXCommunicationClient) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXCommunicationClient = openCDXCommunicationClient;
        this.appProperties = appProperties;
    }

    /**
     * Method to sing up a new user
     *
     * @param request SignUpRequest for new user.
     * @return SignUpResponse with the new user created.
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .systemName(request.getSystemName())
                .email(request.getEmail())
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(request.getType())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .build());

        this.openCDXCommunicationClient.sendNotification(
                Notification.newBuilder()
                        .setEventId(OpenCDXCommunicationClient.VERIFY_EMAIL_USER)
                        .addAllToEmail(List.of(model.getEmail()))
                        .putAllVariables(Map.of(
                                FIRST_NAME,
                                model.getFirstName(),
                                LAST_NAME,
                                model.getLastName(),
                                "user_id",
                                model.getId().toHexString(),
                                "verification_server",
                                appProperties.getVerificationUrl()))
                        .build(),
                new OpenCDXCallCredentials(this.jwtTokenUtil.generateAccessToken(model)));

        try {
            this.openCDXAuditService.piiCreated(
                    this.openCDXCurrentUser.getCurrentUser(model).getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return SignUpResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to list users
     *
     * @param request ListIamUserRequest for the users to list.
     * @return ListIamUsersResponse for the users being listed
     */
    @Override
    public ListIamUsersResponse listIamUsers(ListIamUsersRequest request) {
        Page<OpenCDXIAMUserModel> all =
                this.openCDXIAMUserRepository.findAll(PageRequest.of(request.getPageNumber(), request.getPageSize()));
        all.forEach(model -> {
            try {
                this.openCDXAuditService.piiAccessed(
                        this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                        this.openCDXCurrentUser.getCurrentUserType(),
                        USER_RECORD_ACCESSED,
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        model.getId().toHexString(),
                        IAM_USER + model.getId().toHexString(),
                        this.objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
                throw openCDXNotAcceptable;
            }
        });

        return ListIamUsersResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllIamUsers(
                        all.get().map(OpenCDXIAMUserModel::getProtobufMessage).toList())
                .build();
    }

    /**
     * Method to get a particular User
     *
     * @param request Request for the user to get.
     * @return Response with the requested user.
     */
    @Override
    public GetIamUserResponse getIamUser(GetIamUserRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed t" + "o find user: " + request.getId()));

        try {
            this.openCDXAuditService.piiAccessed(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    USER_RECORD_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return GetIamUserResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }

    /**
     * Update the User informaiton
     *
     * @param request Request with information to update.
     * @return Response the updated user.
     */
    @Override
    public UpdateIamUserResponse updateIamUser(UpdateIamUserRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getIamUser().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 3, FAILED_TO_FIND_USER + request.getIamUser().getId()));

        model.setEmail(request.getIamUser().getEmail());
        model.setFirstName(request.getIamUser().getFirstName());
        model.setLastName(request.getIamUser().getLastName());
        model.setPhone(request.getIamUser().getPhone());
        model.setSystemName(request.getIamUser().getSystemName());
        model.setType(request.getIamUser().getType());

        model = this.openCDXIAMUserRepository.save(model);

        try {
            this.openCDXAuditService.piiUpdated(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateIamUserResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to change a user password
     *
     * @param request Request to change a user password
     * @return Response for changing a users password.
     */
    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 4, FAILED_TO_FIND_USER + request.getId()));

        if (passwordEncoder.matches(request.getOldPassword(), model.getPassword())) {
            model.setPassword(passwordEncoder.encode(request.getNewPassword()));
        } else {
            throw new OpenCDXNotAcceptable(
                    DOMAIN, 5, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, new Throwable("Password mismatch"));
        }

        model = this.openCDXIAMUserRepository.save(model);

        this.openCDXCommunicationClient.sendNotification(
                Notification.newBuilder()
                        .setEventId(OpenCDXCommunicationClient.CHANGE_PASSWORD)
                        .addAllToEmail(List.of(model.getEmail()))
                        .addAllToPhoneNumber(List.of(model.getPhone()))
                        .putAllVariables(Map.of(
                                FIRST_NAME,
                                model.getFirstName(),
                                LAST_NAME,
                                model.getLastName(),
                                "notification",
                                "Password changed"))
                        .build(),
                new OpenCDXCallCredentials(this.jwtTokenUtil.generateAccessToken(model)));

        this.openCDXAuditService.passwordChange(
                this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                this.openCDXCurrentUser.getCurrentUserType(),
                "User Password Change",
                model.getId().toHexString());
        return ChangePasswordResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to delete a user. User's status is udpated to DELETED. User it not actually removed.
     *
     * @param request Request to delete the specified user.
     * @return Response for deleting a user.
     */
    @Override
    public DeleteIamUserResponse deleteIamUser(DeleteIamUserRequest request) {
        OpenCDXIAMUserModel userModel = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 5, FAILED_TO_FIND_USER + request.getId()));

        userModel.setStatus(IamUserStatus.IAM_USER_STATUS_DELETED);

        userModel = this.openCDXIAMUserRepository.save(userModel);
        log.info("Deleted User: {}", request.getId());

        try {
            this.openCDXAuditService.piiDeleted(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "User record deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    userModel.getId().toHexString(),
                    IAM_USER + userModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(userModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return DeleteIamUserResponse.newBuilder()
                .setIamUser(userModel.getProtobufMessage())
                .build();
    }

    /**
     * Method to check if a user exists.
     *
     * @param request Request to check if a user exists
     * @return Response if the user exists.
     */
    @Override
    public UserExistsResponse userExists(UserExistsRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 6, FAILED_TO_FIND_USER + request.getId()));

        model = this.openCDXIAMUserRepository.save(model);

        try {
            this.openCDXAuditService.piiAccessed(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    USER_RECORD_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 7, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UserExistsResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to verify user email
     *
     * @param id Request for the user to get.
     * @return Response with the requested user.
     */
    @Override
    public void verifyEmailIamUser(String id) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(id))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, FAILED_TO_FIND_USER + id));

        model.setEmailVerified(true);
        model = this.openCDXIAMUserRepository.save(model);

        this.openCDXCommunicationClient.sendNotification(
                Notification.newBuilder()
                        .setEventId(OpenCDXCommunicationClient.WELCOME_EMAIL_USER)
                        .addAllToEmail(List.of(model.getEmail()))
                        .putAllVariables(Map.of(
                                FIRST_NAME,
                                model.getFirstName(),
                                LAST_NAME,
                                model.getLastName(),
                                "email",
                                model.getEmail()))
                        .build(),
                new OpenCDXCallCredentials(this.jwtTokenUtil.generateAccessToken(model)));

        try {
            this.openCDXAuditService.piiUpdated(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User email verification",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("id", id);
            throw openCDXNotAcceptable;
        }
    }

    /**
     * Method to authenticate user login.
     *
     * @param request Request to authenticate user
     * @return Response if the user login is successful.
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

            OpenCDXIAMUserModel userModel = this.openCDXIAMUserRepository
                    .findByEmail(request.getUserName())
                    .orElseThrow(() -> new OpenCDXUnauthorized(
                            "OpenCDXIAMUserRestController",
                            1,
                            "Failed to authenticate user: " + request.getUserName()));
            String token = jwtTokenUtil.generateAccessToken(userModel);

            return LoginResponse.newBuilder().setToken(token).build();
        } catch (BadCredentialsException ex) {
            throw new OpenCDXUnauthorized(
                    "OpenCDXIAMUserRestController", 2, "Failed to authenticate user: " + request.getUserName(), ex);
        }
    }

    /**
     * Method to fetch current user.
     *
     * @param request Request to fetch current user
     * @return Response is the current user.
     */
    @Override
    public CurrentUserResponse currentUser(CurrentUserRequest request) {
        OpenCDXIAMUserModel model = this.openCDXCurrentUser.getCurrentUser();

        try {
            this.openCDXAuditService.piiAccessed(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    USER_RECORD_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 8, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return CurrentUserResponse.newBuilder()
                .setIamUser(model.getProtobufMessage())
                .build();
    }
}
