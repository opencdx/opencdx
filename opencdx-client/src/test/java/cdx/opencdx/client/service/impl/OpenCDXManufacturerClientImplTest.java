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
import cdx.opencdx.client.service.OpenCDXManufacturerClient;
import cdx.opencdx.grpc.data.Manufacturer;
import cdx.opencdx.grpc.service.logistics.*;
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
class OpenCDXManufacturerClientImplTest {

    @Mock
    ManufacturerServiceGrpc.ManufacturerServiceBlockingStub manufacturerServiceBlockingStub;

    OpenCDXManufacturerClient openCDXManufacturerClient;

    @BeforeEach
    void setUp() {
        this.manufacturerServiceBlockingStub =
                Mockito.mock(ManufacturerServiceGrpc.ManufacturerServiceBlockingStub.class);
        this.openCDXManufacturerClient = new OpenCDXManufacturerClientImpl(this.manufacturerServiceBlockingStub);
        Mockito.when(manufacturerServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.manufacturerServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.manufacturerServiceBlockingStub);
    }

    @Test
    void getManufacturerById() {
        Mockito.when(this.manufacturerServiceBlockingStub.getManufacturerById(Mockito.any(ManufacturerIdRequest.class)))
                .thenReturn(Manufacturer.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Manufacturer.getDefaultInstance(),
                this.openCDXManufacturerClient.getManufacturerById(
                        ManufacturerIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getManufacturerByIdException() {
        Mockito.when(this.manufacturerServiceBlockingStub.getManufacturerById(Mockito.any(ManufacturerIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ManufacturerIdRequest request = ManufacturerIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXManufacturerClient.getManufacturerById(request, openCDXCallCredentials));
    }

    @Test
    void addManufacturer() {
        Mockito.when(this.manufacturerServiceBlockingStub.addManufacturer(Mockito.any(Manufacturer.class)))
                .thenReturn(Manufacturer.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Manufacturer.getDefaultInstance(),
                this.openCDXManufacturerClient.addManufacturer(
                        Manufacturer.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void addManufacturerException() {
        Mockito.when(this.manufacturerServiceBlockingStub.addManufacturer(Mockito.any(Manufacturer.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Manufacturer request = Manufacturer.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXManufacturerClient.addManufacturer(request, openCDXCallCredentials));
    }

    @Test
    void updateManufacturer() {
        Mockito.when(this.manufacturerServiceBlockingStub.updateManufacturer(Mockito.any(Manufacturer.class)))
                .thenReturn(Manufacturer.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Manufacturer.getDefaultInstance(),
                this.openCDXManufacturerClient.updateManufacturer(
                        Manufacturer.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateManufacturerException() {
        Mockito.when(this.manufacturerServiceBlockingStub.updateManufacturer(Mockito.any(Manufacturer.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Manufacturer request = Manufacturer.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXManufacturerClient.updateManufacturer(request, openCDXCallCredentials));
    }

    @Test
    void deleteManufacturer() {
        Mockito.when(this.manufacturerServiceBlockingStub.deleteManufacturer(Mockito.any(ManufacturerIdRequest.class)))
                .thenReturn(DeleteResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteResponse.getDefaultInstance(),
                this.openCDXManufacturerClient.deleteManufacturer(
                        ManufacturerIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteManufacturerException() {
        Mockito.when(this.manufacturerServiceBlockingStub.deleteManufacturer(Mockito.any(ManufacturerIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ManufacturerIdRequest request = ManufacturerIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXManufacturerClient.deleteManufacturer(request, openCDXCallCredentials));
    }

    @Test
    void listManufacturers() {
        Mockito.when(this.manufacturerServiceBlockingStub.listManufacturers(Mockito.any(ManufacturerListRequest.class)))
                .thenReturn(ManufacturersListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ManufacturersListResponse.getDefaultInstance(),
                this.openCDXManufacturerClient.listManufacturers(
                        ManufacturerListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listManufacturersException() {
        Mockito.when(this.manufacturerServiceBlockingStub.listManufacturers(Mockito.any(ManufacturerListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ManufacturerListRequest request = ManufacturerListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXManufacturerClient.listManufacturers(request, openCDXCallCredentials));
    }
}
