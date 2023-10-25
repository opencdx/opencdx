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

import cdx.opencdx.commons.aspects.AuditAspect;
import cdx.opencdx.commons.config.CommonsConfig;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.audit.*;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest(
        classes = {
            CommonsConfig.class,
            OpenCDXAuditServiceImpl.class,
            NoOpOpenCDXMessageServiceImpl.class,
            ObservationAutoConfiguration.class
        })
@ExtendWith(SpringExtension.class)
class OpenCDXAuditServiceImplTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @BeforeEach
    void setup() {
        AuditAspect.setCurrentThreadInfo(
                UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @AfterEach
    void tearDown() {
        AuditAspect.removeCurrentThreadInfo();
    }

    @Test
    void userLoginSucceed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginSucceed(AgentType.AGENT_TYPE_HUMAN_USER, "purpose");
        });
    }

    @Test
    void userLoginFailure() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginFailure(AgentType.AGENT_TYPE_OTHER_ENTITY, "purpose");
        });
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLogout(AgentType.AGENT_TYPE_SYSTEM, "purpose");
        });
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userAccessChange(AgentType.AGENT_TYPE_HUMAN_USER, "purpose");
        });
    }

    @Test
    void passwordChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.passwordChange(AgentType.AGENT_TYPE_HUMAN_USER, "purpose");
        });
    }

    @Test
    void piiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiAccessed(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void piiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiCreated(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void piiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiUpdated(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void piiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiDeleted(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void phiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiAccessed(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void phiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiCreated(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiUpdated(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiDeleted(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void communication() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.communication(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_MEDIUM,
                    "COMMUNICATION: id",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void config() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.config(
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    SensitivityLevel.SENSITIVITY_LEVEL_MEDIUM,
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }
}
