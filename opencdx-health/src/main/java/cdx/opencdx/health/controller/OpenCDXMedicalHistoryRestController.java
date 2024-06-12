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

import cdx.opencdx.grpc.service.health.CreateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.DeleteMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesRequest;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesResponse;
import cdx.opencdx.grpc.service.health.SuccessResponse;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryResponse;
import cdx.opencdx.health.service.OpenCDXMedicalHistoryService;
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
        value = "/MedicalHistory",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXMedicalHistoryRestController {
    private final OpenCDXMedicalHistoryService openCDXMedicalHistoryService;

    /**
     * Constructor for the MedicalHistory Administration Rest Controller
     * @param openCDXMedicalHistoryService Service Interface
     */
    public OpenCDXMedicalHistoryRestController(OpenCDXMedicalHistoryService openCDXMedicalHistoryService) {
        this.openCDXMedicalHistoryService = openCDXMedicalHistoryService;
    }

    /**
     * Track MedicalHistory Administration.
     * @param request for the MedicalHistory.
     * @return Response with the MedicalHistory.
     */
    @PostMapping()
    public ResponseEntity<CreateMedicalHistoryResponse> createMedicalHistory(
            @RequestBody CreateMedicalHistoryRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalHistoryService.createMedicalHistory(request), HttpStatus.OK);
    }

    /**
     * Method to get MedicalHistory by ID
     * @param request MedicalHistory ID.
     * @return Response MedicalHistory.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMedicalHistoryResponse> getMedicalHistory(@RequestBody GetMedicalHistoryRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalHistoryService.getMedicalHistory(request), HttpStatus.OK);
    }

    /**
     * Update MedicalHistory
     * @param request for the MedicalHistory.
     * @return Response with the MedicalHistory.
     */
    @PutMapping()
    public ResponseEntity<UpdateMedicalHistoryResponse> updateMedicalHistory(
            @RequestBody UpdateMedicalHistoryRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalHistoryService.updateMedicalHistory(request), HttpStatus.OK);
    }

    /**
     * Delete MedicalHistory
     * @param request for the MedicalHistory.
     * @return Response with the MedicalHistory.
     */
    @DeleteMapping()
    public ResponseEntity<SuccessResponse> deleteMedicalHistory(@RequestBody DeleteMedicalHistoryRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalHistoryService.deleteMedicalHistory(request), HttpStatus.OK);
    }

    /**
     * Method to get MedicalHistories by Patient ID
     * @param request Patient ID request
     * @return All the MedicalHistories.
     */
    @PostMapping(value = "/list")
    public ResponseEntity<ListMedicalHistoriesResponse> listMedicalHistories(
            @RequestBody ListMedicalHistoriesRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalHistoryService.listMedicalHistories(request), HttpStatus.OK);
    }
}
