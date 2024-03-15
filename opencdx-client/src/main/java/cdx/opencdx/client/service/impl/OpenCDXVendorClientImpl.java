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
import cdx.opencdx.client.service.OpenCDXVendorClient;
import cdx.opencdx.grpc.inventory.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Vendor gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXVendorClientImpl implements OpenCDXVendorClient {

    private static final String OPEN_CDX_VENDOR_CLIENT_IMPL = "OpenCDXVendorClientImpl";
    private final VendorServiceGrpc.VendorServiceBlockingStub vendorServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXVendorClientImpl(ManagedChannel channel) {
        this.vendorServiceBlockingStub = VendorServiceGrpc.newBlockingStub(channel);
    }
    /**
     * Constructor for creating the Vendor client implementation.
     * @param vendorServiceBlockingStub gRPC Blocking Stub for Country.
     */
    public OpenCDXVendorClientImpl(VendorServiceGrpc.VendorServiceBlockingStub vendorServiceBlockingStub) {
        this.vendorServiceBlockingStub = vendorServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Vendor Service getVendorById() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Vendor getVendorById(VendorIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return vendorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getVendorById(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VENDOR_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Vendor Service addVendor() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Vendor addVendor(Vendor request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return vendorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .addVendor(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VENDOR_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Vendor Service updateVendor() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Vendor updateVendor(Vendor request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return vendorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateVendor(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VENDOR_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Vendor Service deleteVendor() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteResponse deleteVendor(VendorIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return vendorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteVendor(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VENDOR_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Vendor Service listVendors() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public VendorsListResponse listVendors(VendorsListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return vendorServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listVendors(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VENDOR_CLIENT_IMPL,
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
