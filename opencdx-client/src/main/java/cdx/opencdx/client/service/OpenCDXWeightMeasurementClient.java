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
 * Interface for communicating with the Weight service.
 */
public interface OpenCDXWeightMeasurementClient {

    /**
     * Method to gRPC Call weight measurement createWeightMeasurement() api.
     * @param request CreateWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    CreateWeightMeasurementResponse createWeightMeasurement(
            CreateWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Weight Service getWeightMeasurement() api.
     * @param request GetWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    GetWeightMeasurementResponse getWeightMeasurement(
            GetWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Weight Service updateWeightMeasurement() api.
     * @param request UpdateWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateWeightMeasurementResponse updateWeightMeasurement(
            UpdateWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Weight Service deleteWeightMeasurement() api.
     * @param request DeleteWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SuccessResponse deleteWeightMeasurement(
            DeleteWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Weight Service listWeightMeasurements() api.
     * @param request ListWeightMeasurementsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListWeightMeasurementsResponse listWeightMeasurements(
            ListWeightMeasurementsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
