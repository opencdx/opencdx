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
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller for Connected Lab
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcConnectedLabController extends ConnectedLabServiceGrpc.ConnectedLabServiceImplBase {

    private final OpenCDXConnectedLabService openCDXConnectedLabService;

    /**
     * Constructor using the ConnectedLabService
     * @param openCDXConnectedLabService service to use for processing
     */
    public OpenCDXGrpcConnectedLabController(OpenCDXConnectedLabService openCDXConnectedLabService) {
        this.openCDXConnectedLabService = openCDXConnectedLabService;
    }

    @Override
    public void submitLabFindings(LabFindings request, StreamObserver<LabFindingsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.submitLabFindings(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createConnectedLab(
            CreateConnectedLabRequest request, StreamObserver<CreateConnectedLabResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.createConnectedLab(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectedLab(
            GetConnectedLabRequest request, StreamObserver<GetConnectedLabResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.getConnectedLab(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateConnectedLab(
            UpdateConnectedLabRequest request, StreamObserver<UpdateConnectedLabResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.updateConnectedLab(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteConnectedLab(
            DeleteConnectedLabRequest request, StreamObserver<DeleteConnectedLabResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.deleteConnectedLab(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listConnectedLabs(
            ListConnectedLabsRequest request, StreamObserver<ListConnectedLabsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedLabService.listConnectedLabs(request));
        responseObserver.onCompleted();
    }
}
