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
package health.safe.api.opencdx.tinkar.controller;

import health.safe.api.opencdx.grpc.tinkar.TinkarGrpc;
import health.safe.api.opencdx.grpc.tinkar.TinkarReply;
import health.safe.api.opencdx.grpc.tinkar.TinkarRequest;
import health.safe.api.opencdx.tinkar.service.TinkarService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Tinkar
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx-tinkar")
public class GrpcTinkarController extends TinkarGrpc.TinkarImplBase {

    private final TinkarService tinkarService;

    /**
     * Constructor with the TinkcarServer
     * @param tinkarService service to use for processing
     */
    @Autowired
    public GrpcTinkarController(TinkarService tinkarService) {
        this.tinkarService = tinkarService;
    }

    /**
     * sayTinkar gRPC Service Call
     * @param request Request to process
     * @param responseObserver Observer to process the response
     */
    @Override
    public void sayTinkar(TinkarRequest request, StreamObserver<TinkarReply> responseObserver) {

        TinkarReply reply = TinkarReply.newBuilder()
                .setMessage(this.tinkarService.sayTinkar(request))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
