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
package health.safe.api.opencdx.audit.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXInternal;
import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import health.safe.api.opencdx.grpc.audit.AuditEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenCDXAuditMessageHandler implements OpenCDXMessageHandler {
    private ObjectMapper objectMapper;

    private OpenCDXMessageService openCDXMessageService;

    public OpenCDXAuditMessageHandler(ObjectMapper objectMapper, OpenCDXMessageService openCDXMessageService) {
        this.objectMapper = objectMapper;
        this.openCDXMessageService = openCDXMessageService;

        this.openCDXMessageService.subscribe(OpenCDXAuditService.AUDIT_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            log.info("Audit Event: {}", objectMapper.readValue(message, AuditEvent.class));
        } catch (IOException e) {
            OpenCDXInternal exception =
                    new OpenCDXInternal("OpenCDXAuditMessageHandler", 1, "Failed to parse message to AuditEvent", e);
            exception.setMetaData(new HashMap<>());
            exception.getMetaData().put("message", Arrays.toString(message));
            throw exception;
        }
    }
}
