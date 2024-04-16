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
 * Interface for the OpenCDXBPMService
 */
public interface OpenCDXBPMService {
    /**
     * Method to create BPM measurement.
     * @param request CreateBPMRequest for measurement.
     * @return CreateBPMResponse with measurement.
     */
    CreateBPMResponse createBPMMeasurement(CreateBPMRequest request);

    /**
     * Method to get BPM measurement.
     * @param request GetBPMRequest for measurement.
     * @return GetBPMResponse with measurement.
     */
    GetBPMResponse getBPMMeasurement(GetBPMRequest request);

    /**
     * Method to update BPM measurement.
     * @param request UpdateBPMRequest for measurement.
     * @return UpdateBPMResponse with measurement.
     */
    UpdateBPMResponse updateBPMMeasurement(UpdateBPMRequest request);

    /**
     * Method to delete BPM measurement.
     * @param request DeleteBPMRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    SuccessResponse deleteBPMMeasurement(DeleteBPMRequest request);

    /**
     * Method to list BPM measurement.
     * @param request ListBPMRequest for measurement.
     * @return ListBPMResponse with measurement.
     */
    ListBPMResponse listBPMMeasurements(ListBPMRequest request);
}
