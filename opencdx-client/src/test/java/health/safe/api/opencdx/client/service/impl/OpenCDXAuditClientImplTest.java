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
package health.safe.api.opencdx.client.service.impl;

import cdx.open_audit.v2alpha.AgentType;
import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditServiceGrpc;
import cdx.open_audit.v2alpha.AuditStatus;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXAuditClientImplTest {

    @Mock
    AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub;

    OpenCDXAuditClientImpl openCDXAuditService;

    @BeforeEach
    void setUp() {
        this.auditServiceBlockingStub = Mockito.mock(AuditServiceGrpc.AuditServiceBlockingStub.class);
        Mockito.when(this.auditServiceBlockingStub.event(Mockito.any(AuditEvent.class)))
                .thenReturn(AuditStatus.newBuilder().setSuccess(true).build());
        this.openCDXAuditService = new OpenCDXAuditClientImpl("test", this.auditServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.auditServiceBlockingStub);
    }

    @Test
    void userLoginSucceed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginSucceed(
                    UUID.randomUUID().toString(), AgentType.AGENT_TYPE_HUMAN_USER, "purpose");
        });
    }

    @Test
    void userLoginFailure() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginFailure(
                    UUID.randomUUID().toString(), AgentType.AGENT_TYPE_OTHER_ENTITY, "purpose");
        });
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLogout(UUID.randomUUID().toString(), AgentType.AGENT_TYPE_SYSTEM, "purpose");
        });
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userAccessChange(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void passwordChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.passwordChange(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void piiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiAccessed(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void piiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiCreated(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void piiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiUpdated(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void piiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiDeleted(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void phiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiAccessed(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void phiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiCreated(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiUpdated(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiDeleted(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void communication() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.communication(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    UUID.randomUUID().toString(),
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }

    @Test
    void config() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "purpose",
                    "COMMUNICATION: 123",
                    "{\"name\":\"John\", \"age\":30, \"car\":null}");
        });
    }
}
