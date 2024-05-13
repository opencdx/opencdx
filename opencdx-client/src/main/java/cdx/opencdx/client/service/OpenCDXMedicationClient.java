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
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for communicating with the Medication service.
 */
public interface OpenCDXMedicationClient {
    /**
     * Method to gRPC Call Medication prescribing() api.
     * @param request Medication to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Medication prescribing(Medication request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Medication Service listAllMedications() api.
     * @param request EndMedicationRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Medication ending(EndMedicationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Medication Service listAllMedications() api.
     * @param request UpdateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListMedicationsResponse listAllMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Medication Service listCurrentMedications() api.
     * @param request ListMedicationsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListMedicationsResponse listCurrentMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Medication Service searchMedications() api.
     * @param request SearchMedicationsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ListMedicationsResponse searchMedications(
            SearchMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
