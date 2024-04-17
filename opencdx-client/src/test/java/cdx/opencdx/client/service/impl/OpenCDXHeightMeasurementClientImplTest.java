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
import cdx.opencdx.client.service.OpenCDXHeightMeasurementClient;
import cdx.opencdx.grpc.service.health.*;
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
class OpenCDXHeightMeasurementClientImplTest {

    @Mock
    HeightMeasurementServiceGrpc.HeightMeasurementServiceBlockingStub heightMeasurementServiceBlockingStub;

    OpenCDXHeightMeasurementClient openCDXHeightMeasurementClient;

    @BeforeEach
    void setUp() {
        this.heightMeasurementServiceBlockingStub =
                Mockito.mock(HeightMeasurementServiceGrpc.HeightMeasurementServiceBlockingStub.class);
        this.openCDXHeightMeasurementClient =
                new OpenCDXHeightMeasurementClientImpl(this.heightMeasurementServiceBlockingStub);
        Mockito.when(heightMeasurementServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.heightMeasurementServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.heightMeasurementServiceBlockingStub);
    }

    @Test
    void createHeightMeasurement() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.createHeightMeasurement(
                        Mockito.any(CreateHeightMeasurementRequest.class)))
                .thenReturn(CreateHeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateHeightMeasurementResponse.getDefaultInstance(),
                this.openCDXHeightMeasurementClient.createHeightMeasurement(
                        CreateHeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createHeightMeasurementException() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.createHeightMeasurement(
                        Mockito.any(CreateHeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateHeightMeasurementRequest request = CreateHeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeightMeasurementClient.createHeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void getHeightMeasurement() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.getHeightMeasurement(
                        Mockito.any(GetHeightMeasurementRequest.class)))
                .thenReturn(GetHeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetHeightMeasurementResponse.getDefaultInstance(),
                this.openCDXHeightMeasurementClient.getHeightMeasurement(
                        GetHeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getHeightMeasurementException() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.getHeightMeasurement(
                        Mockito.any(GetHeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetHeightMeasurementRequest request = GetHeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeightMeasurementClient.getHeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void updateHeightMeasurement() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.updateHeightMeasurement(
                        Mockito.any(UpdateHeightMeasurementRequest.class)))
                .thenReturn(UpdateHeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateHeightMeasurementResponse.getDefaultInstance(),
                this.openCDXHeightMeasurementClient.updateHeightMeasurement(
                        UpdateHeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateHeightMeasurementException() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.updateHeightMeasurement(
                        Mockito.any(UpdateHeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateHeightMeasurementRequest request = UpdateHeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeightMeasurementClient.updateHeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void deleteHeightMeasurement() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.deleteHeightMeasurement(
                        Mockito.any(DeleteHeightMeasurementRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXHeightMeasurementClient.deleteHeightMeasurement(
                        DeleteHeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteHeightMeasurementException() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.deleteHeightMeasurement(
                        Mockito.any(DeleteHeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteHeightMeasurementRequest request = DeleteHeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeightMeasurementClient.deleteHeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void listHeightMeasurements() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.listHeightMeasurements(
                        Mockito.any(ListHeightMeasurementsRequest.class)))
                .thenReturn(ListHeightMeasurementsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListHeightMeasurementsResponse.getDefaultInstance(),
                this.openCDXHeightMeasurementClient.listHeightMeasurements(
                        ListHeightMeasurementsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listHeightMeasurementsException() {
        Mockito.when(this.heightMeasurementServiceBlockingStub.listHeightMeasurements(
                        Mockito.any(ListHeightMeasurementsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListHeightMeasurementsRequest request = ListHeightMeasurementsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXHeightMeasurementClient.listHeightMeasurements(request, openCDXCallCredentials));
    }
}
