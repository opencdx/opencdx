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
import static org.springframework.http.MediaType.*;

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.rpc.Code;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class OpenCDXConnectedTestClientTest {

    public static MockWebServer mockWebServer;
    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private OpenCDXConnectedTestClientImpl client;

    @BeforeEach
    void setup() throws IOException {
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new JsonMixinModule());
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
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            return Mono.just(ClientResponse.from(
                            clientResponse) // clientResponse  is immutable, so,we create a clone. but from() only
                    // clones
                    // headers and status code
                    .headers(headers -> headers.remove(HttpHeaders.CONTENT_TYPE))
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .body(clientResponse.body(
                            BodyExtractors.toDataBuffers())) // copy the body as bytes with no processing
                    .build());
        });
    }

    // Create WebClient instance switchable ith or without the filter.
    WebClient initializeWebClient(boolean withFilter) {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        if (withFilter) {
            return WebClient.builder()
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .clientConnector(new ReactorClientHttpConnector())
                    .filter(contentTypeInterceptor())
                    .baseUrl(baseUrl)
                    .exchangeStrategies(ExchangeStrategies.builder()
                            .codecs(this::acceptedCodecs)
                            .build())
                    .build();
        } else {
            return WebClient.builder().baseUrl(baseUrl).build();
        }
    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer.customCodecs().register(new Jackson2JsonEncoder(mapper, APPLICATION_JSON));
        clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(mapper, APPLICATION_JSON));
    }

    @Test
    void testSubmitTestSuccess() throws JsonProcessingException {
        client = new OpenCDXConnectedTestClientImpl(initializeWebClient(true));
        TestSubmissionResponse response =
                TestSubmissionResponse.newBuilder().setSubmissionId("test").build();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId("test")
                        .setNationalHealthId(10)
                        .setUserId("test")
                        .build())
                .setOrderInfo(OrderInfo.newBuilder()
                        .setOrderId("test")
                        .setEncounterId("test")
                        .build())
                .setTestNotes(TestNotes.newBuilder().setNotes("test").build())
                .setTestDetails(
                        TestDetails.newBuilder().setInternalTestId("test").build())
                .setProviderInfo(
                        ProviderInfo.newBuilder().setAssignedProviderId(1).build())
                .setPaymentDetails(
                        PaymentDetails.newBuilder().setPaymentMode("test").build())
                .build();
        Assertions.assertNotNull(client.submitTest(connectedTest));
    }

    @Test
    void testSubmitTestWebClientResponseExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        TestSubmissionResponse response =
                TestSubmissionResponse.newBuilder().setSubmissionId("test").build();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId("test")
                        .setNationalHealthId(10)
                        .setUserId("test")
                        .build())
                .setOrderInfo(OrderInfo.newBuilder()
                        .setOrderId("test")
                        .setEncounterId("test")
                        .build())
                .setTestNotes(TestNotes.newBuilder().setNotes("test").build())
                .setTestDetails(
                        TestDetails.newBuilder().setInternalTestId("test").build())
                .setProviderInfo(
                        ProviderInfo.newBuilder().setAssignedProviderId(1).build())
                .setPaymentDetails(
                        PaymentDetails.newBuilder().setPaymentMode("test").build())
                .build();
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new WebClientResponseException(
                        500, "dummy", new HttpHeaders(), new byte[1], Charset.defaultCharset()));

        Assertions.assertThrows(OpenCDXClientException.class, () -> client.submitTest(connectedTest));
    }

    @Test
    void testSubmitTestOpenCDXClientExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        TestSubmissionResponse response =
                TestSubmissionResponse.newBuilder().setSubmissionId("test").build();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId("test")
                        .setNationalHealthId(10)
                        .setUserId("test")
                        .build())
                .setOrderInfo(OrderInfo.newBuilder()
                        .setOrderId("test")
                        .setEncounterId("test")
                        .build())
                .setTestNotes(TestNotes.newBuilder().setNotes("test").build())
                .setTestDetails(
                        TestDetails.newBuilder().setInternalTestId("test").build())
                .setProviderInfo(
                        ProviderInfo.newBuilder().setAssignedProviderId(1).build())
                .setPaymentDetails(
                        PaymentDetails.newBuilder().setPaymentMode("test").build())
                .build();

        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.submitTest(connectedTest));
    }

    @Test
    void getTestDetailsByIdSuccess() throws JsonProcessingException {
        client = new OpenCDXConnectedTestClientImpl(initializeWebClient(true));
        ConnectedTest response = ConnectedTest.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        Assertions.assertNotNull(client.getTestDetailsById("test"));
    }

    @Test
    void getTestDetailsByIdOpenCDXClientExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTest response = ConnectedTest.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.get())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.getTestDetailsById("test"));
    }

    @Test
    void getTestDetailsByIdWebClientResponseExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTest response = ConnectedTest.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        Mockito.when(webClient.get())
                .thenThrow(new WebClientResponseException(
                        500, "dummy", new HttpHeaders(), new byte[1], Charset.defaultCharset()));
        Assertions.assertThrows(OpenCDXClientException.class, () -> client.getTestDetailsById("test"));
    }

    @Test
    void listConnectedTestsByNHIDSuccess() throws JsonProcessingException {
        client = new OpenCDXConnectedTestClientImpl(initializeWebClient(true));
        ConnectedTestListByNHIDResponse response =
                ConnectedTestListByNHIDResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest =
                ConnectedTestListByNHIDRequest.newBuilder().getDefaultInstanceForType();
        Assertions.assertNotNull(client.listConnectedTestsByNHID(connectedTestListByNHIDRequest));
    }

    @Test
    void listConnectedTestsByNHIDOpenCDXClientExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTestListByNHIDResponse response =
                ConnectedTestListByNHIDResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest =
                ConnectedTestListByNHIDRequest.newBuilder().getDefaultInstanceForType();
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> client.listConnectedTestsByNHID(connectedTestListByNHIDRequest));
    }

    @Test
    void listConnectedTestsByNHIDWebClientResponseExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTestListByNHIDResponse response =
                ConnectedTestListByNHIDResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest =
                ConnectedTestListByNHIDRequest.newBuilder().getDefaultInstanceForType();
        Mockito.when(webClient.post())
                .thenThrow(new WebClientResponseException(
                        500, "dummy", new HttpHeaders(), new byte[1], Charset.defaultCharset()));
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> client.listConnectedTestsByNHID(connectedTestListByNHIDRequest));
    }

    @Test
    void listConnectedTestsSuccess() throws JsonProcessingException {
        client = new OpenCDXConnectedTestClientImpl(initializeWebClient(true));
        ConnectedTestListResponse response =
                ConnectedTestListResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListRequest connectedTestListRequestList =
                ConnectedTestListRequest.newBuilder().getDefaultInstanceForType();
        Assertions.assertNotNull(client.listConnectedTests(connectedTestListRequestList));
    }

    @Test
    void listConnectedTestsOpenCDXClientExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTestListResponse response =
                ConnectedTestListResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListRequest connectedTestListRequestList =
                ConnectedTestListRequest.newBuilder().getDefaultInstanceForType();
        Exception dummy = new Exception("dummy");
        Mockito.when(webClient.post())
                .thenThrow(new OpenCDXClientException(Code.ABORTED, "TEST", 0, "message", new ArrayList<>(), dummy));
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> client.listConnectedTests(connectedTestListRequestList));
    }

    @Test
    void listConnectedTestsWebClientResponseExceptionThrown() throws JsonProcessingException {
        WebClient webClient = mock(WebClient.class);
        client = new OpenCDXConnectedTestClientImpl(webClient);
        ConnectedTestListResponse response =
                ConnectedTestListResponse.newBuilder().getDefaultInstanceForType();
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(response)));
        ConnectedTestListRequest connectedTestListRequestList =
                ConnectedTestListRequest.newBuilder().getDefaultInstanceForType();
        Mockito.when(webClient.post())
                .thenThrow(new WebClientResponseException(
                        500, "dummy", new HttpHeaders(), new byte[1], Charset.defaultCharset()));
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> client.listConnectedTests(connectedTestListRequestList));
    }
}
