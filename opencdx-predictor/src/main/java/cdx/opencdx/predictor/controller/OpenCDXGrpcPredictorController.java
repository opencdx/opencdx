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

import cdx.opencdx.grpc.neural.predictor.NeuralPredictorServiceGrpc;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.OpenCDXPredictorService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Predictor World.
 *
 * This controller provides gRPC service implementations for the Neural Predictor service,
 * offering functionalities such as making predictions.
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcPredictorController extends NeuralPredictorServiceGrpc.NeuralPredictorServiceImplBase {

    private final OpenCDXPredictorService predictorService;

    /**
     * Constructor using the predictorService.
     *
     * @param predictorService Service to use for processing.
     */
    @Autowired
    public OpenCDXGrpcPredictorController(OpenCDXPredictorService predictorService) {
        this.predictorService = predictorService;
    }

    /**
     * gRPC Service Call to predict.
     *
     * @param request           Request to process.
     * @param responseObserver  Observer to process the response.
     */
    @Override
    @Secured({})
    public void predict(PredictorRequest request, StreamObserver<PredictorResponse> responseObserver) {
        log.info("Received request to analyze Prediction data: {}", request);
        PredictorResponse response = predictorService.predict(request);
        log.info("Returning predict response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
