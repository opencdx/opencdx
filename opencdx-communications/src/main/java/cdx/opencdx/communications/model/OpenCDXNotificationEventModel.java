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
import cdx.opencdx.grpc.data.NotificationEvent;
import cdx.opencdx.grpc.types.NotificationPriority;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for OpenCDXNotificationEvent in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("notification-event")
public class OpenCDXNotificationEventModel {
    @Id
    private OpenCDXIdentifier id;

    private String eventName;
    private String eventDescription;
    private OpenCDXIdentifier emailTemplateId;
    private OpenCDXIdentifier smsTemplateId;
    private OpenCDXIdentifier messageTemplateId;
    private SensitivityLevel sensitivityLevel;
    private NotificationPriority priority;
    List<String> parameters;

    @Builder.Default
    private Integer emailRetry = 0;

    @Builder.Default
    private Integer smsRetry = 0;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Constructor to create this model based on an NotificationEvent
     * @param event NotificationEvent to base this model on.
     */
    public OpenCDXNotificationEventModel(NotificationEvent event) {
        if (event.hasEventId()) {
            this.id = new OpenCDXIdentifier(event.getEventId());
        }
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        if (event.hasEmailTemplateId()) {
            this.emailTemplateId = new OpenCDXIdentifier(event.getEmailTemplateId());
        }
        if (event.hasSmsTemplateId()) {
            this.smsTemplateId = new OpenCDXIdentifier(event.getSmsTemplateId());
        }
        if (event.hasMessageTemplateId()) {
            this.messageTemplateId = new OpenCDXIdentifier(event.getMessageTemplateId());
        }
        this.parameters = event.getEventParametersList();
        this.sensitivityLevel = event.getSensitivity();
        this.priority = event.getPriority();
        this.smsRetry = event.getSmsRetry();
        this.emailRetry = event.getEmailRetry();

        if (event.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    event.getCreated().getSeconds(), event.getCreated().getNanos());
        }
        if (event.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    event.getModified().getSeconds(), event.getModified().getNanos());
        }
        if (event.hasCreator()) {
            this.creator = new OpenCDXIdentifier(event.getCreator());
        }
        if (event.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(event.getModifier());
        }
    }

    /**
     * Return this model as an EmailTemplate
     * @return EmailTemplate representing this model.
     */
    public NotificationEvent getProtobufMessage() {
        NotificationEvent.Builder builder = NotificationEvent.newBuilder();

        if (id != null) {
            builder.setEventId(this.id.toHexString());
        }
        if (eventName != null) {
            builder.setEventName(this.eventName);
        }
        if (eventDescription != null) {
            builder.setEventDescription(this.eventDescription);
        }
        if (emailTemplateId != null) {
            builder.setEmailTemplateId(this.emailTemplateId.toHexString());
        }
        if (smsTemplateId != null) {
            builder.setSmsTemplateId(this.smsTemplateId.toHexString());
        }
        if (messageTemplateId != null) {
            builder.setMessageTemplateId(this.messageTemplateId.toHexString());
        }
        if (parameters != null) {
            builder.addAllEventParameters(this.parameters);
        }
        if (this.priority != null) {
            builder.setPriority(this.priority);
        }
        if (this.sensitivityLevel != null) {
            builder.setSensitivity(this.sensitivityLevel);
        }
        builder.setSmsRetry(this.smsRetry);
        builder.setEmailRetry(this.emailRetry);
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
}
