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

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.grpc.media.*;
import cdx.opencdx.media.model.OpenCDXMediaModel;
import cdx.opencdx.media.repository.OpenCDXMediaRepository;
import cdx.opencdx.media.service.OpenCDXMediaService;
import cdx.opencdx.media.service.impl.OpenCDXMediaServiceImpl;
import io.grpc.stub.StreamObserver;
import java.util.Collections;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXGrpcMediaControllerTest {

    OpenCDXMediaService openCDXMediaService;

    OpenCDXGrpcMediaController openCDXGrpcMediaController;

    @Mock
    OpenCDXMediaRepository openCDXMediaRepository;

    @BeforeEach
    void setUp() {
        this.openCDXMediaRepository = Mockito.mock(OpenCDXMediaRepository.class);
        Mockito.when(this.openCDXMediaRepository.save(Mockito.any(OpenCDXMediaModel.class)))
                .thenAnswer(new Answer<OpenCDXMediaModel>() {
                    @Override
                    public OpenCDXMediaModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMediaModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMediaModel>>() {
                    @Override
                    public Optional<OpenCDXMediaModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXMediaModel.builder().id(argument).build());
                    }
                });

        this.openCDXMediaService = new OpenCDXMediaServiceImpl(openCDXMediaRepository);
        this.openCDXGrpcMediaController = new OpenCDXGrpcMediaController(this.openCDXMediaService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() {
        StreamObserver<CreateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcMediaController.createMedia(CreateMediaRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateMediaResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listMedia() {
        StreamObserver<ListMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXMediaRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        this.openCDXGrpcMediaController.listMedia(
                ListMediaRequest.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListMediaResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedia() {
        StreamObserver<GetMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcMediaController.getMedia(
                GetMediaRequest.newBuilder().setId(ObjectId.get().toHexString()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetMediaResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMediaFail_1() {
        Mockito.reset(this.openCDXMediaRepository);
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        StreamObserver<GetMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetMediaRequest request =
                GetMediaRequest.newBuilder().setId(ObjectId.get().toHexString()).build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXGrpcMediaController.getMedia(request, responseObserver));
    }

    @Test
    void updateMedia() {
        StreamObserver<UpdateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcMediaController.updateMedia(
                UpdateMediaRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateMediaResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateMediaFail_1() {
        Mockito.reset(this.openCDXMediaRepository);
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        StreamObserver<UpdateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        UpdateMediaRequest request = UpdateMediaRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXGrpcMediaController.updateMedia(request, responseObserver));
    }

    @Test
    void deleteMedia() {
        StreamObserver<DeleteMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcMediaController.deleteMedia(
                DeleteMediaRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteMediaResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteMediaFail_1() {
        Mockito.reset(this.openCDXMediaRepository);
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        StreamObserver<DeleteMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteMediaRequest request = DeleteMediaRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXGrpcMediaController.deleteMedia(request, responseObserver));
    }
}
