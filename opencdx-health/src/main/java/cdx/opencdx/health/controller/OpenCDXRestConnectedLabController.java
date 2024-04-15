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

import cdx.opencdx.grpc.lab.connected.*;
import cdx.opencdx.health.service.OpenCDXConnectedLabService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /lab api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestConnectedLabController {
    private final OpenCDXConnectedLabService openCDXConnectedLabService;

    /**
     * Constructor that takes a ConnectedLabService
     * @param openCDXConnectedLabService service for processing requests.
     */
    public OpenCDXRestConnectedLabController(OpenCDXConnectedLabService openCDXConnectedLabService) {
        this.openCDXConnectedLabService = openCDXConnectedLabService;
    }

    /** Post Lab Rest API
     * @param request LabFindings indicating who to say hello to.
     * @return LabFindingsResponse with the hello message.
     */
    @PostMapping(value = "/lab/findings", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabFindingsResponse> submitLabFindings(@RequestBody LabFindings request) {
        return new ResponseEntity<>(this.openCDXConnectedLabService.submitLabFindings(request), HttpStatus.OK);
    }
    /** Get Connected Lab Rest API
     * @param id ConnectedLabId indicating who to say hello to.
     * @return GetConnectedLabResponse with the hello message.
     */
    @GetMapping("/lab/{id}")
    public ResponseEntity<GetConnectedLabResponse> getConnectedLab(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXConnectedLabService.getConnectedLab(GetConnectedLabRequest.newBuilder()
                        .setConnectedLabId(id)
                        .build()),
                HttpStatus.OK);
    }
    /** Create Connected Lab Rest API
     * @param request CreateConnectedLabRequest indicating who to say hello to.
     * @return CreateConnectedLabResponse with the hello message.
     */
    @PostMapping(value = "/lab", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateConnectedLabResponse> createConnectedLab(
            @RequestBody CreateConnectedLabRequest request) {
        return new ResponseEntity<>(this.openCDXConnectedLabService.createConnectedLab(request), HttpStatus.OK);
    }
    /** Update Connected Lab Rest API
     * @param request UpdateConnectedLabRequest indicating who to say hello to.
     * @return UpdateConnectedLabResponse with the hello message.
     */
    @PutMapping(value = "/lab", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateConnectedLabResponse> updatupdateConnectedLabeOrder(
            @RequestBody UpdateConnectedLabRequest request) {
        return new ResponseEntity<>(this.openCDXConnectedLabService.updateConnectedLab(request), HttpStatus.OK);
    }
    /** Delete Connected Lab Rest API
     * @param id ConnectedLabId indicating who to say hello to.
     * @return DeleteConnectedLabResponse with the hello message.
     */
    @DeleteMapping("/lab/{id}")
    public ResponseEntity<DeleteConnectedLabResponse> deleteConnectedLab(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXConnectedLabService.deleteConnectedLab(DeleteConnectedLabRequest.newBuilder()
                        .setConnectedLabId(id)
                        .build()),
                HttpStatus.OK);
    }
    /** List Connected Lab Rest API
     * @param request ListConnectedLabsRequest indicating who to say hello to.
     * @return ListConnectedLabsResponse with the hello message.
     */
    @PostMapping(value = "/lab/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListConnectedLabsResponse> listConnectedLabs(@RequestBody ListConnectedLabsRequest request) {
        return new ResponseEntity<>(this.openCDXConnectedLabService.listConnectedLabs(request), HttpStatus.OK);
    }
}
