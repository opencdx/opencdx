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

/**
 * Interface to the OpenCDX CDC Notification service.  Used to send CDC Notification messages
 */
public interface OpenCDXCDCMessageService {
    /**
     * Method to send CDC Notification
     * @param message Content of the CDC notification
     * @return boolean indicating if successful.
     */
    boolean sendCDCMessage(String message);
}