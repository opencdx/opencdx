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
package cdx.opencdx.routine.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.routine.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXRoutineServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXRoutineServiceImpl routineService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() {

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, this.objectMapper, this.openCDXCurrentUser);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createRoutine() {
        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

        RoutineResponse response = routineService.createRoutine(routineRequest);

        Assertions.assertEquals("789", response.getRoutine().getRoutineId());
    }

    @Test
    void getRoutine() {
        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

        RoutineResponse response = routineService.getRoutine(routineRequest);

        Assertions.assertEquals("789", response.getRoutine().getRoutineId());
    }

    @Test
    void createDeliveryTracking() {
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setDeliveryId("789").build())
                .build();

        DeliveryTrackingResponse response = routineService.createDeliveryTracking(deliveryTrackingRequest);

        Assertions.assertEquals("789", response.getDeliveryTracking().getDeliveryId());
    }

    @Test
    void getDeliveryTracking() {
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setDeliveryId("789").build())
                .build();

        DeliveryTrackingResponse response = routineService.getDeliveryTracking(deliveryTrackingRequest);

        Assertions.assertEquals("789", response.getDeliveryTracking().getDeliveryId());
    }

    @Test
    void createClinicalProtocolExecution() {
        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        ClinicalProtocolExecutionResponse response =
                routineService.createClinicalProtocolExecution(clinicalProtocolExecutionRequest);

        Assertions.assertEquals("789", response.getClinicalProtocolExecution().getExecutionId());
    }

    @Test
    void getClinicalProtocolExecution() {
        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        ClinicalProtocolExecutionResponse response =
                routineService.getClinicalProtocolExecution(clinicalProtocolExecutionRequest);

        Assertions.assertEquals("789", response.getClinicalProtocolExecution().getExecutionId());
    }

    @Test
    void triggerLabOrder() {
        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        LabOrderResponse response = routineService.triggerLabOrder(labOrderRequest);

        Assertions.assertEquals("789", response.getLabOrder().getLabOrderId());
    }

    @Test
    void getLabOrder() {
        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        LabOrderResponse response = routineService.getLabOrder(labOrderRequest);

        Assertions.assertEquals("789", response.getLabOrder().getLabOrderId());
    }

    @Test
    void triggerDiagnosis() {
        DiagnosisRequest diagnosisRequest = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder().setDiagnosisId("789").build())
                .build();

        DiagnosisResponse response = routineService.triggerDiagnosis(diagnosisRequest);

        Assertions.assertEquals("789", response.getDiagnosis().getDiagnosisId());
    }

    @Test
    void getDiagnosis() {
        DiagnosisRequest diagnosisRequest = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder().setDiagnosisId("789").build())
                .build();

        DiagnosisResponse response = routineService.getDiagnosis(diagnosisRequest);

        Assertions.assertEquals("789", response.getDiagnosis().getDiagnosisId());
    }

    @Test
    void triggerSuspectedDiagnosis() {
        SuspectedDiagnosisRequest suspectedDiagnosisRequest = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId("789")
                        .build())
                .build();

        SuspectedDiagnosisResponse response = routineService.triggerSuspectedDiagnosis(suspectedDiagnosisRequest);

        Assertions.assertEquals("789", response.getSuspectedDiagnosis().getSuspectedDiagnosisId());
    }

    @Test
    void getSuspectedDiagnosis() {
        SuspectedDiagnosisRequest suspectedDiagnosisRequest = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId("789")
                        .build())
                .build();

        SuspectedDiagnosisResponse response = routineService.getSuspectedDiagnosis(suspectedDiagnosisRequest);

        Assertions.assertEquals("789", response.getSuspectedDiagnosis().getSuspectedDiagnosisId());
    }

    @Test
    void triggerLabResult() {
        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        LabResultResponse response = routineService.triggerLabResult(labResultRequest);

        Assertions.assertEquals("789", response.getLabResult().getResultId());
    }

    @Test
    void getLabResult() {
        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        LabResultResponse response = routineService.getLabResult(labResultRequest);

        Assertions.assertEquals("789", response.getLabResult().getResultId());
    }

    @Test
    void triggerMedication() {
        MedicationRequest medicationRequest = MedicationRequest.newBuilder()
                .setMedication(Medication.newBuilder().setMedicationId("789").build())
                .build();

        MedicationResponse response = routineService.triggerMedication(medicationRequest);

        Assertions.assertEquals("789", response.getMedication().getMedicationId());
    }

    @Test
    void getMedication() {
        MedicationRequest medicationRequest = MedicationRequest.newBuilder()
                .setMedication(Medication.newBuilder().setMedicationId("789").build())
                .build();

        MedicationResponse response = routineService.getMedication(medicationRequest);

        Assertions.assertEquals("789", response.getMedication().getMedicationId());
    }

    ////////

    @Test
    void createRoutineFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

       Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> routineService.createRoutine(routineRequest));
    }

    @Test
    void getRoutineFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getRoutine(routineRequest));
    }

    @Test
    void createDeliveryTrackingFail() throws JsonProcessingException  {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setDeliveryId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> routineService.createDeliveryTracking(deliveryTrackingRequest));
    }

    @Test
    void getDeliveryTrackingFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setDeliveryId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getDeliveryTracking(deliveryTrackingRequest));
    }

    @Test
    void createClinicalProtocolExecutionFail() throws JsonProcessingException  {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->
                routineService.createClinicalProtocolExecution(clinicalProtocolExecutionRequest));
    }

    @Test
    void getClinicalProtocolExecutionFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->
                routineService.getClinicalProtocolExecution(clinicalProtocolExecutionRequest));
    }

    @Test
    void triggerLabOrderFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.triggerLabOrder(labOrderRequest));
    }

    @Test
    void getLabOrderFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getLabOrder(labOrderRequest));
    }

    @Test
    void triggerDiagnosisFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        DiagnosisRequest diagnosisRequest = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder().setDiagnosisId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.triggerDiagnosis(diagnosisRequest));
    }

    @Test
    void getDiagnosisFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        DiagnosisRequest diagnosisRequest = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder().setDiagnosisId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> routineService.getDiagnosis(diagnosisRequest));
    }

    @Test
    void triggerSuspectedDiagnosisFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        SuspectedDiagnosisRequest suspectedDiagnosisRequest = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId("789")
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.triggerSuspectedDiagnosis(suspectedDiagnosisRequest));
    }

    @Test
    void getSuspectedDiagnosisFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        SuspectedDiagnosisRequest suspectedDiagnosisRequest = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId("789")
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getSuspectedDiagnosis(suspectedDiagnosisRequest));
    }

    @Test
    void triggerLabResultFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.triggerLabResult(labResultRequest));
    }

    @Test
    void getLabResultFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getLabResult(labResultRequest));
    }

    @Test
    void triggerMedicationFail() throws JsonProcessingException  {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        MedicationRequest medicationRequest = MedicationRequest.newBuilder()
                .setMedication(Medication.newBuilder().setMedicationId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.triggerMedication(medicationRequest));
    }

    @Test
    void getMedicationFail()  throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.routineService =
                new OpenCDXRoutineServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        MedicationRequest medicationRequest = MedicationRequest.newBuilder()
                .setMedication(Medication.newBuilder().setMedicationId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->  routineService.getMedication(medicationRequest));
    }
}