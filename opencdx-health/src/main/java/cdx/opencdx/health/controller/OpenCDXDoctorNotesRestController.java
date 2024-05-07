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
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
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
@RequestMapping(value = "/doctor-notes", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXDoctorNotesRestController {
    private final OpenCDXDoctorNotesService openCDXDoctorNotesService;

    /**
     * Constructor for the DoctorNotes Rest Controller
     * @param openCDXDoctorNotesService Service Interface
     */
    public OpenCDXDoctorNotesRestController(OpenCDXDoctorNotesService openCDXDoctorNotesService) {
        this.openCDXDoctorNotesService = openCDXDoctorNotesService;
    }

    /**
     * Method to create Doctor Notes
     * @param request CreateDoctorNotesRequest for doctor notes.
     * @return CreateDoctorNotesResponse with doctor notes.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateDoctorNotesResponse> createDoctorNotes(@RequestBody CreateDoctorNotesRequest request) {
        return new ResponseEntity<>(this.openCDXDoctorNotesService.createDoctorNotes(request), HttpStatus.OK);
    }

    /**
     * Method to get DoctorNotes.
     * @param id id of the doctor notes.
     * @return GetDoctorNotesResponse with doctor notes.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetDoctorNotesResponse> getDoctorNotes(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXDoctorNotesService.getDoctorNotes(
                        GetDoctorNotesRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to update DoctorNotes.
     * @param request UpdateDoctorNotesRequest for doctor notes.
     * @return UpdateDoctorNotesResponse with doctor notes.
     */
    @PutMapping
    public ResponseEntity<UpdateDoctorNotesResponse> updateDoctorNotes(@RequestBody UpdateDoctorNotesRequest request) {
        return new ResponseEntity<>(this.openCDXDoctorNotesService.updateDoctorNotes(request), HttpStatus.OK);
    }

    /**
     * Method to delete DoctorNotes.
     * @param id of the doctor notes.
     * @return SuccessResponse with doctor notes.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteDoctorNotes(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXDoctorNotesService.deleteDoctorNotes(
                        DeleteDoctorNotesRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to list Doctor Notes
     * @param request ListDoctorNotesRequest for doctor notes.
     * @return ListDoctorNotesResponse with doctor notes.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListDoctorNotesResponse> listAllByPatientId(@RequestBody ListDoctorNotesRequest request) {
        return new ResponseEntity<>(this.openCDXDoctorNotesService.listAllByPatientId(request), HttpStatus.OK);
    }
}
