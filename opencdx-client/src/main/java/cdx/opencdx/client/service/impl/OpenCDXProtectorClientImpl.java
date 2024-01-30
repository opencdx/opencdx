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
import cdx.opencdx.client.service.OpenCDXProtectorClient;
import cdx.opencdx.grpc.neural.protector.*;
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
 * Implementation of the Protector gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.protector", name = "enabled", havingValue = "true")
public class OpenCDXProtectorClientImpl implements OpenCDXProtectorClient {

    public static final String OPEN_CDX_PROTECTOR_CLIENT_IMPL = "OpenCDXProtectorClientImpl";
    private final NeuralProtectorServiceGrpc.NeuralProtectorServiceBlockingStub neuralProtectorServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXProtectorClientImpl(
            @Value("${opencdx.client.protector.server}") String server,
            @Value("${opencdx.client.protector.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.neuralProtectorServiceBlockingStub = NeuralProtectorServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Protector client implementation.
     * @param neuralProtectorServiceBlockingStub gRPC Blocking Stub for Protector.
     */
    public OpenCDXProtectorClientImpl(
            NeuralProtectorServiceGrpc.NeuralProtectorServiceBlockingStub neuralProtectorServiceBlockingStub) {
        this.neuralProtectorServiceBlockingStub = neuralProtectorServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Neural Protector Service detectAnomalies() api.
     *
     * @param request Anomaly Detection Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    @Override
    public SecurityResponse detectAnomalies(
            AnomalyDetectionDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return neuralProtectorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .detectAnomalies(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROTECTOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Neural Protector Service enforceAuthorizationControl() api.
     *
     * @param request Authorization Control Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    @Override
    public SecurityResponse enforceAuthorizationControl(
            AuthorizationControlDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return neuralProtectorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .enforceAuthorizationControl(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROTECTOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Neural Protector Service protectPrivacy() api.
     *
     * @param request Privacy Protection Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    @Override
    public SecurityResponse protectPrivacy(
            PrivacyProtectionDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return neuralProtectorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .protectPrivacy(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROTECTOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Neural Protector Service monitorRealTimeActivity() api.
     *
     * @param request Real Time Monitoring Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    @Override
    public SecurityResponse monitorRealTimeActivity(
            RealTimeMonitoringDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return neuralProtectorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .monitorRealTimeActivity(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROTECTOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Neural Protector Service analyzeUserBehavior() api.
     *
     * @param request User Behavior Analysis Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    @Override
    public SecurityResponse analyzeUserBehavior(
            UserBehaviorAnalysisDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return neuralProtectorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .analyzeUserBehavior(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROTECTOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
