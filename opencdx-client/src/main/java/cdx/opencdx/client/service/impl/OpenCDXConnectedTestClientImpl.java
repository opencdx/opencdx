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

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.grpc.connected.*;
import com.google.rpc.Code;
import java.util.ArrayList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * WebClient for Connected tests.
 */
public class OpenCDXConnectedTestClientImpl implements OpenCDXConnectedTestClient {

    private static final String DOMAIN = "OpenCDXConnectedTestClient";

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";

    private final WebClient webClient;

    /**
     * Constructor using WebClient
     *
     * @param webClient WebClient to be used.
     */
    public OpenCDXConnectedTestClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest) throws OpenCDXClientException {
        try {
            return webClient
                    .post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(connectedTest)
                    .retrieve()
                    .bodyToMono(TestSubmissionResponse.class)
                    .block();
        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = INTERNAL_SERVER_ERROR_MESSAGE;
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 2, message, new ArrayList<>(), ex);
        }
    }

    @Override
    public ConnectedTest getTestDetailsById(String id) throws OpenCDXClientException {
        try {
            return webClient
                    .get()
                    .uri("/" + id)
                    .retrieve()
                    .bodyToMono(ConnectedTest.class)
                    .block();

        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = INTERNAL_SERVER_ERROR_MESSAGE;
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 2, message, new ArrayList<>(), ex);
        }
    }

    @Override
    public ConnectedTestListResponse listConnectedTests(ConnectedTestListRequest connectedTestListRequest)
            throws OpenCDXClientException {
        try {
            return webClient
                    .post()
                    .uri("/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(connectedTestListRequest)
                    .retrieve()
                    .bodyToMono(ConnectedTestListResponse.class)
                    .block();

        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = INTERNAL_SERVER_ERROR_MESSAGE;
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 2, message, new ArrayList<>(), ex);
        }
    }

    @Override
    public ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest) throws OpenCDXClientException {
        try {
            return webClient
                    .post()
                    .uri("/listbynhid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(connectedTestListByNHIDRequest)
                    .retrieve()
                    .bodyToMono(ConnectedTestListByNHIDResponse.class)
                    .block();

        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = INTERNAL_SERVER_ERROR_MESSAGE;
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 2, message, new ArrayList<>(), ex);
        }
    }
}
