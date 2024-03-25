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
package cdx.opencdx.commons.repository;

import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import io.micrometer.observation.annotation.Observed;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OpenCDX Profile collection
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXProfileRepository extends OpenCDXRepository<OpenCDXProfileModel> {
    /**
     * Method to find a profile by their national health id
     * @param nationalHealthId String containing the national health id to look up for the user.
     * @return Optional OpenCDXProfileModel of the user.
     */
    Optional<OpenCDXProfileModel> findByNationalHealthId(String nationalHealthId);

    /**
     * Method to identify if a profile exists by their national health id
     * @param nationalHealthId String containing the national health id to look up for the user.
     * @return boolean true if the profile exists, false otherwise.
     */
    boolean existsByNationalHealthId(String nationalHealthId);

    /**
     * Method to find a profile by their user id
     * @param userId ObjectId of the user to look up.
     * @return Optional OpenCDXProfileModel of the user.
     */
    Optional<OpenCDXProfileModel> findByUserId(ObjectId userId);
}
