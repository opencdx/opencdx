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

import cdx.opencdx.grpc.data.Notification;

/**
 * The OpenCDXCommunicationService interface provides methods to send notifications to the opencdx-communication system.
 * Notifications can be sent for various events such as verifying email, changing password, and sending welcome emails.
 * @implSpec This implementation is based on {@link cdx.opencdx.commons.service.OpenCDXMessageService}.  Any changes
 * will require changes to {@link cdx.opencdx.communications} be modified as well.
 */
public interface OpenCDXCommunicationService {

    /**
     * ID of the verify email notification event
     */
    String VERIFY_EMAIL_USER = "60f1e6b1f075a361a94d3760";

    /**
     * Notification ID for sending notifications.
     */
    String NOTIFICATION = "60f1e6b1f075a361a94d3750";
    /**
     * ID of the Change password Notification Event.
     */
    String CHANGE_PASSWORD = "60f1e6b1f075a361a94d3753";
    /**
     * ID of the welcome email notification event.
     */
    String WELCOME_EMAIL_USER = "60f1e6b1f075a361a94d373e";
    /**
     * ID of create shipping email notification event.
     */
    String CREATE_SHIPMENT = "60f1e6b1f075a361a94d3764";
    /**
     * ID to create test results notification event.
     */
    String TEST_RESULT = "60f1e6b1f075a361a94d3770";
    /**
     * ID to create test diagnosis status event.
     */
    String DIAGNOSIS_STATUS = "60f1e6b1f075a361a94d3774";
    /**
     * ID to send a medical record message.
     */
    String MEDICAL_RECORD_EXPORT = "60f1e6b1f075a361a94d376c";
    /**
     * ID to send a medical record message.
     */
    String MEDICAL_RECORD_IMPORT = "60f1e6b1f075a361a94d376d";
    /**
     * Method to send a Notificaiton to opencdx-communication
     * @param notification Notificaiton to send.
     */
    void sendNotification(Notification notification);
}
