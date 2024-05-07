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
import cdx.opencdx.client.service.OpenCDXDoctorNotesClient;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Allergy gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXDoctorNotesClientImpl implements OpenCDXDoctorNotesClient {
    private static final String OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL = "OpenCDXDoctorNotesClientImpl";
    private final DoctorNotesServiceGrpc.DoctorNotesServiceBlockingStub doctorNotesServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXDoctorNotesClientImpl(ManagedChannel channel) {
        this.doctorNotesServiceBlockingStub = DoctorNotesServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Doctor Notes client implementation.
     * @param doctorNotesServiceBlockingStub gRPC Blocking Stub for Allergy.
     */
    public OpenCDXDoctorNotesClientImpl(
            DoctorNotesServiceGrpc.DoctorNotesServiceBlockingStub doctorNotesServiceBlockingStub) {
        this.doctorNotesServiceBlockingStub = doctorNotesServiceBlockingStub;
    }

    @Override
    public CreateDoctorNotesResponse createDoctorNotes(
            CreateDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return doctorNotesServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createDoctorNotes(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public GetDoctorNotesResponse getDoctorNotes(
            GetDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return doctorNotesServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getDoctorNotes(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public UpdateDoctorNotesResponse updateDoctorNotes(
            UpdateDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return doctorNotesServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateDoctorNotes(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public SuccessResponse deleteDoctorNotes(
            DeleteDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return doctorNotesServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteDoctorNotes(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public ListDoctorNotesResponse listAllByPatientId(
            ListDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return doctorNotesServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listAllByPatientId(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DOCTOR_NOTES_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
