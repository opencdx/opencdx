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

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXIAMWorkspaceClient;
import cdx.opencdx.grpc.service.iam.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXIAMWorkspaceClientImplTest {

    @Mock
    WorkspaceServiceGrpc.WorkspaceServiceBlockingStub workspaceServiceBlockingStub;

    OpenCDXIAMWorkspaceClient openCDXIAMWorkspaceClient;

    @BeforeEach
    void setUp() {
        this.workspaceServiceBlockingStub = Mockito.mock(WorkspaceServiceGrpc.WorkspaceServiceBlockingStub.class);
        this.openCDXIAMWorkspaceClient = new OpenCDXIAMWorkspaceClientImpl(this.workspaceServiceBlockingStub);
        Mockito.when(workspaceServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.workspaceServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.workspaceServiceBlockingStub);
    }

    @Test
    void createWorkspace() {
        Mockito.when(this.workspaceServiceBlockingStub.createWorkspace(Mockito.any(CreateWorkspaceRequest.class)))
                .thenReturn(CreateWorkspaceResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateWorkspaceResponse.getDefaultInstance(),
                this.openCDXIAMWorkspaceClient.createWorkspace(
                        CreateWorkspaceRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createWorkspaceException() {
        Mockito.when(this.workspaceServiceBlockingStub.createWorkspace(Mockito.any(CreateWorkspaceRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateWorkspaceRequest request = CreateWorkspaceRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMWorkspaceClient.createWorkspace(request, openCDXCallCredentials));
    }

    @Test
    void getWorkspaceDetailsById() {
        Mockito.when(this.workspaceServiceBlockingStub.getWorkspaceDetailsById(
                        Mockito.any(GetWorkspaceDetailsByIdRequest.class)))
                .thenReturn(GetWorkspaceDetailsByIdResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetWorkspaceDetailsByIdResponse.getDefaultInstance(),
                this.openCDXIAMWorkspaceClient.getWorkspaceDetailsById(
                        GetWorkspaceDetailsByIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getWorkspaceDetailsByIdException() {
        Mockito.when(this.workspaceServiceBlockingStub.getWorkspaceDetailsById(
                        Mockito.any(GetWorkspaceDetailsByIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetWorkspaceDetailsByIdRequest request = GetWorkspaceDetailsByIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMWorkspaceClient.getWorkspaceDetailsById(request, openCDXCallCredentials));
    }

    @Test
    void updateWorkspace() {
        Mockito.when(this.workspaceServiceBlockingStub.updateWorkspace(Mockito.any(UpdateWorkspaceRequest.class)))
                .thenReturn(UpdateWorkspaceResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateWorkspaceResponse.getDefaultInstance(),
                this.openCDXIAMWorkspaceClient.updateWorkspace(
                        UpdateWorkspaceRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateWorkspaceException() {
        Mockito.when(this.workspaceServiceBlockingStub.updateWorkspace(Mockito.any(UpdateWorkspaceRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateWorkspaceRequest request = UpdateWorkspaceRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMWorkspaceClient.updateWorkspace(request, openCDXCallCredentials));
    }

    @Test
    void listWorkspaces() {
        Mockito.when(this.workspaceServiceBlockingStub.listWorkspaces(Mockito.any(ListWorkspacesRequest.class)))
                .thenReturn(ListWorkspacesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListWorkspacesResponse.getDefaultInstance(),
                this.openCDXIAMWorkspaceClient.listWorkspaces(ListWorkspacesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listWorkspacesException() {
        Mockito.when(this.workspaceServiceBlockingStub.listWorkspaces(Mockito.any(ListWorkspacesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListWorkspacesRequest request = ListWorkspacesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMWorkspaceClient.listWorkspaces(request, openCDXCallCredentials));
    }
}
