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
package cdx.opencdx.commons.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenCDXAuditServiceImplTest {

    OpenCDXAuditService openCDXAuditService;

    @BeforeEach
    void setUp() {
        openCDXAuditService = new OpenCDXAuditServiceImpl(new NoOpOpenCDXMessageServiceImpl(), "test");
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(
                () -> openCDXAuditService.userLogout("test", AgentType.AGENT_TYPE_HUMAN_USER, "Logout"));
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() ->
                openCDXAuditService.userAccessChange("test", AgentType.AGENT_TYPE_HUMAN_USER, "Access Change", "test"));
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.phiUpdated(
                "test",
                AgentType.AGENT_TYPE_HUMAN_USER,
                "PHI Updated",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                "test",
                "resource",
                "jsonRecord"));
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.phiDeleted(
                "test",
                AgentType.AGENT_TYPE_HUMAN_USER,
                "PHI Deleted",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                "test",
                "resource",
                "jsonRecord"));
    }
}
