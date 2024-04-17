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
import cdx.opencdx.client.service.OpenCDXOrderServiceClient;
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
class OpenCDXOrderServiceClientImplTest {

    @Mock
    OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    OpenCDXOrderServiceClient orderServiceClient;

    @BeforeEach
    void setUp() {
        this.orderServiceBlockingStub = Mockito.mock(OrderServiceGrpc.OrderServiceBlockingStub.class);
        this.orderServiceClient = new OpenCDXOrderServiceClientImpl(this.orderServiceBlockingStub);
        Mockito.when(orderServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.orderServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.orderServiceBlockingStub);
    }

    @Test
    void createOrder() {
        Mockito.when(this.orderServiceBlockingStub.createOrder(Mockito.any(CreateOrderRequest.class)))
                .thenReturn(CreateOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateOrderResponse.getDefaultInstance(),
                this.orderServiceClient.createOrder(CreateOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createOrderException() {
        Mockito.when(this.orderServiceBlockingStub.createOrder(Mockito.any(CreateOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateOrderRequest request = CreateOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.orderServiceClient.createOrder(request, openCDXCallCredentials));
    }

    @Test
    void getOrder() {
        Mockito.when(this.orderServiceBlockingStub.getOrder(Mockito.any(GetOrderRequest.class)))
                .thenReturn(GetOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetOrderResponse.getDefaultInstance(),
                this.orderServiceClient.getOrder(GetOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getOrderException() {
        Mockito.when(this.orderServiceBlockingStub.getOrder(Mockito.any(GetOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetOrderRequest request = GetOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.orderServiceClient.getOrder(request, openCDXCallCredentials));
    }

    @Test
    void updateOrder() {
        Mockito.when(this.orderServiceBlockingStub.updateOrder(Mockito.any(UpdateOrderRequest.class)))
                .thenReturn(UpdateOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateOrderResponse.getDefaultInstance(),
                this.orderServiceClient.updateOrder(UpdateOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateOrderException() {
        Mockito.when(this.orderServiceBlockingStub.updateOrder(Mockito.any(UpdateOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateOrderRequest request = UpdateOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.orderServiceClient.updateOrder(request, openCDXCallCredentials));
    }

    @Test
    void cancelOrder() {
        Mockito.when(this.orderServiceBlockingStub.cancelOrder(Mockito.any(CancelOrderRequest.class)))
                .thenReturn(CancelOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CancelOrderResponse.getDefaultInstance(),
                this.orderServiceClient.cancelOrder(CancelOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void cancelOrderException() {
        Mockito.when(this.orderServiceBlockingStub.cancelOrder(Mockito.any(CancelOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CancelOrderRequest request = CancelOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.orderServiceClient.cancelOrder(request, openCDXCallCredentials));
    }

    @Test
    void listOrders() {
        Mockito.when(this.orderServiceBlockingStub.listOrders(Mockito.any(ListOrdersRequest.class)))
                .thenReturn(ListOrdersResponse.getDefaultInstance());
        ListOrdersRequest request = ListOrdersRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListOrdersResponse.getDefaultInstance(),
                this.orderServiceClient.listOrders(request, openCDXCallCredentials));
    }

    @Test
    void listOrdersException() {
        Mockito.when(this.orderServiceBlockingStub.listOrders(Mockito.any(ListOrdersRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListOrdersRequest request = ListOrdersRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.orderServiceClient.listOrders(request, openCDXCallCredentials));
    }
}
