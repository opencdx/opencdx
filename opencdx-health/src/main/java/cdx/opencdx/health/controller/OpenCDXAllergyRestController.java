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
import cdx.opencdx.health.service.OpenCDXAllergyService;
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
@RequestMapping(value = "/allergy", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXAllergyRestController {
    private final OpenCDXAllergyService openCDXAllergyService;

    /**
     * Constructor for the BPM Rest Controller
     * @param openCDXAllergyService Service Interface
     */
    public OpenCDXAllergyRestController(OpenCDXAllergyService openCDXAllergyService) {
        this.openCDXAllergyService = openCDXAllergyService;
    }

    /**
     * Method to create Known Allergy.
     * @param request CreateAllergyRequest for allergy.
     * @return CreateAllergyResponse with allergy.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAllergyResponse> createAllergy(@RequestBody CreateAllergyRequest request) {
        return new ResponseEntity<>(this.openCDXAllergyService.createAllergy(request), HttpStatus.OK);
    }

    /**
     * Method to get Allergy.
     * @param id id of the allergy.
     * @return GetAllergyResponse with allergy.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetAllergyResponse> getBPMRequest(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXAllergyService.getAllergy(
                        GetAllergyRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to update Allergy.
     * @param request UpdateAllergyRequest for allergy.
     * @return UpdateAllergyResponse with allergy.
     */
    @PutMapping
    public ResponseEntity<UpdateAllergyResponse> updateBPMRequest(@RequestBody UpdateAllergyRequest request) {
        return new ResponseEntity<>(this.openCDXAllergyService.updateAllergy(request), HttpStatus.OK);
    }

    /**
     * Method to delete Allergy.
     * @param id of the allergy.
     * @return SuccessResponse with allergy.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBPM(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXAllergyService.deleteAllergy(
                        DeleteAllergyRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to list Known Allergy.
     * @param request ListAllergyRequest for allergy.
     * @return ListAllergyResponse with allergy.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListAllergyResponse> listBPMRequest(@RequestBody ListAllergyRequest request) {
        return new ResponseEntity<>(this.openCDXAllergyService.listAllergies(request), HttpStatus.OK);
    }
}
