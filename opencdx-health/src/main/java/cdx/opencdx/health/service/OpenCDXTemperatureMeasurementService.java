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
