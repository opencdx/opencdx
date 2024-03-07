/*
 * Copyright 2024 Safe Health Systems, Inc.
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

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.grpc.inventory.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXTestCaseClientImplTest {

    @Mock
    TestCaseServiceGrpc.TestCaseServiceBlockingStub testCaseServiceBlockingStub;

    OpenCDXTestCaseClient openCDXTestCaseClient;

    @BeforeEach
    void setUp() {
        this.testCaseServiceBlockingStub = Mockito.mock(TestCaseServiceGrpc.TestCaseServiceBlockingStub.class);
        this.openCDXTestCaseClient = new OpenCDXTestCaseClientImpl(this.testCaseServiceBlockingStub);
        Mockito.when(testCaseServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.testCaseServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.testCaseServiceBlockingStub);
    }

    @Test
    void getTestCaseById() {
        Mockito.when(this.testCaseServiceBlockingStub.getTestCaseById(Mockito.any(TestCaseIdRequest.class)))
                .thenReturn(TestCase.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TestCase.getDefaultInstance(),
                this.openCDXTestCaseClient.getTestCaseById(
                        TestCaseIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getTestCaseByIdException() {
        Mockito.when(this.testCaseServiceBlockingStub.getTestCaseById(Mockito.any(TestCaseIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestCaseIdRequest request = TestCaseIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTestCaseClient.getTestCaseById(request, openCDXCallCredentials));
    }

    @Test
    void addTestCase() {
        Mockito.when(this.testCaseServiceBlockingStub.addTestCase(Mockito.any(TestCase.class)))
                .thenReturn(TestCase.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TestCase.getDefaultInstance(),
                this.openCDXTestCaseClient.addTestCase(TestCase.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void addTestCaseException() {
        Mockito.when(this.testCaseServiceBlockingStub.addTestCase(Mockito.any(TestCase.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestCase request = TestCase.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTestCaseClient.addTestCase(request, openCDXCallCredentials));
    }

    @Test
    void updateTestCase() {
        Mockito.when(this.testCaseServiceBlockingStub.updateTestCase(Mockito.any(TestCase.class)))
                .thenReturn(TestCase.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TestCase.getDefaultInstance(),
                this.openCDXTestCaseClient.updateTestCase(TestCase.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateTestCaseException() {
        Mockito.when(this.testCaseServiceBlockingStub.updateTestCase(Mockito.any(TestCase.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestCase request = TestCase.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTestCaseClient.updateTestCase(request, openCDXCallCredentials));
    }

    @Test
    void deleteTestCase() {
        Mockito.when(this.testCaseServiceBlockingStub.deleteTestCase(Mockito.any(TestCaseIdRequest.class)))
                .thenReturn(DeleteResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteResponse.getDefaultInstance(),
                this.openCDXTestCaseClient.deleteTestCase(
                        TestCaseIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteTestCaseException() {
        Mockito.when(this.testCaseServiceBlockingStub.deleteTestCase(Mockito.any(TestCaseIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestCaseIdRequest request = TestCaseIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTestCaseClient.deleteTestCase(request, openCDXCallCredentials));
    }

    @Test
    void listTestCase() {
        Mockito.when(this.testCaseServiceBlockingStub.listTestCase(Mockito.any(TestCaseListRequest.class)))
                .thenReturn(TestCaseListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TestCaseListResponse.getDefaultInstance(),
                this.openCDXTestCaseClient.listTestCase(
                        TestCaseListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listTestCaseException() {
        Mockito.when(this.testCaseServiceBlockingStub.listTestCase(Mockito.any(TestCaseListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TestCaseListRequest request = TestCaseListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTestCaseClient.listTestCase(request, openCDXCallCredentials));
    }
}
