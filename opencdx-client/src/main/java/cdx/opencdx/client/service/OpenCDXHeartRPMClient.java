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
import cdx.opencdx.grpc.health.*;

/**
 * Interface for communicating with the HeartRPM service.
 */
public interface OpenCDXHeartRPMClient {
    /**
     * Method to gRPC Call HeartRPM createHeartRPM() api.
     * @param request CreateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    CreateHeartRPMResponse createHeartRPMMeasurement(
            CreateHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call HeartRPM Service getHeartRPM() api.
     * @param request GetHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    GetHeartRPMResponse getHeartRPMMeasurement(
            GetHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call HeartRPM Service updateHeartRPM() api.
     * @param request UpdateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateHeartRPMResponse updateHeartRPMMeasurement(
            UpdateHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call HeartRPM Service deleteHeartRPM() api.
     * @param request DeleteHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SuccessResponse deleteHeartRPMMeasurement(
            DeleteHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call HeartRPM Service listHeartRPMs() api.
     * @param request ListHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListHeartRPMResponse listHeartRPMMeasurements(
            ListHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;
}
