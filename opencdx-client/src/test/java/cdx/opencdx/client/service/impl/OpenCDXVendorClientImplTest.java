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
import cdx.opencdx.client.service.OpenCDXVendorClient;
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
class OpenCDXVendorClientImplTest {

    @Mock
    VendorServiceGrpc.VendorServiceBlockingStub vendorServiceBlockingStub;

    OpenCDXVendorClient openCDXVendorClient;

    @BeforeEach
    void setUp() {
        this.vendorServiceBlockingStub = Mockito.mock(VendorServiceGrpc.VendorServiceBlockingStub.class);
        this.openCDXVendorClient = new OpenCDXVendorClientImpl(this.vendorServiceBlockingStub);
        Mockito.when(vendorServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.vendorServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.vendorServiceBlockingStub);
    }

    @Test
    void getVendorById() {
        Mockito.when(this.vendorServiceBlockingStub.getVendorById(Mockito.any(VendorIdRequest.class)))
                .thenReturn(Vendor.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Vendor.getDefaultInstance(),
                this.openCDXVendorClient.getVendorById(VendorIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getVendorByIdException() {
        Mockito.when(this.vendorServiceBlockingStub.getVendorById(Mockito.any(VendorIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        VendorIdRequest request = VendorIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVendorClient.getVendorById(request, openCDXCallCredentials));
    }

    @Test
    void addVendor() {
        Mockito.when(this.vendorServiceBlockingStub.addVendor(Mockito.any(Vendor.class)))
                .thenReturn(Vendor.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Vendor.getDefaultInstance(),
                this.openCDXVendorClient.addVendor(Vendor.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void addVendorException() {
        Mockito.when(this.vendorServiceBlockingStub.addVendor(Mockito.any(Vendor.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Vendor request = Vendor.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVendorClient.addVendor(request, openCDXCallCredentials));
    }

    @Test
    void updateVendor() {
        Mockito.when(this.vendorServiceBlockingStub.updateVendor(Mockito.any(Vendor.class)))
                .thenReturn(Vendor.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Vendor.getDefaultInstance(),
                this.openCDXVendorClient.updateVendor(Vendor.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateVendorException() {
        Mockito.when(this.vendorServiceBlockingStub.updateVendor(Mockito.any(Vendor.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Vendor request = Vendor.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVendorClient.updateVendor(request, openCDXCallCredentials));
    }

    @Test
    void deleteVendor() {
        Mockito.when(this.vendorServiceBlockingStub.deleteVendor(Mockito.any(VendorIdRequest.class)))
                .thenReturn(DeleteResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteResponse.getDefaultInstance(),
                this.openCDXVendorClient.deleteVendor(VendorIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteVendorException() {
        Mockito.when(this.vendorServiceBlockingStub.deleteVendor(Mockito.any(VendorIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        VendorIdRequest request = VendorIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVendorClient.deleteVendor(request, openCDXCallCredentials));
    }

    @Test
    void listVendors() {
        Mockito.when(this.vendorServiceBlockingStub.listVendors(Mockito.any(VendorsListRequest.class)))
                .thenReturn(VendorsListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                VendorsListResponse.getDefaultInstance(),
                this.openCDXVendorClient.listVendors(VendorsListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listVendorsException() {
        Mockito.when(this.vendorServiceBlockingStub.listVendors(Mockito.any(VendorsListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        VendorsListRequest request = VendorsListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVendorClient.listVendors(request, openCDXCallCredentials));
    }
}
