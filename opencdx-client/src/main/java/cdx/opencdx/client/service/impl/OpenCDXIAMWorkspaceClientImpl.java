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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXIAMWorkspaceClient;
import cdx.opencdx.grpc.organization.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Workspace gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXIAMWorkspaceClientImpl implements OpenCDXIAMWorkspaceClient {

    private static final String OPEN_CDX_WORKSPACE_CLIENT_IMPL = "OpenCDXIAMWorkspaceClientImpl";
    private final WorkspaceServiceGrpc.WorkspaceServiceBlockingStub workspaceServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXIAMWorkspaceClientImpl(ManagedChannel channel) {
        this.workspaceServiceBlockingStub = WorkspaceServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Workspace client implementation.
     * @param workspaceServiceBlockingStub gRPC Blocking Stub for Workspace.
     */
    public OpenCDXIAMWorkspaceClientImpl(
            WorkspaceServiceGrpc.WorkspaceServiceBlockingStub workspaceServiceBlockingStub) {
        this.workspaceServiceBlockingStub = workspaceServiceBlockingStub;
    }

    /**
     * Method to gRPC Call IAM Workspace Service createWorkspace() api.
     *
     * @param request                CreateWorkspaceRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateWorkspaceResponse createWorkspace(
            CreateWorkspaceRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return workspaceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createWorkspace(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WORKSPACE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call  IAM Workspace Service getWorkspaceDetailsById() api.
     *
     * @param request                GetWorkspaceDetailsByIdRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetWorkspaceDetailsByIdResponse getWorkspaceDetailsById(
            GetWorkspaceDetailsByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return workspaceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getWorkspaceDetailsById(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WORKSPACE_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Workspace Service updateWorkspace() api.
     *
     * @param request                UpdateWorkspaceRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateWorkspaceResponse updateWorkspace(
            UpdateWorkspaceRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return workspaceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateWorkspace(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WORKSPACE_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Workspace Service listWorkspaces() api.
     *
     * @param request                Empty to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListWorkspacesResponse listWorkspaces(Empty request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return workspaceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listWorkspaces(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WORKSPACE_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
