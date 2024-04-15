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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.service.iam.*;

/**
 * Interface for communicating with the  IAM Workspace microservice.
 */
public interface OpenCDXIAMWorkspaceClient {
    /**
     * Method to gRPC Call IAM Workspace Service createWorkspace() api.
     * @param request CreateWorkspaceRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    CreateWorkspaceResponse createWorkspace(
            CreateWorkspaceRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call  IAM Workspace Service getWorkspaceDetailsById() api.
     * @param request GetWorkspaceDetailsByIdRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    GetWorkspaceDetailsByIdResponse getWorkspaceDetailsById(
            GetWorkspaceDetailsByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call IAM Workspace Service updateWorkspace() api.
     * @param request UpdateWorkspaceRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateWorkspaceResponse updateWorkspace(
            UpdateWorkspaceRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call IAM Workspace Service listWorkspaces() api.
     * @param request Empty to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListWorkspacesResponse listWorkspaces(Empty request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
