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
package health.safe.api.opencdx.communications.service.impl;

import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.grpc.communication.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {
    @SuppressWarnings("javaL:S1068")
    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructor taking a PersonRepository
     * @param openCDXAuditService Audit service for tracking FDA requirements
     */
    @Autowired
    public CommunicationServiceImpl(OpenCDXAuditService openCDXAuditService) {
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) {
        return EmailTemplate.newBuilder(emailTemplate)
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) {
        return EmailTemplate.newBuilder()
                .setTemplateId(templateRequest.getTemplateId())
                .build();
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) {
        return emailTemplate;
    }

    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) {
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) {
        return SMSTemplate.newBuilder(smsTemplate)
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) {
        return SMSTemplate.newBuilder()
                .setTemplateId(templateRequest.getTemplateId())
                .build();
    }

    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) {
        return smsTemplate;
    }

    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) {
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) {
        return NotificationEvent.newBuilder(notificationEvent)
                .setEventId(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) {
        return NotificationEvent.newBuilder()
                .setEventId(templateRequest.getTemplateId())
                .build();
    }

    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent) {
        return notificationEvent;
    }

    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) {
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SuccessResponse sendNotification(Notification notification) {
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request) {
        return SMSTemplateListResponse.newBuilder()
                .setPageCount(1)
                .setPageNumber(1)
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addTemplates(
                        SMSTemplate.newBuilder().setTemplateId(UUID.randomUUID().toString()))
                .build();
    }

    @Override
    public EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request) {
        return EmailTemplateListResponse.newBuilder()
                .setPageCount(1)
                .setPageNumber(1)
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addTemplates(EmailTemplate.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Override
    public NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request) {
        return NotificationEventListResponse.newBuilder()
                .setPageCount(1)
                .setPageNumber(1)
                .build();
    }
}
