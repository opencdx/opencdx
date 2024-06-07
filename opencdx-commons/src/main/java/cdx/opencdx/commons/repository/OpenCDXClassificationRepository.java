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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.commons.model.OpenCDXClassificationModel;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for protobuf Classification and OpenCDXClassificationModel.
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXClassificationRepository extends OpenCDXRepository<OpenCDXClassificationModel> {

    /**
     * Find all classification by patient id.
     * @param id identifier.
     * @return List of classification.
     */
    @Query(value = "{ 'patient.id' : ?0 }")
    List<OpenCDXClassificationModel> findAllByPatientId(OpenCDXIdentifier id);
}
