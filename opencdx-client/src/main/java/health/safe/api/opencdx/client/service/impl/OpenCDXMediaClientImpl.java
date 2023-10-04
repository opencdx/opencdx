/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.client.service.impl;

import cdx.media.v2alpha.*;
import com.google.rpc.Code;
import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.client.service.OpenCDXMediaClient;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Observed(name = "opencdx")
public class OpenCDXMediaClientImpl implements OpenCDXMediaClient {

    private static final String DOMAIN = "OpenCDXMediaClientImpl";
    private final MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;

    public OpenCDXMediaClientImpl(MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub) {
        this.mediaServiceBlockingStub = mediaServiceBlockingStub;
    }

    public CreateMediaResponse createMedia(CreateMediaRequest request) {
        try {
            log.info("Processing Create Media: {}", request);
            return mediaServiceBlockingStub.createMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);

            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    public DeleteMediaResponse deleteMedia(DeleteMediaRequest request) {
        try {
            log.info("Processing Delete Media: {}", request);
            return mediaServiceBlockingStub.deleteMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    public GetMediaResponse getMedia(GetMediaRequest request) {
        try {
            log.info("Processing Get Media: {}", request);
            return mediaServiceBlockingStub.getMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {
        try {
            log.info("Processing Update Media: {}", request);
            return mediaServiceBlockingStub.updateMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }

    public ListMediaResponse listMedia(ListMediaRequest request) {
        try {
            log.info("Processing List Media: {}", request);
            return mediaServiceBlockingStub.listMedia(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 5, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
