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
package cdx.opencdx.health.repository;

import cdx.opencdx.health.model.OpenCDXMedicationModel;
import io.micrometer.observation.annotation.Observed;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the Database medication model.
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXMedicationRepository extends MongoRepository<OpenCDXMedicationModel, ObjectId> {

    /**
     * Find all medications by patient id.
     * @param patientId the patient id
     * @return the Page of medications
     */
    Page<OpenCDXMedicationModel> findAllByPatientId(ObjectId patientId, Pageable pageable);
    /**
     * Find all medications by national health id.
     * @param nationalHealthId the national health id
     * @return the Page of medications
     */
    Page<OpenCDXMedicationModel> findAllByNationalHealthId(String nationalHealthId, Pageable pageable);
    /**
     * Find all medications by patient id and end date is null.
     * @param patientId the patient id
     * @return the Page of medications
     */
    Page<OpenCDXMedicationModel> findAllByPatientIdAndEndDateIsNull(ObjectId patientId, Pageable pageable);
    /**
     * Find all medications by national health id and end date is null.
     * @param nationalHealthId the national health id
     * @return the Page of medications
     */
    Page<OpenCDXMedicationModel> findAllByNationalHealthIdAndEndDateIsNull(String nationalHealthId, Pageable pageable);
}