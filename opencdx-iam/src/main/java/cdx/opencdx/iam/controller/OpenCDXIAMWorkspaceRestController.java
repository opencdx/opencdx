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
import cdx.opencdx.iam.service.OpenCDXIAMWorkspaceService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /iam/workspace  api's
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/workspace",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXIAMWorkspaceRestController {

    private final OpenCDXIAMWorkspaceService openCDXIAMWorkspaceService;

    /**
     * Constructor for the Workspace Rest Controller
     * @param openCDXIAMWorkspaceService Service Interface
     */
    public OpenCDXIAMWorkspaceRestController(OpenCDXIAMWorkspaceService openCDXIAMWorkspaceService) {
        this.openCDXIAMWorkspaceService = openCDXIAMWorkspaceService;
    }

    /**
     * Add a new Workspace.
     * @param request Request to create a workspace.
     * @return Response with the workspace.
     */
    @PostMapping()
    public ResponseEntity<CreateWorkspaceResponse> createWorkspace(@RequestBody CreateWorkspaceRequest request) {
        return new ResponseEntity<>(this.openCDXIAMWorkspaceService.createWorkspace(request), HttpStatus.OK);
    }

    /**
     * Get a Organization by Id.
     * @param id for the workspace requested.
     * @return Response with the workspace.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetWorkspaceDetailsByIdResponse> getWorkspaceDetailsById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMWorkspaceService.getWorkspaceDetailsById(GetWorkspaceDetailsByIdRequest.newBuilder()
                        .setWorkspaceId(id)
                        .build()),
                HttpStatus.OK);
    }

    /**
     * Update an Organization by Id.
     * @param request for the workspace to be updated.
     * @return Response with the updated workspace.
     */
    @PutMapping()
    public ResponseEntity<UpdateWorkspaceResponse> updateWorkspace(@RequestBody UpdateWorkspaceRequest request) {
        return new ResponseEntity<>(this.openCDXIAMWorkspaceService.updateWorkspace(request), HttpStatus.OK);
    }

    /**
     * List of workspaces
     * @return All the workspaces.
     */
    @PostMapping("/list")
    public ResponseEntity<ListWorkspacesResponse> listWorkspaces() {
        return new ResponseEntity<>(this.openCDXIAMWorkspaceService.listWorkspaces(), HttpStatus.OK);
    }
}
