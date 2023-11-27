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
package cdx.opencdx.commons.service;

import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;

/**
 * Interface for implementing OpenCDXMessageService. An implemenation
 * agnostic interface.
 */
public interface OpenCDXMessageService {

    /**
     * OpenCDXMessage Subject for Audit messages.
     */
    public static final String AUDIT_MESSAGE_SUBJECT = "opencdx.audit.message";

    /**
     * OpenCDXMessage Subject for Communication messages
     */
    public static final String NOTIFICATION_MESSAGE_SUBJECT = "opencdx.communication.notification.message";
    
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
