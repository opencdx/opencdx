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
package cdx.opencdx.client.service.impl;

import cdx.media.v2alpha.*;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXMediaClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXMediaClientImplTest {

    @Mock
    MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;

    OpenCDXMediaClient openCDXMediaClient;

    @BeforeEach
    void setUp() {
        this.mediaServiceBlockingStub = Mockito.mock(MediaServiceGrpc.MediaServiceBlockingStub.class);
        this.openCDXMediaClient = new OpenCDXMediaClientImpl(this.mediaServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.mediaServiceBlockingStub);
    }

    @Test
    void createMedia() {
        Mockito.when(this.mediaServiceBlockingStub.createMedia(Mockito.any(CreateMediaRequest.class)))
                .thenReturn(CreateMediaResponse.getDefaultInstance());

        Assertions.assertEquals(
                CreateMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void createMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.createMedia(Mockito.any(CreateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        CreateMediaRequest request = CreateMediaRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.createMedia(request));
    }

    @Test
    void deleteMedia() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenReturn(DeleteMediaResponse.getDefaultInstance());

        Assertions.assertEquals(
                DeleteMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.deleteMedia(DeleteMediaRequest.getDefaultInstance()));
    }

    @Test
    void deleteMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        DeleteMediaRequest request = DeleteMediaRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.deleteMedia(request));
    }

    @Test
    void getMedia() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenReturn(GetMediaResponse.getDefaultInstance());

        Assertions.assertEquals(
                GetMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.getMedia(GetMediaRequest.getDefaultInstance()));
    }

    @Test
    void getMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetMediaRequest request = GetMediaRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.getMedia(request));
    }

    @Test
    void updateMedia() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenReturn(UpdateMediaResponse.getDefaultInstance());

        Assertions.assertEquals(
                UpdateMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.updateMedia(UpdateMediaRequest.getDefaultInstance()));
    }

    @Test
    void updateMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateMediaRequest request = UpdateMediaRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.updateMedia(request));
    }

    @Test
    void listMedia() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenReturn(ListMediaResponse.getDefaultInstance());

        Assertions.assertEquals(
                ListMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.listMedia(ListMediaRequest.getDefaultInstance()));
    }

    @Test
    void listMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMediaRequest request = ListMediaRequest.getDefaultInstance();

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.listMedia(request));
    }
}
