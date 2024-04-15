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
import cdx.opencdx.client.service.OpenCDXWeightMeasurementClient;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Weight Measurement gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXWeightMeasurementClientImpl implements OpenCDXWeightMeasurementClient {
    private static final String OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL = "OpenCDXWeightMeasurementClientImpl";
    private final WeightMeasurementServiceGrpc.WeightMeasurementServiceBlockingStub
            weightMeasurementServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXWeightMeasurementClientImpl(ManagedChannel channel) {
        this.weightMeasurementServiceBlockingStub = WeightMeasurementServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Weight measurement client implementation.
     * @param weightMeasurementServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXWeightMeasurementClientImpl(
            WeightMeasurementServiceGrpc.WeightMeasurementServiceBlockingStub weightMeasurementServiceBlockingStub) {
        this.weightMeasurementServiceBlockingStub = weightMeasurementServiceBlockingStub;
    }

    /**
     * Method to gRPC Call weight measurement createWeightMeasurement() api.
     *
     * @param request                CreateWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateWeightMeasurementResponse createWeightMeasurement(
            CreateWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return weightMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createWeightMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Weight Service getWeightMeasurement() api.
     *
     * @param request                GetWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetWeightMeasurementResponse getWeightMeasurement(
            GetWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return weightMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getWeightMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Weight Service updateWeightMeasurement() api.
     *
     * @param request                UpdateWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateWeightMeasurementResponse updateWeightMeasurement(
            UpdateWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return weightMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateWeightMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Weight Service deleteWeightMeasurement() api.
     *
     * @param request                DeleteWeightMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SuccessResponse deleteWeightMeasurement(
            DeleteWeightMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return weightMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteWeightMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Weight Service listWeightMeasurements() api.
     *
     * @param request                ListWeightMeasurementsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListWeightMeasurementsResponse listWeightMeasurements(
            ListWeightMeasurementsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return weightMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listWeightMeasurements(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_WEIGHT_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
