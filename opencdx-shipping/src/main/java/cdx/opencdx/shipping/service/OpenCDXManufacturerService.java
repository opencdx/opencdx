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
package cdx.opencdx.shipping.service;

import cdx.opencdx.grpc.inventory.*;

/**
 * Manufacturer Service for manufacturer identificaiton
 */
public interface OpenCDXManufacturerService {

    /**
     * Method to retrieve a manufacturer
     * @param request ID of manufacturer
     * @return the Manufacturer
     */
    Manufacturer getManufacturerById(ManufacturerIdRequest request);

    /**
     * Method to add a manufacturer
     * @param request the manufacturer data.
     * @return the Manufacturer
     */
    Manufacturer addManufacturer(Manufacturer request);

    /**
     * Method to update manufacturer
     * @param request Updated manufacturer data
     * @return the Manufacturer
     */
    Manufacturer updateManufacturer(Manufacturer request);

    /**
     * Request to delete a manufacturer
     * @param request ID of manufacturer to delete
     * @return Response indicating if success.
     */
    DeleteResponse deleteManufacturer(ManufacturerIdRequest request);

    /**
     * Method to get list of manufacturer
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested Manufacturer with page, sorting, and page size
     */
    ManufacturersListResponse listManufacturers(ManufacturerListRequest request);
}
