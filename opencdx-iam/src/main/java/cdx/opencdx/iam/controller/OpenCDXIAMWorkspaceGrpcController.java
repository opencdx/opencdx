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
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for IAM Workspace
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXIAMWorkspaceGrpcController extends WorkspaceServiceGrpc.WorkspaceServiceImplBase {

    private final OpenCDXIAMWorkspaceService openCDXIAMWorkspaceService;

    /**
     * Constructor using the OpenCDXIAMWorkspaceService
     * @param openCDXIAMWorkspaceService service to use for processing
     */
    @Autowired
    public OpenCDXIAMWorkspaceGrpcController(OpenCDXIAMWorkspaceService openCDXIAMWorkspaceService) {
        this.openCDXIAMWorkspaceService = openCDXIAMWorkspaceService;
    }

    @Override
    public void createWorkspace(
            CreateWorkspaceRequest request, StreamObserver<CreateWorkspaceResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMWorkspaceService.createWorkspace(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getWorkspaceDetailsById(
            GetWorkspaceDetailsByIdRequest request, StreamObserver<GetWorkspaceDetailsByIdResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMWorkspaceService.getWorkspaceDetailsById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateWorkspace(
            UpdateWorkspaceRequest request, StreamObserver<UpdateWorkspaceResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMWorkspaceService.updateWorkspace(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listWorkspaces(Empty request, StreamObserver<ListWorkspacesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMWorkspaceService.listWorkspaces());
        responseObserver.onCompleted();
    }
}
