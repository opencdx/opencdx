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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.grpc.inventory.*;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /greeting api's
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/manufacturer",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestManufacturerController {

    private final OpenCDXManufacturerService openCDXManufacturerService;

    /**
     * Constructor with OpenCDXManufacturerService for processing
     * @param openCDXManufacturerService OpenCDXManufacturerService for processing requests.
     */
    @Autowired
    public OpenCDXRestManufacturerController(OpenCDXManufacturerService openCDXManufacturerService) {
        this.openCDXManufacturerService = openCDXManufacturerService;
    }

    /**
     * Method to get a ManufacturerById
     *
     * @param id id of the Manufacturer to retrieve.
     * @return The requested Manufacturer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXManufacturerService.getManufacturerById(
                        ManufacturerIdRequest.newBuilder().setManufacturerId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to add a manufacturer
     *
     * @param manufacturer Manufacturer to add.
     * @return The added Manufacturer.
     */
    @PostMapping()
    public ResponseEntity<Manufacturer> addManufacturer(@RequestBody Manufacturer manufacturer) {
        return new ResponseEntity<>(this.openCDXManufacturerService.addManufacturer(manufacturer), HttpStatus.OK);
    }

    /**
     * Method to update a manufacturer
     *
     * @param manufacturer Manufacturer to update.
     * @return The updated Manufacturer.
     */
    @PutMapping()
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer) {
        return new ResponseEntity<>(this.openCDXManufacturerService.updateManufacturer(manufacturer), HttpStatus.OK);
    }

    /**
     * Method to delete a manufacturer.
     * @param id Id of the manufacturer to delete.
     * @return Response with the deleted manufacturer.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteManufacturer(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXManufacturerService.deleteManufacturer(
                        ManufacturerIdRequest.newBuilder().setManufacturerId(id).build()),
                HttpStatus.OK);
    }
}
