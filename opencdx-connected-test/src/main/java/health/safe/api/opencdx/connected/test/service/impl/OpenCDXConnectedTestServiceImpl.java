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
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import io.micrometer.observation.annotation.Observed;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXConnectedTestServiceImpl implements OpenCDXConnectedTestService {

    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructore with OpenCDXAuditService
     * @param openCDXAuditService user for recording PHI
     */
    public OpenCDXConnectedTestServiceImpl(OpenCDXAuditService openCDXAuditService) {
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) {
        this.openCDXAuditService.phiCreated(
                ObjectId.get().toHexString(),
                AgentType.AGENT_TYPE_SYSTEM,
                "Connected Test Submitted.",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                ObjectId.get().toHexString());
        return TestSubmissionResponse.getDefaultInstance();
    }

    @Override
    public ConnectedTest getTestDetailsById(TestIdRequest testIdRequest) {
        this.openCDXAuditService.phiAccessed(
                ObjectId.get().toHexString(),
                AgentType.AGENT_TYPE_HUMAN_USER,
                "Connected Test Accessed.",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                ObjectId.get().toHexString());
        return ConnectedTest.getDefaultInstance();
    }
}
