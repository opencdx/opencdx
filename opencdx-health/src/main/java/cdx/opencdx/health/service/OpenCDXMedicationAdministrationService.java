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
package cdx.opencdx.health.service;

import cdx.opencdx.grpc.health.medication.*;

/**
 * Service for Medication Administration
 */
public interface OpenCDXMedicationAdministrationService {

    /**
     * Method to track when medication is given to a patient.
     * @param request Request for the medication administration.
     * @return Response with the medication administration.
     */
    MedicationAdministration trackMedicationAdministration(MedicationAdministration request);
    /**
     * Method to get medication information by ID.
     * @param request Request for the medication by ID.
     * @return Response with the medication.
     */
    Medication getMedicationById(GetMedicationByIdRequest request);
    /**
     * Method to get medication information by patient ID within a date range.
     * @param request Request for the medication by patient ID.
     * @return Response with the list of medication.
     */
    ListMedicationsResponse listMedications(ListMedicationsRequest request);
}
