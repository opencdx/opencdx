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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.grpc.audit.AgentType;
import health.safe.api.opencdx.grpc.communication.*;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final OpenCDXAuditService openCDXAuditService;

    private final ObjectMapper objectMapper;
    /**
     * Constructor taking a PersonRepository
     *
     * @param openCDXAuditService Audit service for tracking FDA requirements
     * @param objectMapper
     */
    @Autowired
    public CommunicationServiceImpl(OpenCDXAuditService openCDXAuditService, ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Creating Email Template",
                    emailTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(emailTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 1, "Failed to convert EmailTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", emailTemplate.toString());
            throw openCDXNotAcceptable;
        }
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
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Deleting Email Template",
                    emailTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(emailTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 2, "Failed to convert EmailTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", emailTemplate.toString());
            throw openCDXNotAcceptable;
        }
        return emailTemplate;
    }

    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Deleting Email Template",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 3, "Failed to convert TemplateRequest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Creating SMS Template",
                    smsTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(smsTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 4, "Failed to convert SMSTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", smsTemplate.toString());
            throw openCDXNotAcceptable;
        }
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
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Updating SMS Template",
                    smsTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(smsTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 5, "Failed to convert SMSTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", smsTemplate.toString());
            throw openCDXNotAcceptable;
        }
        return smsTemplate;
    }

    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Deleting SMS Template",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 8, "Failed to convert TemplateRequest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Creating Notification Event",
                    notificationEvent.getEventId(),
                    this.objectMapper.writeValueAsString(notificationEvent));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 6, "Failed to convert NotificationEvent", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", notificationEvent.toString());
            throw openCDXNotAcceptable;
        }
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
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Updating Notification Event",
                    notificationEvent.getEventId(),
                    this.objectMapper.writeValueAsString(notificationEvent));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 7, "Failed to convert NotificationEvent", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", notificationEvent.toString());
            throw openCDXNotAcceptable;
        }
        return notificationEvent;
    }

    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.HUMAN_USER,
                    "Deleting Notification Event",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("CommunicationServiceImpl", 9, "Failed to convert TemplateRequest", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", templateRequest.toString());
            throw openCDXNotAcceptable;
        }
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
