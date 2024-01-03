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
package cdx.opencdx.communications.handlers;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.communications.service.OpenCDXCDCMessageService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Message Handler for processing and sending CDC messages.
 */
@Slf4j
@Observed(name = "opencdx")
@ExcludeFromJacocoGeneratedReport
public class OpenCDXCDCMessageHandler implements OpenCDXMessageHandler {

    private final OpenCDXCDCMessageService openCDXCDCMessageService;

    private final OpenCDXMessageService openCDXMessageService;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for the Audit microservice
     *
     * @param openCDXCDCMessageService CDC Notification Service to process messages.
     * @param openCDXMessageService      Message service used for receiving messages.
     * @param openCDXCurrentUser Used to specify the system as the user.
     */
    public OpenCDXCDCMessageHandler(
            OpenCDXCDCMessageService openCDXCDCMessageService,
            OpenCDXMessageService openCDXMessageService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXCDCMessageService = openCDXCDCMessageService;
        this.openCDXMessageService = openCDXMessageService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        log.info("OpenCDXCDCMessageHandler - Constructor");
        this.openCDXMessageService.subscribe(OpenCDXMessageService.CDC_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        log.info("Received CDC Payload");
        this.openCDXCurrentUser.configureAuthentication("SYSTEM");
        String cdcPayload = new String(message);
        this.openCDXCDCMessageService.sendCDCMessage(cdcPayload);
    }
}
