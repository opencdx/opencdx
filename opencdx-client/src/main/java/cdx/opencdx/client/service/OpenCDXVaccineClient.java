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
import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.GetVaccineByIdRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesResponse;

/**
 * Interface for vaccine administration
 */
public interface OpenCDXVaccineClient {
    /**
     * Method to track when vaccine is given to a patient.
     * @param request Request for the vaccine administration.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the vaccine administration.
     */
    Vaccine trackVaccineAdministration(Vaccine request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to get vaccine information by ID.
     * @param request Request for the medication by ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the vaccine.
     */
    Vaccine getVaccineById(GetVaccineByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to get vaccine information by patient ID within a date range.
     * @param request Request for the medication by patient ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of medication.
     */
    ListVaccinesResponse listVaccines(ListVaccinesRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
