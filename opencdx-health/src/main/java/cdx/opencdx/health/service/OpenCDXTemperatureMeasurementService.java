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
 * Interface for the OpenCDXTemperatureMeasurementService
 */
public interface OpenCDXTemperatureMeasurementService {
    /**
     * Method to create temperature measurement.
     * @param request CreateTemperatureMeasurementRequest for measurement.
     * @return CreateTemperatureMeasurementResponse with measurement.
     */
    CreateTemperatureMeasurementResponse createTemperatureMeasurement(CreateTemperatureMeasurementRequest request);

    /**
     * Method to get temperature measurement.
     * @param request GetTemperatureMeasurementResponse for measurement.
     * @return GetTemperatureMeasurementRequest with measurement.
     */
    GetTemperatureMeasurementResponse getTemperatureMeasurement(GetTemperatureMeasurementRequest request);

    /**
     * Method to update temperature measurement.
     * @param request UpdateTemperatureMeasurementRequest for measurement.
     * @return UpdateTemperatureMeasurementResponse with measurement.
     */
    UpdateTemperatureMeasurementResponse updateTemperatureMeasurement(UpdateTemperatureMeasurementRequest request);

    /**
     * Method to delete temperature measurement.
     * @param request DeleteTemperatureMeasurementRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    SuccessResponse deleteTemperatureMeasurement(DeleteTemperatureMeasurementRequest request);

    /**
     * Method to list temperature measurement.
     * @param request ListTemperatureMeasurementsRequest for measurement.
     * @return ListTemperatureMeasurementsResponse with measurement.
     */
    ListTemperatureMeasurementsResponse listTemperatureMeasurements(ListTemperatureMeasurementsRequest request);
}
