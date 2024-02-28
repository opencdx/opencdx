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

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.communications.service.OpenCDXCDCMessageService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String NOTIFICATION_EVENT = "NOTIFICATION-EVENT: ";

    private final String cdcUri;
    private final String cdcClient;
    private final String cdcKey;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for the OpenCDXCDCMessageServiceImpl
     * @param uri URI of the CDC Notification endpoint
     * @param client Client ID for the CDC Notification endpoint
     * @param key Key for the CDC Notification endpoint
     * @param openCDXAuditService Audit service for recording CDC Notification events
     * @param openCDXCurrentUser Current user service for retrieving the current user
     */
    @Autowired
    public OpenCDXCDCMessageServiceImpl(
            @Value("${cdc.message.uri}") String uri,
            @Value("${cdc.message.client}") String client,
            @Value("${cdc.message.key}") String key,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.cdcUri = uri;
        this.cdcClient = client;
        this.cdcKey = key;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    public void sendCDCMessage(String message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (!StringUtils.hasLength(message)) {
                throw new OpenCDXBadRequest(DOMAIN, 1, "Cannot send Empty message");
            }

            URIBuilder uriBuilder = new URIBuilder(cdcUri);

            String requestString = message.replace("\\n", "")
                    .replace("\\", "")
                    .replace("\"{", "{")
                    .replace("}\"", "}");

            log.debug(requestString);

            StringEntity requestEntity = new StringEntity(requestString);

            HttpUriRequest request = RequestBuilder.post()
                    .setUri(uriBuilder.build())
                    .setEntity(requestEntity)
                    .addHeader("Accept", "application/fhir+ndjson")
                    .addHeader("Content-Type", "application/fhir+ndjson")
                    .addHeader("client", cdcClient)
                    .addHeader("x-functions-key", cdcKey)
                    .build();

            // Execute the request and process the results.
            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            String responseMessage = EntityUtils.toString(responseEntity);
            log.debug(responseMessage.replace("\n", ""));

            log.info("CDC Message sent Successfully.");

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                throw new OpenCDXInternalServerError(
                        DOMAIN,
                        2,
                        "Exception sending CDC message "
                                + response.getStatusLine().toString() + " " + responseMessage);
            }

            record CDCMessage(String request, String response) {
                public String toString() {
                    return "{\"request\": " + request + ", \"response\": " + response + "}";
                }
            }

            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.communication(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "CDC Notification",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    currentUser.getId().toHexString(),
                    "",
                    NOTIFICATION_EVENT + ": CDC MESSAGE",
                    (new CDCMessage(requestString, responseMessage)).toString());

            log.debug("CDC Send message audit record created.");

        } catch (URISyntaxException e) {
            throw new OpenCDXBadRequest(DOMAIN, 3, "Invalid URL Syntax");
        } catch (IOException e) {
            throw new OpenCDXBadRequest(DOMAIN, 4, "IOException in sending CDC message");
        }
    }
}
