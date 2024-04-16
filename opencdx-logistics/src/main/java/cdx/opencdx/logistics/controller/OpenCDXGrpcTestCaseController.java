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
package cdx.opencdx.logistics.controller;

import cdx.opencdx.grpc.data.TestCase;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.logistics.service.OpenCDXTestCaseService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Test Cases
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcTestCaseController extends TestCaseServiceGrpc.TestCaseServiceImplBase {

    private final OpenCDXTestCaseService openCDXTestCaseService;

    /**
     * Seutp the GRPC Test Case Controller
     * @param openCDXTestCaseService TestCase Service for processing
     */
    public OpenCDXGrpcTestCaseController(OpenCDXTestCaseService openCDXTestCaseService) {
        this.openCDXTestCaseService = openCDXTestCaseService;
    }

    @Secured({})
    @Override
    public void getTestCaseById(TestCaseIdRequest request, StreamObserver<TestCase> responseObserver) {
        responseObserver.onNext(this.openCDXTestCaseService.getTestCaseById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void addTestCase(TestCase request, StreamObserver<TestCase> responseObserver) {
        responseObserver.onNext(this.openCDXTestCaseService.addTestCase(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateTestCase(TestCase request, StreamObserver<TestCase> responseObserver) {
        responseObserver.onNext(this.openCDXTestCaseService.updateTestCase(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteTestCase(TestCaseIdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTestCaseService.deleteTestCase(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listTestCase(TestCaseListRequest request, StreamObserver<TestCaseListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXTestCaseService.listTestCase(request));
        responseObserver.onCompleted();
    }
}
