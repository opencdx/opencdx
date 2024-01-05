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
package cdx.opencdx.classification.controller;

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Classification
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcClassificationController extends ClassificationServiceGrpc.ClassificationServiceImplBase {

    /**
     * Constructor using the ClassificationService
     * @param classificationService service to use for processing
     */
    @Autowired
    public OpenCDXGrpcClassificationController(OpenCDXClassificationService classificationService) {}

    /**
     * submitClassification gRPC Service Call
     * @param request Request to process
     * @param responseObserver Observer to process the response
     */
    @Secured({})
    @Override
    public void classify(ClassificationRequest request, StreamObserver<ClassificationResponse> responseObserver) {

        ClassificationResponse response = ClassificationResponse.newBuilder()
                .setMessage("Executed classify operation.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
