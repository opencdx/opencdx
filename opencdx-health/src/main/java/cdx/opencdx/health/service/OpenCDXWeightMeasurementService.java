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
 * Interface for the OpenCDXWeightMeasurementService
 */
public interface OpenCDXWeightMeasurementService {
    /**
     * Method to create height measurement.
     * @param request CreateWeightMeasurementRequest for measurement.
     * @return CreateWeightMeasurementResponse with measurement.
     */
    CreateWeightMeasurementResponse createWeightMeasurement(CreateWeightMeasurementRequest request);

    /**
     * Method to get height measurement.
     * @param request GetWeightMeasurementResponse for measurement.
     * @return GetWeightMeasurementRequest with measurement.
     */
    GetWeightMeasurementResponse getWeightMeasurement(GetWeightMeasurementRequest request);

    /**
     * Method to update height measurement.
     * @param request UpdateWeightMeasurementRequest for measurement.
     * @return UpdateWeightMeasurementResponse with measurement.
     */
    UpdateWeightMeasurementResponse updateWeightMeasurement(UpdateWeightMeasurementRequest request);

    /**
     * Method to delete height measurement.
     * @param request DeleteWeightMeasurementRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    SuccessResponse deleteWeightMeasurement(DeleteWeightMeasurementRequest request);

    /**
     * Method to list height measurement.
     * @param request ListWeightMeasurementsRequest for measurement.
     * @return ListWeightMeasurementsResponse with measurement.
     */
    ListWeightMeasurementsResponse listWeightMeasurements(ListWeightMeasurementsRequest request);
}
