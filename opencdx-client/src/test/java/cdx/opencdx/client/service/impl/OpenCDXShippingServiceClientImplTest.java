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
import cdx.opencdx.client.service.OpenCDXShippingServiceClient;
import cdx.opencdx.grpc.shipping.*;
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
class OpenCDXShippingServiceClientImplTest {
    @Mock
    ShippingServiceGrpc.ShippingServiceBlockingStub shippingServiceBlockingStub;

    OpenCDXShippingServiceClient shippingServiceClient;

    @BeforeEach
    void setUp() {
        this.shippingServiceBlockingStub = Mockito.mock(ShippingServiceGrpc.ShippingServiceBlockingStub.class);
        this.shippingServiceClient = new OpenCDXShippingServiceClientImpl(this.shippingServiceBlockingStub);
        Mockito.when(shippingServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.shippingServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.shippingServiceBlockingStub);
    }

    @Test
    void getShippingVendors() {
        Mockito.when(this.shippingServiceBlockingStub.getShippingVendors(Mockito.any(ShippingRequest.class)))
                .thenReturn(ShippingVendorResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ShippingVendorResponse.getDefaultInstance(),
                this.shippingServiceClient.getShippingVendors(
                        ShippingRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getShippingVendorsException() {
        Mockito.when(this.shippingServiceBlockingStub.getShippingVendors(Mockito.any(ShippingRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ShippingRequest request = ShippingRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.shippingServiceClient.getShippingVendors(request, openCDXCallCredentials));
    }

    @Test
    void shipPackage() {
        Mockito.when(this.shippingServiceBlockingStub.shipPackage(Mockito.any(Shipping.class)))
                .thenReturn(ShippingResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ShippingResponse.getDefaultInstance(),
                this.shippingServiceClient.shipPackage(Shipping.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void shipPackageException() {
        Mockito.when(this.shippingServiceBlockingStub.shipPackage(Mockito.any(Shipping.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Shipping request = Shipping.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.shippingServiceClient.shipPackage(request, openCDXCallCredentials));
    }

    @Test
    void createDeliveryTracking() {
        Mockito.when(this.shippingServiceBlockingStub.createDeliveryTracking(
                        Mockito.any(DeliveryTrackingRequest.class)))
                .thenReturn(DeliveryTrackingResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeliveryTrackingResponse.getDefaultInstance(),
                this.shippingServiceClient.createDeliveryTracking(
                        DeliveryTrackingRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createDeliveryTrackingException() {
        Mockito.when(this.shippingServiceBlockingStub.createDeliveryTracking(
                        Mockito.any(DeliveryTrackingRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeliveryTrackingRequest request = DeliveryTrackingRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.shippingServiceClient.createDeliveryTracking(request, openCDXCallCredentials));
    }

    @Test
    void getDeliveryTracking() {
        Mockito.when(this.shippingServiceBlockingStub.getDeliveryTracking(Mockito.any(DeliveryTrackingRequest.class)))
                .thenReturn(DeliveryTrackingResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeliveryTrackingResponse.getDefaultInstance(),
                this.shippingServiceClient.getDeliveryTracking(
                        DeliveryTrackingRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getDeliveryTrackingException() {
        Mockito.when(this.shippingServiceBlockingStub.getDeliveryTracking(Mockito.any(DeliveryTrackingRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeliveryTrackingRequest request = DeliveryTrackingRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.shippingServiceClient.getDeliveryTracking(request, openCDXCallCredentials));
    }
}
