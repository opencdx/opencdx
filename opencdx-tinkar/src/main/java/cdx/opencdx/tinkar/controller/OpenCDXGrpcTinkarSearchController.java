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
package cdx.opencdx.tinkar.controller;

import cdx.opencdx.grpc.tinkar.*;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Tinkar Service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcTinkarSearchController extends TinkarGrpc.TinkarImplBase {

    private final OpenCDXTinkarService openCDXTinkarService;

    /**
     * Constructor taking the OpenCDXTinkarService
     * @param openCDXTinkarService OpenCDXTInkarServer to use
     */
    public OpenCDXGrpcTinkarSearchController(OpenCDXTinkarService openCDXTinkarService) {
        this.openCDXTinkarService = openCDXTinkarService;
    }

    /**
     * Method to search Tinkar DB using a query string
     * @param request TinkarQueryRequest submitted.
     * @param responseObserver Observer to process the response
     */
    @Secured({})
    @Override
    public void searchTinkar(TinkarQueryRequest request, StreamObserver<TinkarQueryResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTinkarService.search(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to search Tinkar DB using a query string
     * @param request ConnectedTest submitted.
     * @param responseObserver Observer to process the response
     */
    @Secured({})
    @Override
    public void getTinkarEntity(TinkarGetRequest request, StreamObserver<TinkarQueryResult> responseObserver) {
        responseObserver.onNext(this.openCDXTinkarService.getEntity(request));
        responseObserver.onCompleted();
    }
}
