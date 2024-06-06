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
import cdx.opencdx.health.service.OpenCDXTemperatureMeasurementService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Temperature Measurement
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXTemperatureMeasurementGrpcController
        extends TemperatureMeasurementServiceGrpc.TemperatureMeasurementServiceImplBase {
    private final OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService;

    /**
     * Constructor using the OpenCDXTemperatureMeasurementService
     * @param openCDXTemperatureMeasurementService service to use for processing
     */
    @Autowired
    public OpenCDXTemperatureMeasurementGrpcController(
            OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService) {
        this.openCDXTemperatureMeasurementService = openCDXTemperatureMeasurementService;
    }

    @Override
    public void createTemperatureMeasurement(
            CreateTemperatureMeasurementRequest request,
            StreamObserver<CreateTemperatureMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTemperatureMeasurementService.createTemperatureMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getTemperatureMeasurement(
            GetTemperatureMeasurementRequest request,
            StreamObserver<GetTemperatureMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTemperatureMeasurementService.getTemperatureMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateTemperatureMeasurement(
            UpdateTemperatureMeasurementRequest request,
            StreamObserver<UpdateTemperatureMeasurementResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTemperatureMeasurementService.updateTemperatureMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTemperatureMeasurement(
            DeleteTemperatureMeasurementRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTemperatureMeasurementService.deleteTemperatureMeasurement(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listTemperatureMeasurements(
            ListTemperatureMeasurementsRequest request,
            StreamObserver<ListTemperatureMeasurementsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTemperatureMeasurementService.listTemperatureMeasurements(request));
        responseObserver.onCompleted();
    }
}
