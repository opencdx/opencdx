package cdx.opencdx.media.service.impl;

import cdx.media.v2alpha.*;
import cdx.opencdx.media.service.OpenCDXMediaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXMediaServiceImplTest {
    OpenCDXMediaService openCDXMediaService;

    @BeforeEach
    void setUp() {
        this.openCDXMediaService = new OpenCDXMediaServiceImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createMedia() {
        Assertions.assertEquals(CreateMediaResponse.getDefaultInstance(),this.openCDXMediaService.createMedia(CreateMediaRequest.getDefaultInstance()));
    }

    @Test
    void listMedia() {
        Assertions.assertEquals(ListMediaResponse.getDefaultInstance(),this.openCDXMediaService.listMedia(ListMediaRequest.getDefaultInstance()));
    }

    @Test
    void getMedia() {
        Assertions.assertEquals(GetMediaResponse.getDefaultInstance(),this.openCDXMediaService.getMedia(GetMediaRequest.getDefaultInstance()));
    }

    @Test
    void updateMedia() {
        Assertions.assertEquals(UpdateMediaResponse.getDefaultInstance(),this.openCDXMediaService.updateMedia(UpdateMediaRequest.getDefaultInstance()));
    }

    @Test
    void deleteMedia() {
        Assertions.assertEquals(DeleteMediaResponse.getDefaultInstance(),this.openCDXMediaService.deleteMedia(DeleteMediaRequest.getDefaultInstance()));
    }
}