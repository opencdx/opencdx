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
import cdx.opencdx.client.service.OpenCDXTemperatureMeasurementClient;
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
class OpenCDXTemperatureMeasurementClientImplTest {

    @Mock
    TemperatureMeasurementServiceGrpc.TemperatureMeasurementServiceBlockingStub temperatureMeasurementServiceBlockingStub;

    OpenCDXTemperatureMeasurementClient openCDXTemperatureMeasurementClient;

    @BeforeEach
    void setUp() {
        this.temperatureMeasurementServiceBlockingStub =
                Mockito.mock(TemperatureMeasurementServiceGrpc.TemperatureMeasurementServiceBlockingStub.class);
        this.openCDXTemperatureMeasurementClient =
                new OpenCDXTemperatureMeasurementClientImpl(this.temperatureMeasurementServiceBlockingStub);
        Mockito.when(temperatureMeasurementServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.temperatureMeasurementServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.temperatureMeasurementServiceBlockingStub);
    }

    @Test
    void createTemperatureMeasurement() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.createTemperatureMeasurement(
                        Mockito.any(CreateTemperatureMeasurementRequest.class)))
                .thenReturn(CreateTemperatureMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateTemperatureMeasurementResponse.getDefaultInstance(),
                this.openCDXTemperatureMeasurementClient.createTemperatureMeasurement(
                        CreateTemperatureMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createTemperatureMeasurementException() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.createTemperatureMeasurement(
                        Mockito.any(CreateTemperatureMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateTemperatureMeasurementRequest request = CreateTemperatureMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTemperatureMeasurementClient.createTemperatureMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void getTemperatureMeasurement() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.getTemperatureMeasurement(
                        Mockito.any(GetTemperatureMeasurementRequest.class)))
                .thenReturn(GetTemperatureMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetTemperatureMeasurementResponse.getDefaultInstance(),
                this.openCDXTemperatureMeasurementClient.getTemperatureMeasurement(
                        GetTemperatureMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getTemperatureMeasurementException() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.getTemperatureMeasurement(
                        Mockito.any(GetTemperatureMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetTemperatureMeasurementRequest request = GetTemperatureMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTemperatureMeasurementClient.getTemperatureMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void updateTemperatureMeasurement() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.updateTemperatureMeasurement(
                        Mockito.any(UpdateTemperatureMeasurementRequest.class)))
                .thenReturn(UpdateTemperatureMeasurementResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateTemperatureMeasurementResponse.getDefaultInstance(),
                this.openCDXTemperatureMeasurementClient.updateTemperatureMeasurement(
                        UpdateTemperatureMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateTemperatureMeasurementException() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.updateTemperatureMeasurement(
                        Mockito.any(UpdateTemperatureMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateTemperatureMeasurementRequest request = UpdateTemperatureMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTemperatureMeasurementClient.updateTemperatureMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void deleteTemperatureMeasurement() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.deleteTemperatureMeasurement(
                        Mockito.any(DeleteTemperatureMeasurementRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXTemperatureMeasurementClient.deleteTemperatureMeasurement(
                        DeleteTemperatureMeasurementRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteTemperatureMeasurementException() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.deleteTemperatureMeasurement(
                        Mockito.any(DeleteTemperatureMeasurementRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteTemperatureMeasurementRequest request = DeleteTemperatureMeasurementRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTemperatureMeasurementClient.deleteTemperatureMeasurement(request, openCDXCallCredentials));
    }

    @Test
    void listTemperatureMeasurements() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.listTemperatureMeasurements(
                        Mockito.any(ListTemperatureMeasurementsRequest.class)))
                .thenReturn(ListTemperatureMeasurementsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListTemperatureMeasurementsResponse.getDefaultInstance(),
                this.openCDXTemperatureMeasurementClient.listTemperatureMeasurements(
                        ListTemperatureMeasurementsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listTemperatureMeasurementsException() {
        Mockito.when(this.temperatureMeasurementServiceBlockingStub.listTemperatureMeasurements(
                        Mockito.any(ListTemperatureMeasurementsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListTemperatureMeasurementsRequest request = ListTemperatureMeasurementsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTemperatureMeasurementClient.listTemperatureMeasurements(request, openCDXCallCredentials));
    }
}
