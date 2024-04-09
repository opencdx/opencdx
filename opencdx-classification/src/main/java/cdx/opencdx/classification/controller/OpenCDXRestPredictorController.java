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

import cdx.opencdx.classification.service.OpenCDXPredictorService;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the /predictor API's.
 * <p>
 * This controller handles REST API endpoints related to the Predictor service,
 * providing functionalities for making predictions.
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestPredictorController {

    private final OpenCDXPredictorService predictorService;

    /**
     * Constructor that takes a predictorService.
     *
     * @param predictorService Service for processing predictor requests.
     */
    @Autowired
    public OpenCDXRestPredictorController(OpenCDXPredictorService predictorService) {
        this.predictorService = predictorService;
    }

    /**
     * POST /predictor/predict API.
     * <p>
     * Endpoint for processing prediction requests.
     *
     * @param request PredictorRequest indicating data to be predicted.
     * @return ResponseEntity with a PredictorResponse containing the prediction.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PredictorResponse> postPredict(@RequestBody PredictorRequest request) {
        return new ResponseEntity<>(
                PredictorResponse.newBuilder()
                        .setPredictorOutput(predictorService.predict(request).getPredictorOutput())
                        .build(),
                HttpStatus.OK);
    }
}
