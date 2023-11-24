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
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.service.OpenCDXIAMWorkspaceService;

public class OpenCDXIAMWorkspaceServiceImpl implements OpenCDXIAMWorkspaceService {

    /**
     * Method to create a new workspace.
     *
     * @param request CreateWorkspaceRequest for new workspace.
     * @return CreateWorkspaceResponse with the new workspace created.
     */
    @Override
    public CreateWorkspaceResponse createWorkspace(CreateWorkspaceRequest request) {
        return null;
    }

    /**
     * Method to get a specific workspace.
     *
     * @param request GetWorkspaceDetailsByIdRequest for workspace to get.
     * @return GetWorkspaceDetailsByIdResponse with the workspace.
     */
    @Override
    public GetWorkspaceDetailsByIdResponse getWorkspaceDetailsById(GetWorkspaceDetailsByIdRequest request) {
        return null;
    }

    /**
     * Method to update a workspace.
     *
     * @param request UpdateWorkspaceRequest for a workspace to be updated.
     * @return UpdateOrganizationResponse with the updated workspace.
     */
    @Override
    public UpdateWorkspaceResponse updateWorkspace(UpdateWorkspaceRequest request) {
        return null;
    }

    /**
     * Method to get the list of workspace.
     *
     * @return ListWorkspacesResponse with all the workspace.
     */
    @Override
    public ListWorkspacesResponse listWorkspaces() {
        return null;
    }
}
