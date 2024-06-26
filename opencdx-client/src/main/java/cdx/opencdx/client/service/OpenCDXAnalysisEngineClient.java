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
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for communicating with the Temperature service.
 */
public interface OpenCDXAnalysisEngineClient {

    /**
     * Method to gRPC Call temperature measurement createTemperatureMeasurement() api.
     * @param request CreateTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    CreateAnalysisEngineResponse createAnalysisEngine(
            CreateAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Temperature Service getAnalysisEngine() api.
     * @param request GetAnalysisEngineRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    GetAnalysisEngineResponse getAnalysisEngine(
            GetAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Temperature Service updateAnalysisEngine() api.
     * @param request UpdateAnalysisEngineRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateAnalysisEngineResponse updateAnalysisEngine(
            UpdateAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Temperature Service deleteAnalysisEngine() api.
     * @param request DeleteAnalysisEngineRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SuccessResponse deleteAnalysisEngine(
            DeleteAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Temperature Service listAnalysisEngines() api.
     * @param request ListAnalysisEnginesRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListAnalysisEnginesResponse listAnalysisEngines(
            ListAnalysisEnginesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
