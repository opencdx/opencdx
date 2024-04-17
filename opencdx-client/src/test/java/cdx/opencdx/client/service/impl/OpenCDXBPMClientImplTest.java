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
import cdx.opencdx.client.service.OpenCDXBPMClient;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.service.health.BPMServiceGrpc;
import cdx.opencdx.grpc.service.health.CreateBPMRequest;
import cdx.opencdx.grpc.service.health.CreateBPMResponse;
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
class OpenCDXBPMClientImplTest {

    @Mock
    BPMServiceGrpc.BPMServiceBlockingStub bpmServiceBlockingStub;

    OpenCDXBPMClient openCDXBPMClient;

    @BeforeEach
    void setUp() {
        this.bpmServiceBlockingStub = Mockito.mock(BPMServiceGrpc.BPMServiceBlockingStub.class);
        this.openCDXBPMClient = new OpenCDXBPMClientImpl(this.bpmServiceBlockingStub);
        Mockito.when(bpmServiceBlockingStub.withCallCredentials(Mockito.any())).thenReturn(this.bpmServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.bpmServiceBlockingStub);
    }

    @Test
    void createBPM() {
        Mockito.when(this.bpmServiceBlockingStub.createBPMMeasurement(Mockito.any(CreateBPMRequest.class)))
                .thenReturn(CreateBPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateBPMResponse.getDefaultInstance(),
                this.openCDXBPMClient.createBPMMeasurement(
                        CreateBPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createBPMException() {
        Mockito.when(this.bpmServiceBlockingStub.createBPMMeasurement(Mockito.any(CreateBPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateBPMRequest request = CreateBPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXBPMClient.createBPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void getBPM() {
        Mockito.when(this.bpmServiceBlockingStub.getBPMMeasurement(Mockito.any(GetBPMRequest.class)))
                .thenReturn(GetBPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetBPMResponse.getDefaultInstance(),
                this.openCDXBPMClient.getBPMMeasurement(GetBPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getBPMException() {
        Mockito.when(this.bpmServiceBlockingStub.getBPMMeasurement(Mockito.any(GetBPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetBPMRequest request = GetBPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXBPMClient.getBPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void updateBPM() {
        Mockito.when(this.bpmServiceBlockingStub.updateBPMMeasurement(Mockito.any(UpdateBPMRequest.class)))
                .thenReturn(UpdateBPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateBPMResponse.getDefaultInstance(),
                this.openCDXBPMClient.updateBPMMeasurement(
                        UpdateBPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateBPMException() {
        Mockito.when(this.bpmServiceBlockingStub.updateBPMMeasurement(Mockito.any(UpdateBPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateBPMRequest request = UpdateBPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXBPMClient.updateBPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void deleteBPM() {
        Mockito.when(this.bpmServiceBlockingStub.deleteBPMMeasurement(Mockito.any(DeleteBPMRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXBPMClient.deleteBPMMeasurement(
                        DeleteBPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteBPMException() {
        Mockito.when(this.bpmServiceBlockingStub.deleteBPMMeasurement(Mockito.any(DeleteBPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteBPMRequest request = DeleteBPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXBPMClient.deleteBPMMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void listBPMs() {
        Mockito.when(this.bpmServiceBlockingStub.listBPMMeasurements(Mockito.any(ListBPMRequest.class)))
                .thenReturn(ListBPMResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListBPMResponse.getDefaultInstance(),
                this.openCDXBPMClient.listBPMMeasurements(ListBPMRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listBPMsException() {
        Mockito.when(this.bpmServiceBlockingStub.listBPMMeasurements(Mockito.any(ListBPMRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListBPMRequest request = ListBPMRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXBPMClient.listBPMMeasurements(request, openCDXCallCredentials));
    }
}
