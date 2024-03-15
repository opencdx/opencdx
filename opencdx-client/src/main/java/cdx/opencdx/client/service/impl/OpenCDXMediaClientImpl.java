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
import cdx.opencdx.client.service.OpenCDXMediaClient;
import cdx.opencdx.grpc.media.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenCDXMediaClient for talking to the Media Service.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXMediaClientImpl implements OpenCDXMediaClient {

    /**
     * Domain name for exception handling
     */
    private static final String DOMAIN = "OpenCDXMediaClientImpl";

    private final MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXMediaClientImpl(ManagedChannel channel) {
        this.mediaServiceBlockingStub = MediaServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the OpenCDXMediaClientImpl
     * @param mediaServiceBlockingStub gRPC Stub for the client.
     */
    public OpenCDXMediaClientImpl(MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub) {
        this.mediaServiceBlockingStub = mediaServiceBlockingStub;
    }

    @Override
    public CreateMediaResponse createMedia(CreateMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing Create Media: {}", request);
            return mediaServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public DeleteMediaResponse deleteMedia(DeleteMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing Delete Media: {}", request);
            return mediaServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public GetMediaResponse getMedia(GetMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing Get Media: {}", request);
            return mediaServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing Update Media: {}", request);
            return mediaServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public ListMediaResponse listMedia(ListMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.trace("Processing List Media: {}", request);
            return mediaServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 5, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
