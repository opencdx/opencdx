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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.profile.*;
import cdx.opencdx.iam.service.OpenCDXIAMProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMProfileServiceImpl implements OpenCDXIAMProfileService {
    private static final String DOMAIN = "OpenCDXIAMProfileServiceImpl";
    private static final String USER_RECORD_ACCESSED = "User record Accessed";
    private static final String IAM_USER = "IAM_USER: ";
    private static final String FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL = "Failed to convert OpenCDXIAMUserModel";
    private static final String FAILED_TO_FIND_USER = "Failed to find user: ";
    private static final String OBJECT = "OBJECT";
    private static final String COUNTRY = "country";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for setting up Profile service
     *
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXIAMUserRepository User Repository
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXDocumentValidator Document validator
     */
    public OpenCDXIAMProfileServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public UserProfileResponse getUserProfile(UserProfileRequest request) {
        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getUserId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed t" + "o find user: " + request.getUserId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    USER_RECORD_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    model.getNationalHealthId(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return UserProfileResponse.newBuilder()
                .setUserProfile(model.getUserProfileProtobufMessage())
                .build();
    }

    @Override
    public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request) {
        this.openCDXDocumentValidator.validateDocumentsOrThrow(
                "users",
                request.getUpdatedProfile().getDependentIdList().stream()
                        .map(ObjectId::new)
                        .toList());
        if (request.getUpdatedProfile().hasPrimaryAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new ObjectId(request.getUpdatedProfile().getPrimaryAddress().getCountry()));
        }
        if (request.getUpdatedProfile().hasShippingAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new ObjectId(
                            request.getUpdatedProfile().getShippingAddress().getCountry()));
        }
        if (request.getUpdatedProfile().hasBillingAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new ObjectId(request.getUpdatedProfile().getBillingAddress().getCountry()));
        }
        if (request.getUpdatedProfile().hasEmergencyContact()) {
            if (request.getUpdatedProfile().getEmergencyContact().hasWorkAddress()) {
                this.openCDXDocumentValidator.validateDocumentOrThrow(
                        COUNTRY,
                        new ObjectId(request.getUpdatedProfile()
                                .getEmergencyContact()
                                .getWorkAddress()
                                .getCountry()));
            }
            if (request.getUpdatedProfile().getEmergencyContact().hasResidenceAddress()) {
                this.openCDXDocumentValidator.validateDocumentOrThrow(
                        COUNTRY,
                        new ObjectId(request.getUpdatedProfile()
                                .getEmergencyContact()
                                .getResidenceAddress()
                                .getCountry()));
            }
        }
        if (request.getUpdatedProfile().hasPharmacyDetails()
                && request.getUpdatedProfile().getPharmacyDetails().hasPharmacyAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new ObjectId(request.getUpdatedProfile()
                            .getPharmacyDetails()
                            .getPharmacyAddress()
                            .getCountry()));
        }
        if (request.getUpdatedProfile().hasPlaceOfBirth()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new ObjectId(request.getUpdatedProfile().getPlaceOfBirth().getCountry()));
        }
        if (request.getUpdatedProfile().hasEmployeeIdentity()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "organization",
                    new ObjectId(
                            request.getUpdatedProfile().getEmployeeIdentity().getOrganizationId()));
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "workspace",
                    new ObjectId(
                            request.getUpdatedProfile().getEmployeeIdentity().getWorkspaceId()));
        }

        OpenCDXIAMUserModel model = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getUserId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_USER + request.getUserId()));

        model = this.openCDXIAMUserRepository.save(model.update(request.getUpdatedProfile()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    model.getNationalHealthId(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return UpdateUserProfileResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public DeleteUserProfileResponse deleteUserProfile(DeleteUserProfileRequest request) {
        OpenCDXIAMUserModel userModel = this.openCDXIAMUserRepository
                .findById(new ObjectId(request.getUserId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 5, FAILED_TO_FIND_USER + request.getUserId()));

        userModel.setStatus(IamUserStatus.IAM_USER_STATUS_DELETED);

        userModel = this.openCDXIAMUserRepository.save(userModel);
        log.info("Deleted User: {}", request.getUserId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User record deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    userModel.getId().toHexString(),
                    userModel.getNationalHealthId(),
                    IAM_USER + userModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(userModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return DeleteUserProfileResponse.newBuilder().setSuccess(true).build();
    }
}
