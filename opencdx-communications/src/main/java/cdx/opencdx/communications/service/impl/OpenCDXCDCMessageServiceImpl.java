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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.communications.service.OpenCDXCDCMessageService;
import io.micrometer.observation.annotation.Observed;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service for processing CDC Notification requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCDCMessageServiceImpl implements OpenCDXCDCMessageService {

    private static final String DOMAIN = "OpenCDXCDCMessageServiceImpl";

    @Override
    public boolean sendCDCMessage(String message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (!StringUtils.hasLength(message)) return false;

            String uri = "https://staging.prime.cdc.gov/api/reports";

            URIBuilder uriBuilder = new URIBuilder(uri);
            StringEntity requestEntity = new StringEntity(message.replace("\n", ""));

            log.info(message);

            HttpUriRequest request = RequestBuilder.post()
                    .setUri(uriBuilder.build())
                    .setEntity(requestEntity)
                    .addHeader("Accept", "application/fhir+ndjson")
                    .addHeader("Content-Type", "application/fhir+ndjson")
                    .addHeader("client", "connectathon.CON_FULL_ELR_SENDER")
                    .addHeader("x-functions-key", "CMVUUt4ySvpmasN55_Kz4Mu1SgzlaETrZbdxU41Si1NmAzFuCwiFLQ==")
                    .build();

            // Execute the request and process the results.
            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                throw new OpenCDXInternalServerError(
                        DOMAIN,
                        1,
                        "Exception sending CDC message "
                                + response.getStatusLine().toString());
            }
            log.debug("FHIR resource bundle sent.");
            log.debug(EntityUtils.toString(responseEntity));
            return true;
        } catch (URISyntaxException e) {
            throw new OpenCDXBadRequest(DOMAIN, 1, "Invalid URL Syntax");
        } catch (IOException e) {
            throw new OpenCDXBadRequest(DOMAIN, 2, "IOException in sending CDC message");
        }
    }
}
