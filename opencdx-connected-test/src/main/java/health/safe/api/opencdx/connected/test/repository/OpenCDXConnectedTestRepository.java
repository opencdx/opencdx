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
package health.safe.api.opencdx.connected.test.repository;

import health.safe.api.opencdx.connected.test.model.OpenCDXConnectedTest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for protobuf ConnectedTest and OpenCDXConnectedTest.
 */
@SuppressWarnings("java:S100")
public interface OpenCDXConnectedTestRepository extends MongoRepository<OpenCDXConnectedTest, ObjectId> {
    /**
     * Lookup ConnectedTests for a user.
     * @param userId User to lookup
     * @param pageable Pageable information to pull only required tests
     * @return Page information for the returned tests.
     */
    Page<OpenCDXConnectedTest> findAllByBasicInfo_UserId(ObjectId userId, Pageable pageable);
}
