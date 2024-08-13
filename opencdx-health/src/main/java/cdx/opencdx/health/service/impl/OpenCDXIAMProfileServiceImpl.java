/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXConflict;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Address;
import cdx.opencdx.grpc.data.AuditEntity;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.service.OpenCDXIAMProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/**
 * Service for processing IAM Profile Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMProfileServiceImpl implements OpenCDXIAMProfileService {
    private static final String DOMAIN = "OpenCDXIAMProfileServiceImpl";
    private static final String USER_RECORD_ACCESSED = "User record Accessed";
    private static final String IAM_USER = "USERS: ";
    private static final String FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL = "Failed to convert OpenCDXIAMUserModel";
    private static final String FAILED_TO_FIND_USER = "Failed to find user: ";
    private static final String OBJECT = "OBJECT";
    private static final String COUNTRY = "country";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for setting up Profile service
     *
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXProfileRepository Profile repository
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXDocumentValidator Document validator
     */
    public OpenCDXIAMProfileServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public UserProfileResponse getUserProfile(UserProfileRequest request) {
        OpenCDXProfileModel model = null;

        if (request.hasUserId()) {
            model = this.openCDXProfileRepository
                    .findByUserId(new OpenCDXIdentifier(request.getUserId()))
                    .orElseThrow(
                            () -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find by user id: " + request.getUserId()));
        } else if (request.hasPatientId()) {
            model = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(request.getPatientId()))
                    .orElseThrow(() ->
                            new OpenCDXNotFound(DOMAIN, 10, "Failed to find by patient id: " + request.getUserId()));
        } else {
            Optional<OpenCDXProfileModel> currentUserProfile = this.openCDXProfileRepository.findByUserId(
                    openCDXCurrentUser.getCurrentUser().getId());
            if (currentUserProfile.isPresent()) {
                model = currentUserProfile.get();
            } else {
                model = new OpenCDXProfileModel();
                model.setUserId(openCDXCurrentUser.getCurrentUser().getId());
                model = this.openCDXProfileRepository.save(model);
            }
        }

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    USER_RECORD_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
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
                        .map(OpenCDXIdentifier::new)
                        .toList());
        for (Address address : request.getUpdatedProfile().getAddressList()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY, new OpenCDXIdentifier(address.getCountryId()));
        }

        if (request.getUpdatedProfile().hasEmergencyContact()) {
            for (Address address : request.getUpdatedProfile()
                    .getEmergencyContact()
                    .getContactInfo()
                    .getAddressesList()) {
                this.openCDXDocumentValidator.validateDocumentOrThrow(
                        COUNTRY, new OpenCDXIdentifier(address.getCountryId()));
            }
        }
        if (request.getUpdatedProfile().hasPharmacyDetails()
                && request.getUpdatedProfile().getPharmacyDetails().hasPharmacyAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new OpenCDXIdentifier(request.getUpdatedProfile()
                            .getPharmacyDetails()
                            .getPharmacyAddress()
                            .getCountryId()));
        }
        if (request.getUpdatedProfile().hasPlaceOfBirth()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new OpenCDXIdentifier(
                            request.getUpdatedProfile().getPlaceOfBirth().getCountry()));
        }
        if (request.getUpdatedProfile().hasEmployeeIdentity()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "organization",
                    new OpenCDXIdentifier(
                            request.getUpdatedProfile().getEmployeeIdentity().getOrganizationId()));
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "workspace",
                    new OpenCDXIdentifier(
                            request.getUpdatedProfile().getEmployeeIdentity().getWorkspaceId()));
        }

        OpenCDXProfileModel model = this.openCDXProfileRepository
                .findById(new OpenCDXIdentifier(request.getUpdatedProfile().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_USER + request.getUserId()));
        model = this.openCDXProfileRepository.save(model.update(request.getUpdatedProfile()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
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

    /**
     * Method to create a user Profile
     *
     * @param request Request containing the user profile.
     * @return Response with user profile.
     */
    @Override
    public CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request) {
        boolean forceUser = false;

        if (!openCDXCurrentUser.getCurrentUser().getAgentType().equals(AgentType.AGENT_TYPE_SYSTEM)) {
            Optional<OpenCDXProfileModel> userProfile = this.openCDXProfileRepository.findByUserId(
                    openCDXCurrentUser.getCurrentUser().getId());
            if (userProfile.isPresent()) {
                throw new OpenCDXConflict(
                        DOMAIN,
                        2,
                        "User Profile exists: " + userProfile.get().getId().toHexString());
            }
            forceUser = true;
        }

        this.openCDXDocumentValidator.validateDocumentsOrThrow(
                "users",
                request.getUserProfile().getDependentIdList().stream()
                        .map(OpenCDXIdentifier::new)
                        .toList());
        for (Address address : request.getUserProfile().getAddressList()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY, new OpenCDXIdentifier(address.getCountryId()));
        }

        if (request.getUserProfile().hasEmergencyContact()) {
            for (Address address : request.getUserProfile()
                    .getEmergencyContact()
                    .getContactInfo()
                    .getAddressesList()) {
                this.openCDXDocumentValidator.validateDocumentOrThrow(
                        COUNTRY, new OpenCDXIdentifier(address.getCountryId()));
            }
        }
        if (request.getUserProfile().hasPharmacyDetails()
                && request.getUserProfile().getPharmacyDetails().hasPharmacyAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new OpenCDXIdentifier(request.getUserProfile()
                            .getPharmacyDetails()
                            .getPharmacyAddress()
                            .getCountryId()));
        }
        if (request.getUserProfile().hasPlaceOfBirth()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    COUNTRY,
                    new OpenCDXIdentifier(
                            request.getUserProfile().getPlaceOfBirth().getCountry()));
        }
        if (request.getUserProfile().hasEmployeeIdentity()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "organization",
                    new OpenCDXIdentifier(
                            request.getUserProfile().getEmployeeIdentity().getOrganizationId()));
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "workspace",
                    new OpenCDXIdentifier(
                            request.getUserProfile().getEmployeeIdentity().getWorkspaceId()));
        }

        if (this.openCDXProfileRepository.existsByNationalHealthId(
                request.getUserProfile().getNationalHealthId())) {
            throw new OpenCDXConflict(DOMAIN, 3, "Profile exist with NHID " + request.getUserProfile());
        }

        OpenCDXProfileModel model = new OpenCDXProfileModel(request.getUserProfile());
        if (forceUser) {
            model.setUserId(openCDXCurrentUser.getCurrentUser().getId());
        }

        model = this.openCDXProfileRepository.save(model);

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User record updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return CreateUserProfileResponse.newBuilder()
                .setUserProfile(model.getUserProfileProtobufMessage())
                .build();
    }

    @Override
    public DeleteUserProfileResponse deleteUserProfile(DeleteUserProfileRequest request) {
        OpenCDXProfileModel model = this.openCDXProfileRepository
                .findById(new OpenCDXIdentifier(request.getUserId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 5, FAILED_TO_FIND_USER + request.getUserId()));

        model.setActive(false);

        model = this.openCDXProfileRepository.save(model);
        log.trace("Deleted User: {}", request.getUserId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User record deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    IAM_USER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
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
