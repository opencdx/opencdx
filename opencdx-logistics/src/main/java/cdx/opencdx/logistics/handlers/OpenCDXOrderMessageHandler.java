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
package cdx.opencdx.logistics.handlers;

import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.Order;
import cdx.opencdx.grpc.service.logistics.CreateOrderRequest;
import cdx.opencdx.logistics.service.OpenCDXShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The OpenCDXOrderMessageHandler class is responsible for handling order messages
 * received from the OpenCDXMessageService. It implements the OpenCDXMessageHandler interface.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXOrderMessageHandler implements OpenCDXMessageHandler {

    private final ObjectMapper objectMapper;
    private final OpenCDXShippingService openCDXShippingService;

    /**
     * Instantiates a new OpenCDXOrderMessageHandler.
     *
     * @param objectMapper the object mapper
     * @param openCDXShippingService the open cdx shipping service
     * @param openCDXMessageService the open cdx message service
     */
    public OpenCDXOrderMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXShippingService openCDXShippingService,
            OpenCDXMessageService openCDXMessageService) {
        this.objectMapper = objectMapper;
        this.openCDXShippingService = openCDXShippingService;
        log.info("Instantiating OpenCDXOrderMessageHandler.");

        openCDXMessageService.subscribe(OpenCDXMessageService.ORDER_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            Order order = this.objectMapper.readValue(message, Order.class);
            log.trace("Received Order Event");
            this.openCDXShippingService.createOrder(
                    CreateOrderRequest.newBuilder().setOrder(order).build());
        } catch (IOException e) {
            log.error("Failed to process classification event", e);
        }
    }
}
