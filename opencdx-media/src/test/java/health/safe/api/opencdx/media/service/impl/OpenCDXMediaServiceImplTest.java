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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
                .then(AdditionalAnswers.returnsFirstArg());

        this.openCDXMediaService = new OpenCDXMediaServiceImpl(openCDXMediaRepository);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() {
        Assertions.assertEquals(
                CreateMediaResponse.getDefaultInstance(),
                this.openCDXMediaService.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void listMedia() {
        Assertions.assertEquals(
                ListMediaResponse.getDefaultInstance(),
                this.openCDXMediaService.listMedia(ListMediaRequest.getDefaultInstance()));
    }

    @Test
    void getMedia() {
        Assertions.assertEquals(
                GetMediaResponse.getDefaultInstance(),
                this.openCDXMediaService.getMedia(GetMediaRequest.getDefaultInstance()));
    }

    @Test
    void updateMedia() {
        Assertions.assertEquals(
                UpdateMediaResponse.getDefaultInstance(),
                this.openCDXMediaService.updateMedia(UpdateMediaRequest.getDefaultInstance()));
    }

    @Test
    void deleteMedia() {
        Assertions.assertEquals(
                DeleteMediaResponse.getDefaultInstance(),
                this.openCDXMediaService.deleteMedia(DeleteMediaRequest.getDefaultInstance()));
    }
}
