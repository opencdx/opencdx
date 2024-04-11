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
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
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
@RequestMapping(value = "/vitals/heartrpm", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXHeartRPMRestController {
    private final OpenCDXHeartRPMService openCDXHeartRPMService;

    /**
     * Constructor for the HeartRPM Rest Controller
     * @param openCDXHeartRPMService Service Interface
     */
    public OpenCDXHeartRPMRestController(OpenCDXHeartRPMService openCDXHeartRPMService) {
        this.openCDXHeartRPMService = openCDXHeartRPMService;
    }

    /**
     * Get a HeartRPM by number.
     * @param id for the patient.
     * @return Response with the HeartRpm.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetHeartRPMResponse> getHeartRPMRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXHeartRPMService.getHeartRPMMeasurement(
                        GetHeartRPMRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a HeartRPM.
     * @param request HeartRPM.
     * @return Response HeartRPM.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateHeartRPMResponse> createHeartRPMRequest(@RequestBody CreateHeartRPMRequest request) {
        return new ResponseEntity<>(this.openCDXHeartRPMService.createHeartRPMMeasurement(request), HttpStatus.OK);
    }

    /**
     * Update a HeartRPM by number.
     * @param request for the HeartRpm.
     * @return Response with the HeartRpm.
     */
    @PutMapping
    public ResponseEntity<UpdateHeartRPMResponse> updateHeartRPMRequest(@RequestBody UpdateHeartRPMRequest request) {
        return new ResponseEntity<>(this.openCDXHeartRPMService.updateHeartRPMMeasurement(request), HttpStatus.OK);
    }

    /**
     * Delete a HeartRPM by number.
     * @param id for the HeartRpm entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteHeartRPM(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXHeartRPMService.deleteHeartRPMMeasurement(
                        DeleteHeartRPMRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of HeartRPMs
     * @param request List of HeartRPM.
     * @return All the HeartRPM.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListHeartRPMResponse> listHeartRPMRequest(@RequestBody ListHeartRPMRequest request) {
        return new ResponseEntity<>(this.openCDXHeartRPMService.listHeartRPMMeasurements(request), HttpStatus.OK);
    }
}
