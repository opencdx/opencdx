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

import cdx.open_communication.v2alpha.Attachment;
import cdx.open_communication.v2alpha.Notification;
import cdx.open_communication.v2alpha.NotificationStatus;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("notifications")
public class OpenCDXNotificationModel {
    @Id
    private ObjectId id;

    private ObjectId eventId;
    private NotificationStatus smsStatus;

    @Builder.Default
    private Integer smsFailCount = 0;

    private NotificationStatus emailStatus;

    @Builder.Default
    private Integer emailFailCount = 0;

    private Instant timestamp;
    private Map<String, String> customData;
    private List<String> toEmail;
    private List<String> ccEmail;
    private List<String> bccEmail;
    private List<Attachment> attachments;
    private List<String> phoneNumbers;
    private Map<String, String> variables;
    private List<ObjectId> recipients;

    public OpenCDXNotificationModel(Notification notification) {
        this.emailFailCount = 0;
        this.smsFailCount = 0;
        if (notification.hasQueueId()) {
            this.id = new ObjectId(notification.getQueueId());
        }
        this.eventId = new ObjectId(notification.getEventId());
        if (notification.hasSmsStatus()) {
            this.smsStatus = notification.getSmsStatus();
        } else {
            this.smsStatus = NotificationStatus.NOTIFICATION_STATUS_PENDING;
        }
        if (notification.hasEmailStatus()) {
            this.emailStatus = notification.getEmailStatus();
        } else {
            this.emailStatus = NotificationStatus.NOTIFICATION_STATUS_PENDING;
        }
        if (notification.hasTimestamp()) {
            this.timestamp = Instant.ofEpochSecond(
                    notification.getTimestamp().getSeconds(),
                    notification.getTimestamp().getNanos());
        } else {
            this.timestamp = Instant.now();
        }

        this.customData = notification.getCustomDataMap();
        this.toEmail = notification.getToEmailList();
        this.ccEmail = notification.getCcEmailList();
        this.bccEmail = notification.getBccEmailList();
        this.attachments = notification.getEmailAttachmentsList();
        this.phoneNumbers = notification.getToPhoneNumberList();
        this.variables = notification.getVariablesMap();
        this.recipients =
                notification.getRecipientsIdList().stream().map(ObjectId::new).toList();
    }

    public Notification getProtobufMessage() {
        return Notification.newBuilder()
                .setQueueId(this.id.toHexString())
                .setEventId(this.eventId.toHexString())
                .setSmsStatus(this.smsStatus)
                .setEmailStatus(this.emailStatus)
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(this.timestamp.getEpochSecond())
                        .setNanos(this.timestamp.getNano())
                        .build())
                .putAllCustomData(this.customData)
                .addAllToEmail(this.toEmail)
                .addAllCcEmail(this.ccEmail)
                .addAllBccEmail(this.bccEmail)
                .addAllEmailAttachments(this.attachments)
                .addAllToPhoneNumber(this.phoneNumbers)
                .putAllVariables(this.variables)
                .addAllRecipientsId(
                        this.recipients.stream().map(ObjectId::toHexString).toList())
                .build();
    }
}
