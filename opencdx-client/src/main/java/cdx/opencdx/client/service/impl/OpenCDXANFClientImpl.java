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
import cdx.opencdx.client.service.OpenCDXANFClient;
import cdx.opencdx.grpc.anf.ANFServiceGrpc;
import cdx.opencdx.grpc.anf.AnfStatement;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.annotation.Observed;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.InputStream;
import javax.net.ssl.SSLException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the ANF gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXANFClientImpl implements OpenCDXANFClient {

    private static final String OPEN_CDX_ANF_CLIENT_IMPL = "OpenCDXANFClientImpl";
    private final ANFServiceGrpc.ANFServiceBlockingStub anfServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @param observationGrpcClientInterceptor client interceptor.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXANFClientImpl(
            String server, Integer port, ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        if (certChain == null) {
            throw new SSLException("Could not load certificate chain");
        }
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .intercept(observationGrpcClientInterceptor)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build())
                .build();
        this.anfServiceBlockingStub = ANFServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the ANF client implementation.
     * @param anfServiceBlockingStub gRPC Blocking Stub for Protector.
     */
    public OpenCDXANFClientImpl(ANFServiceGrpc.ANFServiceBlockingStub anfServiceBlockingStub) {
        this.anfServiceBlockingStub = anfServiceBlockingStub;
    }

    /**
     * Method to gRPC Call ANF Service createANFStatement() api.
     *
     * @param request                ANF Statement to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public AnfStatement.Identifier createANFStatement(
            AnfStatement.ANFStatement request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return anfServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createANFStatement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANF_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call ANF Service getANFStatement() api.
     *
     * @param request                Identifier to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public AnfStatement.ANFStatement getANFStatement(
            AnfStatement.Identifier request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return anfServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getANFStatement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANF_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call ANF Service UpdateANFStatement() api.
     *
     * @param request                ANF Statement to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public AnfStatement.Identifier updateANFStatement(
            AnfStatement.ANFStatement request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return anfServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateANFStatement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANF_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call ANF Service deleteANFStatement() api.
     *
     * @param request                Identifier to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public AnfStatement.Identifier deleteANFStatement(
            AnfStatement.Identifier request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return anfServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteANFStatement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANF_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
