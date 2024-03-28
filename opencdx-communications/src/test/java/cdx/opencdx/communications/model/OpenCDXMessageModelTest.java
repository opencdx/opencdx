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
package cdx.opencdx.communications.model;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.communication.Message;
import cdx.opencdx.grpc.communication.MessageStatus;
import cdx.opencdx.grpc.communication.MessageType;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXMessageModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTitle("title")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ)
                .build());
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_1() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTitle("title")
                .setMessage("message")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ)
                .build());
        Assertions.assertNull(model.getId());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTitle("title")
                .setMessage("message")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ)
                .setCreated(Timestamp.newBuilder().getDefaultInstanceForType())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModified(Timestamp.newBuilder())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build());
        Assertions.assertNull(model.getId());
    }
}
