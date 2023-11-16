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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.grpc.connected.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javax.net.ssl.SSLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXConnectedTestClientTest {

    @Mock
    HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub;

    OpenCDXConnectedTestClient openCDXConnectedTestClient;

    @BeforeEach
    void setUp() {
        this.healthcareServiceBlockingStub = Mockito.mock(HealthcareServiceGrpc.HealthcareServiceBlockingStub.class);
        this.openCDXConnectedTestClient = new OpenCDXConnectedTestClientImpl(this.healthcareServiceBlockingStub);
        Mockito.when(this.healthcareServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.healthcareServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.healthcareServiceBlockingStub);
    }

    @Test
    void submitTest() {
        Mockito.when(this.healthcareServiceBlockingStub.submitTest(Mockito.any(ConnectedTest.class)))
                .thenReturn(TestSubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TestSubmissionResponse.getDefaultInstance(),
                this.openCDXConnectedTestClient.submitTest(ConnectedTest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void submitTestException() {
        Mockito.when(this.healthcareServiceBlockingStub.submitTest(Mockito.any(ConnectedTest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ConnectedTest request = ConnectedTest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXConnectedTestClient.submitTest(request, openCDXCallCredentials));
    }

    @Test
    void testDetailsById() {
        Mockito.when(this.healthcareServiceBlockingStub.getTestDetailsById(Mockito.any(TestIdRequest.class)))
                .thenReturn(ConnectedTest.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ConnectedTest.getDefaultInstance(),
                this.openCDXConnectedTestClient.getTestDetailsById(
                        TestIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void testDetailsByIdException() {
        Mockito.when(this.healthcareServiceBlockingStub.getTestDetailsById(Mockito.any(TestIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestIdRequest request = TestIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXConnectedTestClient.getTestDetailsById(request, openCDXCallCredentials));
    }

    @Test
    void listConnectedTestsByNHID() {
        Mockito.when(this.healthcareServiceBlockingStub.listConnectedTestsByNHID(
                        Mockito.any(ConnectedTestListByNHIDRequest.class)))
                .thenReturn(ConnectedTestListByNHIDResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ConnectedTestListByNHIDResponse.getDefaultInstance(),
                this.openCDXConnectedTestClient.listConnectedTestsByNHID(
                        ConnectedTestListByNHIDRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listConnectedTestsByNHIDException() {
        Mockito.when(this.healthcareServiceBlockingStub.listConnectedTestsByNHID(
                        Mockito.any(ConnectedTestListByNHIDRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ConnectedTestListByNHIDRequest request = ConnectedTestListByNHIDRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXConnectedTestClient.listConnectedTestsByNHID(request, openCDXCallCredentials));
    }

    @Test
    void listConnectedTests() {
        Mockito.when(this.healthcareServiceBlockingStub.listConnectedTests(Mockito.any(ConnectedTestListRequest.class)))
                .thenReturn(ConnectedTestListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ConnectedTestListResponse.getDefaultInstance(),
                this.openCDXConnectedTestClient.listConnectedTests(
                        ConnectedTestListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void llistConnectedTestsException() {
        Mockito.when(this.healthcareServiceBlockingStub.listConnectedTests(Mockito.any(ConnectedTestListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ConnectedTestListRequest request = ConnectedTestListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXConnectedTestClient.listConnectedTests(request, openCDXCallCredentials));
    }

    @Test
    void testNoArgConst() throws SSLException {
        OpenCDXConnectedTestClientImpl client = new OpenCDXConnectedTestClientImpl();
        TestIdRequest request = TestIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> client.getTestDetailsById(request, openCDXCallCredentials));
    }
}
