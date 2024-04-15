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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.service.health.PredictorRequest;
import cdx.opencdx.grpc.service.health.PredictorResponse;


/**
 * Interface for communicating with the Predictor microservice.
 */
public interface OpenCDXPredictorClient {
    /**
     * Method to gRPC Call Neural Predictor Service predict() api.
     * @param request Predictor request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Predictor Response.
     */
    PredictorResponse predict(PredictorRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
