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
package cdx.opencdx.iam.controller;

import cdx.opencdx.grpc.service.iam.*;
import cdx.opencdx.iam.service.OpenCDXAnalysisEngineService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /analysis-engine  api's
 */
@Slf4j
@RestController
@RequestMapping(value = "/analysis-engine", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineRestController {
    private final OpenCDXAnalysisEngineService openCDXAnalysisEngineService;

    /**
     * Constructor for the Temperature Measurement Rest Controller
     * @param openCDXAnalysisEngineService Service Interface
     */
    public OpenCDXAnalysisEngineRestController(OpenCDXAnalysisEngineService openCDXAnalysisEngineService) {
        this.openCDXAnalysisEngineService = openCDXAnalysisEngineService;
    }

    /**
     * Get a Analysis Engine by id.
     * @param id for the patient.
     * @return Response with the Analysis Engine.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetAnalysisEngineResponse> getAnalysisEngineRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXAnalysisEngineService.getAnalysisEngine(
                        GetAnalysisEngineRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to create a Analysis Engine.
     * @param request Analysis Engine.
     * @return Response Analysis Engine.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAnalysisEngineResponse> createAnalysisEngineRequest(
            @RequestBody CreateAnalysisEngineRequest request) {
        return new ResponseEntity<>(this.openCDXAnalysisEngineService.createAnalysisEngine(request), HttpStatus.OK);
    }

    /**
     * Update a Analysis Engine by id.
     * @param request for the Analysis Engine.
     * @return Response with the Analysis Engine.
     */
    @PutMapping
    public ResponseEntity<UpdateAnalysisEngineResponse> updateAnalysisEngineRequest(
            @RequestBody UpdateAnalysisEngineRequest request) {
        return new ResponseEntity<>(this.openCDXAnalysisEngineService.updateAnalysisEngine(request), HttpStatus.OK);
    }

    /**
     * Delete a Analysis Engine by id.
     * @param id for the Analysis Engine entry.
     * @return Response with the status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteAnalysisEngineResponse> deleteAnalysisEngine(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXAnalysisEngineService.deleteAnalysisEngine(
                        DeleteAnalysisEngineRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of Analysis Engine
     * @param request List of Analysis Engine.
     * @return All the Analysis Engine.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListAnalysisEnginesResponse> listAnalysisEngineRequest(
            @RequestBody ListAnalysisEnginesRequest request) {
        return new ResponseEntity<>(this.openCDXAnalysisEngineService.listAnalysisEngines(request), HttpStatus.OK);
    }
}
