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

import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXRoutineService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /routine API's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestRoutineController {

    private final OpenCDXRoutineService openCDXRoutineService;

    /**
     * Constructor that takes a RoutineService.
     * @param openCDXRoutineService Service for processing requests.
     */
    @Autowired
    public OpenCDXRestRoutineController(OpenCDXRoutineService openCDXRoutineService) {
        this.openCDXRoutineService = openCDXRoutineService;
    }

    /**
     * Post Routine Rest API
     * @param request RoutineRequest indicating input.
     * @return RoutineResponse with the data.
     */
    @PostMapping(value = "/routine", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoutineResponse> createRoutine(@RequestBody RoutineRequest request) {
        RoutineResponse response = openCDXRoutineService.createRoutine(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get routine using GET method
     * @param routineId The ID of the routine to retrieve.
     * @return RoutineResponse with the data.
     */
    @GetMapping(value = "/routine/{routineId}")
    public ResponseEntity<RoutineResponse> getRoutineById(@PathVariable(value = "routineId") String routineId) {
        RoutineRequest request = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId(routineId).build())
                .build();

        RoutineResponse response = openCDXRoutineService.getRoutine(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Clinical Protocol Execution
    /**
     * Post Clinical Protocol Execution Rest API
     * @param request ClinicalProtocolExecutionRequest indicating input.
     * @return ClinicalProtocolExecutionResponse with the data.
     */
    @PostMapping(value = "/clinicalProtocolExecution", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClinicalProtocolExecutionResponse> createClinicalProtocolExecution(
            @RequestBody ClinicalProtocolExecutionRequest request) {
        ClinicalProtocolExecutionResponse response = openCDXRoutineService.createClinicalProtocolExecution(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Clinical Protocol Execution using GET method
     * @param executionId The ID of the clinical protocol execution to retrieve.
     * @return ClinicalProtocolExecutionResponse with the data.
     */
    @GetMapping("/clinicalProtocolExecution/{executionId}")
    public ResponseEntity<ClinicalProtocolExecutionResponse> getClinicalProtocolExecution(
            @PathVariable(value = "executionId") String executionId) {
        ClinicalProtocolExecutionRequest request = ClinicalProtocolExecutionRequest.newBuilder()
                .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                        .setExecutionId(executionId)
                        .build())
                .build();
        ClinicalProtocolExecutionResponse response = openCDXRoutineService.getClinicalProtocolExecution(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lab Order
    /**
     * Post Lab Order Rest API
     * @param request LabOrderRequest indicating input.
     * @return LabOrderResponse with the data.
     */
    @PostMapping(value = "/labOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabOrderResponse> triggerLabOrder(@RequestBody LabOrderRequest request) {
        LabOrderResponse response = openCDXRoutineService.triggerLabOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Lab Order using GET method
     * @param labOrderId The ID of the lab order to retrieve.
     * @return LabOrderResponse with the data.
     */
    @GetMapping("/labOrder/{labOrderId}")
    public ResponseEntity<LabOrderResponse> getLabOrder(@PathVariable(value = "labOrderId") String labOrderId) {
        LabOrderRequest request = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId(labOrderId).build())
                .build();
        LabOrderResponse response = openCDXRoutineService.getLabOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lab Result
    /**
     * Post Lab Result Rest API
     * @param request LabResultRequest indicating input.
     * @return LabResultResponse with the data.
     */
    @PostMapping(value = "/labResult", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabResultResponse> triggerLabResult(@RequestBody LabResultRequest request) {
        LabResultResponse response = openCDXRoutineService.triggerLabResult(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Lab Result using GET method
     * @param resultId The ID of the lab result to retrieve.
     * @return LabResultResponse with the data.
     */
    @GetMapping("/labResult/{resultId}")
    public ResponseEntity<LabResultResponse> getLabResult(@PathVariable(value = "resultId") String resultId) {
        LabResultRequest request = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId(resultId).build())
                .build();
        LabResultResponse response = openCDXRoutineService.getLabResult(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
