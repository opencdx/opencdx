package cdx.opencdx.media.controller;

import cdx.media.v2alpha.*;
import cdx.open_connected_test.v2alpha.BasicInfo;
import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import cdx.opencdx.media.service.OpenCDXMediaService;
import cdx.opencdx.media.service.impl.OpenCDXMediaServiceImpl;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    void tearDown() {
    }

    @Test
    void createMedia() {
        StreamObserver<CreateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.createMedia(CreateMediaRequest.getDefaultInstance(),responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(CreateMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listMedia() {
        StreamObserver<ListMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.listMedia(ListMediaRequest.getDefaultInstance(),responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(ListMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

    }

    @Test
    void getMedia() {
        StreamObserver<GetMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.getMedia(GetMediaRequest.getDefaultInstance(),responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(GetMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

    }

    @Test
    void updateMedia() {
        StreamObserver<UpdateMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.updateMedia(UpdateMediaRequest.getDefaultInstance(),responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(UpdateMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

    }

    @Test
    void deleteMedia() {
        StreamObserver<DeleteMediaResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcMediaController.deleteMedia(DeleteMediaRequest.getDefaultInstance(),responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(DeleteMediaResponse.getDefaultInstance());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

    }
}