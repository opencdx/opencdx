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
import cdx.opencdx.logistics.model.OpenCDXVendorModel;
import io.micrometer.observation.annotation.Observed;

/**
 * Repository for Mongo Vendor documents
 */
@SuppressWarnings("java:S100")
@Observed(name = "opencdx")
public interface OpenCDXVendorRepository extends OpenCDXRepository<OpenCDXVendorModel> {
    /**
     * Determine if a country is used in the address of a vendor
     * @param countryId String of the vendor id
     * @return Boolean indicating if found.
     */
    Boolean existsByAddress_CountryId(OpenCDXIdentifier countryId);
}
