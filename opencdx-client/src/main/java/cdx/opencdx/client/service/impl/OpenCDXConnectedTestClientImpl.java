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
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.grpc.connected.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Open CDX gRPC Connected Test Client
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXConnectedTestClientImpl implements OpenCDXConnectedTestClient {

    private static final String DOMAIN = "OpenCDXConnectedTestClientImpl";

    private final HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXConnectedTestClientImpl(ManagedChannel channel) {
        this.healthcareServiceBlockingStub = HealthcareServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the OpenCDXConnectedTestClientImpl
     * @param healthcareServiceBlockingStub gRPC Stub for the client.
     */
    public OpenCDXConnectedTestClientImpl(
            HealthcareServiceGrpc.HealthcareServiceBlockingStub healthcareServiceBlockingStub) {
        this.healthcareServiceBlockingStub = healthcareServiceBlockingStub;
    }

    @Override
    public TestSubmissionResponse submitTest(
            ConnectedTest connectedTest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing submit test: {}", connectedTest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .submitTest(connectedTest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTest getTestDetailsById(
            TestIdRequest testIdRequest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing test details by Id: {}", testIdRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTestDetailsById(testIdRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(
            ConnectedTestListRequest connectedTestListRequest, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing listConnectedTests: {}", connectedTestListRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listConnectedTests(connectedTestListRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest,
            OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing listConnectedTestsByNHID: {}", connectedTestListByNHIDRequest);
            return healthcareServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listConnectedTestsByNHID(connectedTestListByNHIDRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
