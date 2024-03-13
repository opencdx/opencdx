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
package cdx.opencdx.logistics.controller;

import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.logistics.service.OpenCDXShippingService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Order Service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcShippingController extends OrderServiceGrpc.OrderServiceImplBase {

    private final OpenCDXShippingService openCDXShippingService;

    /**
     * Constructor
     * @param openCDXShippingService Service to use
     */
    @Autowired
    public OpenCDXGrpcShippingController(OpenCDXShippingService openCDXShippingService) {
        this.openCDXShippingService = openCDXShippingService;
    }

    @Secured({})
    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingService.createOrder(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getOrder(GetOrderRequest request, StreamObserver<GetOrderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingService.getOrder(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateOrder(UpdateOrderRequest request, StreamObserver<UpdateOrderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingService.updateOrder(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void cancelOrder(CancelOrderRequest request, StreamObserver<CancelOrderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingService.cancelOrder(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listOrders(ListOrdersRequest request, StreamObserver<ListOrdersResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingService.listOrders(request));
        responseObserver.onCompleted();
    }
}
