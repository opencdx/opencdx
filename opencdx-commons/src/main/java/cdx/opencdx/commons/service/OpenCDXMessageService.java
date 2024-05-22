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
package cdx.opencdx.commons.service;

import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import java.util.List;

/**
 * The OpenCDXMessageService interface provides methods for subscribing to message subjects,
 * unsubscribing from message subjects, and sending messages.
 * @implNote Two implementation are provided {@link cdx.opencdx.commons.service.impl.NatsOpenCDXMessageServiceImpl} based
 * on the NATS message server, and {@link cdx.opencdx.commons.service.impl.NoOpOpenCDXMessageServiceImpl} a NOOP implementation
 * that is used for JUnit testing.
 * @implSpec This interface was designed to be agnostic of messaging system.
 */
public interface OpenCDXMessageService {

    /**
     * OpenCDXMessage Subject for Audit messages.
     */
    String AUDIT_MESSAGE_SUBJECT = "opencdx.audit.message";

    /**
     * OpenCDXMessage Subject for Communication messages
     */
    String NOTIFICATION_MESSAGE_SUBJECT = "opencdx.communication.notification.message";

    /**
     * OpenCDXMessage Subject for Classification messages
     */
    String CLASSIFICATION_MESSAGE_SUBJECT = "opencdx.classification.message";

    /**
     * OpenCDXMessage Subject for CDC Payload messages
     */
    String CDC_MESSAGE_SUBJECT = "opencdx.communication.cdc.notification.message";

    /**
     * OpenCDXMessage Subject for Order messages
     */
    String ORDER_MESSAGE_SUBJECT = "opencdx.order.message";
    /**
     * OpenCDXMessage Subject for Delivery Tracking messages
     */
    String DELIVERY_TRACKING_MESSAGE_SUBJECT = "openCDX.delivery.tracking.message";

    /**
     * OpenCDXMessage Subject for Connected Lab Findings messages
     */
    String CONNECTED_LAB_FINDINGS = "opencdx.connected.lab.findings";

    /**
     * OpenCDXMessage Subject for Medical Record messages
     */
    String MEDICAL_RECORD_MESSAGE = "opencdx.medical.record.message";

    /**
     * List of all the subjects.
     */
    List<String> SUBJECTS = List.of(
            AUDIT_MESSAGE_SUBJECT,
            NOTIFICATION_MESSAGE_SUBJECT,
            CLASSIFICATION_MESSAGE_SUBJECT,
            CDC_MESSAGE_SUBJECT,
            ORDER_MESSAGE_SUBJECT,
            DELIVERY_TRACKING_MESSAGE_SUBJECT,
            CONNECTED_LAB_FINDINGS,
            MEDICAL_RECORD_MESSAGE);

    /**
     * Subscribe to a message subject and the handlers for received those messages
     * for processing.
     * @param subject String indicating the subject to subscribe to.
     * @param handler OpenCDXMessageHandler for processing the messages.
     */
    void subscribe(String subject, OpenCDXMessageHandler handler);

    /**
     * Method to unsubscribe to a set of messages.
     * @param subject String indicating the subject to un-subscribe to.
     */
    void unSubscribe(String subject);

    /**
     * Method to send an object as a Message.
     * @param subject Subject to send the message on.
     * @param object Object to send as message.
     */
    void send(String subject, Object object);
}
