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
package cdx.opencdx.routine.handlers;

import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.routine.DeliveryTracking;
import cdx.opencdx.grpc.routine.DeliveryTrackingRequest;
import cdx.opencdx.routine.service.OpenCDXRoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Observed(name = "opencdx")
public class OpenCDXDeliveryTrackingMessageHandler implements OpenCDXMessageHandler {
    private final ObjectMapper objectMapper;

    private final OpenCDXRoutineService openCDXRoutineService;

    public OpenCDXDeliveryTrackingMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXRoutineService openCDXRoutineService,
            OpenCDXMessageService openCDXMessageService) {
        this.objectMapper = objectMapper;
        this.openCDXRoutineService = openCDXRoutineService;
        log.trace("Instantiating OpenCDXDeliveryTrackingMessageHandler.");

        openCDXMessageService.subscribe(OpenCDXMessageService.DELIVERY_TRACKING_MESSAGE_SUBJECT, this);
    }

    @Override
    public void receivedMessage(byte[] message) {
        try {
            log.trace("Received Delivery Tracking Event");
            DeliveryTracking deliveryTracking = objectMapper.readValue(message, DeliveryTracking.class);
            this.openCDXRoutineService.createDeliveryTracking(DeliveryTrackingRequest.newBuilder()
                    .setDeliveryTracking(deliveryTracking)
                    .build());
        } catch (Exception e) {
            log.error("Failed to process delivery tracking event", e);
        }
    }
}