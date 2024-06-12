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
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
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
@RequestMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordRestController {
    private final OpenCDXMedicalRecordService openCDXMedicalRecordService;

    /**
     * Constructor for the Medical Record Rest Controller
     * @param openCDXMedicalRecordService Service Interface
     */
    public OpenCDXMedicalRecordRestController(OpenCDXMedicalRecordService openCDXMedicalRecordService) {
        this.openCDXMedicalRecordService = openCDXMedicalRecordService;
    }

    /**
     * Method to create Medical Record.
     * @param request GetMedicalRecordRequest for medical record.
     * @return GetMedicalRecordResponse with medical record.
     */
    @PostMapping("/request")
    public ResponseEntity<GetMedicalRecordResponse> requestMedicalRecord(@RequestBody GetMedicalRecordRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalRecordService.requestMedicalRecord(request), HttpStatus.OK);
    }

    /**
     * Method to get medical record status.
     * @param id request of the medical record.
     * @return GetAllergyResponse with medical record.
     */
    @GetMapping("/status/{id}")
    public ResponseEntity<SuccessResponse> getMedicalRecordStatus(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMedicalRecordService.getMedicalRecordStatus(MedicalRecordByIdRequest.newBuilder()
                        .setMedicalRecordId(id)
                        .build()),
                HttpStatus.OK);
    }

    /**
     * Method to get medical record.
     * @param id MedicalRecordByIdRequest for medical record.
     * @return MedicalRecordByIdResponse with medical record.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordByIdResponse> getMedicalRecordById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMedicalRecordService.getMedicalRecordById(MedicalRecordByIdRequest.newBuilder()
                        .setMedicalRecordId(id)
                        .build()),
                HttpStatus.OK);
    }

    /**
     * Method to create Medical Record.
     * @param request CreateMedicalRecordRequest for medical record.
     * @return CreateMedicalRecordResponse with medical record.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateMedicalRecordResponse> createMedicalRecord(
            @RequestBody CreateMedicalRecordRequest request) {
        return new ResponseEntity<>(this.openCDXMedicalRecordService.createMedicalRecord(request), HttpStatus.OK);
    }
}
