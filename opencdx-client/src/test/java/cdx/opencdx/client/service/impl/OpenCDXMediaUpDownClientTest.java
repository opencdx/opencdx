/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import cdx.opencdx.client.dto.FileUploadResponse;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.rpc.Code;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class OpenCDXMediaUpDownClientTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OpenCDXMediaUpDownClientImpl client;

    public static MockWebServer mockWebServer;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void shutdowm() throws IOException {
        mockWebServer.shutdown();
    }

    // This filter is necessary to change the media type on the response which is wrong and
    // will cause response checking to throw an exception even though the request is
    // returning success. The filter is specified when creating the WebClient instance.
    private ExchangeFilterFunction contentTypeInterceptor() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> Mono.just(ClientResponse.from(
                        clientResponse) // clientResponse  is immutable, so,we create a clone. but from() only clones
                // headers and status code
                .headers(headers -> headers.remove(HttpHeaders.CONTENT_TYPE)) // override the content type
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(clientResponse.body(BodyExtractors.toDataBuffers())) // copy the body as bytes with no processing
                .build()));
    }

    // Create WebClient instance switchable ith or without the filter.
    WebClient initializeWebClient(boolean withFilter) {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        if (withFilter) {
            return WebClient.builder()
                    .filter(contentTypeInterceptor())
                    .baseUrl(baseUrl)
                    .build();
        } else {
            return WebClient.builder().baseUrl(baseUrl).build();
        }
    }

    @Test
    void testMediaWebClientUploadSuccess() throws JsonProcessingException {
        client = new OpenCDXMediaUpDownClientImpl(initializeWebClient(true));
        FileUploadResponse response = new FileUploadResponse(true);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        FileUploadResponse theResponse = client.upload("file", "fileid");
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testMediaWebClientUploadFailed() throws JsonProcessingException {
        client = new OpenCDXMediaUpDownClientImpl(initializeWebClient(true));
        FileUploadResponse response = new FileUploadResponse(false);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        FileUploadResponse theResponse = client.upload("file", "fileid");
        Assertions.assertFalse(theResponse.isSuccess());
    }

    @Test
    void testMediaWebClientUploadException() throws JsonProcessingException {
        client = new OpenCDXMediaUpDownClientImpl(initializeWebClient(false));
        FileUploadResponse response = new FileUploadResponse(true);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.upload("file", "fileid"));
    }

    @Test
    void testMediaWebClientUploadOpenCDXClientException() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXMediaUpDownClientImpl(webClient);
        FileUploadResponse response = new FileUploadResponse(true);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.upload("file", "fileid"));
    }

    @Test
    void testMediaWebClientDownloadSuccess() throws JsonProcessingException {
        client = new OpenCDXMediaUpDownClientImpl(initializeWebClient(true));
        Resource response = mock(InputStreamResource.class);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        Resource theResponse = client.download("fileid", "ext");
        Assertions.assertNotNull(theResponse);
    }

    @Test
    void testMediaWebClientDownloadException() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXMediaUpDownClientImpl(webClient);
        Resource response = mock(InputStreamResource.class);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        Mockito.when(webClient.post())
                .thenThrow(new WebClientResponseException(
                        500, "dummy", new HttpHeaders(), new byte[1], Charset.defaultCharset()));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.download("fileid", "ext"));
    }

    @Test
    void testMediaWebClientDownloadOpenCDXClientException() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXMediaUpDownClientImpl(webClient);
        Resource response = mock(InputStreamResource.class);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response)));
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.download("fileid", "ext"));
    }
}
