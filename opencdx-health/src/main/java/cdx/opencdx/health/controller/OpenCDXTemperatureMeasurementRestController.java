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
@RequestMapping(value = "/vitals/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXTemperatureMeasurementRestController {
    private final OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService;

    /**
     * Constructor for the Temperature Measurement Rest Controller
     * @param openCDXTemperatureMeasurementService Service Interface
     */
    public OpenCDXTemperatureMeasurementRestController(OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService) {
        this.openCDXTemperatureMeasurementService = openCDXTemperatureMeasurementService;
    }

    /**
     * Get a Temperature Measurement by id.
     * @param id for the patient.
     * @return Response with the temperature.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetTemperatureMeasurementResponse> getTemperatureMeasurementRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXTemperatureMeasurementService.getTemperatureMeasurement(
                        GetTemperatureMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a Temperature Measurement.
     * @param request Temperature Measurement.
     * @return Response Temperature Measurement.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateTemperatureMeasurementResponse> createTemperatureMeasurementRequest(
            @RequestBody CreateTemperatureMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXTemperatureMeasurementService.createTemperatureMeasurement(request), HttpStatus.OK);
    }

    /**
     * Update a Temperature Measurement by id.
     * @param request for the temperature.
     * @return Response with the temperature.
     */
    @PutMapping
    public ResponseEntity<UpdateTemperatureMeasurementResponse> updateTemperatureMeasurementRequest(
            @RequestBody UpdateTemperatureMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXTemperatureMeasurementService.updateTemperatureMeasurement(request), HttpStatus.OK);
    }

    /**
     * Delete a Temperature Measurement by id.
     * @param id for the Temperature entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteTemperatureMeasurement(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXTemperatureMeasurementService.deleteTemperatureMeasurement(
                        DeleteTemperatureMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of Temperature Measurements
     * @param request List of Temperature Measurement.
     * @return All the Temperature Measurement.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListTemperatureMeasurementsResponse> listTemperatureMeasurementRequest(
            @RequestBody ListTemperatureMeasurementsRequest request) {
        return new ResponseEntity<>(
                this.openCDXTemperatureMeasurementService.listTemperatureMeasurements(request), HttpStatus.OK);
    }
}
