package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.service.OpenCDXAdrMessageService;
import cdx.opencdx.grpc.data.ANFStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenCDXAdrMessageServiceImplTest {

    OpenCDXAdrMessageService openCDXAdrMessageService;

    @BeforeEach
    void setUp() {
        openCDXAdrMessageService = new OpenCDXAdrMessageServiceImpl(new NoOpOpenCDXMessageServiceImpl());
    }

    @Test
    void sendAdrMessage() {
        Assertions.assertDoesNotThrow(() -> openCDXAdrMessageService.sendAdrMessage(ANFStatement.getDefaultInstance()));
    }
}