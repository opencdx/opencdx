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

import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.service.OpenCDXHeightMeasurementService;
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
@RequestMapping(value = "/vitals/height", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXHeightMeasurementRestController {
    private final OpenCDXHeightMeasurementService openCDXHeightMeasurementService;

    /**
     * Constructor for the Height Measurement Rest Controller
     * @param openCDXHeightMeasurementService Service Interface
     */
    public OpenCDXHeightMeasurementRestController(OpenCDXHeightMeasurementService openCDXHeightMeasurementService) {
        this.openCDXHeightMeasurementService = openCDXHeightMeasurementService;
    }

    /**
     * Get a Height Measurement by number.
     * @param id for the patient.
     * @return Response with the height.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetHeightMeasurementResponse> getHeightMeasurementRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXHeightMeasurementService.getHeightMeasurement(
                        GetHeightMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a Height Measurement.
     * @param request Height Measurement.
     * @return Response Height Measurement.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateHeightMeasurementResponse> createHeightMeasurementRequest(
            @RequestBody CreateHeightMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXHeightMeasurementService.createHeightMeasurement(request), HttpStatus.OK);
    }

    /**
     * Update a Height Measurement by number.
     * @param request for the height.
     * @return Response with the height.
     */
    @PutMapping
    public ResponseEntity<UpdateHeightMeasurementResponse> updateHeightMeasurementRequest(
            @RequestBody UpdateHeightMeasurementRequest request) {
        return new ResponseEntity<>(
                this.openCDXHeightMeasurementService.updateHeightMeasurement(request), HttpStatus.OK);
    }

    /**
     * Delete a Height Measurement by number.
     * @param id for the Height entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteHeightMeasurement(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXHeightMeasurementService.deleteHeightMeasurement(
                        DeleteHeightMeasurementRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of Height Measurements
     * @param request List of Height Measurement.
     * @return All the Height Measurement.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListHeightMeasurementsResponse> listHeightMeasurementRequest(
            @RequestBody ListHeightMeasurementsRequest request) {
        return new ResponseEntity<>(
                this.openCDXHeightMeasurementService.listHeightMeasurements(request), HttpStatus.OK);
    }
}
