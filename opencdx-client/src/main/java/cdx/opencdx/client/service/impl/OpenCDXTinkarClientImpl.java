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
import cdx.opencdx.client.service.OpenCDXTinkarClient;
import cdx.opencdx.grpc.tinkar.TinkarGrpc;
import cdx.opencdx.grpc.tinkar.TinkarRequest;
import cdx.opencdx.grpc.tinkar.TinkarResponse;
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
 * Implementation of the Tinkar gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.tinkar", name = "enabled", havingValue = "true")
public class OpenCDXTinkarClientImpl implements OpenCDXTinkarClient {

    private final TinkarGrpc.TinkarBlockingStub tinkarBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXTinkarClientImpl(
            @Value("${opencdx.client.tinkar.server}") String server,
            @Value("${opencdx.client.tinakr.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.tinkarBlockingStub = TinkarGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Tinkar client implementation.
     * @param tinkarBlockingStub gRPC Blocking Stub for Tinkar.
     */
    public OpenCDXTinkarClientImpl(TinkarGrpc.TinkarBlockingStub tinkarBlockingStub) {
        this.tinkarBlockingStub = tinkarBlockingStub;
    }

    /**
     * Method to gRPC Call Tinkar Service sayTinkar() api.
     *
     * @param request Tinkar request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Classification Response.
     */
    @Override
    public TinkarResponse sayTinkar(TinkarRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return tinkarBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .sayTinkar(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXTinkarClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}