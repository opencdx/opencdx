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
import cdx.opencdx.logistics.model.OpenCDXManufacturerModel;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Repository;

/**
 * Repository for protobuf Manufacturer
 */
@Repository
@Observed(name = "opencdx")
@SuppressWarnings("java:S100")
public interface OpenCDXManufacturerRepository extends OpenCDXRepository<OpenCDXManufacturerModel> {
    /**
     * Method to determine if a country is used in an address of an entity.
     * @param countryId Country ID to search for.
     * @return Boolean indicating if found.
     */
    Boolean existsByAddress_CountryId(OpenCDXIdentifier countryId);
}
