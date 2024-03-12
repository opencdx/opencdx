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
package cdx.opencdx.logistics.service;

import cdx.opencdx.grpc.inventory.*;

/**
 * Device service for device identification
 */
public interface OpenCDXDeviceService {
    /**
     * Method to retrieve a Device
     * @param request ID of device to rerieve
     * @return Device requested
     */
    Device getDeviceById(DeviceIdRequest request);

    /**
     * Method to add a new device to the database.
     * @param request Device data to add
     * @return Device with id.
     */
    Device addDevice(Device request);

    /**
     * Method to update a device
     * @param request updated device data
     * @return the updated device
     */
    Device updateDevice(Device request);

    /**
     * Method to delete a device
     * @param request ID of device to delete
     * @return Response indicating if successful.
     */
    DeleteResponse deleteDevice(DeviceIdRequest request);

    /**
     * Method to get list of devices
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested test case with page, sorting, and page size
     */
    DeviceListResponse listDevices(DeviceListRequest request);
}
