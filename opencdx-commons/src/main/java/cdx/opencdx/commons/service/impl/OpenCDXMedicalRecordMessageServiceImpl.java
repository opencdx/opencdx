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

import cdx.opencdx.commons.service.OpenCDXMedicalRecordMessageService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.MedicalRecord;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementaiton for OpenCDXCommunicationService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordMessageServiceImpl implements OpenCDXMedicalRecordMessageService {

    private final OpenCDXMessageService messageService;

    /**
     * Constructor to use the OpenCDXMessageService to send the notificaiton.
     * @param messageService Message service to use to send.
     * //* @param openCDXDocumentValidator Document validator to validate the document.
     */
    public OpenCDXMedicalRecordMessageServiceImpl(OpenCDXMessageService messageService) {
        log.info("OpenCDXMedicalRecordMessageServiceImpl created");
        this.messageService = messageService;
    }

    @Override
    public void sendMedicalRecord(MedicalRecord medicalRecord) {
        log.trace("Sending notification");
        this.messageService.send(OpenCDXMessageService.MEDICAL_RECORD_MESSAGE, medicalRecord);
    }
}
