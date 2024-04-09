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
import cdx.opencdx.health.service.OpenCDXBPMService;
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
@RequestMapping(value = "/vitals/bpm", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXBPMRestController {
    private final OpenCDXBPMService openCDXBPMService;

    /**
     * Constructor for the BPM Rest Controller
     * @param openCDXBPMService Service Interface
     */
    public OpenCDXBPMRestController(OpenCDXBPMService openCDXBPMService) {
        this.openCDXBPMService = openCDXBPMService;
    }

    /**
     * Get a BPM by number.
     * @param id for the patient.
     * @return Response with the bpm.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetBPMResponse> getBPMRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXBPMService.getBPMMeasurement(
                        GetBPMRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a BPM.
     * @param request BPM.
     * @return Response BPM.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateBPMResponse> createBPMRequest(
            @RequestBody CreateBPMRequest request) {
        return new ResponseEntity<>(
                this.openCDXBPMService.createBPMMeasurement(request), HttpStatus.OK);
    }

    /**
     * Update a BPM by number.
     * @param request for the bpm.
     * @return Response with the bpm.
     */
    @PutMapping
    public ResponseEntity<UpdateBPMResponse> updateBPMRequest(
            @RequestBody UpdateBPMRequest request) {
        return new ResponseEntity<>(
                this.openCDXBPMService.updateBPMMeasurement(request), HttpStatus.OK);
    }

    /**
     * Delete a BPM by number.
     * @param id for the bpm entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBPM(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXBPMService.deleteBPMMeasurement(
                        DeleteBPMRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of BPMs
     * @param request List of BPM.
     * @return All the BPM.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListBPMResponse> listBPMRequest(
            @RequestBody ListBPMRequest request) {
        return new ResponseEntity<>(
                this.openCDXBPMService.listBPMMeasurements(request), HttpStatus.OK);
    }
}
