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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.grpc.health.medication.*;

/**
 * Interface for medication administration
 */
public interface OpenCDXMedicationAdministrationClient {
    /**
     * Method to track when medication is given to a patient.
     * @param request Request for the medication administration.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the medication administration.
     */
    MedicationAdministration trackMedicationAdministration(
            MedicationAdministration request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to get medication information by ID.
     * @param request Request for the medication by ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the medication.
     */
    Medication getMedicationById(GetMedicationByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to get medication information by patient ID within a date range.
     * @param request Request for the medication by patient ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of medication.
     */
    ListMedicationsResponse listMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
