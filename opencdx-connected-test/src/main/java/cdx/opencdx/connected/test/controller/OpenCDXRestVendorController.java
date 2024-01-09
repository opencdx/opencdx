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

import cdx.opencdx.connected.test.service.OpenCDXVendorService;
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
@RequestMapping(value = "/vendor", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestVendorController {

    private final OpenCDXVendorService openCDXVendorService;

    /**
     * Constructor with OpenCDXVendorService for processing
     * @param openCDXVendorService OpenCDXVendorService for processing requests.
     */
    @Autowired
    public OpenCDXRestVendorController(OpenCDXVendorService openCDXVendorService) {
        this.openCDXVendorService = openCDXVendorService;
    }

    /**
     * Method to get a VendorById
     *
     * @param id id of the Vendor to retrieve.
     * @return The requested Vendor.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXVendorService.getVendorById(
                        VendorIdRequest.newBuilder().setVendorId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to add a Vendor
     *
     * @param vendor Vendor to be added
     * @return The added Vendor.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(this.openCDXVendorService.addVendor(vendor), HttpStatus.OK);
    }

    /**
     * Method to update a vendor
     *
     * @param vendor Vendor to update.
     * @return The updated Vendor.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(this.openCDXVendorService.updateVendor(vendor), HttpStatus.OK);
    }

    /**
     * Method to delete a vendor.
     * @param id Id of the vendor to delete.
     * @return Response with the deleted vendor.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteVendor(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXVendorService.deleteVendor(
                        VendorIdRequest.newBuilder().setVendorId(id).build()),
                HttpStatus.OK);
    }
}
