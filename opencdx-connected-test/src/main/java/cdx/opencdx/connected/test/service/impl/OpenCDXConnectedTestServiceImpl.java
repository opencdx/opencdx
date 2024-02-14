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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.communication.Notification;
import cdx.opencdx.grpc.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for processing connected test Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXConnectedTestServiceImpl implements OpenCDXConnectedTestService {

    private static final String DOMAIN = "OpenCDXConnectedTestServiceImpl";
    private static final String CONNECTED_TEST = "CONNECTED-TEST: ";
    private static final String CONNECTED_TEST_ACCESSED = "Connected Test Accessed.";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_CONNECTED_TEST = "Failed to convert ConnectedTest";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXCommunicationService openCDXCommunicationService;
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXClassificationMessageService openCDXClassificationMessageService;

    /**
     * Constructore with OpenCDXAuditService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXConnectedTestRepository Mongo Repository for OpenCDXConnectedTest
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXCommunicationService    Communication Service for informing user test received.
     * @param openCDXIAMUserRepository       Repository to look up patient.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXClassificationMessageService Service for submitting connected tests for classification
     */
    public OpenCDXConnectedTestServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXCommunicationService openCDXCommunicationService,
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXClassificationMessageService openCDXClassificationMessageService) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXCommunicationService = openCDXCommunicationService;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXClassificationMessageService = openCDXClassificationMessageService;
    }

    @Override
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) {

        if (!connectedTest.hasBasicInfo()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 1, "Connected Test does not have basic info");
        }

        ObjectId patientID = new ObjectId(connectedTest.getBasicInfo().getUserId());

        this.openCDXDocumentValidator.validateOrganizationWorkspaceOrThrow(
                new ObjectId(connectedTest.getBasicInfo().getOrganizationId()),
                new ObjectId(connectedTest.getBasicInfo().getWorkspaceId()));

        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "devices", new ObjectId(connectedTest.getTestDetails().getDeviceIdentifier()));

        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "media", new ObjectId(connectedTest.getTestDetails().getMediaId()));

        OpenCDXIAMUserModel patient = this.openCDXIAMUserRepository
                .findById(patientID)
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find patient"));

        ConnectedTest submittedTest = this.openCDXConnectedTestRepository
                .save(new OpenCDXConnectedTestModel(connectedTest))
                .getProtobufMessage();

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Connected Test Submitted.",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
                    CONNECTED_TEST + submittedTest.getBasicInfo().getId(),
                    this.objectMapper.writeValueAsString(submittedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_CONNECTED_TEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, submittedTest.toString());
            throw openCDXNotAcceptable;
        }

        if (patient.getPrimaryContactInfo() != null) {

            Notification.Builder builder = Notification.newBuilder()
                    .setEventId(OpenCDXCommunicationService.NOTIFICATION)
                    .putAllVariables(Map.of(
                            "firstName",
                            patient.getFullName().getFirstName(),
                            "lastName",
                            patient.getFullName().getLastName(),
                            "notification",
                            "OpenCDX received a new test for you: "
                                    + submittedTest.getTestDetails().getTestName()));

            EmailAddress emailAddress = patient.getPrimaryContactInfo().getEmailsList().stream()
                    .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_PERSONAL))
                    .findFirst()
                    .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                            .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_WORK))
                            .findFirst()
                            .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                                    .findFirst()
                                    .orElse(null)));
            if (emailAddress != null) {
                builder.addAllToEmail(List.of(emailAddress.getEmail()));
            }
            List<String> mobileList = patient.getPrimaryContactInfo().getPhoneNumbersList().stream()
                    .filter(phoneNumber -> phoneNumber.getType().equals(PhoneType.PHONE_TYPE_MOBILE))
                    .map(PhoneNumber::getNumber)
                    .toList();
            if (!mobileList.isEmpty()) {
                builder.addAllToPhoneNumber(mobileList);
            }

            this.openCDXCommunicationService.sendNotification(builder.build());
        }

        this.openCDXClassificationMessageService.submitConnectedTest(
                patientID, new ObjectId(submittedTest.getBasicInfo().getId()), null);
        return TestSubmissionResponse.newBuilder()
                .setSubmissionId(submittedTest.getBasicInfo().getId())
                .build();
    }

    @Override
    public ConnectedTest getTestDetailsById(TestIdRequest testIdRequest) {
        ConnectedTest connectedTest = this.openCDXConnectedTestRepository
                .findById(new ObjectId(testIdRequest.getTestId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 3, "Failed to find connected test: " + testIdRequest.getTestId()))
                .getProtobufMessage();

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    CONNECTED_TEST_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    connectedTest.getBasicInfo().getUserId(),
                    connectedTest.getBasicInfo().getNationalHealthId(),
                    CONNECTED_TEST + connectedTest.getBasicInfo().getId(),
                    this.objectMapper.writeValueAsString(connectedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_CONNECTED_TEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, connectedTest.toString());
            throw openCDXNotAcceptable;
        }
        return connectedTest;
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(ConnectedTestListRequest request) {

        ObjectId objectId = new ObjectId(request.getUserId());

        log.info("Searching Database");

        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }

        Page<OpenCDXConnectedTestModel> all = this.openCDXConnectedTestRepository.findAllByUserId(objectId, pageable);
        log.info("found database results");

        all.get().forEach(openCDXConnectedTestModel -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        CONNECTED_TEST_ACCESSED,
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        openCDXConnectedTestModel.getBasicInfo().getUserId(),
                        openCDXConnectedTestModel.getBasicInfo().getNationalHealthId(),
                        CONNECTED_TEST + openCDXConnectedTestModel.getId(),
                        this.objectMapper.writeValueAsString(openCDXConnectedTestModel.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_CONNECTED_TEST, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXConnectedTestModel.toString());
                throw openCDXNotAcceptable;
            }
        });

        return ConnectedTestListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllConnectedTests(all.get()
                        .map(OpenCDXConnectedTestModel::getProtobufMessage)
                        .toList())
                .build();
    }

    /**
     * Retrieve a list of connected tests by national health id
     *
     * @param request Request message containing the pageable information and user to request records on.
     * @return Response containing the indicated page of recards.
     */
    @Override
    public ConnectedTestListByNHIDResponse listConnectedTestsByNHID(ConnectedTestListByNHIDRequest request) {

        String nationalHealthId = request.getNationalHealthId();

        log.info("Searching Database");
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }
        Page<OpenCDXConnectedTestModel> all =
                this.openCDXConnectedTestRepository.findAllByNationalHealthId(nationalHealthId, pageable);
        log.info("found database results");

        all.get().forEach(openCDXConnectedTestModel -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        CONNECTED_TEST_ACCESSED,
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        openCDXConnectedTestModel.getBasicInfo().getUserId(),
                        openCDXConnectedTestModel.getBasicInfo().getNationalHealthId(),
                        CONNECTED_TEST + openCDXConnectedTestModel.getId(),
                        this.objectMapper.writeValueAsString(openCDXConnectedTestModel.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_CONNECTED_TEST, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXConnectedTestModel.toString());
                throw openCDXNotAcceptable;
            }
        });

        return ConnectedTestListByNHIDResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllConnectedTests(all.get()
                        .map(OpenCDXConnectedTestModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
