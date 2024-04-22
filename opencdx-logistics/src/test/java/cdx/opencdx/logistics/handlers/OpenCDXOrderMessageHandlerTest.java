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
package cdx.opencdx.logistics.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.logistics.service.OpenCDXShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class OpenCDXOrderMessageHandlerTest {
    private ObjectMapper objectMapper;
    OpenCDXMessageService openCDXMessageService;
    OpenCDXShippingService openCDXShippingService;

    @Test
    void test() throws IOException {
        objectMapper = mock(ObjectMapper.class);
        openCDXMessageService = mock(OpenCDXMessageService.class);
        openCDXShippingService = mock(OpenCDXShippingService.class);
        cdx.opencdx.grpc.data.Order order = mock(cdx.opencdx.grpc.data.Order.class);
        when(objectMapper.readValue(new byte[0], cdx.opencdx.grpc.data.Order.class))
                .thenReturn(order);
        OpenCDXOrderMessageHandler openCDXOrderMessageHandler =
                new OpenCDXOrderMessageHandler(objectMapper, openCDXShippingService, openCDXMessageService);
        openCDXOrderMessageHandler.receivedMessage(new byte[0]);
        assertNotNull(openCDXOrderMessageHandler);
    }

    @Test
    void testException() throws IOException {
        ObjectMapper objectMapper1 = mock(ObjectMapper.class);
        openCDXMessageService = mock(OpenCDXMessageService.class);
        openCDXShippingService = mock(OpenCDXShippingService.class);
        doThrow(IOException.class).when(objectMapper1).readValue("k".getBytes(), cdx.opencdx.grpc.data.Order.class);
        OpenCDXOrderMessageHandler openCDXOrderMessageHandler =
                new OpenCDXOrderMessageHandler(objectMapper1, openCDXShippingService, openCDXMessageService);
        assertDoesNotThrow(() -> openCDXOrderMessageHandler.receivedMessage("k".getBytes()));
    }
}
