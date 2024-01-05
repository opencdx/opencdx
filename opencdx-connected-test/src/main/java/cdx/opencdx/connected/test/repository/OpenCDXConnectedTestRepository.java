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

import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import io.micrometer.observation.annotation.Observed;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for protobuf ConnectedTest and OpenCDXConnectedTest.
 */
@Repository
@Observed(name = "opencdx")
@SuppressWarnings("java:S100")
public interface OpenCDXConnectedTestRepository extends MongoRepository<OpenCDXConnectedTestModel, ObjectId> {
    /**
     * Lookup ConnectedTests for a user.
     * @param userId User to lookup
     * @param pageable Pageable information to pull only required tests
     * @return Page information for the returned tests.
     */
    Page<OpenCDXConnectedTestModel> findAllByUserId(ObjectId userId, Pageable pageable);

    /**
     * Lookup ConnectedTests by national health id
     * @param nationalHealthId to lookup
     * @param pageable Pageable information to pull only required tests
     * @return Page information for the returned tests.
     */
    Page<OpenCDXConnectedTestModel> findAllByNationalHealthId(Integer nationalHealthId, Pageable pageable);
}
