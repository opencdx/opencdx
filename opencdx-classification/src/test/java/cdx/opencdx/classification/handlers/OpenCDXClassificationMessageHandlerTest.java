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
package cdx.opencdx.classification.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * This class tests the OpenCDXClassificationMessageHandler class, specifically the `receivedMessage` method.
 * The `receivedMessage` method is responsible for deserializing received messages from bytes into a
 * a `ClassificationRequest` object, logging if the event is received, and calling the `classify`
 * method of `OpenCDXClassificationService` with the `ClassificationRequest` object.
 */
class OpenCDXClassificationMessageHandlerTest {

    @Test
    void testReceivedMessage_validBytePayload_successProcessing() throws IOException {
        // arrange
        var objectMapper = mock(com.fasterxml.jackson.databind.ObjectMapper.class);
        var openCDXClassificationService = mock(OpenCDXClassificationService.class);
        var openCDXMessageService = mock(OpenCDXMessageService.class);

        byte[] validPayload = "bytes in string form for representation".getBytes();
        ClassificationRequest classificationRequest =
                ClassificationRequest.getDefaultInstance(); // actual object creation might vary

        when(objectMapper.readValue(validPayload, ClassificationRequest.class)).thenReturn(classificationRequest);

        OpenCDXClassificationMessageHandler handler = new OpenCDXClassificationMessageHandler(
                objectMapper, openCDXClassificationService, openCDXMessageService);

        // act
        assertDoesNotThrow(() -> handler.receivedMessage(validPayload));

        // assert
        verify(openCDXClassificationService, times(1)).classify(classificationRequest);
    }

    @Test
    void testReceivedMessage_invalidBytePayload_exceptionCaughtSuccessfully() throws IOException {
        // arrange
        var objectMapper = mock(com.fasterxml.jackson.databind.ObjectMapper.class);
        var openCDXClassificationService = mock(OpenCDXClassificationService.class);
        var openCDXMessageService = mock(OpenCDXMessageService.class);

        byte[] invalidPayload = "invalid bytes in string form".getBytes();

        when(objectMapper.readValue(invalidPayload, ClassificationRequest.class))
                .thenThrow(JsonProcessingException.class);

        OpenCDXClassificationMessageHandler handler = new OpenCDXClassificationMessageHandler(
                objectMapper, openCDXClassificationService, openCDXMessageService);

        // act
        assertDoesNotThrow(() -> handler.receivedMessage(invalidPayload));

        // assert
        verify(openCDXClassificationService, never()).classify(any());
    }
}
