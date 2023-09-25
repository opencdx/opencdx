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
package health.safe.api.opencdx.connected.test.controller;

import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
import health.safe.api.opencdx.connected.test.service.impl.OpenCDXConnectedTestServiceImpl;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class GrpcConnectedTestControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    OpenCDXConnectedTestService openCDXConnectedTestService;

    GrpcConnectedTestController grpcConnectedTestController;

    @BeforeEach
    void setUp() {
        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService, this.openCDXConnectedTestRepository, objectMapper);
        this.grpcConnectedTestController = new GrpcConnectedTestController(this.openCDXConnectedTestService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void submitTest() {
        StreamObserver<TestSubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);
        ConnectedTest connectedTest = ConnectedTest.getDefaultInstance();
        this.grpcConnectedTestController.submitTest(connectedTest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(TestSubmissionResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getTestDetailsById() {
        StreamObserver<ConnectedTest> responseObserver = Mockito.mock(StreamObserver.class);
        TestIdRequest testIdRequest = TestIdRequest.getDefaultInstance();
        this.grpcConnectedTestController.getTestDetailsById(testIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(ConnectedTest.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
