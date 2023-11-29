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
}
