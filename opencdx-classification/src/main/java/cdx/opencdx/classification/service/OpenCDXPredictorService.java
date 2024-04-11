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
package cdx.opencdx.classification.service;

import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;

/**
 * Interface for the Predictor Service.
 * <p>
 * This service provides methods for various security and protection functionalities.
 * Implementations of this interface handle anomaly detection, authorization control,
 * privacy protection, real-time monitoring, and user behavior analysis.
 */
public interface OpenCDXPredictorService {

    /**
     * Process the Predictor Data.
     *
     * @param request The request containing data for prediction.
     * @return A response message generated for the prediction request.
     */
    PredictorResponse predict(PredictorRequest request);
}
