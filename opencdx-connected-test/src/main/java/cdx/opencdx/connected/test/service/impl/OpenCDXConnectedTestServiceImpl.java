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
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTest;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for processing connected test Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@ExtendWith(MockitoExtension.class)
public class OpenCDXConnectedTestServiceImpl implements OpenCDXConnectedTestService {

    private static final String DOMAIN = "OpenCDXConnectedTestServiceImpl";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    private final ObjectMapper objectMapper;

    /**
     * Constructore with OpenCDXAuditService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXConnectedTestRepository Mongo Repository for OpenCDXConnectedTest
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     */
    public OpenCDXConnectedTestServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) {
        ConnectedTest submittedTest = this.openCDXConnectedTestRepository
                .save(new OpenCDXConnectedTest(connectedTest))
                .getProtobufMessage();

        try {
            this.openCDXAuditService.phiCreated(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_SYSTEM,
                    "Connected Test Submitted.",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    "Connected Test Submissions",
                    this.objectMapper.writeValueAsString(submittedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert ConnectedTest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", submittedTest.toString());
            throw openCDXNotAcceptable;
        }
        log.info("Created test: {}", submittedTest.getBasicInfo().getId());
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
            this.openCDXAuditService.phiAccessed(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
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
        Page<OpenCDXConnectedTest> all = this.openCDXConnectedTestRepository.findAllByUserId(
                objectId, PageRequest.of(request.getPageNumber(), request.getPageSize()));
        log.info("found database results");

        return ConnectedTestListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllConnectedTests(
                        all.get().map(OpenCDXConnectedTest::getProtobufMessage).toList())
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
        Page<OpenCDXConnectedTest> all = this.openCDXConnectedTestRepository.findAllByNationalHealthId(
                nationalHealthId, PageRequest.of(request.getPageNumber(), request.getPageSize()));
        log.info("found database results");

        return ConnectedTestListByNHIDResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllConnectedTests(
                        all.get().map(OpenCDXConnectedTest::getProtobufMessage).toList())
                .build();
    }
}
