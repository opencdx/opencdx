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
import cdx.opencdx.client.service.OpenCDXHelloworldClient;
import cdx.opencdx.grpc.helloworld.GreeterGrpc;
import cdx.opencdx.grpc.helloworld.HelloReply;
import cdx.opencdx.grpc.helloworld.HelloRequest;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Helloworld gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client", name = "helloworld", havingValue = "true")
public class OpenCDXHelloworldClientImpl implements OpenCDXHelloworldClient {

    private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXHelloworldClientImpl() throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress("helloworld", 9090)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();

        this.greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Helloworld client implementation.
     * @param greeterBlockingStub gRPC Blocking Stub for Helloworld.
     */
    public OpenCDXHelloworldClientImpl(GreeterGrpc.GreeterBlockingStub greeterBlockingStub) {
        this.greeterBlockingStub = greeterBlockingStub;
    }

    /**
     * Method to query the Hello Message from Helloworld microservice
     * @param name Name to say Hello to.
     * @return String containing the Hello message.
     */
    public String sayHello(String name) throws OpenCDXClientException {

        try {
            HelloReply helloReply = greeterBlockingStub.sayHello(
                    HelloRequest.newBuilder().setName(name).build());

            return helloReply.getMessage();
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXHelloworldClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
