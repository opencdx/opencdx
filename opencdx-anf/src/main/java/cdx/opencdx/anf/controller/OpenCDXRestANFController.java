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
package cdx.opencdx.anf.controller;

import cdx.opencdx.anf.service.OpenCDXANFService;
import cdx.opencdx.grpc.anf.AnfStatement;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents a REST controller for managing ANF Statements in the OpenCDX platform.
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestANFController {

    private final OpenCDXANFService openCDXANFService;
    /**
     * Constructor with OpenCDXDeviceService for processing
     * @param openCDXANFService OpenCDXDeviceService for processing requests.
     */
    @Autowired
    public OpenCDXRestANFController(OpenCDXANFService openCDXANFService) {
        this.openCDXANFService = openCDXANFService;
    }

    /**
     * Method to get an ANF Statement by Id
     * @param id id of the ANF Statement to retrieve.
     * @return The requested ANF Statement.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnfStatement.ANFStatement> getANFStatement(@PathVariable("id") String id) {
        log.info("Getting ANF Statement");
        return new ResponseEntity<>(
                this.openCDXANFService.getANFStatement(
                        AnfStatement.Identifier.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }
    /**
     * Method to create an ANF Statement
     * @param anfStatement ANF Statement to create.
     * @return The created ANF Statement.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnfStatement.Identifier> createANFStatement(
            @RequestBody AnfStatement.ANFStatement anfStatement) {
        log.info("Creating ANF Statement");
        return new ResponseEntity<>(this.openCDXANFService.createANFStatement(anfStatement), HttpStatus.OK);
    }
    /**
     * Method to update an ANF Statement
     * @param anfStatement ANF Statement to update.
     * @return The updated ANF Statement.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnfStatement.Identifier> updateANFStatement(
            @RequestBody AnfStatement.ANFStatement anfStatement) {
        log.info("Updating ANF Statement");
        return new ResponseEntity<>(this.openCDXANFService.updateANFStatement(anfStatement), HttpStatus.OK);
    }
    /**
     * Method to delete an ANF Statement
     * @param id id of the ANF Statement to delete.
     * @return The deleted ANF Statement.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<AnfStatement.Identifier> deleteANFStatement(@PathVariable String id) {
        log.info("Deleting ANF Statement");
        return new ResponseEntity<>(
                this.openCDXANFService.deleteANFStatement(
                        AnfStatement.Identifier.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }
}
