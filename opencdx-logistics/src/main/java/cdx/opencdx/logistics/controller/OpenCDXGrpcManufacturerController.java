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

import cdx.opencdx.grpc.data.Manufacturer;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.logistics.service.OpenCDXManufacturerService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * GRPC Manufacturer Controller for GRPC services
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

    @Secured({})
    @Override
    public void listManufacturers(
            ManufacturerListRequest request, StreamObserver<ManufacturersListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXManufacturerService.listManufacturers(request));
        responseObserver.onCompleted();
    }
}
