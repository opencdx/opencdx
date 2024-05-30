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

import cdx.opencdx.grpc.data.MedicalRecord;

/**
 * The OpenCDXCommunicationService interface provides methods to send notifications to the opencdx-communication system.
 * Notifications can be sent for various events such as verifying email, changing password, and sending welcome emails.
 * @implSpec This implementation is based on {@link OpenCDXMessageService}.  Any changes
 * will require changes to {@link cdx.opencdx.communications} be modified as well.
 */
public interface OpenCDXMedicalRecordMessageService {

    /**
     * Method to send a Notificaiton to opencdx-communication
     * @param medicalRecord The Medical Record to send.
     */
    void sendMedicalRecord(MedicalRecord medicalRecord);
}
