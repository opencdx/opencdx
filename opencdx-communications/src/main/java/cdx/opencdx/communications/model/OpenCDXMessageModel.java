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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Message;
import cdx.opencdx.grpc.types.MessageStatus;
import cdx.opencdx.grpc.types.MessageType;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for OpenCDXMessageModel in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("messages")
public class OpenCDXMessageModel {

    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXIdentifier patientId;
    private String title;
    private String message;

    private MessageType messageType;
    private MessageStatus messageStatus;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor taking a Message and generating the Model
     * @param message Message to generate model for.
     */
    public OpenCDXMessageModel(Message message) {
        if (message.hasId()) {
            this.id = new OpenCDXIdentifier(message.getId());
        }
        this.patientId = new OpenCDXIdentifier(message.getPatientId());
        this.title = message.getTitle();
        this.message = message.getMessage();
        this.messageType = message.getType();
        this.messageStatus = message.getStatus();

        if (message.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    message.getCreated().getSeconds(), message.getCreated().getNanos());
        }
        if (message.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    message.getModified().getSeconds(), message.getModified().getNanos());
        }
        if (message.hasCreator()) {
            this.creator = new OpenCDXIdentifier(message.getCreator());
        }
        if (message.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(message.getModifier());
        }
    }

    /**
     * Method to generate an Protobuf equivalent message
     * @return Message as the protobuf message.
     */
    public Message getProtobufMessage() {
        Message.Builder builder = Message.newBuilder();
        builder.setId(this.id.toHexString());

        builder.setPatientId(this.patientId.toHexString());

        builder.setTitle(this.title);
        builder.setType(this.messageType);
        builder.setStatus(this.messageStatus);
        builder.setMessage(this.message);

        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }

        return builder.build();
    }

    public OpenCDXMessageModel update(Message message) {
        this.patientId = new OpenCDXIdentifier(message.getPatientId());
        this.title = message.getTitle();
        this.message = message.getMessage();
        this.messageType = message.getType();
        this.messageStatus = message.getStatus();
        return this;
    }
}
