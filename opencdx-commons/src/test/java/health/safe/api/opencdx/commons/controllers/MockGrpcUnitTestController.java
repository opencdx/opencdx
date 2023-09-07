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
package health.safe.api.opencdx.commons.controllers;

import health.safe.api.opencdx.commons.exceptions.OpenCDXUnimplemented;
import health.safe.api.opencdx.grpc.unit.test.UnitTestGrpc;
import health.safe.api.opencdx.grpc.unit.test.UnitTestRequest;
import health.safe.api.opencdx.grpc.unit.test.UnitTestResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
public class MockGrpcUnitTestController extends UnitTestGrpc.UnitTestImplBase {

    @Override
    public void testA(UnitTestRequest request, StreamObserver<UnitTestResponse> responseObserver) {
        throw new OpenCDXUnimplemented("OpenCDX-Common", 1, "JUnit Testing");
    }

    @Override
    public void testB(UnitTestRequest request, StreamObserver<UnitTestResponse> responseObserver) {
        throw new OpenCDXUnimplemented("OpenCDX-Common", 1, "JUnit Testing", new NullPointerException());
    }

    @Override
    public void testC(UnitTestRequest request, StreamObserver<UnitTestResponse> responseObserver) {
        throw new OpenCDXUnimplemented(
                "OpenCDX-Common", 1, "JUnit Testing", new NullPointerException("Junit Testing Null Pointer Exception"));
    }

    @Override
    public void testD(UnitTestRequest request, StreamObserver<UnitTestResponse> responseObserver) {
        throw new RuntimeException("Test");
    }
}
