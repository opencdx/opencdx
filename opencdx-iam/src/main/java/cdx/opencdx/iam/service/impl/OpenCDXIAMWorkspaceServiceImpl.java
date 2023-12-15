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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.model.OpenCDXIAMWorkspaceModel;
import cdx.opencdx.iam.repository.OpenCDXIAMWorkspaceRepository;
import cdx.opencdx.iam.service.OpenCDXIAMWorkspaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Workspace Service
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMWorkspaceServiceImpl implements OpenCDXIAMWorkspaceService {

    private static final String DOMAIN = "OpenCDXIAMWorkspaceServiceImpl";
    private static final String WORKSPACE = "WORKSPACE: ";

    private final OpenCDXIAMWorkspaceRepository openCDXIAMWorkspaceRepository;

    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;

    /**
     * Worksapce Service
     * @param openCDXIAMWorkspaceRepository Database repository for Workspace
     * @param openCDXAuditService Audit Service to record information
     * @param openCDXCurrentUser Current User for accessing the current user.
     * @param objectMapper ObjectMapper for converting to JSON
     */
    public OpenCDXIAMWorkspaceServiceImpl(
            OpenCDXIAMWorkspaceRepository openCDXIAMWorkspaceRepository,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper) {
        this.openCDXIAMWorkspaceRepository = openCDXIAMWorkspaceRepository;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
    }

    /**
     * Method to create a new workspace.
     *
     * @param request CreateWorkspaceRequest for new workspace.
     * @return CreateWorkspaceResponse with the new workspace created.
     */
    @Override
    public CreateWorkspaceResponse createWorkspace(CreateWorkspaceRequest request) {
        OpenCDXIAMWorkspaceModel model = new OpenCDXIAMWorkspaceModel(request.getWorkspace());
        model = this.openCDXIAMWorkspaceRepository.save(model);

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Create WorkSpace",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    WORKSPACE + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert OpenCDXIAMModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", request.toString());
            throw openCDXNotAcceptable;
        }

        return CreateWorkspaceResponse.newBuilder()
                .setWorkspace(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to get a specific workspace.
     *
     * @param request GetWorkspaceDetailsByIdRequest for workspace to get.
     * @return GetWorkspaceDetailsByIdResponse with the workspace.
     */
    @Override
    public GetWorkspaceDetailsByIdResponse getWorkspaceDetailsById(GetWorkspaceDetailsByIdRequest request) {
        OpenCDXIAMWorkspaceModel model = this.openCDXIAMWorkspaceRepository
                .findById(new ObjectId(request.getWorkspaceId()))
                .orElseThrow(
                        () -> new OpenCDXNotFound(DOMAIN, 3, "FAILED_TO_FIND_WORKSPACE" + request.getWorkspaceId()));
        return GetWorkspaceDetailsByIdResponse.newBuilder()
                .setWorkspace(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to update a workspace.
     *
     * @param request UpdateWorkspaceRequest for a workspace to be updated.
     * @return UpdateOrganizationResponse with the updated workspace.
     */
    @Override
    public UpdateWorkspaceResponse updateWorkspace(UpdateWorkspaceRequest request) {
        if (!this.openCDXIAMWorkspaceRepository.existsById(
                new ObjectId(request.getWorkspace().getId()))) {
            throw new OpenCDXNotFound(
                    DOMAIN,
                    3,
                    "FAILED_TO_FIND_WORKSPACE" + request.getWorkspace().getId());
        }
        OpenCDXIAMWorkspaceModel model =
                this.openCDXIAMWorkspaceRepository.save(new OpenCDXIAMWorkspaceModel(request.getWorkspace()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Update WorkSpace",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    WORKSPACE + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert OpenCDXIAMOrganizationModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateWorkspaceResponse.newBuilder()
                .setWorkspace(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to get the list of workspace.
     *
     * @return ListWorkspacesResponse with all the workspace.
     */
    @Override
    public ListWorkspacesResponse listWorkspaces() {
        List<OpenCDXIAMWorkspaceModel> all = this.openCDXIAMWorkspaceRepository.findAll();
        return ListWorkspacesResponse.newBuilder()
                .addAllWorkspaces(all.stream()
                        .map(OpenCDXIAMWorkspaceModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
