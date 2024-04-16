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
package cdx.opencdx.health.service;


import cdx.opencdx.grpc.data.LabFindings;
import cdx.opencdx.grpc.service.health.*;

/**
 * This interface provides the contract for the OpenCDXConnectedLabService.
 * <p>
 * The OpenCDXConnectedLabService is responsible for handling all the connected lab services.
 * </p>
 */
public interface OpenCDXConnectedLabService {

    /**
     * This method is responsible for submitting lab findings.
     *
     * @param request The request object containing the lab findings.
     * @return The response object containing the status of the lab findings submission.
     */
    LabFindingsResponse submitLabFindings(LabFindings request);
    /**
     * This method is responsible for creating a connected lab.
     *
     * @param request The request object containing the connected lab details.
     * @return The response object containing the status of the connected lab creation.
     */
    CreateConnectedLabResponse createConnectedLab(CreateConnectedLabRequest request);
    /**
     * This method is responsible for retrieving a connected lab.
     *
     * @param request The request object containing the connected lab details.
     * @return The response object containing the connected lab details.
     */
    GetConnectedLabResponse getConnectedLab(GetConnectedLabRequest request);
    /**
     * This method is responsible for updating a connected lab.
     *
     * @param request The request object containing the connected lab details.
     * @return The response object containing the status of the connected lab update.
     */
    UpdateConnectedLabResponse updateConnectedLab(UpdateConnectedLabRequest request);
    /**
     * This method is responsible for deleting a connected lab.
     *
     * @param request The request object containing the connected lab details.
     * @return The response object containing the status of the connected lab deletion.
     */
    DeleteConnectedLabResponse deleteConnectedLab(DeleteConnectedLabRequest request);
    /** This method is responsible for listing connected labs.
     *
     * @param request The request object containing the connected lab details.
     * @return The response object containing the list of connected labs.
     */
    ListConnectedLabsResponse listConnectedLabs(ListConnectedLabsRequest request);
}
