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
import cdx.opencdx.client.service.OpenCDXWeightMeasurementClient;
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
class OpenCDXWeightMeasurementClientImplTest {

    @Mock
    WeightMeasurementServiceGrpc.WeightMeasurementServiceBlockingStub weightMeasurementServiceBlockingStub;

    OpenCDXWeightMeasurementClient openCDXWeightMeasurementClient;

    @BeforeEach
    void setUp() {
        this.weightMeasurementServiceBlockingStub =
                Mockito.mock(WeightMeasurementServiceGrpc.WeightMeasurementServiceBlockingStub.class);
        this.openCDXWeightMeasurementClient =
                new OpenCDXWeightMeasurementClientImpl(this.weightMeasurementServiceBlockingStub);
        Mockito.when(weightMeasurementServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.weightMeasurementServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.weightMeasurementServiceBlockingStub);
    }

    @Test
    void createWeightMeasurement() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.createWeightMeasurement(
                        Mockito.any(CreateWeightMeasurementRequest.class)))
                .thenReturn(CreateWeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateWeightMeasurementResponse.getDefaultInstance(),
                this.openCDXWeightMeasurementClient.createWeightMeasurement(
                        CreateWeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createWeightMeasurementException() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.createWeightMeasurement(
                        Mockito.any(CreateWeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateWeightMeasurementRequest request = CreateWeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXWeightMeasurementClient.createWeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void getWeightMeasurement() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.getWeightMeasurement(
                        Mockito.any(GetWeightMeasurementRequest.class)))
                .thenReturn(GetWeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetWeightMeasurementResponse.getDefaultInstance(),
                this.openCDXWeightMeasurementClient.getWeightMeasurement(
                        GetWeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getWeightMeasurementException() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.getWeightMeasurement(
                        Mockito.any(GetWeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetWeightMeasurementRequest request = GetWeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXWeightMeasurementClient.getWeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void updateWeightMeasurement() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.updateWeightMeasurement(
                        Mockito.any(UpdateWeightMeasurementRequest.class)))
                .thenReturn(UpdateWeightMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateWeightMeasurementResponse.getDefaultInstance(),
                this.openCDXWeightMeasurementClient.updateWeightMeasurement(
                        UpdateWeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateWeightMeasurementException() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.updateWeightMeasurement(
                        Mockito.any(UpdateWeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateWeightMeasurementRequest request = UpdateWeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXWeightMeasurementClient.updateWeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void deleteWeightMeasurement() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.deleteWeightMeasurement(
                        Mockito.any(DeleteWeightMeasurementRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXWeightMeasurementClient.deleteWeightMeasurement(
                        DeleteWeightMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteWeightMeasurementException() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.deleteWeightMeasurement(
                        Mockito.any(DeleteWeightMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteWeightMeasurementRequest request = DeleteWeightMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXWeightMeasurementClient.deleteWeightMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void listWeightMeasurements() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.listWeightMeasurements(
                        Mockito.any(ListWeightMeasurementsRequest.class)))
                .thenReturn(ListWeightMeasurementsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListWeightMeasurementsResponse.getDefaultInstance(),
                this.openCDXWeightMeasurementClient.listWeightMeasurements(
                        ListWeightMeasurementsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listWeightMeasurementsException() {
        Mockito.when(this.weightMeasurementServiceBlockingStub.listWeightMeasurements(
                        Mockito.any(ListWeightMeasurementsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListWeightMeasurementsRequest request = ListWeightMeasurementsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXWeightMeasurementClient.listWeightMeasurements(request, openCDXCallCredentials));
    }
}
