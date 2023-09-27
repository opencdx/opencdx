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
package health.safe.api.opencdx.communications.model;

import cdx.open_audit.v2alpha.SensitivityLevel;
import cdx.open_communication.v2alpha.NotificationEvent;
import cdx.open_communication.v2alpha.NotificationPriority;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    private ObjectId id;

    private String eventName;
    private String eventDescription;
    private ObjectId emailTemplateId;
    private ObjectId smsTemplateId;
    private SensitivityLevel sensitivityLevel;
    private NotificationPriority priority;
    List<String> parameters;

    /**
     * Constructor to create this model based on an NotificationEvent
     * @param event NotificationEvent to base this model on.
     */
    public OpenCDXNotificationEventModel(NotificationEvent event) {
        if (event.hasEventId()) {
            this.id = new ObjectId(event.getEventId());
        }
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        if (event.hasEmailTemplateId()) {
            this.emailTemplateId = new ObjectId(event.getEmailTemplateId());
        }
        if (event.hasSmsTemplateId()) {
            this.smsTemplateId = new ObjectId(event.getSmsTemplateId());
        }
        this.parameters = event.getEventParametersList();
        this.sensitivityLevel = event.getSensitivity();
        this.priority = event.getPriority();
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
        if (parameters != null) {
            builder.addAllEventParameters(this.parameters);
        }
        if (this.priority != null) {
            builder.setPriority(this.priority);
        }
        if (this.sensitivityLevel != null) {
            builder.setSensitivity(this.sensitivityLevel);
        }

        return builder.build();
    }
}
