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
import cdx.opencdx.client.service.OpenCDXShippingServiceClient;
import cdx.opencdx.grpc.shipping.*;
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
 * Implementation of the Shipping Service gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXShippingServiceClientImpl implements OpenCDXShippingServiceClient {

    private static final String OPEN_CDX_SHIPPING_CLIENT_IMPL = "OpenCDXShippingServiceClientImpl";
    private final ShippingServiceGrpc.ShippingServiceBlockingStub shippingServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @param observationGrpcClientInterceptor Interceptor for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXShippingServiceClientImpl(
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
        this.shippingServiceBlockingStub = ShippingServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Shipping Service implementation.
     * @param shippingServiceBlockingStub gRPC Blocking Stub for Shipping.
     */
    public OpenCDXShippingServiceClientImpl(
            ShippingServiceGrpc.ShippingServiceBlockingStub shippingServiceBlockingStub) {
        this.shippingServiceBlockingStub = shippingServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Shipping Service getShippingVendors() api.
     *
     * @param request                Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ShippingVendorResponse getShippingVendors(
            ShippingRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return shippingServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getShippingVendors(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_SHIPPING_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Shipping Service shipPackage() api.
     *
     * @param request                Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ShippingResponse shipPackage(Shipping request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return shippingServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .shipPackage(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_SHIPPING_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
