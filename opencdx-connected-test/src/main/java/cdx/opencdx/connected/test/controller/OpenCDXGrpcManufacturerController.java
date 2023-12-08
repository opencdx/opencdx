/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import cdx.opencdx.grpc.inventory.ManufacturerServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * GRPC Controller for GRPC services
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcManufacturerController extends ManufacturerServiceGrpc.ManufacturerServiceImplBase {

    private final OpenCDXManufacturerService openCDXManufacturerService;

    /**
     * GRPC Controller for Manufacturer
     * @param openCDXManufacturerService Serivce for processing requests.
     */
    public OpenCDXGrpcManufacturerController(OpenCDXManufacturerService openCDXManufacturerService) {
        this.openCDXManufacturerService = openCDXManufacturerService;
    }

    @Secured({})
    @Override
    public void getManufacturerById(ManufacturerIdRequest request, StreamObserver<Manufacturer> responseObserver) {
        responseObserver.onNext(this.openCDXManufacturerService.getManufacturerById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void addManufacturer(Manufacturer request, StreamObserver<Manufacturer> responseObserver) {
        responseObserver.onNext(this.openCDXManufacturerService.addManufacturer(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateManufacturer(Manufacturer request, StreamObserver<Manufacturer> responseObserver) {
        responseObserver.onNext(this.openCDXManufacturerService.updateManufacturer(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteManufacturer(ManufacturerIdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        responseObserver.onNext(this.openCDXManufacturerService.deleteManufacturer(request));
        responseObserver.onCompleted();
    }
}
