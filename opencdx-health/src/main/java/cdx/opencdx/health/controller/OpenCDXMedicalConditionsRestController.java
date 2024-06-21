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
import cdx.opencdx.health.service.OpenCDXMedicalConditionsService;
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
@RequestMapping(
        value = "/medicalconditions",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXMedicalConditionsRestController {
    private final OpenCDXMedicalConditionsService openCDXMedicalConditionsService;

    /**
     * Constructor for the Medical Conditions Rest Controller
     * @param openCDXMedicalConditionsService Service Interface
     */
    public OpenCDXMedicalConditionsRestController(OpenCDXMedicalConditionsService openCDXMedicalConditionsService) {
        this.openCDXMedicalConditionsService = openCDXMedicalConditionsService;
    }
    /**
     * Create Medical Conditions.
     * @param request for the MedicalConditions.
     * @return Response with the MedicalConditions.
     */
    @PostMapping
    public ResponseEntity<DiagnosisResponse> createDiagnosis(@RequestBody DiagnosisRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalConditionsService.createDiagnosis(request), HttpStatus.OK);
    }

    /**
     * Method to get MedicalConditions
     * @param id MedicalConditions id
     * @return Response MedicalConditions.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<DiagnosisResponse> getDiagnosis(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMedicalConditionsService.getDiagnosis(
                        GetDiagnosisByIdRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update MedicalConditions
     * @param request for the MedicalConditions.
     * @return Response with the MedicalConditions.
     */
    @PutMapping
    public ResponseEntity<DiagnosisResponse> updateDiagnosis(@RequestBody DiagnosisRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalConditionsService.updateDiagnosis(request), HttpStatus.OK);
    }

    /**
     * Delete MedicalConditions
     * @param id MedicalConditions id
     * @return Response with the MedicalConditions.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DiagnosisResponse> deleteDiagnosis(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMedicalConditionsService.deleteDiagnosis(
                        DeleteDiagnosisRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to get MedicalConditions by Patient ID or national health ID
     * @param request Patient ID or national health ID request
     * @return All the MedicalConditions.
     */
    @PostMapping(value = "/list")
    public ResponseEntity<ListDiagnosisResponse> listDiagnosis(@RequestBody ListDiagnosisRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalConditionsService.listDiagnosis(request), HttpStatus.OK);
    }
}
