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
package cdx.opencdx.commons.handlers;

/**
 * Handler interface for processing received messages from the
 * OpenCDXMessageService
 */
public interface OpenCDXMessageHandler {
    /**
     * Method to receive the byte array of data for the message being
     * handled. This handlers is responsible for converting the bytes
     * to the appropriate representation.
     * @param message Byte array containing the message
     */
    void receivedMessage(byte[] message);
}
