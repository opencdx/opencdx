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
package cdx.opencdx.routine.controller;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecution;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse;
import cdx.opencdx.grpc.routine.DeliveryTracking;
import cdx.opencdx.grpc.routine.DeliveryTrackingRequest;
import cdx.opencdx.grpc.routine.DeliveryTrackingResponse;
import cdx.opencdx.grpc.routine.Diagnosis;
import cdx.opencdx.grpc.routine.DiagnosisRequest;
import cdx.opencdx.grpc.routine.DiagnosisResponse;
import cdx.opencdx.grpc.routine.LabOrder;
import cdx.opencdx.grpc.routine.LabOrderRequest;
import cdx.opencdx.grpc.routine.LabOrderResponse;
import cdx.opencdx.grpc.routine.LabResult;
import cdx.opencdx.grpc.routine.LabResultRequest;
import cdx.opencdx.grpc.routine.LabResultResponse;
import cdx.opencdx.grpc.routine.Medication;
import cdx.opencdx.grpc.routine.MedicationRequest;
import cdx.opencdx.grpc.routine.MedicationResponse;
import cdx.opencdx.grpc.routine.Routine;
import cdx.opencdx.grpc.routine.RoutineRequest;
import cdx.opencdx.grpc.routine.RoutineResponse;
import cdx.opencdx.grpc.routine.SuspectedDiagnosis;
import cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest;
import cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse;
import cdx.opencdx.routine.service.impl.OpenCDXRoutineServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcRoutineControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXRoutineServiceImpl routineService;

    OpenCDXGrpcRoutineController openCDXGrpcRoutineController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.routineService = new OpenCDXRoutineServiceImpl(openCDXDocumentValidator);
        this.openCDXGrpcRoutineController = new OpenCDXGrpcRoutineController(this.routineService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testCreateRoutine() {
        StreamObserver<RoutineResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createRoutine(
                RoutineRequest.newBuilder(RoutineRequest.getDefaultInstance())
                        .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(RoutineResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetRoutine() {
        StreamObserver<RoutineResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getRoutine(
                RoutineRequest.newBuilder()
                        .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(RoutineResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateDeliveryTracking() {
        StreamObserver<DeliveryTrackingResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createDeliveryTracking(
                DeliveryTrackingRequest.newBuilder(DeliveryTrackingRequest.getDefaultInstance())
                        .setDeliveryTracking(DeliveryTracking.newBuilder()
                                .setTrackingId("789")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeliveryTrackingResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetDeliveryTracking() {
        StreamObserver<DeliveryTrackingResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getDeliveryTracking(
                DeliveryTrackingRequest.newBuilder()
                        .setDeliveryTracking(DeliveryTracking.newBuilder()
                                .setTrackingId("789")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeliveryTrackingResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateClinicalProtocolExecution() {
        StreamObserver<ClinicalProtocolExecutionResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createClinicalProtocolExecution(
                ClinicalProtocolExecutionRequest.newBuilder(ClinicalProtocolExecutionRequest.getDefaultInstance())
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ClinicalProtocolExecutionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetClinicalProtocolExecution() {
        StreamObserver<ClinicalProtocolExecutionResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getClinicalProtocolExecution(
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ClinicalProtocolExecutionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateLabOrder() {
        StreamObserver<LabOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createLabOrder(
                LabOrderRequest.newBuilder(LabOrderRequest.getDefaultInstance())
                        .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LabOrderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetLabOrder() {
        StreamObserver<LabOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getLabOrder(
                LabOrderRequest.newBuilder()
                        .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LabOrderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateDiagnosis() {
        StreamObserver<DiagnosisResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createDiagnosis(
                DiagnosisRequest.newBuilder(DiagnosisRequest.getDefaultInstance())
                        .setDiagnosis(
                                Diagnosis.newBuilder().setDiagnosisId("789").build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DiagnosisResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetDiagnosis() {
        StreamObserver<DiagnosisResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getDiagnosis(
                DiagnosisRequest.newBuilder()
                        .setDiagnosis(
                                Diagnosis.newBuilder().setDiagnosisId("789").build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DiagnosisResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateSuspectedDiagnosis() {
        StreamObserver<SuspectedDiagnosisResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createSuspectedDiagnosis(
                SuspectedDiagnosisRequest.newBuilder(SuspectedDiagnosisRequest.getDefaultInstance())
                        .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                                .setSuspectedDiagnosisId("789")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuspectedDiagnosisResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetSuspectedDiagnosis() {
        StreamObserver<SuspectedDiagnosisResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getSuspectedDiagnosis(
                SuspectedDiagnosisRequest.newBuilder()
                        .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                                .setSuspectedDiagnosisId("789")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuspectedDiagnosisResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateLabResult() {
        StreamObserver<LabResultResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createLabResult(
                LabResultRequest.newBuilder(LabResultRequest.getDefaultInstance())
                        .setLabResult(LabResult.newBuilder().setResultId("789").build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LabResultResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetLabResult() {
        StreamObserver<LabResultResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getLabResult(
                LabResultRequest.newBuilder()
                        .setLabResult(LabResult.newBuilder().setResultId("789").build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LabResultResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateMedication() {
        StreamObserver<MedicationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.createMedication(
                MedicationRequest.newBuilder(MedicationRequest.getDefaultInstance())
                        .setMedication(
                                Medication.newBuilder().setMedicationId("789").build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(MedicationResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetMedication() {
        StreamObserver<MedicationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcRoutineController.getMedication(
                MedicationRequest.newBuilder()
                        .setMedication(
                                Medication.newBuilder().setMedicationId("789").build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(MedicationResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
