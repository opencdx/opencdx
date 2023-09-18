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

import health.safe.api.opencdx.grpc.communication.NotificationEvent;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Model for OpenCDXNotificationEvent in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXNotificationEventModel {
    private String id;
    private String eventName;
    private String eventDescription;
    private String emailTemplateId;
    private String smsTemplateId;
    List<String> parameters;

    /**
     * Constructor to create this model based on an NotificationEvent
     * @param event NotificationEvent to base this model on.
     */
    public OpenCDXNotificationEventModel(NotificationEvent event) {
        this.id = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.emailTemplateId = event.getEmailTemplateId();
        this.smsTemplateId = event.getSmsTemplateId();
        this.parameters = event.getEventParametersList();
    }

    /**
     * Return this model as an EmailTemplate
     * @return EmailTemplate representing this model.
     */
    public NotificationEvent getProtobufMessage() {
        NotificationEvent.Builder builder = NotificationEvent.newBuilder();

        if (id != null) {
            builder.setEventId(this.id);
        }
        if (eventName != null) {
            builder.setEventName(this.eventName);
        }
        if (eventDescription != null) {
            builder.setEventDescription(this.eventDescription);
        }
        if (emailTemplateId != null) {
            builder.setEmailTemplateId(this.emailTemplateId);
        }
        if (smsTemplateId != null) {
            builder.setSmsTemplateId(this.smsTemplateId);
        }
        if (parameters != null) {
            builder.addAllEventParameters(this.parameters);
        }

        return builder.build();
    }
}
