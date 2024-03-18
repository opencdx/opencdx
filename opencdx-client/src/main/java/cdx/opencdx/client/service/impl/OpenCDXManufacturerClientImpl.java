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
import cdx.opencdx.client.service.OpenCDXManufacturerClient;
import cdx.opencdx.grpc.inventory.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Manufacturer gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXManufacturerClientImpl implements OpenCDXManufacturerClient {

    private static final String OPEN_CDX_MANUFACTURER_CLIENT_IMPL = "OpenCDXManufacturerClientImpl";
    private final ManufacturerServiceGrpc.ManufacturerServiceBlockingStub manufacturerServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXManufacturerClientImpl(ManagedChannel channel) {
        this.manufacturerServiceBlockingStub = ManufacturerServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Country client implementation.
     * @param manufacturerServiceBlockingStub gRPC Blocking Stub for Manufacturer.
     */
    public OpenCDXManufacturerClientImpl(
            ManufacturerServiceGrpc.ManufacturerServiceBlockingStub manufacturerServiceBlockingStub) {
        this.manufacturerServiceBlockingStub = manufacturerServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Manufacturer Service getManufacturerById() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Manufacturer getManufacturerById(
            ManufacturerIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return manufacturerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getManufacturerById(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MANUFACTURER_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Manufacturer Service addManufacturer() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Manufacturer addManufacturer(Manufacturer request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return manufacturerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .addManufacturer(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MANUFACTURER_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Manufacturer Service updateManufacturer() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Manufacturer updateManufacturer(Manufacturer request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return manufacturerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateManufacturer(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MANUFACTURER_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Manufacturer Service deleteManufacturer() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteResponse deleteManufacturer(
            ManufacturerIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return manufacturerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteManufacturer(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MANUFACTURER_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Manufacturer Service listManufacturers() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ManufacturersListResponse listManufacturers(
            ManufacturerListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return manufacturerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listManufacturers(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MANUFACTURER_CLIENT_IMPL,
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
