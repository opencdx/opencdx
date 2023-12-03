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
package cdx.opencdx.protector.controller;

import cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest;
import cdx.opencdx.grpc.neural.protector.NeuralProtectorServiceGrpc;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest;
import cdx.opencdx.grpc.neural.protector.SecurityResponse;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest;
import cdx.opencdx.protector.service.ProtectorService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Protector World
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class GrpcProtectorController extends NeuralProtectorServiceGrpc.NeuralProtectorServiceImplBase {

    private final ProtectorService protectorService;

    /**
     * Constructor using the protectorService
     * @param protectorService service to use for processing
     */
    @Autowired
    public GrpcProtectorController(ProtectorService protectorService) {
        this.protectorService = protectorService;
    }

    /**
     * gRPC Service Call to createRoutine
     * @param request Request to process
     * @param responseObserver Observer to process the response
     */
    @Override
    @Secured({})
    public void detectAnomalies(
            AnomalyDetectionDataRequest request, StreamObserver<SecurityResponse> responseObserver) {
        log.info("Received request to analyze Detection data: {}", request);
        log.info("Returning detectAnomalies response: {}", protectorService.detectAnomalies(request));
        responseObserver.onNext(protectorService.detectAnomalies(request));
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void enforceAuthorizationControl(
            AuthorizationControlDataRequest request, StreamObserver<SecurityResponse> responseObserver) {
        log.info("Received request to enforce Authorization data: {}", request);
        log.info(
                "Returning enforceAuthorizationControl response: {}",
                protectorService.enforceAuthorizationControl(request));
        responseObserver.onNext(protectorService.enforceAuthorizationControl(request));
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void protectPrivacy(
            PrivacyProtectionDataRequest request, StreamObserver<SecurityResponse> responseObserver) {
        log.info("Received request to protect Privacy data: {}", request);
        SecurityResponse response = protectorService.protectPrivacy(request);
        log.info("Returning protectPrivacy response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void monitorRealTimeActivity(
            RealTimeMonitoringDataRequest request, StreamObserver<SecurityResponse> responseObserver) {
        log.info("Received request to monitor Realtime data: {}", request);
        SecurityResponse response = protectorService.monitorRealTimeActivity(request);
        log.info("Returning monitorRealTimeActivity response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Secured({})
    public void analyzeUserBehavior(
            UserBehaviorAnalysisDataRequest request, StreamObserver<SecurityResponse> responseObserver) {
        log.info("Received request to analyze data: {}", request);
        SecurityResponse response = protectorService.analyzeUserBehavior(request);
        log.info("Returning analyzeUserBehavior response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
