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
import cdx.opencdx.client.service.OpenCDXLabConnectedClient;
import cdx.opencdx.grpc.data.LabFindings;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.Generated;

/**
 * Implementation of the Lab Connected Service gRPC Client.
 */
public class OpenCDXLabConnectedClientImpl implements OpenCDXLabConnectedClient {
    private final ConnectedLabServiceGrpc.ConnectedLabServiceBlockingStub connectedLabServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXLabConnectedClientImpl(ManagedChannel channel) {
        this.connectedLabServiceBlockingStub = ConnectedLabServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Shipping Service implementation.
     * @param connectedLabServiceBlockingStub gRPC Blocking Stub for Connected Lab.
     */
    public OpenCDXLabConnectedClientImpl(
            ConnectedLabServiceGrpc.ConnectedLabServiceBlockingStub connectedLabServiceBlockingStub) {
        this.connectedLabServiceBlockingStub = connectedLabServiceBlockingStub;
    }

    @Override
    public LabFindingsResponse submitLabFindings(
            LabFindings labFindings, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return this.connectedLabServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .submitLabFindings(labFindings);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    this.getClass().getName(),
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public CreateConnectedLabResponse createConnectedLab(
            CreateConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return this.connectedLabServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createConnectedLab(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    this.getClass().getName(),
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public GetConnectedLabResponse getConnectedLab(
            GetConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return this.connectedLabServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getConnectedLab(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    this.getClass().getName(),
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public UpdateConnectedLabResponse updateConnectedLab(
            UpdateConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return this.connectedLabServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateConnectedLab(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    this.getClass().getName(),
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public DeleteConnectedLabResponse deleteConnectedLab(
            DeleteConnectedLabRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return this.connectedLabServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteConnectedLab(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    this.getClass().getName(),
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
