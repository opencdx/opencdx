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

import cdx.open_connected_test.v2alpha.BasicInfo;
import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXConnectedTestServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXConnectedTestService openCDXConnectedTestService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @BeforeEach
    void beforeEach() {
        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(this.openCDXAuditService, objectMapper);
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
    void submitTestFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .build();
        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService =
                new OpenCDXConnectedTestServiceImpl(this.openCDXAuditService, mapper);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void getTestDetailsById() {
        Assertions.assertEquals(
                ConnectedTest.getDefaultInstance(),
                this.openCDXConnectedTestService.getTestDetailsById(TestIdRequest.getDefaultInstance()));
    }

    @Test
    void getTestDetailsByIdFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService =
                new OpenCDXConnectedTestServiceImpl(this.openCDXAuditService, mapper);
        TestIdRequest testIdRequest = TestIdRequest.getDefaultInstance();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.getTestDetailsById(testIdRequest));
    }
}
