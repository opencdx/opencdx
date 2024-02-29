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
package cdx.opencdx.predictor.service.impl;

import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.neural.predictor.PredictorOutput;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.OpenCDXPredictorService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing predictor requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXPredictorServiceImpl implements OpenCDXPredictorService {
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for OpenCDXPredictorServiceImpl.
     *
     * @param openCDXDocumentValidator Document Validator Service.
     */
    @Autowired
    public OpenCDXPredictorServiceImpl(OpenCDXDocumentValidator openCDXDocumentValidator) {

        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    /**
     * Process the Predictor Data.
     *
     * @param request The request containing data for prediction.
     * @return A response message generated for the prediction request.
     */
    @Override
    public PredictorResponse predict(PredictorRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "connected-test", new ObjectId(request.getPredictorInput().getTestId()));

        return PredictorResponse.newBuilder()
                .setPredictorOutput(PredictorOutput.newBuilder()
                        .setPredictedValue("OpenCDXPredictorServiceImpl [predict]")
                        .setEncounterId(request.getPredictorInput().getEncounterId()))
                .build();
    }
}
