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
import cdx.opencdx.client.service.OpenCDXTemperatureMeasurementClient;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Temperature Measurement gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXTemperatureMeasurementClientImpl implements OpenCDXTemperatureMeasurementClient {
    private static final String OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL = "OpenCDXTemperatureMeasurementClientImpl";
    private final TemperatureMeasurementServiceGrpc.TemperatureMeasurementServiceBlockingStub
            temperatureMeasurementServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXTemperatureMeasurementClientImpl(ManagedChannel channel) {
        this.temperatureMeasurementServiceBlockingStub = TemperatureMeasurementServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Temperature measurement client implementation.
     * @param temperatureMeasurementServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXTemperatureMeasurementClientImpl(
            TemperatureMeasurementServiceGrpc.TemperatureMeasurementServiceBlockingStub temperatureMeasurementServiceBlockingStub) {
        this.temperatureMeasurementServiceBlockingStub = temperatureMeasurementServiceBlockingStub;
    }

    /**
     * Method to gRPC Call temperature measurement createTemperatureMeasurement() api.
     *
     * @param request                CreateTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateTemperatureMeasurementResponse createTemperatureMeasurement(
            CreateTemperatureMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return temperatureMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createTemperatureMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service getTemperatureMeasurement() api.
     *
     * @param request                GetTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetTemperatureMeasurementResponse getTemperatureMeasurement(
            GetTemperatureMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return temperatureMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTemperatureMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service updateTemperatureMeasurement() api.
     *
     * @param request                UpdateTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateTemperatureMeasurementResponse updateTemperatureMeasurement(
            UpdateTemperatureMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return temperatureMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateTemperatureMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service deleteTemperatureMeasurement() api.
     *
     * @param request                DeleteTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SuccessResponse deleteTemperatureMeasurement(
            DeleteTemperatureMeasurementRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return temperatureMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteTemperatureMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service listTemperatureMeasurements() api.
     *
     * @param request                ListTemperatureMeasurementsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListTemperatureMeasurementsResponse listTemperatureMeasurements(
            ListTemperatureMeasurementsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return temperatureMeasurementServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listTemperatureMeasurements(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_TEMPERATURE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
