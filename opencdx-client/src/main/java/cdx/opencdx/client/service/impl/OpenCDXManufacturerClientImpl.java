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
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.observation.annotation.Observed;
import java.io.InputStream;
import javax.net.ssl.SSLException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Manufacturer gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
public class OpenCDXManufacturerClientImpl implements OpenCDXManufacturerClient {

    public static final String OPEN_CDX_MANUFACTURER_CLIENT_IMPL = "OpenCDXManufacturerClientImpl";
    private final ManufacturerServiceGrpc.ManufacturerServiceBlockingStub manufacturerServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXManufacturerClientImpl(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
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