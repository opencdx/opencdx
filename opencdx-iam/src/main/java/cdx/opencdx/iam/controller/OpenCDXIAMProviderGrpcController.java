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

import cdx.opencdx.grpc.provider.*;
import cdx.opencdx.iam.service.OpenCDXIAMProviderService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for IAM Provider
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXIAMProviderGrpcController extends ProviderServiceGrpc.ProviderServiceImplBase {
    private final OpenCDXIAMProviderService openCDXIAMProviderService;

    /**
     * Constructor using the OpenCDXIAMOrganizationService
     * @param openCDXIAMProviderService service to use for processing
     */
    @Autowired
    public OpenCDXIAMProviderGrpcController(OpenCDXIAMProviderService openCDXIAMProviderService) {
        this.openCDXIAMProviderService = openCDXIAMProviderService;
    }

    @Override
    public void getProviderByNumber(GetProviderRequest request, StreamObserver<GetProviderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProviderService.getProviderByNumber(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProvider(DeleteProviderRequest request, StreamObserver<DeleteProviderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProviderService.deleteProvider(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listProviders(ListProvidersRequest request, StreamObserver<ListProvidersResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProviderService.listProviders(request));
        responseObserver.onCompleted();
    }

    @Override
    public void loadProvider(LoadProviderRequest request, StreamObserver<LoadProviderResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProviderService.loadProvider(request));
        responseObserver.onCompleted();
    }
}
