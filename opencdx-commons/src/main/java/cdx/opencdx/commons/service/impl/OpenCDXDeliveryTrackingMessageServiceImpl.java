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

import cdx.opencdx.commons.service.OpenCDXDeliveryTrackingMessageService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.routine.DeliveryTracking;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The OpenCDXDeliveryTrackingMessageServiceImpl class is responsible for submitting delivery tracking to the OpenCDXMessageService.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDeliveryTrackingMessageServiceImpl implements OpenCDXDeliveryTrackingMessageService {
    private final OpenCDXMessageService messageService;

    /**
     * Instantiates a new OpenCDXDeliveryTrackingMessageServiceImpl.
     *
     * @param messageService the message service
     */
    public OpenCDXDeliveryTrackingMessageServiceImpl(OpenCDXMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void submitDeliveryTracking(DeliveryTracking deliveryTracking) {
        log.info("Submitting delivery tracking: {}", deliveryTracking);
        this.messageService.send(OpenCDXMessageService.DELIVERY_TRACKING_MESSAGE_SUBJECT, deliveryTracking);
    }
}
