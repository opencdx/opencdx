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
package cdx.opencdx.routine.controller;

import cdx.opencdx.grpc.routine.*;
import cdx.opencdx.routine.service.OpenCDXRoutineService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Routine
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcRoutineController extends RoutineSystemServiceGrpc.RoutineSystemServiceImplBase {

    private final OpenCDXRoutineService openCDXRoutineService;

    /**
     * Constructor using the RoutineService.
     * @param openCDXRoutineService Service to use for processing.
     */
    @Autowired
    public OpenCDXGrpcRoutineController(final OpenCDXRoutineService openCDXRoutineService) {
        this.openCDXRoutineService = openCDXRoutineService;
    }

    /**
     * gRPC Service Call to createRoutine.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createRoutine(RoutineRequest request, StreamObserver<RoutineResponse> responseObserver) {
        RoutineResponse response = openCDXRoutineService.createRoutine(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getRoutine.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getRoutine(RoutineRequest request, StreamObserver<RoutineResponse> responseObserver) {
        RoutineResponse response = openCDXRoutineService.getRoutine(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createDeliveryTracking.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createDeliveryTracking(
            DeliveryTrackingRequest request, StreamObserver<DeliveryTrackingResponse> responseObserver) {
        DeliveryTrackingResponse response = openCDXRoutineService.createDeliveryTracking(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getDeliveryTracking.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getDeliveryTracking(
            DeliveryTrackingRequest request, StreamObserver<DeliveryTrackingResponse> responseObserver) {
        DeliveryTrackingResponse response = openCDXRoutineService.getDeliveryTracking(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createClinicalProtocolExecution.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request,
            StreamObserver<ClinicalProtocolExecutionResponse> responseObserver) {
        ClinicalProtocolExecutionResponse response = openCDXRoutineService.createClinicalProtocolExecution(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getClinicalProtocolExecution.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request,
            StreamObserver<ClinicalProtocolExecutionResponse> responseObserver) {
        ClinicalProtocolExecutionResponse response = openCDXRoutineService.getClinicalProtocolExecution(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createLabOrder.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createLabOrder(LabOrderRequest request, StreamObserver<LabOrderResponse> responseObserver) {
        LabOrderResponse response = openCDXRoutineService.triggerLabOrder(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getLabOrder.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getLabOrder(LabOrderRequest request, StreamObserver<LabOrderResponse> responseObserver) {
        LabOrderResponse response = openCDXRoutineService.getLabOrder(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createDiagnosis.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createDiagnosis(DiagnosisRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        DiagnosisResponse response = openCDXRoutineService.triggerDiagnosis(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getDiagnosis.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getDiagnosis(DiagnosisRequest request, StreamObserver<DiagnosisResponse> responseObserver) {
        DiagnosisResponse response = openCDXRoutineService.getDiagnosis(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createSuspectedDiagnosis.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, StreamObserver<SuspectedDiagnosisResponse> responseObserver) {
        SuspectedDiagnosisResponse response = openCDXRoutineService.triggerSuspectedDiagnosis(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getSuspectedDiagnosis.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, StreamObserver<SuspectedDiagnosisResponse> responseObserver) {
        SuspectedDiagnosisResponse response = openCDXRoutineService.getSuspectedDiagnosis(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createLabResult.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createLabResult(LabResultRequest request, StreamObserver<LabResultResponse> responseObserver) {
        LabResultResponse response = openCDXRoutineService.triggerLabResult(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getLabResult.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getLabResult(LabResultRequest request, StreamObserver<LabResultResponse> responseObserver) {
        LabResultResponse response = openCDXRoutineService.getLabResult(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to createMedication.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void createMedication(MedicationRequest request, StreamObserver<MedicationResponse> responseObserver) {
        MedicationResponse response = openCDXRoutineService.triggerMedication(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * gRPC Service Call to getMedication.
     * @param request Request to process.
     * @param responseObserver Observer to process the response.
     */
    @Override
    @Secured({})
    public void getMedication(MedicationRequest request, StreamObserver<MedicationResponse> responseObserver) {
        MedicationResponse response = openCDXRoutineService.getMedication(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
