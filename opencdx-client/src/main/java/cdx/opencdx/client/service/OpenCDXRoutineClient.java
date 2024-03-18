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
import cdx.opencdx.grpc.routine.*;

/**
 * Interface for communicating with the Routine microservice.
 */
public interface OpenCDXRoutineClient {
    /**
     * Method to gRPC Call Routine Service createRoutine() api.
     * @param request Routine Request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Routine response.
     */
    RoutineResponse createRoutine(RoutineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getRoutine() api.
     * @param request Routine Request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    RoutineResponse getRoutine(RoutineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createClinicalProtocolExecution() api.
     * @param request Clinical Protocol Execution request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Clinical Protocol Execution response.
     */
    ClinicalProtocolExecutionResponse createClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getClinicalProtocolExecution() api.
     * @param request Clinical Protocol Execution request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Clinical Protocol Execution response.
     */
    ClinicalProtocolExecutionResponse getClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createLabOrder() api.
     * @param request Lab order request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Order response.
     */
    LabOrderResponse createLabOrder(LabOrderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getLabOrder() api.
     * @param request Lab Order request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Order response.
     */
    LabOrderResponse getLabOrder(LabOrderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createDiagnosis() api.
     * @param request Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Diagnosis response.
     */
    DiagnosisResponse createDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getDiagnosis() api.
     * @param request Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Diagnosis response.
     */
    DiagnosisResponse getDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createSuspectedDiagnosis() api.
     * @param request Suspected Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Suspected Diagnosis response.
     */
    SuspectedDiagnosisResponse createSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getSuspectedDiagnosis() api.
     * @param request Suspected Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Suspected Diagnosis response.
     */
    SuspectedDiagnosisResponse getSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createLabResult() api.
     * @param request Lab Result request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Result response.
     */
    LabResultResponse createLabResult(LabResultRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getLabResult() api.
     * @param request Lab Result request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Result response.
     */
    LabResultResponse getLabResult(LabResultRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
