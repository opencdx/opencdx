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

import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXWeightMeasurementService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Weight Measurement
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXWeightMeasurementGrpcController
        extends WeightMeasurementServiceGrpc.WeightMeasurementServiceImplBase {
    private final OpenCDXWeightMeasurementService openCDXWeightMeasurementService;

    /**
     * Constructor using the OpenCDXWeightMeasurementService
     * @param openCDXWeightMeasurementService service to use for processing
     */
    @Autowired
    public OpenCDXWeightMeasurementGrpcController(OpenCDXWeightMeasurementService openCDXWeightMeasurementService) {
        this.openCDXWeightMeasurementService = openCDXWeightMeasurementService;
    }

    @Override
    public void createWeightMeasurement(
            CreateWeightMeasurementRequest request, StreamObserver<CreateWeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXWeightMeasurementService.createWeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getWeightMeasurement(
            GetWeightMeasurementRequest request, StreamObserver<GetWeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXWeightMeasurementService.getWeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateWeightMeasurement(
            UpdateWeightMeasurementRequest request, StreamObserver<UpdateWeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXWeightMeasurementService.updateWeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteWeightMeasurement(
            DeleteWeightMeasurementRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXWeightMeasurementService.deleteWeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listWeightMeasurements(
            ListWeightMeasurementsRequest request, StreamObserver<ListWeightMeasurementsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXWeightMeasurementService.listWeightMeasurements(request));
        responseObserver.onCompleted();
    }
}
