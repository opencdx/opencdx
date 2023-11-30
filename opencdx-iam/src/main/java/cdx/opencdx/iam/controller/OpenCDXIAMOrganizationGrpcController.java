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
package cdx.opencdx.iam.controller;

import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for IAM Organization
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXIAMOrganizationGrpcController extends OrganizationServiceGrpc.OrganizationServiceImplBase {

    private final OpenCDXIAMOrganizationService openCDXIAMOrganizationService;

    /**
     * Constructor using the OpenCDXIAMOrganizationService
     * @param openCDXIAMOrganizationService service to use for processing
     */
    @Autowired
    public OpenCDXIAMOrganizationGrpcController(OpenCDXIAMOrganizationService openCDXIAMOrganizationService) {
        this.openCDXIAMOrganizationService = openCDXIAMOrganizationService;
    }

    @Override
    public void createOrganization(
            CreateOrganizationRequest request, StreamObserver<CreateOrganizationResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMOrganizationService.createOrganization(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getOrganizationDetailsById(
            GetOrganizationDetailsByIdRequest request,
            StreamObserver<GetOrganizationDetailsByIdResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMOrganizationService.getOrganizationDetailsById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateOrganization(
            UpdateOrganizationRequest request, StreamObserver<UpdateOrganizationResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMOrganizationService.updateOrganization(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listOrganizations(Empty request, StreamObserver<ListOrganizationsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMOrganizationService.listOrganizations());
        responseObserver.onCompleted();
    }
}
