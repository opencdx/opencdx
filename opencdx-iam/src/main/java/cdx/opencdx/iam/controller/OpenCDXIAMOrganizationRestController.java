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
package cdx.opencdx.iam.controller;

import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /iam/organization  api's
 */
@Slf4j
@RestController
@RequestMapping(value = "/organization", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXIAMOrganizationRestController {
    private final OpenCDXIAMOrganizationService openCDXIAMOrganizationService;

    /**
     * Constructor for the Organizatgion Rest Controller
     * @param openCDXIAMOrganizationService Service Interface
     */
    public OpenCDXIAMOrganizationRestController(OpenCDXIAMOrganizationService openCDXIAMOrganizationService) {
        this.openCDXIAMOrganizationService = openCDXIAMOrganizationService;
    }

    /**
     * Add a new Organization.
     * @param request Request to create an organization.
     * @return Response with the organization.
     */
    @PostMapping()
    public ResponseEntity<CreateOrganizationResponse> createOrganization(
            @RequestBody CreateOrganizationRequest request) {
        return new ResponseEntity<>(this.openCDXIAMOrganizationService.createOrganization(request), HttpStatus.OK);
    }

    /**
     * Get a Organization by Id.
     * @param id for the organization requested.
     * @return Response with the organization.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetOrganizationDetailsByIdResponse> getOrganizationDetailsById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMOrganizationService.getOrganizationDetailsById(
                        GetOrganizationDetailsByIdRequest.newBuilder()
                                .setOrganizationId(id)
                                .build()),
                HttpStatus.OK);
    }

    /**
     * Update an Organization by Id.
     * @param request for the organization to be updated.
     * @return Response with the updated organization.
     */
    @PutMapping()
    public ResponseEntity<UpdateOrganizationResponse> updateOrganization(
            @RequestBody UpdateOrganizationRequest request) {
        return new ResponseEntity<>(this.openCDXIAMOrganizationService.updateOrganization(request), HttpStatus.OK);
    }

    /**
     * List of organizations
     * @return All the organizations.
     */
    @PostMapping("/list")
    public ResponseEntity<ListOrganizationsResponse> listOrganizations() {
        return new ResponseEntity<>(this.openCDXIAMOrganizationService.listOrganizations(), HttpStatus.OK);
    }
}
