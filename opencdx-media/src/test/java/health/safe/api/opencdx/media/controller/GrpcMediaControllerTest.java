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
package health.safe.api.opencdx.media.controller;

import cdx.media.v2alpha.*;
import health.safe.api.opencdx.media.controller.GrpcMediaController;
import health.safe.api.opencdx.media.service.OpenCDXMediaService;
import health.safe.api.opencdx.media.service.impl.OpenCDXMediaServiceImpl;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class GrpcMediaControllerTest {

    OpenCDXMediaService openCDXMediaService;

    GrpcMediaController grpcMediaController;

    @BeforeEach
    void setUp() {
        this.openCDXMediaService = new OpenCDXMediaServiceImpl();
        this.grpcMediaController = new GrpcMediaController(this.openCDXMediaService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() {
        StreamObserver<CreateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.createMedia(CreateMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(CreateMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listMedia() {
        StreamObserver<ListMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.listMedia(ListMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(ListMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedia() {
        StreamObserver<GetMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.getMedia(GetMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(GetMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateMedia() {
        StreamObserver<UpdateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.updateMedia(UpdateMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(UpdateMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteMedia() {
        StreamObserver<DeleteMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.deleteMedia(DeleteMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(DeleteMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
