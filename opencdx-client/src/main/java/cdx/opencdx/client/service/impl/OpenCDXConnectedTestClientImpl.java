/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.grpc.connected.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.observation.annotation.Observed;
import java.io.InputStream;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client", name = "connected-test", havingValue = "true")
public class OpenCDXConnectedTestClientImpl implements OpenCDXConnectedTestClient {

    private static final String DOMAIN = "OpenCDXConnectedTestClientImpl";

    private final HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @throws SSLException creating Client
     */
    public OpenCDXConnectedTestClientImpl() throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress("connected-test", 9090)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
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
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) {
        try {
            log.info("Processing submit test: {}", connectedTest);
            return healthcareServiceBlockingStub.submitTest(connectedTest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTest getTestDetailsById(TestIdRequest testIdRequest) {
        try {
            log.info("Processing test details by Id: {}", testIdRequest);
            return healthcareServiceBlockingStub.getTestDetailsById(testIdRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(ConnectedTestListRequest connectedTestListRequest) {
        try {
            log.info("Processing listConnectedTests: {}", connectedTestListRequest);
            return healthcareServiceBlockingStub.listConnectedTests(connectedTestListRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest) {
        try {
            log.info("Processing listConnectedTestsByNHID: {}", connectedTestListByNHIDRequest);
            return healthcareServiceBlockingStub.listConnectedTestsByNHID(connectedTestListByNHIDRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
