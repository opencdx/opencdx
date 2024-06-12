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
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Medical Record
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordGrpcController extends MedicalRecordServiceGrpc.MedicalRecordServiceImplBase {
    private final OpenCDXMedicalRecordService openCDXMedicalRecordService;

    /**
     * Constructor using the OpenCDXMedicalRecordService
     * @param openCDXMedicalRecordService service to use for processing
     */
    @Autowired
    public OpenCDXMedicalRecordGrpcController(OpenCDXMedicalRecordService openCDXMedicalRecordService) {
        this.openCDXMedicalRecordService = openCDXMedicalRecordService;
    }

    @Override
    public void requestMedicalRecord(
            GetMedicalRecordRequest request, StreamObserver<GetMedicalRecordResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalRecordService.requestMedicalRecord(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getMedicalRecordStatus(
            MedicalRecordByIdRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalRecordService.getMedicalRecordStatus(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getMedicalRecordById(
            MedicalRecordByIdRequest request, StreamObserver<MedicalRecordByIdResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalRecordService.getMedicalRecordById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createMedicalRecord(
            CreateMedicalRecordRequest request, StreamObserver<CreateMedicalRecordResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalRecordService.createMedicalRecord(request));
        responseObserver.onCompleted();
    }
}
