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
package cdx.opencdx.connected.test.repository;

import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for persisting Device entities.
 */
@SuppressWarnings("java:S100")
public interface OpenCDXDeviceRepository extends MongoRepository<OpenCDXDeviceModel, ObjectId> {
    /**
     * Determine if an entity had a Country code
     * @param manufacturerCountryId Country code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByManufacturerCountryId(ObjectId manufacturerCountryId);
    /**
     * Determine if an entity had a Country code
     * @param vendorCountryId Country code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByVendorCountryId(ObjectId vendorCountryId);
    /**
     * Determine if an entity had a Country code
     * @param vendorId Vendor to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByVendorId(ObjectId vendorId);
    /**
     * Determine if an entity had a Country code
     * @param manfacturerId Manufacturer code to look for
     * @return Boolean indicating if successful
     */
    Boolean existsByManufacturerId(ObjectId manfacturerId);
}
