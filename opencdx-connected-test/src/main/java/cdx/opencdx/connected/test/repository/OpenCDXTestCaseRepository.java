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
package cdx.opencdx.connected.test.repository;

import cdx.opencdx.connected.test.model.OpenCDXTestCaseModel;
import io.micrometer.observation.annotation.Observed;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for protobuf Testcase
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXTestCaseRepository extends MongoRepository<OpenCDXTestCaseModel, ObjectId> {
    /**
     * Determine if en entity has a manufacturer ID
     * @param manufacturerId Mandufacturer ID to search for
     * @return Boolean indicating if found.
     */
    Boolean existsByManufacturerId(ObjectId manufacturerId);

    /**
     * Determine if an entity has a vendor ID
     * @param vendorId Vendor ID to search for
     * @return Boolean indicating if found.
     */
    Boolean existsByVendorId(ObjectId vendorId);
}
