/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package cdx.opencdx.connected.test.service;

import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;

/**
 * Vendor service for vendor identification
 */
public interface OpenCDXVendorService {

    /**
     * Method to retrive a vendor by ID
     * @param request ID to vendor to retrive
     * @return Vendor with the associated ID
     */
    Vendor getVendorById(VendorIdRequest request);

    /**
     * Method to add a new vendor to the database.
     * @param request Vendor data to add
     * @return The vendor with it's assigned id.
     */
    Vendor addVendor(Vendor request);

    /**
     * Update the records of the vendor
     * @param request Updated vendor data
     * @return the updated Vendor.
     */
    Vendor updateVendor(Vendor request);

    /**
     * Method to delete a vendor
     * @param request ID of the vendor to delete
     * @return Response indicating if successful.
     */
    DeleteResponse deleteVendor(VendorIdRequest request);
}
