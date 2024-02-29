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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.grpc.connected.*;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Connected Test
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcConnectedTestController extends HealthcareServiceGrpc.HealthcareServiceImplBase {

    private final OpenCDXConnectedTestService openCDXConnectedTestService;

    /**
     * Constructor using the OpenCDXConnectedTestService
     * @param openCDXConnectedTestService service to use for processing
     */
    @Autowired
    public OpenCDXGrpcConnectedTestController(OpenCDXConnectedTestService openCDXConnectedTestService) {
        this.openCDXConnectedTestService = openCDXConnectedTestService;
    }

    /**
     * Method to submit a connected test submission
     * @param request ConnectedTest submitted.
     * @param responseObserver Observer to process the response
     */
    @Secured({})
    @Override
    public void submitTest(ConnectedTest request, StreamObserver<TestSubmissionResponse> responseObserver) {
        log.trace("Received submitTest");
        responseObserver.onNext(this.openCDXConnectedTestService.submitTest(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to retrieve a connectedtest.
     * @param request Request with the ID to retrieve.
     * @param responseObserver Observer to process the response
     */
    @Secured({})
    @Override
    public void getTestDetailsById(TestIdRequest request, StreamObserver<ConnectedTest> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedTestService.getTestDetailsById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listConnectedTests(
            ConnectedTestListRequest request, StreamObserver<ConnectedTestListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedTestService.listConnectedTests(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest request, StreamObserver<ConnectedTestListByNHIDResponse> responseObserver) {
        responseObserver.onNext(this.openCDXConnectedTestService.listConnectedTestsByNHID(request));
        responseObserver.onCompleted();
    }
}
