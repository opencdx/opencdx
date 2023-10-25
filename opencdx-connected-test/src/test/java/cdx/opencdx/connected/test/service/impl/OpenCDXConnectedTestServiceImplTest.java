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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTest;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.grpc.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXConnectedTestServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXConnectedTestService openCDXConnectedTestService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @BeforeEach
    void beforeEach() {
        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService, openCDXConnectedTestRepository, objectMapper);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void submitTest() {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTest.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(ObjectId.get().toHexString())
                        .setNationalHealthId(10)
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        Assertions.assertEquals(
                TestSubmissionResponse.newBuilder()
                        .setSubmissionId(connectedTest.getBasicInfo().getId())
                        .build(),
                this.openCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTestFail() throws JsonProcessingException {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTest.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(ObjectId.get().toHexString())
                        .setNationalHealthId(10)
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService, this.openCDXConnectedTestRepository, mapper);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void getTestDetailsById() {
        OpenCDXConnectedTest openCDXConnectedTest =
                new OpenCDXConnectedTest(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setNationalHealthId(10)
                                .setUserId(ObjectId.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXConnectedTest));
        Assertions.assertEquals(
                openCDXConnectedTest.getProtobufMessage(),
                this.openCDXConnectedTestService.getTestDetailsById(TestIdRequest.newBuilder()
                        .setTestId(openCDXConnectedTest.getId().toHexString())
                        .build()));
    }

    @Test
    void getTestDetailsByIdFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        OpenCDXConnectedTest openCDXConnectedTest =
                new OpenCDXConnectedTest(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setNationalHealthId(10)
                                .setUserId(ObjectId.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXConnectedTest));

        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService, this.openCDXConnectedTestRepository, mapper);
        TestIdRequest testIdRequest = TestIdRequest.newBuilder()
                .setTestId(openCDXConnectedTest.getId().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.getTestDetailsById(testIdRequest));
    }

    @Test
    void listConnectedTests() throws JsonProcessingException {
        ConnectedTestListRequest request = ConnectedTestListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .setUserId(ObjectId.get().toHexString())
                .build();

        log.info("JSON:\n{}", this.objectMapper.writeValueAsString(request));
    }
}
