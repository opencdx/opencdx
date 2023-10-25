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
package proto; /*
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

import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class CommunicationsTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testEmailTemplate() throws JsonProcessingException {
        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setTemplateId("templateIdT")
                .setSubject("subjectT")
                .setContent("contentT")
                .addAllVariables(List.of("variable1", "variable2"))
                .setTemplateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                .build();
        log.info("EmailTemplate: {}", this.mapper.writeValueAsString(emailTemplate));
    }

    @Test
    void testSMSTemplate() throws JsonProcessingException {
        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setTemplateId("templateIdT")
                .setMessage("contentT")
                .addAllVariables(List.of("variable1", "variable2"))
                .setTemplateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                .build();
        log.info("SMSTemplate: {}", this.mapper.writeValueAsString(smsTemplate));
    }

    @Test
    void testNotificationEvent() throws JsonProcessingException {
        NotificationEvent notificationEvent = NotificationEvent.newBuilder()
                .setEventId("eventIdT")
                .setEventName("eventNameT")
                .setEventDescription("descriptionT")
                .setEmailTemplateId("emailTemplateIDT")
                .setSmsTemplateId("smsTemplateIdT")
                .addAllEventParameters(List.of("eventParam1", "eventParam2"))
                .setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE)
                .setSensitivity(SensitivityLevel.SENSITIVITY_LEVEL_HIGH)
                .setEmailRetry(1)
                .setSmsRetry(1)
                .build();
        log.info("NotificationEvent: {}", this.mapper.writeValueAsString(notificationEvent));
    }

    @Test
    void testAttachment() throws JsonProcessingException {
        Attachment attachment = Attachment.newBuilder()
                .setFilename("fileNameT")
                .setData(ByteString.copyFromUtf8("daata"))
                .setMimeType("mimeTypeT")
                .build();
        log.info("Attachment: {}", this.mapper.writeValueAsString(attachment));
    }

    @Test
    void testNotification() throws JsonProcessingException {
        Notification notification = Notification.newBuilder()
                .setQueueId("queueIdT")
                .setEventId("eventId")
                .setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                .setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                .setTimestamp(Timestamp.newBuilder().setSeconds(1696732104))
                .addAllToEmail(List.of("toEmail1", "toEmail2"))
                .addAllCcEmail(List.of("ccEmail1", "ccEmail2"))
                .addAllBccEmail(List.of("bccEmail1", "bccEmail2"))
                .addAllEmailAttachments(List.of(Attachment.newBuilder()
                        .setFilename("fileNameT")
                        .setData(ByteString.copyFromUtf8("daata"))
                        .setMimeType("mimeTypeT")
                        .build()))
                .addAllToPhoneNumber(List.of("toPhoneNumber"))
                .addAllRecipientsId(List.of("recipientsIdT"))
                .build();
        log.info("Notification: {}", this.mapper.writeValueAsString(notification));
    }

    @Test
    void testTemplateRequest() throws JsonProcessingException {
        TemplateRequest templateRequest =
                TemplateRequest.newBuilder().setTemplateId("templateIdT").build();
        log.info("TemplateRequest: {}", this.mapper.writeValueAsString(templateRequest));
    }

    @Test
    void testSuccessResponse() throws JsonProcessingException {
        SuccessResponse successResponse =
                SuccessResponse.newBuilder().setSuccess(true).build();
        log.info("SuccessResponse: {}", this.mapper.writeValueAsString(successResponse));
    }

    @Test
    void testSMSTemplateListRequest() throws JsonProcessingException {
        SMSTemplateListRequest smsTemplateListRequest = SMSTemplateListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(2)
                .setSortAscending(true)
                .build();
        log.info("SMSTemplateListRequest: {}", this.mapper.writeValueAsString(smsTemplateListRequest));
    }

    @Test
    void testEmailTemplateListRequest() throws JsonProcessingException {
        EmailTemplateListRequest emailTemplateListRequest = EmailTemplateListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(2)
                .setSortAscending(true)
                .build();
        log.info("EmailTemplateListRequest: {}", this.mapper.writeValueAsString(emailTemplateListRequest));
    }

    @Test
    void testNotificationEventListRequest() throws JsonProcessingException {
        NotificationEventListRequest notificationEventListRequest = NotificationEventListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(2)
                .setSortAscending(true)
                .build();
        log.info("NotificationEventListRequest: {}", this.mapper.writeValueAsString(notificationEventListRequest));
    }

    @Test
    void testSMSTemplateListResponse() throws JsonProcessingException {
        SMSTemplateListResponse smsTemplateListResponse = SMSTemplateListResponse.newBuilder()
                .setPageSize(2)
                .setPageNumber(1)
                .setSortAscending(true)
                .setPageCount(2)
                .addAllTemplates(List.of(SMSTemplate.newBuilder()
                        .setTemplateId("templateIdT")
                        .setMessage("contentT")
                        .addAllVariables(List.of("variable1", "variable2"))
                        .setTemplateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                        .build()))
                .build();
        log.info("SMSTemplateListResponse: {}", this.mapper.writeValueAsString(smsTemplateListResponse));
    }

    @Test
    void testEmailTemplateListResponse() throws JsonProcessingException {
        EmailTemplateListResponse emailTemplateListResponse = EmailTemplateListResponse.newBuilder()
                .setPageSize(2)
                .setPageNumber(1)
                .setSortAscending(true)
                .setPageCount(2)
                .addAllTemplates(List.of(EmailTemplate.newBuilder()
                        .setTemplateId("templateIdT")
                        .setSubject("subjectT")
                        .setContent("contentT")
                        .addAllVariables(List.of("variable1", "variable2"))
                        .setTemplateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                        .build()))
                .build();
        log.info("EmailTemplateListResponse: {}", this.mapper.writeValueAsString(emailTemplateListResponse));
    }

    @Test
    void testNotificationEventListResponse() throws JsonProcessingException {
        NotificationEventListResponse notificationEventListResponse = NotificationEventListResponse.newBuilder()
                .setPageSize(2)
                .setPageNumber(1)
                .setSortAscending(true)
                .setPageCount(2)
                .addAllTemplates(List.of(NotificationEvent.newBuilder()
                        .setEventId("eventIdT")
                        .setEventName("eventNameT")
                        .setEventDescription("descriptionT")
                        .setEmailTemplateId("emailTemplateIDT")
                        .setSmsTemplateId("smsTemplateIdT")
                        .addAllEventParameters(List.of("eventParam1", "eventParam2"))
                        .setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE)
                        .setSensitivity(SensitivityLevel.SENSITIVITY_LEVEL_HIGH)
                        .setEmailRetry(1)
                        .setSmsRetry(1)
                        .build()))
                .build();
        log.info("NotificationEventListResponse: {}", this.mapper.writeValueAsString(notificationEventListResponse));
    }

    @Test
    void testCommunicationAuditRecord() throws JsonProcessingException {
        CommunicationAuditRecord communicationAuditRecord = CommunicationAuditRecord.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setQueueId("queueIdT")
                        .setEventId("eventId")
                        .setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                        .setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(1696732104))
                        .addAllToEmail(List.of("toEmail1", "toEmail2"))
                        .addAllCcEmail(List.of("ccEmail1", "ccEmail2"))
                        .addAllBccEmail(List.of("bccEmail1", "bccEmail2"))
                        .addAllEmailAttachments(List.of(Attachment.newBuilder()
                                .setFilename("fileNameT")
                                .setData(ByteString.copyFromUtf8("daata"))
                                .setMimeType("mimeTypeT")
                                .build()))
                        .addAllToPhoneNumber(List.of("toPhoneNumber"))
                        .addAllRecipientsId(List.of("recipientsIdT"))
                        .build())
                .setEmailContent("emailContent")
                .setSmsContent("smsContent")
                .build();
        log.info("CommunicationAuditRecord: {}", this.mapper.writeValueAsString(communicationAuditRecord));
    }
}
