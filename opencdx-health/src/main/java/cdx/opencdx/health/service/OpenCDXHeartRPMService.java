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


import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the OpenCDXHeartRPMService
 */
public interface OpenCDXHeartRPMService {
    /**
     * Method to create HeartRPM measurement.
     * @param request CreateHeartRPMRequest for measurement.
     * @return CreateHeartRPMResponse with measurement.
     */
    CreateHeartRPMResponse createHeartRPMMeasurement(CreateHeartRPMRequest request);

    /**
     * Method to get HeartRPM measurement.
     * @param request GetHeartRPMRequest for measurement.
     * @return GetHeartRPMResponse with measurement.
     */
    GetHeartRPMResponse getHeartRPMMeasurement(GetHeartRPMRequest request);

    /**
     * Method to update HeartRPM measurement.
     * @param request UpdateHeartRPMRequest for measurement.
     * @return UpdateHeartRPMResponse with measurement.
     */
    UpdateHeartRPMResponse updateHeartRPMMeasurement(UpdateHeartRPMRequest request);

    /**
     * Method to delete HeartRPM measurement.
     * @param request DeleteHeartRPMRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    SuccessResponse deleteHeartRPMMeasurement(DeleteHeartRPMRequest request);

    /**
     * Method to list HeartRPM measurement.
     * @param request ListHeartRPMRequest for measurement.
     * @return ListHeartRPMResponse with measurement.
     */
    ListHeartRPMResponse listHeartRPMMeasurements(ListHeartRPMRequest request);
}
