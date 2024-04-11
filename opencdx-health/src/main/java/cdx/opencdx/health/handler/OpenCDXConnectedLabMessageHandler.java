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
package cdx.opencdx.health.handler;

import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.lab.connected.LabFindings;
import cdx.opencdx.health.service.OpenCDXConnectedLabService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for processing OpenCDX Messages for Connected Labs
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXConnectedLabMessageHandler implements OpenCDXMessageHandler {

    private final OpenCDXConnectedLabService openCDXConnectedLabService;

    private final ObjectMapper objectMapper;

    /**
     * Constructor for OpenCDXConnectedLabMessageHandler
     * @param openCDXMessageService Service for handling messages
     * @param openCDXConnectedLabService Service for processing connected lab requests
     * @param objectMapper Object Mapper for processing JSON
     */
    public OpenCDXConnectedLabMessageHandler(
            OpenCDXMessageService openCDXMessageService,
            OpenCDXConnectedLabService openCDXConnectedLabService,
            ObjectMapper objectMapper) {
        this.openCDXConnectedLabService = openCDXConnectedLabService;
        this.objectMapper = objectMapper;
        log.trace("Instantiating OpenCDXClassificationMessageHandler.");

        openCDXMessageService.subscribe(OpenCDXMessageService.CONNECTED_LAB_FINDINGS, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            LabFindings findings = objectMapper.readValue(message, LabFindings.class);
            log.trace("Received LabFindings Event");
            this.openCDXConnectedLabService.submitLabFindings(findings);
        } catch (Exception e) {
            log.error("Failed to process classification event", e);
        }
    }
}
