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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.communication.Notification;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Implementaiton for OpenCDXCommunicationService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCommunicationServiceImpl implements OpenCDXCommunicationService {

    private final OpenCDXMessageService messageService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor to use the OpenCDXMessageService to send the notificaiton.
     * @param messageService Message service to use to send.
     * @param openCDXDocumentValidator Document validator to validate the document.
     */
    public OpenCDXCommunicationServiceImpl(
            OpenCDXMessageService messageService, OpenCDXDocumentValidator openCDXDocumentValidator) {
        log.info("OpenCDXCommunicationServiceImpl created");
        this.messageService = messageService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public void sendNotification(Notification notification) {
        openCDXDocumentValidator.validateDocumentOrThrow("notification-event", new ObjectId(notification.getEventId()));
        log.info("Sending notification");
        this.messageService.send(OpenCDXMessageService.NOTIFICATION_MESSAGE_SUBJECT, notification);
    }
}
