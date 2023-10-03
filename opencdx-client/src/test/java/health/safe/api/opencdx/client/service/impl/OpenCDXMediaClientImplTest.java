package health.safe.api.opencdx.client.service.impl;

import cdx.media.v2alpha.*;
import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.client.service.OpenCDXMediaClient;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

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

        Assertions.assertEquals(CreateMediaResponse.getDefaultInstance(), this.openCDXMediaClient.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void createMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.createMedia(Mockito.any(CreateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void deleteMedia() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenReturn(DeleteMediaResponse.getDefaultInstance());

        Assertions.assertEquals(DeleteMediaResponse.getDefaultInstance(), this.openCDXMediaClient.deleteMedia(DeleteMediaRequest.getDefaultInstance()));
    }


    @Test
    void deleteMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.deleteMedia(Mockito.any(DeleteMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.deleteMedia(DeleteMediaRequest.getDefaultInstance()));
    }

    @Test
    void getMedia() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenReturn(GetMediaResponse.getDefaultInstance());

        Assertions.assertEquals(GetMediaResponse.getDefaultInstance(), this.openCDXMediaClient.getMedia(GetMediaRequest.getDefaultInstance()));
    }


    @Test
    void getMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.getMedia(Mockito.any(GetMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.getMedia(GetMediaRequest.getDefaultInstance()));
    }

    @Test
    void updateMedia() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenReturn(UpdateMediaResponse.getDefaultInstance());

        Assertions.assertEquals(UpdateMediaResponse.getDefaultInstance(), this.openCDXMediaClient.updateMedia(UpdateMediaRequest.getDefaultInstance()));
    }

    @Test
    void updateMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.updateMedia(Mockito.any(UpdateMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.updateMedia(UpdateMediaRequest.getDefaultInstance()));
    }

    @Test
    void listMedia() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenReturn(ListMediaResponse.getDefaultInstance());

        Assertions.assertEquals(ListMediaResponse.getDefaultInstance(), this.openCDXMediaClient.listMedia(ListMediaRequest.getDefaultInstance()));
    }


    @Test
    void listMediaException() {
        Mockito.when(this.mediaServiceBlockingStub.listMedia(Mockito.any(ListMediaRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXMediaClient.listMedia(ListMediaRequest.getDefaultInstance()));
    }
}