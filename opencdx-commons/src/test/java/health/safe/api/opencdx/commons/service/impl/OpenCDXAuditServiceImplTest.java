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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import health.safe.api.opencdx.commons.config.CommonsConfig;
import health.safe.api.opencdx.commons.handlers.OpenCDXGrpcExceptionHandler;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest(classes = {CommonsConfig.class, OpenCDXAuditServiceImpl.class, NoOpOpenCDXMessageService.class})
@ExtendWith(SpringExtension.class)
class OpenCDXAuditServiceImplTest {

    @Autowired
    OpenCDXAuditService auditService;

    @Test
    void userLoginSucceed() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.userLoginSucceed(UUID.randomUUID());
        });
    }

    @Test
    void userLoginFailure() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.userLoginFailure(UUID.randomUUID());
        });
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.userLogout(UUID.randomUUID());
        });
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.userAccessChange(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void passwordChange() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.passwordChange(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void piiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.piiAccessed(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void piiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.piiCreated(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void piiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.piiUpdated(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void piiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.piiDeleted(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void phiAccessed() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.phiAccessed(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void phiCreated() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.phiCreated(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.phiUpdated(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> {
            this.auditService.phiDeleted(UUID.randomUUID(), UUID.randomUUID());
        });
    }
}
