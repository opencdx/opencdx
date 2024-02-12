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
package cdx.opencdx.anf.controller;

import cdx.opencdx.anf.service.OpenCDXANFService;
import cdx.opencdx.grpc.anf.ANFServiceGrpc;
import cdx.opencdx.grpc.anf.AnfStatement;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Hello World
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcANFController extends ANFServiceGrpc.ANFServiceImplBase {

    private final OpenCDXANFService openCDXANFService;

    /**
     * Constructor using the HelloworldService
     * @param openCDXANFService service to use for processing
     */
    @Autowired
    public OpenCDXGrpcANFController(OpenCDXANFService openCDXANFService) {
        this.openCDXANFService = openCDXANFService;
    }

    @Secured({})
    @Override
    public void createANFStatement(
            AnfStatement.ANFStatement request, StreamObserver<AnfStatement.Identifier> responseObserver) {
        log.info("Received request to create ANF Statement");
        responseObserver.onNext(this.openCDXANFService.createANFStatement(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getANFStatement(
            AnfStatement.Identifier request, StreamObserver<AnfStatement.ANFStatement> responseObserver) {
        log.info("Received request to get ANF Statement");
        responseObserver.onNext(this.openCDXANFService.getANFStatement(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateANFStatement(
            AnfStatement.ANFStatement request, StreamObserver<AnfStatement.Identifier> responseObserver) {
        log.info("Received request to update ANF Statement");
        responseObserver.onNext(this.openCDXANFService.updateANFStatement(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteANFStatement(
            AnfStatement.Identifier request, StreamObserver<AnfStatement.Identifier> responseObserver) {
        log.info("Received request to delete ANF Statement");
        responseObserver.onNext(this.openCDXANFService.deleteANFStatement(request));
        responseObserver.onCompleted();
    }
}
