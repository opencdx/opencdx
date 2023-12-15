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
package cdx.opencdx.communications.handlers;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.communications.service.OpenCDXNotificationService;
import cdx.opencdx.grpc.communication.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Message Handler for Communication Microservice.
 */
@Slf4j
@Observed(name = "opencdx")
@ExcludeFromJacocoGeneratedReport
public class OpenCDXCommunicationNotificationMessageHandler implements OpenCDXMessageHandler {

    private final ObjectMapper objectMapper;

    private final OpenCDXNotificationService openCDXNotificationService;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for the Audit microservice
     *
     * @param objectMapper               Object mapper used for conversion
     * @param openCDXNotificationService Notification Service to process Notification.
     * @param openCDXMessageService      Message service used for receiving messages.
     * @param openCDXCurrentUser Used to specify the system as the user.
     */
    public OpenCDXCommunicationNotificationMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXNotificationService openCDXNotificationService,
            OpenCDXMessageService openCDXMessageService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.objectMapper = objectMapper;
        this.openCDXNotificationService = openCDXNotificationService;
        this.openCDXCurrentUser = openCDXCurrentUser;

        openCDXMessageService.subscribe(OpenCDXMessageService.NOTIFICATION_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        this.openCDXCurrentUser.configureAuthentication("SYSTEM");
        try {
            Notification notification = objectMapper.readValue(message, Notification.class);
            log.info("Received Notification: {}", notification.getEventId());
            this.openCDXNotificationService.sendNotification(notification);
        } catch (IOException e) {
            OpenCDXInternal exception = new OpenCDXInternal(
                    "OpenCDXCommunicationMessageHandler", 1, "Failed to parse message to Notification", e);
            exception.setMetaData(new HashMap<>());
            exception.getMetaData().put("message", Arrays.toString(message));
            throw exception;
        }
    }
}
