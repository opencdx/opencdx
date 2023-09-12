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
package health.safe.api.opencdx.commons.service.impl;

import health.safe.api.opencdx.commons.config.CommonsConfig;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.grpc.audit.AgentType;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest(classes = {CommonsConfig.class, OpenCDXAuditServiceImpl.class, NoOpOpenCDXMessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OpenCDXAuditServiceImplTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Test
    void userLoginSucceed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginSucceed(UUID.randomUUID(), AgentType.HUMAN_USER);
        });
    }

    @Test
    void userLoginFailure() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLoginFailure(UUID.randomUUID(), AgentType.OTHER_ENTITY);
        });
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userLogout(UUID.randomUUID(), AgentType.SYSTEM);
        });
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.userAccessChange(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void passwordChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.passwordChange(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void piiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiAccessed(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void piiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiCreated(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void piiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiUpdated(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void piiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.piiDeleted(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void phiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiAccessed(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void phiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiCreated(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiUpdated(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXAuditService.phiDeleted(UUID.randomUUID(), AgentType.HUMAN_USER, UUID.randomUUID());
        });
    }
}
