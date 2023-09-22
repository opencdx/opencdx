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

import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXConnectedTestServiceImplTest {

    OpenCDXConnectedTestService openCDXConnectedTestService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @BeforeEach
    void beforeEach() {
        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(this.openCDXAuditService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void submitTest() {
        Assertions.assertEquals(
                TestSubmissionResponse.getDefaultInstance(),
                this.openCDXConnectedTestService.submitTest(ConnectedTest.getDefaultInstance()));
    }

    @Test
    void getTestDetailsById() {
        Assertions.assertEquals(
                ConnectedTest.getDefaultInstance(),
                this.openCDXConnectedTestService.getTestDetailsById(TestIdRequest.getDefaultInstance()));
    }
}
