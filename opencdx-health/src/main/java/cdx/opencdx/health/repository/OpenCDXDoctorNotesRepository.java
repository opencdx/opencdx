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
import cdx.opencdx.health.model.OpenCDXDoctorNotesModel;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Interface for the Database Doctor Notes model.
 */
@Repository
@Observed(name = "opencdx")
@SuppressWarnings("java:S125")
public interface OpenCDXDoctorNotesRepository extends OpenCDXRepository<OpenCDXDoctorNotesModel> {
    /**
     * Find all Doctor Notes by patient id and tags.
     * @param patientId the patient id
     * @param tags the tags
     * @param pageable Pageable for pagination
     * @return the list of doctor notes
     */
    Page<OpenCDXDoctorNotesModel> findAllByPatientIdAndTags(
            OpenCDXIdentifier patientId, String tags, Pageable pageable);

    /**
     * Find all Doctor Notes by Patient ID and Date Range
     * @param patientId the Patient ID
     * @param startDate The range start date
     * @param endDate The range end date
     * @param pageable Pageable for pagination
     * @return the list of doctor notes
     */
    Page<OpenCDXDoctorNotesModel> findAllByPatientIdAndNoteDatetimeBetween(
            OpenCDXIdentifier patientId, Instant startDate, Instant endDate, Pageable pageable);

    /**
     * Find all doctor notes by Patient ID, Tags and Date Range.
     * @param patientId the Patient ID
     * @param tags The tag
     * @param startDate The range start date
     * @param endDate The range end date
     * @param pageable Pageable for pagination
     * @return the list of doctor notes
     */
    Page<OpenCDXDoctorNotesModel> findAllByPatientIdAndTagsContainingAndNoteDatetimeBetween(
            OpenCDXIdentifier patientId, String tags, Instant startDate, Instant endDate, Pageable pageable);

    /**
     * Find all Doctor Notes by patient id.
     * @param patientId the Patient ID
     * @param pageable Pageable for pagination
     * @return the list of bpm
     */
    Page<OpenCDXDoctorNotesModel> findAllByPatientId(OpenCDXIdentifier patientId, Pageable pageable);
}
