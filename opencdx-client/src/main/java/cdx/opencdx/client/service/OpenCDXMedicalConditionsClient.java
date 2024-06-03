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
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for communicating with the Medical Conditions service.
 */
public interface OpenCDXMedicalConditionsClient {
    /**
     * Method to create diagnosis.
     * @param request DiagnosisRequest for the diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the diagnosis.
     */
    DiagnosisResponse createDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to get diagnosis.
     * @param request DiagnosisRequest for diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the diagnosis.
     */
    DiagnosisResponse getDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to update diagnosis.
     * @param request DiagnosisRequest for diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with updated diagnosis
     */
    DiagnosisResponse updateDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to delete diagnosis.
     * @param request DiagnosisRequest for diagnosis
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with diagnosis
     */
    DiagnosisResponse deleteDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to get diagnosis information by patient ID or national health ID.
     * @param request ListDiagnosisRequest for getting diagnosis by patient ID or national health ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of diagnosis.
     */
    ListDiagnosisResponse listDiagnosis(ListDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
