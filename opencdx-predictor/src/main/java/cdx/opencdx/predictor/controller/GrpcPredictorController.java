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
package cdx.opencdx.predictor.controller;

import cdx.opencdx.grpc.neural.predictor.NeuralPredictorServiceGrpc.NeuralPredictorServiceImplBase;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.PredictorService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for predictor World
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class GrpcPredictorController extends NeuralPredictorServiceImplBase {

    private final PredictorService predictorService;

    /**
     * Constructor using the protectorService
     * @param predictorService service to use for processing
     */
    @Autowired
    public GrpcPredictorController(PredictorService predictorService) {
        this.predictorService = predictorService;
    }

    /**
     * gRPC Service Call to predict outcome
     * @param request Request to process
     * @param responseObserver Observer to process the response
     */
    @Override
    @Secured({})
    public void predict(PredictorRequest request, StreamObserver<PredictorResponse> responseObserver) {
        log.info("Received request to analyze Detection data: {}", request);
        log.info("Returning predict response: {}", predictorService.predict(request));
        responseObserver.onNext(predictorService.predict(request));
        responseObserver.onCompleted();
    }
}
