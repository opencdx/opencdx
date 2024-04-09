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
 * Interface for communicating with the BPM service.
 */
public interface OpenCDXBPMClient {

    /**
     * Method to gRPC Call BPM createBPM() api.
     * @param request CreateBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    CreateBPMResponse createBPMMeasurement(
            CreateBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call BPM Service getBPM() api.
     * @param request GetBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    GetBPMResponse getBPMMeasurement(
            GetBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call BPM Service updateBPM() api.
     * @param request UpdateBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateBPMResponse updateBPMMeasurement(
            UpdateBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call BPM Service deleteBPM() api.
     * @param request DeleteBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SuccessResponse deleteBPMMeasurement(
            DeleteBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call BPM Service listBPMs() api.
     * @param request ListBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListBPMResponse listBPMMeasurements(
            ListBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
