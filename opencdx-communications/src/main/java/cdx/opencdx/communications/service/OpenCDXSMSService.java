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
package cdx.opencdx.communications.service;

import java.util.List;

/**
 * Interface to the OpenCDX SMS service.  Used to send SMS notifications
 * to users.
 */
public interface OpenCDXSMSService {
    /**
     * Method to send SMS Notificaiton
     * @param message Content of the SMS notification
     * @param phoneNumbers List of phone numbers to send the notification to.
     * @return boolean indicating if successful.
     */
    boolean sendSMS(String message, List<String> phoneNumbers);
}
