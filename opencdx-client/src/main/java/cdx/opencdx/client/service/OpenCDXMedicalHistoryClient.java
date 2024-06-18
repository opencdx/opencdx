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
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.DeleteMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesRequest;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesResponse;
import cdx.opencdx.grpc.service.health.SuccessResponse;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryResponse;

/**
 * Interface for vaccine administration
 */
public interface OpenCDXMedicalHistoryClient {
    /**
     * Method to create MedicalHistory.
     * @param request Request for the MedicalHistory administration.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the MedicalHistory administration.
     */
    CreateMedicalHistoryResponse createMedicalHistory(
            CreateMedicalHistoryRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to get MedicalHistory.
     * @param request GetMedicalHistoryRequest for Medical History
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return GetMedicalHistoryResponse
     */
    GetMedicalHistoryResponse getMedicalHistory(
            GetMedicalHistoryRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to update MedicalHistory.
     * @param request UpdateMedicalHistoryRequest for Medical History
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with update MedicalHistory
     */
    UpdateMedicalHistoryResponse updateMedicalHistory(
            UpdateMedicalHistoryRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to delete MedicalHistory.
     * @param request DeleteMedicalHistoryRequest for Medical History
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with SuccessResponse of delete MedicalHistory
     */
    SuccessResponse deleteMedicalHistory(
            DeleteMedicalHistoryRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to get MedicalHistories information by patient ID within a date range.
     * @param request Request for getting MedicalHistories by patient ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of MedicalHistories.
     */
    ListMedicalHistoriesResponse listMedicalHistories(
            ListMedicalHistoriesRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
