/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.opencdx.connected.test.service.OpenCDXTestCaseService;
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
        value = "/testcase",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestTestCaseController {

    private final OpenCDXTestCaseService openCDXTestCaseService;

    /**
     * Constructor with OpenCDXTestCaseService for processing
     * @param openCDXTestCaseService OpenCDXTestCaseService for processing requests.
     */
    @Autowired
    public RestTestCaseController(OpenCDXTestCaseService openCDXTestCaseService) {
        this.openCDXTestCaseService = openCDXTestCaseService;
    }

    /**
     * Method to get a TestCaseById
     *
     * @param id id of the TestCase to retrieve.
     * @return The requested TestCase.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXTestCaseService.getTestCaseById(
                        TestCaseIdRequest.newBuilder().setTestCaseId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to add a TestCase
     *
     * @param testCase test case to be added
     * @return The added Testcase.
     */
    @PostMapping()
    public ResponseEntity<TestCase> addTestCase(@RequestBody TestCase testCase) {
        return new ResponseEntity<>(this.openCDXTestCaseService.addTestCase(testCase), HttpStatus.OK);
    }

    /**
     * Method to update a test case
     *
     * @param testCase TestCase to update.
     * @return The updated TestCase.
     */
    @PutMapping()
    public ResponseEntity<TestCase> updateTestCase(@RequestBody TestCase testCase) {
        return new ResponseEntity<>(this.openCDXTestCaseService.updateTestCase(testCase), HttpStatus.OK);
    }

    /**
     * Method to delete a test case.
     * @param id Id of the test case to delete.
     * @return Response with the deleted test case.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteTestCase(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXTestCaseService.deleteTestCase(
                        TestCaseIdRequest.newBuilder().setTestCaseId(id).build()),
                HttpStatus.OK);
    }
}
