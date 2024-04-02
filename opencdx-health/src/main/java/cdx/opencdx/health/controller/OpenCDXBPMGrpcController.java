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
package cdx.opencdx.health.controller;

import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.service.OpenCDXBPMService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for BPM Measurement
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXBPMGrpcController
        extends BPMServiceGrpc.BPMServiceImplBase {
    private final OpenCDXBPMService openCDXBPMService;

    /**
     * Constructor using the OpenCDXBPMService
     * @param openCDXBPMService service to use for processing
     */
    @Autowired
    public OpenCDXBPMGrpcController(OpenCDXBPMService openCDXBPMService) {
        this.openCDXBPMService = openCDXBPMService;
    }

    @Override
    public void createBPMMeasurement(
            CreateBPMRequest request, StreamObserver<CreateBPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXBPMService.createBPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getBPMMeasurement(
            GetBPMRequest request, StreamObserver<GetBPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXBPMService.getBPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateBPMMeasurement(
            UpdateBPMRequest request, StreamObserver<UpdateBPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXBPMService.updateBPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBPMMeasurement(
            DeleteBPMRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXBPMService.deleteBPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listBPMMeasurements(
            ListBPMRequest request, StreamObserver<ListBPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXBPMService.listBPMMeasurements(request));
        responseObserver.onCompleted();
    }
}
