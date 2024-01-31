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
import cdx.opencdx.client.service.OpenCDXIAMProfileClient;
import cdx.opencdx.grpc.profile.*;
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
 * Implementation of the User Profile gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
public class OpenCDXIAMProfileClientImpl implements OpenCDXIAMProfileClient {

    public static final String OPEN_CDX_PROFILE_CLIENT_IMPL = "OpenCDXIAMProfileClientImpl";
    private final UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXIAMProfileClientImpl(
            @Value("${opencdx.client.iam.server}") String server, @Value("${opencdx.client.iam.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.userProfileServiceBlockingStub = UserProfileServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Profile client implementation.
     * @param userProfileServiceBlockingStub gRPC Blocking Stub for Profile.
     */
    public OpenCDXIAMProfileClientImpl(
            UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileServiceBlockingStub) {
        this.userProfileServiceBlockingStub = userProfileServiceBlockingStub;
    }

    /**
     * Method to gRPC Call IAM Profile Service getUserProfile() api.
     *
     * @param request                UserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UserProfileResponse getUserProfile(UserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return userProfileServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getUserProfile(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROFILE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Profile Service updateUserProfile() api.
     *
     * @param request                UpdateUserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateUserProfileResponse updateUserProfile(
            UpdateUserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return userProfileServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateUserProfile(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROFILE_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Profile Service deleteUserProfile() api.
     *
     * @param request                DeleteUserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteUserProfileResponse deleteUserProfile(
            DeleteUserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return userProfileServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteUserProfile(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_PROFILE_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
