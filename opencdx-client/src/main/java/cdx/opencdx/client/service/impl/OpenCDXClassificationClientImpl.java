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
import cdx.opencdx.client.service.OpenCDXClassificationClient;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationServiceGrpc;
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
 * Implementation of the Classification gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.classification", name = "enabled", havingValue = "true")
public class OpenCDXClassificationClientImpl implements OpenCDXClassificationClient {

    private final ClassificationServiceGrpc.ClassificationServiceBlockingStub classificationServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXClassificationClientImpl(
            @Value("${opencdx.client.classification.server}") String server,
            @Value("${opencdx.client.classification.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.classificationServiceBlockingStub = ClassificationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Classification client implementation.
     * @param classificationServiceBlockingStub gRPC Blocking Stub for Classification.
     */
    public OpenCDXClassificationClientImpl(
            ClassificationServiceGrpc.ClassificationServiceBlockingStub classificationServiceBlockingStub) {
        this.classificationServiceBlockingStub = classificationServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Classification Service classify() api.
     *
     * @param request Classification request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Classification Response.
     */
    @Override
    public ClassificationResponse classify(ClassificationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return classificationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .classify(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXClassificationClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
