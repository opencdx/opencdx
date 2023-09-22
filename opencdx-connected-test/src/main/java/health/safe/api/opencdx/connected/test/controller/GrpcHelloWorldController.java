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
package health.safe.api.opencdx.connected.test.controller;

import health.safe.api.opencdx.connected.test.service.HelloWorldService;
import health.safe.api.opencdx.grpc.helloworld.GreeterGrpc;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Hello World
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class GrpcHelloWorldController extends GreeterGrpc.GreeterImplBase {

    private final HelloWorldService helloWorldService;

    /**
     * Constructor using the HelloworldService
     * @param helloWorldService service to use for processing
     */
    @Autowired
    public GrpcHelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    /**
     * sayHello gRPC Service Call
     * @param request Request to process
     * @param responseObserver Observer to process the response
     */
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        HelloReply reply = HelloReply.newBuilder()
                .setMessage(this.helloWorldService.sayHello(request))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
