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
package cdx.opencdx.logistics.repository;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.logistics.model.OpenCDXDeviceModel;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository for persisting Device entities.
 */
@SuppressWarnings("java:S100")
@Observed(name = "opencdx")
public interface OpenCDXDeviceRepository extends OpenCDXRepository<OpenCDXDeviceModel> {
    /**
     * Determine if an entity had a Country code
     * @param manufacturerCountryId Country code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByManufacturerCountryId(OpenCDXIdentifier manufacturerCountryId);
    /**
     * Determine if an entity had a Country code
     * @param vendorCountryId Country code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByVendorCountryId(OpenCDXIdentifier vendorCountryId);
    /**
     * Determine if an entity had a Country code
     * @param vendorId Vendor to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByVendorId(OpenCDXIdentifier vendorId);
    /**
     * Determine if an entity had a Country code
     * @param manfacturerId Manufacturer code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByManufacturerId(OpenCDXIdentifier manfacturerId);
    /**
     * Find all manufacturer for a device.
     * @param manufacturerId The manufacturerId.
     * @param pageable The page to return.
     * @return The devices.
     */
    Page<OpenCDXDeviceModel> findAllByManufacturerId(OpenCDXIdentifier manufacturerId, Pageable pageable);
    /**
     * Find all vendor for a device.
     * @param vendorId The vendorId.
     * @param pageable The page to return.
     * @return The devices
     */
    Page<OpenCDXDeviceModel> findAllByVendorId(OpenCDXIdentifier vendorId, Pageable pageable);
}
