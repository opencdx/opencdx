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
import cdx.opencdx.health.service.OpenCDXMedicalConditionsService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller Medical Conditions service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXMedicalConditionsGrpcController
        extends MedicalConditionsServiceGrpc.MedicalConditionsServiceImplBase {

    private final OpenCDXMedicalConditionsService openCDXMedicalConditionsService;

    /**
     * Constructor that takes a OpenCDXMedicalConditionsService
     * @param openCDXMedicalConditionsService service for procesing requests
     */
    public OpenCDXMedicalConditionsGrpcController(OpenCDXMedicalConditionsService openCDXMedicalConditionsService) {
        this.openCDXMedicalConditionsService = openCDXMedicalConditionsService;
    }
    /**
     * Method to track when MedicalConditions is given to a patient.
     * @param request Request for the MedicalConditions administration.
     * @param responseObserver The response observer.
     */
    @Override
    public void createDiagnosis(DiagnosisRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalConditionsService.createDiagnosis(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get MedicalConditions information.
     * @param request Request for the MedicalConditions.
     * @param responseObserver The response observer.
     */
    @Override
    public void getDiagnosis(GetDiagnosisByIdRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalConditionsService.getDiagnosis(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to update MedicalConditions information.
     * @param request Request for the MedicalConditions.
     * @param responseObserver The response observer.
     */
    @Override
    public void updateDiagnosis(DiagnosisRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalConditionsService.updateDiagnosis(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to delete MedicalConditions information .
     * @param request Request for the MedicalConditions.
     * @param responseObserver The response observer.
     */
    @Override
    public void deleteDiagnosis(DeleteDiagnosisRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalConditionsService.deleteDiagnosis(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to get MedicalConditions information by patient ID or national health ID.
     * @param request Request for the MedicalConditions
     * @param responseObserver The response observer.
     */
    @Override
    public void listDiagnosis(ListDiagnosisRequest request, StreamObserver<ListDiagnosisResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalConditionsService.listDiagnosis(request));
        responseObserver.onCompleted();
    }
}
