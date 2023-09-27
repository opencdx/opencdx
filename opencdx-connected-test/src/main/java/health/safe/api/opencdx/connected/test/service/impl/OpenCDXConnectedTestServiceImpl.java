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
package health.safe.api.opencdx.connected.test.service.impl;

import cdx.open_audit.v2alpha.AgentType;
import cdx.open_audit.v2alpha.SensitivityLevel;
import cdx.open_connected_test.v2alpha.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotFound;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.model.OpenCDXConnectedTest;
import health.safe.api.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
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

        Page<OpenCDXConnectedTest> all = this.openCDXConnectedTestRepository.findAllByBasicInfo_UserId(
                new ObjectId(request.getUserId()), PageRequest.of(request.getPageNumber(), request.getPageSize()));

        return ConnectedTestListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllConnectedTests(
                        all.get().map(OpenCDXConnectedTest::getProtobufMessage).toList())
                .build();
    }
}
