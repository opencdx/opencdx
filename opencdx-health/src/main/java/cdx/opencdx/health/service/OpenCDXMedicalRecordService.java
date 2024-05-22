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

import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the OpenCDXMedicalRecordService
 */
public interface OpenCDXMedicalRecordService {
    /**
     * Method to get Medical Record by patient id or national health id
     * @param request GetMedicalRecordRequest for medical record.
     * @return GetMedicalRecordResponse with medical record.
     */
    GetMedicalRecordResponse requestMedicalRecord(GetMedicalRecordRequest request);

    /**
     * Method to get status of medical record.
     * @param request MedicalRecordByIdRequest for medical record.
     * @return SuccessResponse with medical request.
     */
    SuccessResponse getMedicalRecordStatus(MedicalRecordByIdRequest request);

    /**
     * Method to fetch medical record by ID.
     * @param request MedicalRecordByIdRequest for medical record.
     * @return MedicalRecordByIdResponse with medical record.
     */
    MedicalRecordByIdResponse getMedicalRecordById(MedicalRecordByIdRequest request);

    /**
     * Method to create medical record.
     * @param request CreateMedicalRecordRequest for medical record.
     * @return CreateMedicalRecordResponse with medical record.
     */
    CreateMedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request);
}
