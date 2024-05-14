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

import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.GetVaccineByIdRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesResponse;
import cdx.opencdx.health.service.OpenCDXVaccineService;
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
@RequestMapping(value = "/vaccine", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXVaccineRestController {
    private final OpenCDXVaccineService openCDXVaccineService;

    /**
     * Constructor for the Vaccine Administration Rest Controller
     * @param openCDXVaccineService Service Interface
     */
    public OpenCDXVaccineRestController(OpenCDXVaccineService openCDXVaccineService) {
        this.openCDXVaccineService = openCDXVaccineService;
    }

    /**
     * Track Vaccine Administration.
     * @param request for the Vaccine.
     * @return Response with the Vaccine.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vaccine> trackVaccineAdministration(@RequestBody Vaccine request) {
        return new ResponseEntity<>(this.openCDXVaccineService.trackVaccineAdministration(request), HttpStatus.OK);
    }

    /**
     * Method to get Vaccine by ID
     * @param id Vaccine ID.
     * @return Response Vaccine.
     */
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXVaccineService.getVaccineById(
                        GetVaccineByIdRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to get Vaccine by Patient ID
     * @param request Patient ID request
     * @return All the vaccines.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListVaccinesResponse> listVaccines(@RequestBody ListVaccinesRequest request) {
        return new ResponseEntity<>(this.openCDXVaccineService.listVaccines(request), HttpStatus.OK);
    }
}
