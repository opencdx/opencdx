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
import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXConnectedTestServiceImpl implements OpenCDXConnectedTestService {

    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    private final ObjectMapper objectMapper;

    /**
     * Constructore with OpenCDXAuditService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXConnectedTestRepository
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
        try {
            this.openCDXAuditService.phiCreated(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_SYSTEM,
                    "Connected Test Submitted.",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    "Connected Test Submissions",
                    this.objectMapper.writeValueAsString(connectedTest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    "OpenCDXConnectedTestServiceImpl", 1, "Failed to convert ConnectedTest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", connectedTest.toString());
            throw openCDXNotAcceptable;
        }

        return TestSubmissionResponse.getDefaultInstance();
    }

    @Override
    public ConnectedTest getTestDetailsById(TestIdRequest testIdRequest) {
        try {
            this.openCDXAuditService.phiAccessed(
                    ObjectId.get().toHexString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Connected Test Accessed.",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ObjectId.get().toHexString(),
                    "Connected Test Accessed",
                    this.objectMapper.writeValueAsString(ConnectedTest.getDefaultInstance()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    "OpenCDXConnectedTestServiceImpl", 2, "Failed to convert ConnectedTest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable
                    .getMetaData()
                    .put("OBJECT", ConnectedTest.getDefaultInstance().toString());
            throw openCDXNotAcceptable;
        }
        return ConnectedTest.getDefaultInstance();
    }
}
