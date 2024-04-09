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
import cdx.opencdx.client.service.OpenCDXBPMClient;
import cdx.opencdx.grpc.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the BPM Measurement gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXBPMClientImpl implements OpenCDXBPMClient {
    private static final String OPEN_CDX_BPM_CLIENT_IMPL = "OpenCDXBPMClientImpl";
    private final BPMServiceGrpc.BPMServiceBlockingStub bpmServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXBPMClientImpl(ManagedChannel channel) {
        this.bpmServiceBlockingStub = BPMServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the BPM client implementation.
     * @param bpmServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXBPMClientImpl(BPMServiceGrpc.BPMServiceBlockingStub bpmServiceBlockingStub) {
        this.bpmServiceBlockingStub = bpmServiceBlockingStub;
    }

    /**
     * Method to gRPC Call bpm createBPM() api.
     *
     * @param request                CreateBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateBPMResponse createBPMMeasurement(
            CreateBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return bpmServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createBPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_BPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call BPM Service getBPM() api.
     *
     * @param request                GetBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetBPMResponse getBPMMeasurement(GetBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return bpmServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getBPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_BPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call BPM Service updateBPM() api.
     *
     * @param request                UpdateBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateBPMResponse updateBPMMeasurement(
            UpdateBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return bpmServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateBPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_BPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call BPM Service deleteBPM() api.
     *
     * @param request                DeleteBPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SuccessResponse deleteBPMMeasurement(DeleteBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return bpmServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteBPMMeasurement(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_BPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call BPM Service listBPMs() api.
     *
     * @param request                ListBPMsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListBPMResponse listBPMMeasurements(ListBPMRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return bpmServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listBPMMeasurements(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_BPM_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
