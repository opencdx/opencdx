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


import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.service.health.EndMedicationRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsResponse;
import cdx.opencdx.grpc.service.health.SearchMedicationsRequest;

/**
 * Service for Medication
 */
public interface OpenCDXMedicationService {
    /**
     * Method to prescribe a medication
     * @param request Request for the medication to prescribe.
     * @return Response with the prescription.
     */
    Medication prescribing(Medication request);
    /**
     * Method to end a medication
     * @param request Request for the medication to end.
     * @return Response indicating if successful.
     */
    Medication ending(EndMedicationRequest request);
    /**
     * Method to list all medications for a patient
     * @param request Request for the patient's medications.
     * @return Response with the patient's medications.
     */
    ListMedicationsResponse listAllMedications(ListMedicationsRequest request);
    /**
     * Method to list current medications for a patient
     * @param request Request for the patient's current medications.
     * @return Response with the patient's current medications.
     */
    ListMedicationsResponse listCurrentMedications(ListMedicationsRequest request);
    /**
     * Method to search for medications
     * @param request Request for the medications to search for.
     * @return Response with the medications found.
     */
    ListMedicationsResponse searchMedications(SearchMedicationsRequest request);
}
