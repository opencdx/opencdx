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
package cdx.opencdx.health.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.LabFindings;
import cdx.opencdx.health.service.OpenCDXConnectedLabService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class OpenCDXConnectedLabMessageHandlerTest {
    private OpenCDXConnectedLabService openCDXConnectedLabService;
    private ObjectMapper objectMapper;
    OpenCDXMessageService openCDXMessageService;

    @Test
    void test() {
        openCDXConnectedLabService = mock(OpenCDXConnectedLabService.class);
        objectMapper = mock(ObjectMapper.class);
        openCDXMessageService = mock(OpenCDXMessageService.class);
        OpenCDXConnectedLabMessageHandler openCDXConnectedLabMessageHandler =
                new OpenCDXConnectedLabMessageHandler(openCDXMessageService, openCDXConnectedLabService, objectMapper);
        openCDXConnectedLabMessageHandler.receivedMessage(new byte[0]);
        assertNotNull(openCDXConnectedLabMessageHandler);
    }

    @Test
    void testException() throws IOException {
        openCDXConnectedLabService = mock(OpenCDXConnectedLabService.class);
        ObjectMapper objectMapper1 = mock(ObjectMapper.class);
        openCDXMessageService = mock(OpenCDXMessageService.class);
        doThrow(IOException.class).when(objectMapper1).readValue("k".getBytes(), LabFindings.class);
        OpenCDXConnectedLabMessageHandler openCDXConnectedLabMessageHandler1 =
                new OpenCDXConnectedLabMessageHandler(openCDXMessageService, openCDXConnectedLabService, objectMapper1);
        assertDoesNotThrow(() -> openCDXConnectedLabMessageHandler1.receivedMessage("k".getBytes()));
    }
}
