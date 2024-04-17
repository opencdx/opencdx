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
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /health  api's
 */
@Slf4j
@RestController
@RequestMapping(value = "/vitals/weight", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXWeightMeasurementRestController {
    private final OpenCDXWeightMeasurementService openCDXWeightMeasurementService;

    /**
     * Constructor for the Weight Measurement Rest Controller
     * @param openCDXWeightMeasurementService Service Interface
     */
    public OpenCDXWeightMeasurementRestController(OpenCDXWeightMeasurementService openCDXWeightMeasurementService) {
        this.openCDXWeightMeasurementService = openCDXWeightMeasurementService;
    }

    /**
     * Get a Weight Measurement by number.
     * @param id for the patient.
     * @return Response with the weight.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetWeightMeasurementResponse> getWeightMeasurementRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXWeightMeasurementService.getWeightMeasurement(
                        GetWeightMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a Weight Measurement.
     * @param request Weight Measurement.
     * @return Response Weight Measurement.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateWeightMeasurementResponse> createWeightMeasurementRequest(
            @RequestBody CreateWeightMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXWeightMeasurementService.createWeightMeasurement(request), HttpStatus.OK);
    }

    /**
     * Update a Weight Measurement by number.
     * @param request for the weight.
     * @return Response with the weight.
     */
    @PutMapping
    public ResponseEntity<UpdateWeightMeasurementResponse> updateWeightMeasurementRequest(
            @RequestBody UpdateWeightMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXWeightMeasurementService.updateWeightMeasurement(request), HttpStatus.OK);
    }

    /**
     * Delete a Weight Measurement by number.
     * @param id for the Weight entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteWeightMeasurement(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXWeightMeasurementService.deleteWeightMeasurement(
                        DeleteWeightMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of Weight Measurements
     * @param request List of Weight Measurement.
     * @return All the Weight Measurement.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListWeightMeasurementsResponse> listWeightMeasurementRequest(
            @RequestBody ListWeightMeasurementsRequest request) {
        return new ResponseEntity<>(
                this.openCDXWeightMeasurementService.listWeightMeasurements(request), HttpStatus.OK);
    }
}
