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

import cdx.media.v2alpha.*;
import cdx.open_audit.v2alpha.AgentType;
import cdx.open_audit.v2alpha.SensitivityLevel;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.model.OpenCDXIAMUserModel;
import health.safe.api.opencdx.commons.repository.OpenCDXIAMUserRepository;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMUserServiceImpl implements OpenCDXIAMUserService {

    private static final String DOMAIN = "OpenCDXIAMUserServiceImpl";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL = "Failed to convert OpenCDXIAMUserModel";
    private static final String IAM_USER = "IAM_USER: ";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param objectMapper Object Mapper for converting to JSON
     * @param openCDXAuditService      Audit service for tracking FDA requirements
     * @param openCDXIAMUserRepository Repository for saving users.
     */
    @Autowired
    public OpenCDXIAMUserServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    /**
     * Method to sing up a new user
     *
     * @param request SignUpRequest for new user.
     * @return SignUpResponse with the new user created.
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        // TODO: Implement a check here for the email address.
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .systemName(request.getSystemName())
                .email(request.getEmail())
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(request.getType())
                .password(request.getPassword())
                .build());
        try {
            this.openCDXAuditService.piiCreated(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
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
        // TODO: Log to Audit piiAccessed for each returned OpenCDXIamUserModel returned.
        return ListIamUsersResponse.getDefaultInstance();
    }

    /**
     * Method to get a particular User
     *
     * @param request Request for the user to get.
     * @return Response with the requested user.
     */
    @Override
    public GetIamUserResponse getIamUser(GetIamUserRequest request) {
        // TODO: Update Model to be the newly retrieved model before calling audit.
        OpenCDXIAMUserModel model =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        try {
            this.openCDXAuditService.piiAccessed(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User record Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return GetIamUserResponse.getDefaultInstance();
    }

    /**
     * Update the User informaiton
     *
     * @param request Request with information to update.
     * @return Response the updated user.
     */
    @Override
    public UpdateIamUserResponse updateIamUser(UpdateIamUserRequest request) {

        // TODO: Update Model to the newly saved version before calling Audit.
        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(request.getIamUser());
        try {
            this.openCDXAuditService.piiUpdated(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateIamUserResponse.getDefaultInstance();
    }

    /**
     * Method to change a user password
     *
     * @param request Request to change a user password
     * @return Response for changing a users password.
     */
    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        this.openCDXAuditService.passwordChange(
                ObjectId.get().toHexString(),
                AgentType.AGENT_TYPE_HUMAN_USER,
                "User Password Change",
                ObjectId.get().toHexString());
        return ChangePasswordResponse.getDefaultInstance();
    }

    /**
     * Method to delete a user. User's status is udpated to DELETED. User it not actually removed.
     *
     * @param request Request to delete the specified user.
     * @return Response for deleting a user.
     */
    @Override
    public DeleteIamUserResponse deleteIamUser(DeleteIamUserRequest request) {
        // TODO: Update Model to the newly deleted version.
        OpenCDXIAMUserModel model =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        try {
            this.openCDXAuditService.piiDeleted(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User record deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return DeleteIamUserResponse.getDefaultInstance();
    }

    /**
     * Method to check if a user exists.
     *
     * @param request Request to check if a user exists
     * @return Response if the user exists.
     */
    @Override
    public UserExistsResponse userExists(UserExistsRequest request) {
        // TODO: Update Model to be the newly retrieved model before calling audit.
        OpenCDXIAMUserModel model =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        try {
            this.openCDXAuditService.piiAccessed(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "User record Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UserExistsResponse.getDefaultInstance();
    }
}
