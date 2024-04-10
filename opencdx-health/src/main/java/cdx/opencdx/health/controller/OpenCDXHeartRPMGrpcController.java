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
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Heart RPM Measurement
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXHeartRPMGrpcController extends HeartRPMServiceGrpc.HeartRPMServiceImplBase {
    private final OpenCDXHeartRPMService openCDXHeartRPMService;

    /**
     * Constructor using the OpenCDXHeartRPMService
     * @param openCDXHeartRPMService service to use for processing
     */
    @Autowired
    public OpenCDXHeartRPMGrpcController(OpenCDXHeartRPMService openCDXHeartRPMService) {
        this.openCDXHeartRPMService = openCDXHeartRPMService;
    }

    @Override
    public void createHeartRPMMeasurement(
            CreateHeartRPMRequest request, StreamObserver<CreateHeartRPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeartRPMService.createHeartRPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getHeartRPMMeasurement(
            GetHeartRPMRequest request, StreamObserver<GetHeartRPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeartRPMService.getHeartRPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateHeartRPMMeasurement(
            UpdateHeartRPMRequest request, StreamObserver<UpdateHeartRPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeartRPMService.updateHeartRPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteHeartRPMMeasurement(
            DeleteHeartRPMRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeartRPMService.deleteHeartRPMMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listHeartRPMMeasurements(
            ListHeartRPMRequest request, StreamObserver<ListHeartRPMResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeartRPMService.listHeartRPMMeasurements(request));
        responseObserver.onCompleted();
    }
}
