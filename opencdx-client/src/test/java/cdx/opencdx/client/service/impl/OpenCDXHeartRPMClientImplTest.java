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

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXHeartRPMClient;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.service.health.HeartRPMServiceGrpc;
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
class OpenCDXHeartRPMClientImplTest {
    @Mock
    HeartRPMServiceGrpc.HeartRPMServiceBlockingStub heartRPMServiceBlockingStub;

    OpenCDXHeartRPMClient openCDXHeartRPMClient;

    @BeforeEach
    void setUp() {
        this.heartRPMServiceBlockingStub = Mockito.mock(HeartRPMServiceGrpc.HeartRPMServiceBlockingStub.class);
        this.openCDXHeartRPMClient = new OpenCDXHeartRPMClientImpl(this.heartRPMServiceBlockingStub);
        Mockito.when(heartRPMServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.heartRPMServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.heartRPMServiceBlockingStub);
    }

    @Test
    void createHeartRPM() {
        Mockito.when(this.heartRPMServiceBlockingStub.createHeartRPMMeasurement(
                        Mockito.any(CreateHeartRPMRequest.class)))
                .thenReturn(CreateHeartRPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateHeartRPMResponse.getDefaultInstance(),
                this.openCDXHeartRPMClient.createHeartRPMMeasurement(
                        CreateHeartRPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createHeartRPMException() {
        Mockito.when(this.heartRPMServiceBlockingStub.createHeartRPMMeasurement(
                        Mockito.any(CreateHeartRPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateHeartRPMRequest request = CreateHeartRPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeartRPMClient.createHeartRPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void getHeartRPM() {
        Mockito.when(this.heartRPMServiceBlockingStub.getHeartRPMMeasurement(Mockito.any(GetHeartRPMRequest.class)))
                .thenReturn(GetHeartRPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetHeartRPMResponse.getDefaultInstance(),
                this.openCDXHeartRPMClient.getHeartRPMMeasurement(
                        GetHeartRPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getHeartRPMException() {
        Mockito.when(this.heartRPMServiceBlockingStub.getHeartRPMMeasurement(Mockito.any(GetHeartRPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetHeartRPMRequest request = GetHeartRPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeartRPMClient.getHeartRPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void updateHeartRPM() {
        Mockito.when(this.heartRPMServiceBlockingStub.updateHeartRPMMeasurement(
                        Mockito.any(UpdateHeartRPMRequest.class)))
                .thenReturn(UpdateHeartRPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateHeartRPMResponse.getDefaultInstance(),
                this.openCDXHeartRPMClient.updateHeartRPMMeasurement(
                        UpdateHeartRPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateHeartRPMException() {
        Mockito.when(this.heartRPMServiceBlockingStub.updateHeartRPMMeasurement(
                        Mockito.any(UpdateHeartRPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateHeartRPMRequest request = UpdateHeartRPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeartRPMClient.updateHeartRPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void deleteHeartRPM() {
        Mockito.when(this.heartRPMServiceBlockingStub.deleteHeartRPMMeasurement(
                        Mockito.any(DeleteHeartRPMRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXHeartRPMClient.deleteHeartRPMMeasurement(
                        DeleteHeartRPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteHeartRPMException() {
        Mockito.when(this.heartRPMServiceBlockingStub.deleteHeartRPMMeasurement(
                        Mockito.any(DeleteHeartRPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteHeartRPMRequest request = DeleteHeartRPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeartRPMClient.deleteHeartRPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void listHeartRPMs() {
        Mockito.when(this.heartRPMServiceBlockingStub.listHeartRPMMeasurements(Mockito.any(ListHeartRPMRequest.class)))
                .thenReturn(ListHeartRPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListHeartRPMResponse.getDefaultInstance(),
                this.openCDXHeartRPMClient.listHeartRPMMeasurements(
                        ListHeartRPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listHeartRPMsException() {
        Mockito.when(this.heartRPMServiceBlockingStub.listHeartRPMMeasurements(Mockito.any(ListHeartRPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListHeartRPMRequest request = ListHeartRPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeartRPMClient.listHeartRPMMeasurements(request, openCDXCallCredentials));
    }
}
