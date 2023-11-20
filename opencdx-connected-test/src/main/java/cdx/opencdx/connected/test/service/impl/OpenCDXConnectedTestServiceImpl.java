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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
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
import org.springframework.stereotype.Service;

/**
 * Service for processing connected test Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXConnectedTestServiceImpl implements OpenCDXConnectedTestService {

    private static final String DOMAIN = "OpenCDXConnectedTestServiceImpl";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXCommunicationService openCDXCommunicationService;

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructore with OpenCDXAuditService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXConnectedTestRepository Mongo Repository for OpenCDXConnectedTest
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXCommunicationService    Communication Service for informing user test received.
     * @param openCDXIAMUserRepository       Repository to look up patient.
     */
    public OpenCDXConnectedTestServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXCommunicationService openCDXCommunicationService,
            OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXCommunicationService = openCDXCommunicationService;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) {
        ObjectId patientID = new ObjectId(connectedTest.getBasicInfo().getUserId());

        OpenCDXIAMUserModel patient = this.openCDXIAMUserRepository
                .findById(patientID)
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 4, "Failed to find patient"));

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
                    "Connected Test Submissions",
                    this.objectMapper.writeValueAsString(submittedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert ConnectedTest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", submittedTest.toString());
            throw openCDXNotAcceptable;
        }

        if(patient.getPrimaryContactInfo() != null) {

            Notification.Builder builder = Notification.newBuilder()
                    .setEventId(OpenCDXCommunicationService.VERIFY_EMAIL_USER)
                    .addAllToEmail(List.of(patient.getPrimaryContactInfo().getEmail()))
                    .putAllVariables(Map.of(
                            "firstName",
                            patient.getFullName().getFirstName(),
                            "lastName",
                            patient.getFullName().getLastName(),
                            "notification",
                            "OpenCDX received a new test for you: "
                                    + submittedTest.getTestDetails().getTestName()));
            if ( patient.getPrimaryContactInfo().hasMobileNumber()) {
                builder.addAllToPhoneNumber(
                        List.of(patient.getPrimaryContactInfo().getMobileNumber().getNumber()));
            }
            this.openCDXCommunicationService.sendNotification(builder.build());
        }
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
                    "Connected Test Accessed.",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    "Connected Test Accessed",
                    this.objectMapper.writeValueAsString(connectedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert ConnectedTest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", connectedTest.toString());
            throw openCDXNotAcceptable;
        }
        return connectedTest;
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(ConnectedTestListRequest request) {

        ObjectId objectId = new ObjectId(request.getUserId());

        log.info("Searching Database");
        Page<OpenCDXConnectedTestModel> all = this.openCDXConnectedTestRepository.findAllByUserId(
                objectId, PageRequest.of(request.getPageNumber(), request.getPageSize()));
        log.info("found database results");

        return ConnectedTestListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
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

        Integer nationalHealthId = request.getNationalHealthId();

        log.info("Searching Database");
        Page<OpenCDXConnectedTestModel> all = this.openCDXConnectedTestRepository.findAllByNationalHealthId(
                nationalHealthId, PageRequest.of(request.getPageNumber(), request.getPageSize()));
        log.info("found database results");

        return ConnectedTestListByNHIDResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllConnectedTests(all.get()
                        .map(OpenCDXConnectedTestModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
