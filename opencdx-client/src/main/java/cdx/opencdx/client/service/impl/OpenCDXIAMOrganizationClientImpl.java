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
import cdx.opencdx.client.service.OpenCDXIAMOrganizationClient;
import cdx.opencdx.grpc.service.iam.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Organization gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXIAMOrganizationClientImpl implements OpenCDXIAMOrganizationClient {

    private static final String OPEN_CDX_ORGANIZATION_CLIENT_IMPL = "OpenCDXIAMOrganizationClientImpl";
    private final OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXIAMOrganizationClientImpl(ManagedChannel channel) {
        this.organizationServiceBlockingStub = OrganizationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Organization client implementation.
     * @param organizationServiceBlockingStub gRPC Blocking Stub for Organization.
     */
    public OpenCDXIAMOrganizationClientImpl(
            OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub) {
        this.organizationServiceBlockingStub = organizationServiceBlockingStub;
    }

    /**
     * Method to gRPC Call IAM Organization Service createOrganization() api.
     *
     * @param request                CreateOrganizationRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateOrganizationResponse createOrganization(
            CreateOrganizationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return organizationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createOrganization(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ORGANIZATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Organization Service updateOrganization() api.
     *
     * @param request                GetOrganizationDetailsByIdRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetOrganizationDetailsByIdResponse getOrganizationDetailsById(
            GetOrganizationDetailsByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return organizationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getOrganizationDetailsById(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ORGANIZATION_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Organization Service updateOrganization() api.
     *
     * @param request                UpdateOrganizationRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateOrganizationResponse updateOrganization(
            UpdateOrganizationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return organizationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateOrganization(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ORGANIZATION_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call IAM Organization Service listOrganizations() api.
     *
     * @param request                Empty to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListOrganizationsResponse listOrganizations(Empty request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return organizationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listOrganizations(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ORGANIZATION_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
