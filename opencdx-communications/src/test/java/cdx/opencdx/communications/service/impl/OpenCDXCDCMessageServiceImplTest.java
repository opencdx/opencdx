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
package cdx.opencdx.communications.service.impl;

import static org.mockito.Mockito.*;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.communications.service.OpenCDXCDCMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXCDCMessageServiceImplTest {

    OpenCDXCDCMessageService openCDXCDCMessageService;

    @Mock
    CloseableHttpClient httpClient;

    @Mock
    CloseableHttpResponse response;

    @Mock
    HttpEntity responseEntity;

    @Mock
    StatusLine statusLine;

    @BeforeEach
    void setUp() {
        openCDXCDCMessageService = new OpenCDXCDCMessageServiceImpl("cdcUri", "cdcClient", "cdcKe");
    }

    @Test
    void testSendCDCMessageNull() throws JsonProcessingException {
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> openCDXCDCMessageService.sendCDCMessage(null),
                "Cannot send Empty message");
    }

    @Test
    void testSendCDCMessage() throws IOException {
        try (MockedStatic<HttpClients> httpClients = Mockito.mockStatic(HttpClients.class)) {
            httpClients.when(HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);
            when(response.getEntity()).thenReturn(responseEntity);
            when(response.getStatusLine()).thenReturn(statusLine);
            when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_CREATED);

            openCDXCDCMessageService.sendCDCMessage("CDC Message");

            verify(httpClient).execute(Mockito.any(HttpUriRequest.class));
            verify(response).getEntity();
            verify(response).getStatusLine();
            verify(statusLine).getStatusCode();
        }
    }

    @Test
    void testSendCDCMessageInternalServerError() throws IOException {
        try (MockedStatic<HttpClients> httpClients = Mockito.mockStatic(HttpClients.class)) {
            httpClients.when(HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);
            when(response.getEntity()).thenReturn(responseEntity);
            when(response.getStatusLine()).thenReturn(statusLine);
            when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);

            Assertions.assertThrows(
                    OpenCDXInternalServerError.class, () -> openCDXCDCMessageService.sendCDCMessage("CDC Message"));

            verify(httpClient).execute(Mockito.any(HttpUriRequest.class));
            verify(response).getEntity();
            verify(response, times(2)).getStatusLine();
            verify(statusLine).getStatusCode();
        }
    }

    @Test
    void testSendCDCMessageURISyntaxException() throws IOException {
        ReflectionTestUtils.setField(openCDXCDCMessageService, "cdcUri", "test .com");
        try (MockedStatic<HttpClients> httpClients = Mockito.mockStatic(HttpClients.class)) {
            httpClients.when(HttpClients::createDefault).thenReturn(httpClient);
            Assertions.assertThrows(
                    OpenCDXBadRequest.class,
                    () -> openCDXCDCMessageService.sendCDCMessage("CDC Message"),
                    "Invalid URL Syntax");
        }
    }

    @Test
    void testSendCDCMessageIOException() throws IOException {
        try (MockedStatic<HttpClients> httpClients = Mockito.mockStatic(HttpClients.class)) {
            httpClients.when(HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(IOException.class);

            Assertions.assertThrows(
                    OpenCDXBadRequest.class,
                    () -> openCDXCDCMessageService.sendCDCMessage("CDC Message"),
                    "IOException in sending CDC message");

            verify(httpClient).execute(Mockito.any(HttpUriRequest.class));
        }
    }
}
