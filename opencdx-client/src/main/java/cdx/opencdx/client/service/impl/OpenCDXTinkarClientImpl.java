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
import cdx.opencdx.client.service.OpenCDXTinkarClient;
import cdx.opencdx.grpc.service.tinkar.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Tinkar gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXTinkarClientImpl implements OpenCDXTinkarClient {

    private final TinkarQueryServiceGrpc.TinkarQueryServiceBlockingStub tinkarQueryServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXTinkarClientImpl(ManagedChannel channel) {
        this.tinkarQueryServiceBlockingStub = TinkarQueryServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Tinkar client implementation.
     * @param tinkarQueryServiceBlockingStub gRPC Blocking Stub for Tinkar.
     */
    public OpenCDXTinkarClientImpl(
            TinkarQueryServiceGrpc.TinkarQueryServiceBlockingStub tinkarQueryServiceBlockingStub) {
        this.tinkarQueryServiceBlockingStub = tinkarQueryServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Tinkar Service searchTinkar() api.
     * @param request TinkarQueryRequest request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return TinkarQueryResponse
     */
    @Override
    public TinkarSearchQueryResponse searchTinkar(
            TinkarSearchQueryRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return tinkarQueryServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .searchTinkar(request);
        } catch (StatusRuntimeException e) {
            throw createClientException(e);
        }
    }

    /**
     * Method to gRPC Call Tinkar Service getTinkarEntity() api.
     * @param request TinkarGetResult request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return TinkarQueryResult
     */
    @Override
    public TinkarGetResult getTinkarEntity(TinkarGetRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return tinkarQueryServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTinkarEntity(request);
        } catch (StatusRuntimeException e) {
            throw createClientException(e);
        }
    }

    @Override
    public TinkarGetResponse getTinkarChildConcepts(
            TinkarGetRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return tinkarQueryServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTinkarChildConcepts(request);
        } catch (StatusRuntimeException e) {
            throw createClientException(e);
        }
    }

    @Override
    public TinkarGetResponse getTinkarDescendantConcepts(
            TinkarGetRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return tinkarQueryServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getTinkarDescendantConcepts(request);
        } catch (StatusRuntimeException e) {
            throw createClientException(e);
        }
    }

    private OpenCDXClientException createClientException(StatusRuntimeException e) {
        com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
        return new OpenCDXClientException(
                Code.forNumber(status.getCode()),
                "OpenCDXTinkarClientImpl",
                1,
                status.getMessage(),
                status.getDetailsList(),
                e);
    }
}
