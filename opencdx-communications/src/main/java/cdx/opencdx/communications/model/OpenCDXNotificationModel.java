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

import cdx.opencdx.commons.collections.ListUtils;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRecord;
import cdx.opencdx.grpc.data.Attachment;
import cdx.opencdx.grpc.data.Notification;
import cdx.opencdx.grpc.types.NotificationPriority;
import cdx.opencdx.grpc.types.NotificationStatus;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * OpenCDXNotificationModel for the Protobuf Notification class, translation between types.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@OpenCDXRecord(value="notifications")
//@Document(value="notifications")
public class OpenCDXNotificationModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;

    private OpenCDXIdentifier eventId;
    private NotificationStatus smsStatus;

    @Builder.Default
    private Integer smsFailCount = 0;

    private NotificationStatus emailStatus;

    @Builder.Default
    private Integer emailFailCount = 0;

    private NotificationPriority priority;

    private Instant timestamp;
    private Map<String, String> customData;
    private List<String> toEmail;
    private List<String> ccEmail;
    private List<String> bccEmail;
    private List<Attachment> attachments;
    private List<String> phoneNumbers;
    private Map<String, String> variables;
    private List<OpenCDXIdentifier> recipients;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;
    /**
     * Constructor taking a Notification and generating the Model
     * @param notification Notificaiton to generate model for.
     */
    public OpenCDXNotificationModel(Notification notification) {
        this.emailFailCount = 0;
        this.smsFailCount = 0;
        this.patientId = new OpenCDXIdentifier(notification.getPatientId());

        if (notification.hasQueueId()) {
            this.id = new OpenCDXIdentifier(notification.getQueueId());
        }
        this.eventId = new OpenCDXIdentifier(notification.getEventId());
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
        this.recipients = notification.getRecipientsIdList().stream()
                .map(OpenCDXIdentifier::new)
                .toList();

        if (notification.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    notification.getCreated().getSeconds(),
                    notification.getCreated().getNanos());
        }
        if (notification.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    notification.getModified().getSeconds(),
                    notification.getModified().getNanos());
        }
        if (notification.hasCreator()) {
            this.creator = new OpenCDXIdentifier(notification.getCreator());
        }
        if (notification.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(notification.getModifier());
        }
    }

    /**
     * Method to generate an Protobuf equivalent message
     * @return Notification as the protobuf message.
     */
    public Notification getProtobufMessage() {
        Notification.Builder builder = Notification.newBuilder();
        builder.setQueueId(this.id.toHexString());

        builder.setPatientId(this.patientId.toHexString());

        builder.setEventId(this.eventId.toHexString());
        builder.setSmsStatus(this.smsStatus);
        builder.setEmailStatus(this.emailStatus);
        builder.setTimestamp(Timestamp.newBuilder()
                .setSeconds(this.timestamp.getEpochSecond())
                .setNanos(this.timestamp.getNano())
                .build());
        builder.putAllCustomData(this.customData);
        builder.addAllToEmail(ListUtils.safe(this.toEmail));
        builder.addAllCcEmail(ListUtils.safe(this.ccEmail));
        builder.addAllBccEmail(ListUtils.safe(this.bccEmail));
        builder.addAllEmailAttachments(ListUtils.safe(this.attachments));
        builder.addAllToPhoneNumber(ListUtils.safe(this.phoneNumbers));
        builder.putAllVariables(this.variables);
        builder.addAllRecipientsId(ListUtils.safe(this.recipients).stream()
                .map(OpenCDXIdentifier::toHexString)
                .toList());

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
