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
import cdx.opencdx.client.service.OpenCDXProviderClient;
import cdx.opencdx.grpc.provider.*;
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
 * Implementation of the Provider gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.provider", name = "enabled", havingValue = "true")
public class OpenCDXProviderClientImpl implements OpenCDXProviderClient {

    public static final String OPEN_CDX_PROVIDER_CLIENT_IMPL = "OpenCDXProviderClientImpl";
    private final ProviderServiceGrpc.ProviderServiceBlockingStub providerServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXProviderClientImpl(
            @Value("${opencdx.client.provider.server}") String server,
            @Value("${opencdx.client.provider.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.providerServiceBlockingStub = ProviderServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Provider client implementation.
     * @param providerServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXProviderClientImpl(ProviderServiceGrpc.ProviderServiceBlockingStub providerServiceBlockingStub) {
        this.providerServiceBlockingStub = providerServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Provider Service getProviderByNumber() api.
     *
     * @param request                GetProviderRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetProviderResponse getProviderByNumber(
            GetProviderRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return providerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getProviderByNumber(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROVIDER_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Provider Service deleteProvider() api.
     *
     * @param request                DeleteProviderRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteProviderResponse deleteProvider(
            DeleteProviderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return providerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteProvider(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROVIDER_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Provider Service listProviders() api.
     *
     * @param request                ListProvidersRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListProvidersResponse listProviders(
            ListProvidersRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return providerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listProviders(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROVIDER_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Provider Service loadProvider() api.
     *
     * @param request                LoadProviderRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public LoadProviderResponse loadProvider(LoadProviderRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return providerServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .loadProvider(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROVIDER_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}