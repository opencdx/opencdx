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

import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.commons.service.OpenCDXOrderMessageService;
import cdx.opencdx.grpc.shipping.Order;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * The OpenCDXOrderMessageServiceImpl class is responsible for submitting orders to the OpenCDXMessageService.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXOrderMessageServiceImpl implements OpenCDXOrderMessageService {

    private final OpenCDXMessageService messageService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Instantiates a new OpenCDXOrderMessageServiceImpl.
     *
     * @param messageService the message service
     * @param openCDXDocumentValidator the open cdx document validator
     */
    public OpenCDXOrderMessageServiceImpl(
            OpenCDXMessageService messageService, OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.messageService = messageService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public void submitOrder(Order order) {
        log.info("Submitting order: {}", order);

        this.openCDXDocumentValidator.validateDocumentOrThrow("profiles", new ObjectId(order.getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "testcases", new ObjectId(order.getTestCase().getId()));

        this.messageService.send(OpenCDXMessageService.ORDER_MESSAGE_SUBJECT, order);
    }
}
