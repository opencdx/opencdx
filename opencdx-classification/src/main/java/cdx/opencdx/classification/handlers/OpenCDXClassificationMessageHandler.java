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
package cdx.opencdx.classification.handlers;

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Observed(name = "opencdx")
public class OpenCDXClassificationMessageHandler implements OpenCDXMessageHandler {
    private final ObjectMapper objectMapper;

    private final OpenCDXClassificationService openCDXClassificationService;

    public OpenCDXClassificationMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXClassificationService openCDXClassificationService,
            OpenCDXMessageService openCDXMessageService) {
        this.objectMapper = objectMapper;
        this.openCDXClassificationService = openCDXClassificationService;

        openCDXMessageService.subscribe(OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            ClassificationRequest classificationRequest = objectMapper.readValue(message, ClassificationRequest.class);
            log.info("Received Classification Event");
            this.openCDXClassificationService.classify(classificationRequest);
        } catch (Exception e) {
            log.error("Failed to process classification event", e);
        }
    }
}
