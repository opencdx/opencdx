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
package health.safe.api.opencdx.media.service.impl;

import cdx.media.v2alpha.*;
import health.safe.api.opencdx.media.model.OpenCDXMediaModel;
import health.safe.api.opencdx.media.repository.OpenCDXMediaRepository;
import health.safe.api.opencdx.media.service.OpenCDXMediaService;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;
import java.util.Optional;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXMediaServiceImplTest {
    OpenCDXMediaService openCDXMediaService;

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
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() {
        Assertions.assertDoesNotThrow( () ->
                this.openCDXMediaService.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void listMedia() {
        Mockito.when(this.openCDXMediaRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMediaService.listMedia(ListMediaRequest.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build()));
    }

    @Test
    void getMedia() {
        Assertions.assertDoesNotThrow(() ->
                this.openCDXMediaService.getMedia(GetMediaRequest.newBuilder().setId(ObjectId.get().toHexString()).build()));
    }

    @Test
    void updateMedia() {
        Assertions.assertDoesNotThrow(() ->
                this.openCDXMediaService.updateMedia(UpdateMediaRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build()));
    }

    @Test
    void deleteMedia() {
        Assertions.assertDoesNotThrow(() ->
                this.openCDXMediaService.deleteMedia(DeleteMediaRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build()));
    }
}
