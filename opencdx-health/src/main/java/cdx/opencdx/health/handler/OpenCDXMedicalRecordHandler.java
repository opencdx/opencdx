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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.MedicalRecord;
import cdx.opencdx.health.service.OpenCDXMedicalRecordProcessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for processing OpenCDX Messages for Connected Labs
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordHandler implements OpenCDXMessageHandler {

    private final OpenCDXMedicalRecordProcessService openCDXMedicalRecordProcessService;

    private final ObjectMapper objectMapper;

    /**
     * Constructor for OpenCDXMedicalRecordHandler
     * @param openCDXMessageService Service for handling messages
     * @param openCDXMedicalRecordService Service for processing connected lab requests
     * @param objectMapper Object Mapper for processing JSON
     */
    public OpenCDXMedicalRecordHandler(
            OpenCDXMessageService openCDXMessageService,
            OpenCDXMedicalRecordProcessService openCDXMedicalRecordProcessService,
            ObjectMapper objectMapper) {
        this.openCDXMedicalRecordProcessService = openCDXMedicalRecordProcessService;
        this.objectMapper = objectMapper;
        log.trace("Instantiating OpenCDXClassificationMessageHandler.");

        openCDXMessageService.subscribe(OpenCDXMessageService.MEDICAL_RECORD_MESSAGE, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            MedicalRecord medicalRecord = objectMapper.readValue(message, MedicalRecord.class);
            log.trace("Received Medical Record Message");
            this.openCDXMedicalRecordProcessService.processMedicalRecord(new OpenCDXIdentifier(medicalRecord.getId()));
        } catch (Exception e) {
            log.error("Failed to process classification event", e);
        }
    }
}
