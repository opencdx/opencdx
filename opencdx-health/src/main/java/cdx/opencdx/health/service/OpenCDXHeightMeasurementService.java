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
 * Interface for the OpenCDXHeightMeasurementService
 */
public interface OpenCDXHeightMeasurementService {
    /**
     * Method to create height measurement.
     * @param request CreateHeightMeasurementRequest for measurement.
     * @return CreateHeightMeasurementResponse with measurement.
     */
    CreateHeightMeasurementResponse createHeightMeasurement(CreateHeightMeasurementRequest request);

    /**
     * Method to get height measurement.
     * @param request GetHeightMeasurementResponse for measurement.
     * @return GetHeightMeasurementRequest with measurement.
     */
    GetHeightMeasurementResponse getHeightMeasurement(GetHeightMeasurementRequest request);

    /**
     * Method to update height measurement.
     * @param request UpdateHeightMeasurementRequest for measurement.
     * @return UpdateHeightMeasurementResponse with measurement.
     */
    UpdateHeightMeasurementResponse updateHeightMeasurement(UpdateHeightMeasurementRequest request);

    /**
     * Method to delete height measurement.
     * @param request DeleteHeightMeasurementRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    SuccessResponse deleteHeightMeasurement(DeleteHeightMeasurementRequest request);

    /**
     * Method to list height measurement.
     * @param request ListHeightMeasurementsRequest for measurement.
     * @return ListHeightMeasurementsResponse with measurement.
     */
    ListHeightMeasurementsResponse listHeightMeasurements(ListHeightMeasurementsRequest request);
}
