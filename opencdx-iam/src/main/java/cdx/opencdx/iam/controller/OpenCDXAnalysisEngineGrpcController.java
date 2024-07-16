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

import cdx.opencdx.grpc.service.iam.*;
import cdx.opencdx.iam.service.OpenCDXAnalysisEngineService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for AnalysisEngine
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineGrpcController extends AnalysisEngineServiceGrpc.AnalysisEngineServiceImplBase {
    private final OpenCDXAnalysisEngineService openCDXAnalysisEngineService;

    /**
     * Constructor using the OpenCDXAnalysisEngineService
     * @param openCDXAnalysisEngineService service to use for processing
     */
    @Autowired
    public OpenCDXAnalysisEngineGrpcController(OpenCDXAnalysisEngineService openCDXAnalysisEngineService) {
        this.openCDXAnalysisEngineService = openCDXAnalysisEngineService;
    }

    @Override
    @Secured({})
    public void createAnalysisEngine(
            CreateAnalysisEngineRequest request, StreamObserver<CreateAnalysisEngineResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAnalysisEngineService.createAnalysisEngine(request));
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void getAnalysisEngine(
            GetAnalysisEngineRequest request, StreamObserver<GetAnalysisEngineResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAnalysisEngineService.getAnalysisEngine(request));
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void updateAnalysisEngine(
            UpdateAnalysisEngineRequest request, StreamObserver<UpdateAnalysisEngineResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAnalysisEngineService.updateAnalysisEngine(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAnalysisEngine(DeleteAnalysisEngineRequest request, StreamObserver<DeleteAnalysisEngineResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAnalysisEngineService.deleteAnalysisEngine(request));
        responseObserver.onCompleted();
    }



    @Override
    @Secured({})
    public void listAnalysisEngines(
            ListAnalysisEnginesRequest request, StreamObserver<ListAnalysisEnginesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAnalysisEngineService.listAnalysisEngines(request));
        responseObserver.onCompleted();
    }
}
