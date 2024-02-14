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

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpenCDXClassificationMessageHandlerTest {

    ObjectMapper objectMapper;
    OpenCDXClassificationService openCDXClassificationService;
    OpenCDXMessageService openCDXMessageService;

    @Test
    void receivedMessage() throws JsonProcessingException {
        openCDXMessageService = Mockito.mock(OpenCDXMessageService.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        OpenCDXClassificationMessageHandler openCDXClassificationMessageHandler =
                new OpenCDXClassificationMessageHandler(
                        objectMapper, openCDXClassificationService, openCDXMessageService);
        byte[] msg = "Success".getBytes();
        Mockito.verify(objectMapper, Mockito.times(0)).readValue("test", String.class);
        openCDXClassificationMessageHandler.receivedMessage(msg);
    }

    @Test
    void recievedTest() throws JsonProcessingException {
        openCDXMessageService = Mockito.mock(OpenCDXMessageService.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        openCDXClassificationService = Mockito.mock(OpenCDXClassificationService.class);
        OpenCDXClassificationMessageHandler openCDXClassificationMessageHandler =
                new OpenCDXClassificationMessageHandler(
                        objectMapper, openCDXClassificationService, openCDXMessageService);
        byte[] msg = "Success".getBytes();
        ClassificationRequest request = Mockito.mock(ClassificationRequest.class);

        openCDXClassificationMessageHandler.receivedMessage(msg);
        Mockito.verify(objectMapper, Mockito.times(0)).readValue("test", String.class);
    }
}
