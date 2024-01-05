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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;
import cdx.opencdx.grpc.inventory.VendorServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * GRPC Vendor Controller
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcVendorController extends VendorServiceGrpc.VendorServiceImplBase {

    private final OpenCDXVendorService openCDXVendorService;

    /**
     * Setup the GRPC Vendor Controller
     * @param openCDXVendorService Vendor service for processing.
     */
    public OpenCDXGrpcVendorController(OpenCDXVendorService openCDXVendorService) {
        this.openCDXVendorService = openCDXVendorService;
    }

    @Secured({})
    @Override
    public void getVendorById(VendorIdRequest request, StreamObserver<Vendor> responseObserver) {
        responseObserver.onNext(this.openCDXVendorService.getVendorById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void addVendor(Vendor request, StreamObserver<Vendor> responseObserver) {
        responseObserver.onNext(this.openCDXVendorService.addVendor(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateVendor(Vendor request, StreamObserver<Vendor> responseObserver) {
        responseObserver.onNext(this.openCDXVendorService.updateVendor(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteVendor(VendorIdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        responseObserver.onNext(this.openCDXVendorService.deleteVendor(request));
        responseObserver.onCompleted();
    }
}
