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
 * Interface for the OpenCDXMedicalConditionsService, which defines methods for processing medical conditions requests.
 */
public interface OpenCDXMedicalConditionsService {
    /**
     * Triggers a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for creating a diagnosis.
     * @return A DiagnosisResponse indicating the status of the diagnosis creation.
     */
    DiagnosisResponse createDiagnosis(DiagnosisRequest request);

    /**
     * Retrieves information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for retrieving diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    DiagnosisResponse getDiagnosis(DiagnosisRequest request);

    /**
     * Updates information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for updating  diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    DiagnosisResponse updateDiagnosis(DiagnosisRequest request);

    /**
     * Deletes information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for deleting the diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    DiagnosisResponse deleteDiagnosis(DiagnosisRequest request);

    /**
     * Retrieves information about a diagnosis based on the provided ListDiagnosisRequest.
     *
     * @param request The ListDiagnosisRequest for deleting the diagnosis information.
     * @return A ListDiagnosisResponse containing information about the requested diagnosis.
     */
    ListDiagnosisResponse listDiagnosis(ListDiagnosisRequest request);
}
