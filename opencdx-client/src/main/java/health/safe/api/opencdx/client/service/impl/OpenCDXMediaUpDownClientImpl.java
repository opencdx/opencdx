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
package health.safe.api.opencdx.client.service.impl;

import com.google.rpc.Code;
import health.safe.api.opencdx.client.dto.FileUploadResponse;
import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.client.service.OpenCDXMediaUpDownClient;
import java.util.ArrayList;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// @ExcludeFromJacocoGeneratedReport
public class OpenCDXMediaUpDownClientImpl implements OpenCDXMediaUpDownClient {

    private static final String DOMAIN = "OpenCDXMediaUpDownClient";

    private WebClient webClient;

    public OpenCDXMediaUpDownClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public FileUploadResponse upload(String file, String fileId) throws OpenCDXClientException {

        try {
            return webClient
                    .post()
                    .uri("/upload?file=" + file + "&fileid=" + fileId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FileUploadResponse.class)
                    .block();
        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = "Internal server error";
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 1, message, new ArrayList<>(), ex);
        }
    }

    @Override
    public Resource download(String fileId, String ext) {
        try {
            return webClient
                    .post()
                    .uri("/download?fileid=" + fileId + "." + ext)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Resource.class)
                    .block();

        } catch (Exception ex) {
            Code statusCode = Code.INTERNAL;
            String message = "Internal server error";
            if (ex instanceof WebClientResponseException) {
                message = ex.getMessage();
            }
            throw new OpenCDXClientException(statusCode, DOMAIN, 2, message, new ArrayList<>(), ex);
        }
    }
}
