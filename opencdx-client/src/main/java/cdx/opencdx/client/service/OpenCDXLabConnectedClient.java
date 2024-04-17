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
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the Lab Connected Service gRPC Client.
 */
public interface OpenCDXLabConnectedClient {

    /**
     * Submit lab findings to the connected lab service.
     * @param labFindings The lab findings to submit.
     * @param openCDXCallCredentials The credentials for the call.
     * @return The response from the connected lab service.
     */
    LabFindingsResponse submitLabFindings(LabFindings labFindings, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Get lab findings from the connected lab service.
     * @param request The request to create the connected lab.
     * @param openCDXCallCredentials The credentials for the call.
     * @return The response from the connected lab service.
     */
    CreateConnectedLabResponse createConnectedLab(
            CreateConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Get lab findings from the connected lab service.
     * @param request The request get the connected lab.
     * @param openCDXCallCredentials The credentials for the call.
     * @return The response from the connected lab service.
     */
    GetConnectedLabResponse getConnectedLab(
            GetConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Get lab findings from the connected lab service.
     * @param request The request update the connected lab.
     * @param openCDXCallCredentials The credentials for the call.
     * @return The response from the connected lab service.
     */
    UpdateConnectedLabResponse updateConnectedLab(
            UpdateConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Get lab findings from the connected lab service.
     * @param request The request to delete the connected lab.
     * @param openCDXCallCredentials The credentials for the call.
     * @return The response from the connected lab service.
     */
    DeleteConnectedLabResponse deleteConnectedLab(
            DeleteConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
