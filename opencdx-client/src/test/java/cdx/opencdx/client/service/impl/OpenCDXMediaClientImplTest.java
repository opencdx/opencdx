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
        Mockito.when(this.mediaServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.mediaServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.mediaServiceBlockingStub);
    }

    @Test
    void createMedia() {
        Mockito.when(this.mediaServiceBlockingStub.createMedia(Mockito.any(CreateMediaRequest.class)))
                .thenReturn(CreateMediaResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.createMedia(CreateMediaRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.createMedia(Mockito.any(CreateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        CreateMediaRequest request = CreateMediaRequest.getDefaultInstance();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMediaClient.createMedia(request, openCDXCallCredentials));
    }

    @Test
    void deleteMedia() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenReturn(DeleteMediaResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.deleteMedia(DeleteMediaRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        DeleteMediaRequest request = DeleteMediaRequest.getDefaultInstance();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMediaClient.deleteMedia(request, openCDXCallCredentials));
    }

    @Test
    void getMedia() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenReturn(GetMediaResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.getMedia(GetMediaRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetMediaRequest request = GetMediaRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.openCDXMediaClient.getMedia(request, openCDXCallCredentials));
    }

    @Test
    void updateMedia() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenReturn(UpdateMediaResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.updateMedia(UpdateMediaRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateMediaRequest request = UpdateMediaRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMediaClient.updateMedia(request, openCDXCallCredentials));
    }

    @Test
    void listMedia() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenReturn(ListMediaResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMediaResponse.getDefaultInstance(),
                this.openCDXMediaClient.listMedia(ListMediaRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMediaRequest request = ListMediaRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.openCDXMediaClient.listMedia(request, openCDXCallCredentials));
    }
}
