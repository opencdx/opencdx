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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.health.model.OpenCDXVaccineModel;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Interface for the Database vaccine administration model.
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXVaccineRepository extends OpenCDXRepository<OpenCDXVaccineModel> {
    /**
     * Find all vaccines by patient id.
     * @param patientId the patient id
     * @param pageable the pageable
     * @return the Page of vaccines
     */
    Page<OpenCDXVaccineModel> findAllByPatientId(OpenCDXIdentifier patientId, Pageable pageable);
    /**
     * Find all medications by national health id.
     * @param nationalHealthId the national health id
     * @param pageable the pageable
     * @return the Page of vaccines
     */
    Page<OpenCDXVaccineModel> findAllByNationalHealthId(String nationalHealthId, Pageable pageable);
}
