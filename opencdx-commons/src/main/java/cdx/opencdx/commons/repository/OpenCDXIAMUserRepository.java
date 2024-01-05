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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import io.micrometer.observation.annotation.Observed;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for IAM User collection
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXIAMUserRepository extends MongoRepository<OpenCDXIAMUserModel, ObjectId> {
    /**
     * Method to find a user by their email address
     * @param username String containing the email address to look up for the user.
     * @return Optional OpenCDXIAMUserModel of the user.
     */
    @Cacheable(value = "findByUsername", key = "#username")
    Optional<OpenCDXIAMUserModel> findByUsername(String username);

    /**
     * Method to find a user by their email address
     * @param objectId ObjectId of the user to look up.
     * @return Optional OpenCDXIAMUserModel of the user.
     */
    @Override
    @Cacheable(value = "findById", key = "#objectId")
    Optional<OpenCDXIAMUserModel> findById(ObjectId objectId);

    /**
     * Method to find a user by their email address
     * @param objectId ObjectId of the user to look up.
     * @return Optional OpenCDXIAMUserModel of the user.
     */
    @Override
    @Cacheable(value = "existsById", key = "#objectId")
    boolean existsById(ObjectId objectId);

    @Override
    @Caching(
            evict = {
                @CacheEvict(value = "findByUsername", key = "#entity.username"),
                @CacheEvict(value = "user-details", key = "#entity.username"),
                @CacheEvict(value = "existsById", key = "#entity.id"),
                @CacheEvict(value = "findById", key = "#entity.id")
            })
    <S extends OpenCDXIAMUserModel> S save(S entity);
}
