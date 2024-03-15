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
import cdx.opencdx.client.service.OpenCDXRoutineClient;
import cdx.opencdx.grpc.routine.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Routine gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXRoutineClientImpl implements OpenCDXRoutineClient {

    private static final String OPEN_CDX_ROUTINE_CLIENT_IMPL = "OpenCDXRoutineClientImpl";
    private final RoutineSystemServiceGrpc.RoutineSystemServiceBlockingStub routineSystemServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXRoutineClientImpl(ManagedChannel channel) {
        this.routineSystemServiceBlockingStub = RoutineSystemServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Routine client implementation.
     * @param routineSystemServiceBlockingStub gRPC Blocking Stub for Protector.
     */
    public OpenCDXRoutineClientImpl(
            RoutineSystemServiceGrpc.RoutineSystemServiceBlockingStub routineSystemServiceBlockingStub) {
        this.routineSystemServiceBlockingStub = routineSystemServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Routine Service createRoutine() api.
     *
     * @param request                Routine Request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Routine response.
     */
    @Override
    public RoutineResponse createRoutine(RoutineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createRoutine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getRoutine() api.
     *
     * @param request                Routine Request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public RoutineResponse getRoutine(RoutineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getRoutine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createClinicalProtocolExecution() api.
     *
     * @param request                Clinical Protocol Execution request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Clinical Protocol Execution response.
     */
    @Override
    public ClinicalProtocolExecutionResponse createClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createClinicalProtocolExecution(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getClinicalProtocolExecution() api.
     *
     * @param request                Clinical Protocol Execution request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Clinical Protocol Execution response.
     */
    @Override
    public ClinicalProtocolExecutionResponse getClinicalProtocolExecution(
            ClinicalProtocolExecutionRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getClinicalProtocolExecution(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    6,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createLabOrder() api.
     *
     * @param request                Lab order request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Order response.
     */
    @Override
    public LabOrderResponse createLabOrder(LabOrderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createLabOrder(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    7,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getLabOrder() api.
     *
     * @param request                Lab Order request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Order response.
     */
    @Override
    public LabOrderResponse getLabOrder(LabOrderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getLabOrder(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    8,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createDiagnosis() api.
     *
     * @param request                Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Diagnosis response.
     */
    @Override
    public DiagnosisResponse createDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    9,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getDiagnosis() api.
     *
     * @param request                Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Diagnosis response.
     */
    @Override
    public DiagnosisResponse getDiagnosis(DiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    10,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createSuspectedDiagnosis() api.
     *
     * @param request                Suspected Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Suspected Diagnosis response.
     */
    @Override
    public SuspectedDiagnosisResponse createSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createSuspectedDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    11,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getSuspectedDiagnosis() api.
     *
     * @param request                Suspected Diagnosis request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Suspected Diagnosis response.
     */
    @Override
    public SuspectedDiagnosisResponse getSuspectedDiagnosis(
            SuspectedDiagnosisRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getSuspectedDiagnosis(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    12,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createLabResult() api.
     *
     * @param request                Lab Result request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Result response.
     */
    @Override
    public LabResultResponse createLabResult(LabResultRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createLabResult(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    13,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getLabResult() api.
     *
     * @param request                Lab Result request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Lab Result response.
     */
    @Override
    public LabResultResponse getLabResult(LabResultRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getLabResult(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    14,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service createMedication() api.
     *
     * @param request                Medication request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Medication response.
     */
    @Override
    public MedicationResponse createMedication(MedicationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createMedication(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    15,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Routine Service getMedication() api.
     *
     * @param request                Medication request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Medication response.
     */
    @Override
    public MedicationResponse getMedication(MedicationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return routineSystemServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getMedication(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ROUTINE_CLIENT_IMPL,
                    16,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
