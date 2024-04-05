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

import cdx.opencdx.grpc.health.medication.*;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
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
@RequestMapping(value = "/medicine/administer", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXMedicationAdministrationRestController {
    private final OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;

    /**
     * Constructor for the Medication Administration Rest Controller
     * @param openCDXMedicationAdministrationService Service Interface
     */
    public OpenCDXMedicationAdministrationRestController(
            OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService) {
        this.openCDXMedicationAdministrationService = openCDXMedicationAdministrationService;
    }

    /**
     * Track Medication Administration.
     * @param request for the MedicationAdministration.
     * @return Response with the MedicationAdministration.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationAdministration> trackMedicationAdministration(
            @RequestBody MedicationAdministration request) {
        return new ResponseEntity<>(
                this.openCDXMedicationAdministrationService.trackMedicationAdministration(request), HttpStatus.OK);
    }

    /**
     * Method to get Medication Administration by ID
     * @param request Medication Administration.
     * @return Response Medication.
     */
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medication> getMedicationById(@RequestBody GetMedicationByIdRequest request) {
        return new ResponseEntity<>(
                this.openCDXMedicationAdministrationService.getMedicationById(request), HttpStatus.OK);
    }

    /**
     * Method to get Medication Administration by Patient ID
     * @param request Patient ID request
     * @return All the medications.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListMedicationsResponse> listMedications(@RequestBody ListMedicationsRequest request) {
        return new ResponseEntity<>(
                this.openCDXMedicationAdministrationService.listMedications(request), HttpStatus.OK);
    }
}
