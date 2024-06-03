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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXMedicalConditionsClient;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Medical Conditions gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXMedicalConditionsClientImpl implements OpenCDXMedicalConditionsClient {
    private static final String OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL = "OpenCDXMedicalConditionsClientImpl";
    private final MedicalConditionsServiceGrpc.MedicalConditionsServiceBlockingStub
            medicalConditionsServiceBlockingStub;

    /**
     * Constructor for the Medical Conditions client implementation.
     * @param channel gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicalConditionsClientImpl(ManagedChannel channel) {
        this.medicalConditionsServiceBlockingStub = MedicalConditionsServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for the Medical Conditions client implementation.
     * @param medicalConditionsServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicalConditionsClientImpl(
            MedicalConditionsServiceGrpc.MedicalConditionsServiceBlockingStub medicalConditionsServiceBlockingStub) {
        this.medicalConditionsServiceBlockingStub = medicalConditionsServiceBlockingStub;
    }

    /**
     * Method to create diagnosis.
     *
     * @param request                DiagnosisRequest for the diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the diagnosis.
     */
    @Override
    public DiagnosisResponse createDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicalConditionsServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get diagnosis.
     *
     * @param request                DiagnosisRequest for diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the diagnosis.
     */
    @Override
    public DiagnosisResponse getDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicalConditionsServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to update diagnosis.
     *
     * @param request                DiagnosisRequest for diagnosis.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with updated diagnosis
     */
    @Override
    public DiagnosisResponse updateDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicalConditionsServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to delete diagnosis.
     *
     * @param request                DiagnosisRequest for diagnosis
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with diagnosis
     */
    @Override
    public DiagnosisResponse deleteDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicalConditionsServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get diagnosis information by patient ID or national health ID.
     *
     * @param request                ListDiagnosisRequest for getting diagnosis by patient ID or national health ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of diagnosis.
     */
    @Override
    public ListDiagnosisResponse listDiagnosis(
            ListDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicalConditionsServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICAL_CONDITIONS_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
