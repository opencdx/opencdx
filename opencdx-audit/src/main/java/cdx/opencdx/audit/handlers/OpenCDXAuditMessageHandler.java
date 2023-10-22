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
package cdx.opencdx.audit.handlers;

import cdx.open_audit.v2alpha.AuditEvent;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Message Handler for Audit Microservice.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXAuditMessageHandler implements OpenCDXMessageHandler {
    private ObjectMapper objectMapper;

    private OpenCDXMessageService openCDXMessageService;

    /**
     * Constructor for the Audit microservice
     * @param objectMapper Object mapper used for conversion
     * @param openCDXMessageService Message service used for receiving messages.
     */
    public OpenCDXAuditMessageHandler(ObjectMapper objectMapper, OpenCDXMessageService openCDXMessageService) {
        this.objectMapper = objectMapper;
        this.openCDXMessageService = openCDXMessageService;

        this.openCDXMessageService.subscribe(OpenCDXMessageService.AUDIT_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            AuditEvent auditEvent = objectMapper.readValue(message, AuditEvent.class);
            log.info(
                    "Received Audit Event from: {}", auditEvent.getAuditSource().getSystemInfo());
            this.processAuditEvent(auditEvent);
        } catch (IOException e) {
            OpenCDXInternal exception =
                    new OpenCDXInternal("OpenCDXAuditMessageHandler", 1, "Failed to parse message to AuditEvent", e);
            exception.setMetaData(new HashMap<>());
            exception.getMetaData().put("message", Arrays.toString(message));
            throw exception;
        }
    }

    /**
     * Method to directory call to process an AuditEvent
     * @param event AuditEvent to process.
     */
    public void processAuditEvent(AuditEvent event) {
        log.info("Audit Event:\n {}", event);
    }
}
