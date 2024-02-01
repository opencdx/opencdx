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
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.grpc.connected.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.observation.annotation.Observed;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.InputStream;
import javax.net.ssl.SSLException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Open CDX gRPC Connected Test Client
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXConnectedTestClientImpl implements OpenCDXConnectedTestClient {

    private static final String DOMAIN = "OpenCDXConnectedTestClientImpl";

    private final HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXConnectedTestClientImpl(String server, Integer port) throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        if (certChain == null) {
            throw new SSLException("Could not load certificate chain");
        }
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build())
                .build();

        this.healthcareServiceBlockingStub = HealthcareServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructore for creating the OpenCDXConnectedTestClientImpl
     * @param healthcareServiceBlockingStub gRPC Stub for the client.
     */
    public OpenCDXConnectedTestClientImpl(
            HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub) {
        this.healthcareServiceBlockingStub = healthcareServiceBlockingStub;
    }

    @Override
    public TestSubmissionResponse submitTest(
            ConnectedTest connectedTest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.info("Processing submit test: {}", connectedTest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .submitTest(connectedTest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTest getTestDetailsById(
            TestIdRequest testIdRequest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.info("Processing test details by Id: {}", testIdRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTestDetailsById(testIdRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(
            ConnectedTestListRequest connectedTestListRequest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.info("Processing listConnectedTests: {}", connectedTestListRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listConnectedTests(connectedTestListRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest,
            OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.info("Processing listConnectedTestsByNHID: {}", connectedTestListByNHIDRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listConnectedTestsByNHID(connectedTestListByNHIDRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
