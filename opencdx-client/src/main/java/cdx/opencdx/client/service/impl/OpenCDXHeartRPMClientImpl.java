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
import cdx.opencdx.client.service.OpenCDXHeartRPMClient;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the HeartRPM Measurement gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXHeartRPMClientImpl implements OpenCDXHeartRPMClient {
    private static final String OPEN_CDX_HEART_RPM_CLIENT_IMPL = "OpenCDXHeartRPMClientImpl";
    private final HeartRPMServiceGrpc.HeartRPMServiceBlockingStub heartRPMServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXHeartRPMClientImpl(ManagedChannel channel) {
        this.heartRPMServiceBlockingStub = HeartRPMServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the HeartRPM client implementation.
     * @param heartRPMServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXHeartRPMClientImpl(HeartRPMServiceGrpc.HeartRPMServiceBlockingStub heartRPMServiceBlockingStub) {
        this.heartRPMServiceBlockingStub = heartRPMServiceBlockingStub;
    }

    /**
     * Method to gRPC Call HeartRPM createHeartRPM() api.
     *
     * @param request                CreateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateHeartRPMResponse createHeartRPMMeasurement(
            CreateHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return heartRPMServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createHeartRPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_HEART_RPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call HeartRPM Service getHeartRPM() api.
     *
     * @param request                GetHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetHeartRPMResponse getHeartRPMMeasurement(
            GetHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return heartRPMServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getHeartRPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_HEART_RPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call HeartRPM Service updateHeartRPM() api.
     *
     * @param request                UpdateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateHeartRPMResponse updateHeartRPMMeasurement(
            UpdateHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return heartRPMServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateHeartRPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_HEART_RPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call HeartRPM Service deleteHeartRPM() api.
     *
     * @param request                DeleteHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SuccessResponse deleteHeartRPMMeasurement(
            DeleteHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return heartRPMServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteHeartRPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_HEART_RPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call HeartRPM Service listHeartRPMs() api.
     *
     * @param request                ListHeartRPMsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListHeartRPMResponse listHeartRPMMeasurements(
            ListHeartRPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return heartRPMServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listHeartRPMMeasurements(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_HEART_RPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
