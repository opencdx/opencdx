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
import cdx.opencdx.health.service.OpenCDXHeightMeasurementService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Height Measurement
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXHeightMeasurementGrpcController
        extends HeightMeasurementServiceGrpc.HeightMeasurementServiceImplBase {
    private final OpenCDXHeightMeasurementService openCDXHeightMeasurementService;

    /**
     * Constructor using the OpenCDXHeightMeasurementService
     * @param openCDXHeightMeasurementService service to use for processing
     */
    @Autowired
    public OpenCDXHeightMeasurementGrpcController(OpenCDXHeightMeasurementService openCDXHeightMeasurementService) {
        this.openCDXHeightMeasurementService = openCDXHeightMeasurementService;
    }

    @Override
    public void createHeightMeasurement(
            CreateHeightMeasurementRequest request, StreamObserver<CreateHeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeightMeasurementService.createHeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getHeightMeasurement(
            GetHeightMeasurementRequest request, StreamObserver<GetHeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeightMeasurementService.getHeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateHeightMeasurement(
            UpdateHeightMeasurementRequest request, StreamObserver<UpdateHeightMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeightMeasurementService.updateHeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteHeightMeasurement(
            DeleteHeightMeasurementRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeightMeasurementService.deleteHeightMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listHeightMeasurements(
            ListHeightMeasurementsRequest request, StreamObserver<ListHeightMeasurementsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXHeightMeasurementService.listHeightMeasurements(request));
        responseObserver.onCompleted();
    }
}
