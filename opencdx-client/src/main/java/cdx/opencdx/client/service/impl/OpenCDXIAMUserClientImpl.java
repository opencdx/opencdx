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
import cdx.opencdx.client.service.OpenCDXIAMUserClient;
import cdx.opencdx.grpc.iam.*;
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
 * Implementation of the User gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXIAMUserClientImpl implements OpenCDXIAMUserClient {

    private static final String OPEN_CDX_USER_CLIENT_IMPL = "OpenCDXIAMUserClientImpl";
    private final IamUserServiceGrpc.IamUserServiceBlockingStub iamUserServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXIAMUserClientImpl(String server, Integer port) throws SSLException {
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
        this.iamUserServiceBlockingStub = IamUserServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the User client implementation.
     * @param iamUserServiceBlockingStub gRPC Blocking Stub for User.
     */
    public OpenCDXIAMUserClientImpl(IamUserServiceGrpc.IamUserServiceBlockingStub iamUserServiceBlockingStub) {
        this.iamUserServiceBlockingStub = iamUserServiceBlockingStub;
    }

    /**
     * Method to gRPC Call IAM User Service signUp() api.
     *
     * @param request                SignUpRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .signUp(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service getIamUser() api.
     *
     * @param request                GetIamUserRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetIamUserResponse getIamUser(GetIamUserRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getIamUser(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service updateIamUser() api.
     *
     * @param request                UpdateIamUserRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateIamUserResponse updateIamUser(
            UpdateIamUserRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateIamUser(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service listIamUsers() api.
     *
     * @param request                ListIamUsersRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListIamUsersResponse listIamUsers(ListIamUsersRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listIamUsers(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service changePassword() api.
     *
     * @param request                ChangePasswordRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ChangePasswordResponse changePassword(
            ChangePasswordRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .changePassword(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service deleteIamUser() api.
     *
     * @param request                DeleteIamUserRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteIamUserResponse deleteIamUser(
            DeleteIamUserRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteIamUser(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    6,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service userExists() api.
     *
     * @param request                UserExistsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UserExistsResponse userExists(UserExistsRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .userExists(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    7,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service login() api.
     *
     * @param request                LoginRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public LoginResponse login(LoginRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .login(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    8,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM User Service currentUser() api.
     *
     * @param request                CurrentUserRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CurrentUserResponse currentUser(CurrentUserRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return iamUserServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .currentUser(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_USER_CLIENT_IMPL,
                    9,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
