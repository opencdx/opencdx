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
package health.safe.api.opencdx.connected.test.controller;

import cdx.open_connected_test.v2alpha.*;
import health.safe.api.opencdx.connected.test.service.OpenCDXConnectedTestService;
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
        value = "/connected-test",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestConnectedTestController {

    private final OpenCDXConnectedTestService openCDXConnectedTestService;

    /**
     * Constructor that takes a OpenCDXConnectedTestService
     *
     * @param openCDXConnectedTestService service for processing requests.
     */
    @Autowired
    public RestConnectedTestController(OpenCDXConnectedTestService openCDXConnectedTestService) {
        this.openCDXConnectedTestService = openCDXConnectedTestService;
    }

    /**
     * Method to submit a ConnectedTest for processing.
     *
     * @param connectedTest ConnectedTest submitted
     * @return ID for the connectedTest
     */
    @PostMapping()
    public ResponseEntity<TestSubmissionResponse> submitTest(@RequestBody ConnectedTest connectedTest) {
        return new ResponseEntity<>(this.openCDXConnectedTestService.submitTest(connectedTest), HttpStatus.OK);
    }

    /**
     * Method to get a ConnectedTest
     *
     * @param id id of the ConnectedTest to retrieve.
     * @return The requested ConnectedTest.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConnectedTest> getTestDetailsById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXConnectedTestService.getTestDetailsById(
                        TestIdRequest.newBuilder().setTestId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param connectedTestListRequest request for Connected Tests.
     * @return the requested NotificationEvents.
     */
    @PostMapping("/list")
    public ResponseEntity<ConnectedTestListResponse> listConnectedTests(
            @RequestBody ConnectedTestListRequest connectedTestListRequest) {
        return new ResponseEntity<>(
                this.openCDXConnectedTestService.listConnectedTests(connectedTestListRequest), HttpStatus.OK);
    }
}
