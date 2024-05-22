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
import cdx.opencdx.client.service.OpenCDXAllergyClient;
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
public class OpenCDXAllergyClientImpl implements OpenCDXAllergyClient {
    private static final String OPEN_CDX_ALLERGY_CLIENT_IMPL = "OpenCDXAllergyClientImpl";
    private final KnownAllergyServiceGrpc.KnownAllergyServiceBlockingStub allergyServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXAllergyClientImpl(ManagedChannel channel) {
        this.allergyServiceBlockingStub = KnownAllergyServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Allergy client implementation.
     * @param allergyServiceBlockingStub gRPC Blocking Stub for Allergy.
     */
    public OpenCDXAllergyClientImpl(
            KnownAllergyServiceGrpc.KnownAllergyServiceBlockingStub allergyServiceBlockingStub) {
        this.allergyServiceBlockingStub = allergyServiceBlockingStub;
    }

    @Override
    public CreateAllergyResponse createAllergy(
            CreateAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return allergyServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createAllergy(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ALLERGY_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public GetAllergyResponse getAllergy(GetAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return allergyServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getAllergy(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ALLERGY_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public UpdateAllergyResponse updateAllergy(
            UpdateAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return allergyServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateAllergy(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ALLERGY_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public SuccessResponse deleteAllergy(DeleteAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return allergyServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteAllergy(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ALLERGY_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public ListAllergyResponse listAllergies(ListAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return allergyServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listAllergies(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ALLERGY_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
