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
 * Service for MedicalHistory
 */
public interface OpenCDXMedicalHistoryService {

    /**
     * Method to create MedicalHistory.
     * @param createMedicalHistoryRequest CreateMedicalHistoryRequest for MedicalHistory.
     * @return CreateMedicalHistoryResponse with MedicalHistory.
     */
    CreateMedicalHistoryResponse createMedicalHistory(CreateMedicalHistoryRequest createMedicalHistoryRequest);

    /**
     * Method to get MedicalHistory.
     * @param getMedicalHistoryRequest GetMedicalHistoryRequest for MedicalHistory.
     * @return GetMedicalHistoryResponse with MedicalHistory.
     */
    GetMedicalHistoryResponse getMedicalHistory(GetMedicalHistoryRequest getMedicalHistoryRequest);

    /**
     * Method to update MedicalHistory.
     * @param request UpdateMedicalHistoryRequest for MedicalHistory.
     * @return UpdateMedicalHistoryResponse with MedicalHistory.
     */
    UpdateMedicalHistoryResponse updateMedicalHistory(UpdateMedicalHistoryRequest request);

    /**
     * Method to delete MedicalHistory.
     * @param request DeleteMedicalHistoryRequest for MedicalHistory.
     * @return SuccessResponse with MedicalHistory.
     */
    SuccessResponse deleteMedicalHistory(DeleteMedicalHistoryRequest request);

    /**
     * Method to list MedicalHistory.
     * @param request ListMedicalHistoriesRequest for MedicalHistory.
     * @return ListMedicalHistoriesResponse with MedicalHistory.
     */
    ListMedicalHistoriesResponse listMedicalHistories(ListMedicalHistoriesRequest request);
}
