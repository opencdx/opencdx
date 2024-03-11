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
package cdx.opencdx.shipping.controller;

import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.shipping.service.OpenCDXShippingVendorService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * Controller for processing Shipping Requests
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcShippingVendorController extends ShippingServiceGrpc.ShippingServiceImplBase {

    private final OpenCDXShippingVendorService openCDXShippingVendorService;

    /**
     * Constructor for OpenCDXGrpcShippingVendorController
     * @param openCDXShippingVendorService Service for processing Shipping Requests
     */
    public OpenCDXGrpcShippingVendorController(OpenCDXShippingVendorService openCDXShippingVendorService) {
        this.openCDXShippingVendorService = openCDXShippingVendorService;
    }

    @Override
    public void getShippingVendors(ShippingRequest request, StreamObserver<ShippingVendorResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingVendorService.getShippingVendors(request));
        responseObserver.onCompleted();
    }

    @Override
    public void shipPackage(Shipping request, StreamObserver<ShippingResponse> responseObserver) {
        responseObserver.onNext(this.openCDXShippingVendorService.shipPackage(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createDeliveryTracking(
            DeliveryTrackingRequest request, StreamObserver<DeliveryTrackingResponse> responseObserver) {
        DeliveryTrackingResponse response = openCDXShippingVendorService.createDeliveryTracking(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDeliveryTracking(
            DeliveryTrackingRequest request, StreamObserver<DeliveryTrackingResponse> responseObserver) {
        DeliveryTrackingResponse response = openCDXShippingVendorService.getDeliveryTracking(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
