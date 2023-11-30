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
import cdx.opencdx.routine.service.RoutineService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the /greeting api's
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/routine",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestRoutineController {

    private final RoutineService routineService;

    /**
     * Constructor that takes a RoutineService
     * @param routineService service for processing requests.
     */
    @Autowired
    public RestRoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    /**
     * Post Routine Rest API
     * @param request RoutineRequest indicating input.
     * @return RoutineResponse with the data.
     */
    @PostMapping
    public ResponseEntity<RoutineResponse> createRoutine(@RequestBody RoutineRequest request) {
        RoutineResponse response = routineService.createRoutine(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get routine using GET method
     * @param routineId The ID of the routine to retrieve.
     * @return RoutineResponse with the data.
     */
    @GetMapping(value = "/{routineId}")
    public ResponseEntity<RoutineResponse> getRoutineById(@PathVariable(value = "routineId") String routineId) {
        RoutineRequest request = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId(routineId).build())
                .build();

        RoutineResponse response = routineService.getRoutine(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delivery Tracking
    @PostMapping("/deliveryTracking")
    public ResponseEntity<DeliveryTrackingResponse> createDeliveryTracking(
            @RequestBody DeliveryTrackingRequest request) {
        DeliveryTrackingResponse response = routineService.createDeliveryTracking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deliveryTracking/{deliveryId}")
    public ResponseEntity<DeliveryTrackingResponse> getDeliveryTracking(
            @PathVariable(value = "deliveryId") String deliveryId) {
        DeliveryTrackingRequest request = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setDeliveryId(deliveryId).build())
                .build();
        DeliveryTrackingResponse response = routineService.getDeliveryTracking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Clinical Protocol Execution
    @PostMapping("/clinicalProtocolExecution")
    public ResponseEntity<ClinicalProtocolExecutionResponse> createClinicalProtocolExecution(
            @RequestBody ClinicalProtocolExecutionRequest request) {
        ClinicalProtocolExecutionResponse response = routineService.createClinicalProtocolExecution(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/clinicalProtocolExecution/{executionId}")
    public ResponseEntity<ClinicalProtocolExecutionResponse> getClinicalProtocolExecution(
            @PathVariable(value = "executionId") String executionId) {
        ClinicalProtocolExecutionRequest request = ClinicalProtocolExecutionRequest.newBuilder()
                .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                        .setExecutionId(executionId)
                        .build())
                .build();
        ClinicalProtocolExecutionResponse response = routineService.getClinicalProtocolExecution(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lab Order
    @PostMapping("/labOrder")
    public ResponseEntity<LabOrderResponse> triggerLabOrder(@RequestBody LabOrderRequest request) {
        LabOrderResponse response = routineService.triggerLabOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/labOrder/{labOrderId}")
    public ResponseEntity<LabOrderResponse> getLabOrder(@PathVariable(value = "labOrderId") String labOrderId) {
        LabOrderRequest request = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId(labOrderId).build())
                .build();
        LabOrderResponse response = routineService.getLabOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Diagnosis
    @PostMapping("/diagnosis")
    public ResponseEntity<DiagnosisResponse> triggerDiagnosis(@RequestBody DiagnosisRequest request) {
        DiagnosisResponse response = routineService.triggerDiagnosis(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/diagnosis/{diagnosisId}")
    public ResponseEntity<DiagnosisResponse> getDiagnosis(@PathVariable(value = "diagnosisId") String diagnosisId) {
        DiagnosisRequest request = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder().setDiagnosisId(diagnosisId).build())
                .build();
        DiagnosisResponse response = routineService.getDiagnosis(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Suspected Diagnosis
    @PostMapping("/suspectedDiagnosis")
    public ResponseEntity<SuspectedDiagnosisResponse> triggerSuspectedDiagnosis(
            @RequestBody SuspectedDiagnosisRequest request) {
        SuspectedDiagnosisResponse response = routineService.triggerSuspectedDiagnosis(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/suspectedDiagnosis/{suspectedDiagnosisId}")
    public ResponseEntity<SuspectedDiagnosisResponse> getSuspectedDiagnosis(
            @PathVariable(value = "suspectedDiagnosisId") String suspectedDiagnosisId) {
        SuspectedDiagnosisRequest request = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId(suspectedDiagnosisId)
                        .build())
                .build();
        SuspectedDiagnosisResponse response = routineService.getSuspectedDiagnosis(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lab Result
    @PostMapping("/labResult")
    public ResponseEntity<LabResultResponse> triggerLabResult(@RequestBody LabResultRequest request) {
        LabResultResponse response = routineService.triggerLabResult(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/labResult/{resultId}")
    public ResponseEntity<LabResultResponse> getLabResult(@PathVariable(value = "resultId") String resultId) {
        LabResultRequest request = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId(resultId).build())
                .build();
        LabResultResponse response = routineService.getLabResult(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Medication
    @PostMapping("/medication")
    public ResponseEntity<MedicationResponse> triggerMedication(@RequestBody MedicationRequest request) {
        MedicationResponse response = routineService.triggerMedication(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/medication/{medicationId}")
    public ResponseEntity<MedicationResponse> getMedication(@PathVariable(value = "medicationId") String medicationId) {
        MedicationRequest request = MedicationRequest.newBuilder()
                .setMedication(
                        Medication.newBuilder().setMedicationId(medicationId).build())
                .build();
        MedicationResponse response = routineService.getMedication(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
