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
package cdx.opencdx.media.controller;

import cdx.opencdx.grpc.media.*;
import cdx.opencdx.media.service.OpenCDXMediaService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Media Service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcMediaController extends MediaServiceGrpc.MediaServiceImplBase {

    private final OpenCDXMediaService openCDXMediaService;
    /**
     * Constructor using the OpenCDXMediaService
     * @param openCDXMediaService Media Service used for processing.
     */
    @Autowired
    public OpenCDXGrpcMediaController(OpenCDXMediaService openCDXMediaService) {
        this.openCDXMediaService = openCDXMediaService;
    }

    @Secured({})
    @Override
    public void createMedia(CreateMediaRequest request, StreamObserver<CreateMediaResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMediaService.createMedia(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listMedia(ListMediaRequest request, StreamObserver<ListMediaResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMediaService.listMedia(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getMedia(GetMediaRequest request, StreamObserver<GetMediaResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMediaService.getMedia(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateMedia(UpdateMediaRequest request, StreamObserver<UpdateMediaResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMediaService.updateMedia(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteMedia(DeleteMediaRequest request, StreamObserver<DeleteMediaResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMediaService.deleteMedia(request));
        responseObserver.onCompleted();
    }
}
