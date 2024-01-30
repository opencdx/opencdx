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
import cdx.opencdx.client.service.OpenCDXDeviceClient;
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
 * Implementation of the Device gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
public class OpenCDXDeviceClientImpl implements OpenCDXDeviceClient {

    public static final String OPEN_CDX_DEVICE_CLIENT_IMPL = "OpenCDXDeviceClientImpl";
    private final DeviceServiceGrpc.DeviceServiceBlockingStub deviceServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXDeviceClientImpl(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.deviceServiceBlockingStub = DeviceServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Vendor client implementation.
     * @param deviceServiceBlockingStub gRPC Blocking Stub for Device.
     */
    public OpenCDXDeviceClientImpl(DeviceServiceGrpc.DeviceServiceBlockingStub deviceServiceBlockingStub) {
        this.deviceServiceBlockingStub = deviceServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Device Service getDeviceById() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Device getDeviceById(DeviceIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return deviceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getDeviceById(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DEVICE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Device Service addDevice() api.
     *
     * @param request                Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Device addDevice(Device request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return deviceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .addDevice(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DEVICE_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Device Service updateDevice() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Device updateDevice(Device request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return deviceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateDevice(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DEVICE_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Device Service deleteDevice() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteResponse deleteDevice(DeviceIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return deviceServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteDevice(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_DEVICE_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
