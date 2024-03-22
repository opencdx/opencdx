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
import cdx.opencdx.health.service.OpenCDXMedicationService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /medication API's
 */
@Slf4j
@RestController
@RequestMapping(value = "/medication", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXMedicationRestController {
    private final OpenCDXMedicationService openCDXMedicationService;

    /**
     * Constructor that takes a OpenCDXMedicationService
     * @param openCDXMedicationService service for procesing requests
     */
    public OpenCDXMedicationRestController(OpenCDXMedicationService openCDXMedicationService) {
        this.openCDXMedicationService = openCDXMedicationService;
    }

    /**
     * Method to prescribe medication.
     * @param request The medication to prescribe.
     * @return The prescribed medication.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medication> prescribing(@RequestBody Medication request) {
        return new ResponseEntity<>(this.openCDXMedicationService.prescribing(request), HttpStatus.OK);
    }
    /**
     * Method to end medication.
     * @param request The medication to end.
     * @return The ended medication.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medication> ending(@RequestBody EndMedicationRequest request) {
        return new ResponseEntity<>(this.openCDXMedicationService.ending(request), HttpStatus.OK);
    }
    /**
     * Method to list all medications for a patient.
     * @param request Request for the patient's medications.
     * @return Response with the patient's medications.
     */
    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListMedicationsResponse> listAllMedications(@RequestBody ListMedicationsRequest request) {
        return new ResponseEntity<>(this.openCDXMedicationService.listAllMedications(request), HttpStatus.OK);
    }
    /**
     * Method to list current medications for a patient.
     * @param request Request for the patient's current medications.
     * @return Response with the patient's current medications.
     */
    @PostMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListMedicationsResponse> listCurrentMedications(@RequestBody ListMedicationsRequest request) {
        return new ResponseEntity<>(this.openCDXMedicationService.listCurrentMedications(request), HttpStatus.OK);
    }
    /**
     * Method to search for medications.
     * @param request Request for the medications to search for.
     * @return Response with the medications found.
     */
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListMedicationsResponse> searchMedications(@RequestBody SearchMedicationsRequest request) {
        return new ResponseEntity<>(this.openCDXMedicationService.searchMedications(request), HttpStatus.OK);
    }
}
