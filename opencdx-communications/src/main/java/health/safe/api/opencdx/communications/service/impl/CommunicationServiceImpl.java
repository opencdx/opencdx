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

import cdx.open_audit.v2alpha.AgentType;
import cdx.open_communication.v2alpha.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotFound;
import health.safe.api.opencdx.communications.model.OpenCDXEmailTemplateModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationEventModel;
import health.safe.api.opencdx.communications.model.OpenCDXSMSTemplateModel;
import health.safe.api.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import health.safe.api.opencdx.communications.service.CommunicationService;
import java.util.HashMap;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for processing Communications Requests.
 */
@Slf4j
@Service
public class CommunicationServiceImpl implements CommunicationService {

    private static final String DOMAIN = "CommunicationServiceImpl";
    private static final String OBJECT = "Object";
    private static final String FAILED_TO_CONVERT_TEMPLATE_REQUEST = "Failed to convert TemplateRequest";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;
    private final OpenCDXNotificationEventRepository openCDXNotificationEventRepository;
    private final OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    private final ObjectMapper objectMapper;
    /**
     * Constructor taking a PersonRepository
     *
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     * @param openCDXEmailTemplateRepository
     * @param openCDXNotificationEventRepository
     * @param openCDXSMSTemplateRespository
     * @param objectMapper
     */
    @Autowired
    public CommunicationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXEmailTemplateRepository = openCDXEmailTemplateRepository;
        this.openCDXNotificationEventRepository = openCDXNotificationEventRepository;
        this.openCDXSMSTemplateRespository = openCDXSMSTemplateRespository;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating Email Template",
                    emailTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(emailTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert EmailTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, emailTemplate.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXEmailTemplateModel model =
                this.openCDXEmailTemplateRepository.save(new OpenCDXEmailTemplateModel(emailTemplate));

        log.info("Created Email Template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXEmailTemplateRepository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find email template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXFailedPrecondition {
        if (!emailTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 1, "Update method called without template id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating Email Template",
                    emailTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(emailTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert EmailTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, emailTemplate.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXEmailTemplateModel model =
                this.openCDXEmailTemplateRepository.save(new OpenCDXEmailTemplateModel(emailTemplate));

        log.info("Updated Email Template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting Email Template",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_TEMPLATE_REQUEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, templateRequest.toString());
            throw openCDXNotAcceptable;
        }

        this.openCDXEmailTemplateRepository.deleteById(new ObjectId(templateRequest.getTemplateId()));
        log.info("Deleted email template: {}", templateRequest.getTemplateId());
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating SMS Template",
                    smsTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(smsTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, "Failed to convert SMSTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, smsTemplate.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXSMSTemplateModel model =
                this.openCDXSMSTemplateRespository.save(new OpenCDXSMSTemplateModel(smsTemplate));
        log.info("Created SMS template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXSMSTemplateRespository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find sms template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXFailedPrecondition {
        if (!smsTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 2, "Update method called without template id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating SMS Template",
                    smsTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(smsTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 5, "Failed to convert SMSTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, smsTemplate.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXSMSTemplateModel model =
                this.openCDXSMSTemplateRespository.save(new OpenCDXSMSTemplateModel(smsTemplate));

        log.info("Updated SMS Template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting SMS Template",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 8, FAILED_TO_CONVERT_TEMPLATE_REQUEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXSMSTemplateRespository.deleteById(new ObjectId(templateRequest.getTemplateId()));
        log.info("Deleted SMS Template: {}", templateRequest.getTemplateId());
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating Notification Event",
                    notificationEvent.getEventId(),
                    this.objectMapper.writeValueAsString(notificationEvent));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, "Failed to convert NotificationEvent", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, notificationEvent.toString());
            throw openCDXNotAcceptable;
        }

        OpenCDXNotificationEventModel model =
                this.openCDXNotificationEventRepository.save(new OpenCDXNotificationEventModel(notificationEvent));

        log.info("Created Notification Event: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXNotificationEventRepository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find event notification: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXFailedPrecondition {
        if (!notificationEvent.hasEventId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 3, "Update method called without event id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating Notification Event",
                    notificationEvent.getEventId(),
                    this.objectMapper.writeValueAsString(notificationEvent));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 7, "Failed to convert NotificationEvent", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, notificationEvent.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXNotificationEventModel model =
                this.openCDXNotificationEventRepository.save(new OpenCDXNotificationEventModel(notificationEvent));

        log.info("Updated Notification Event: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting Notification Event",
                    templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 9, FAILED_TO_CONVERT_TEMPLATE_REQUEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXNotificationEventRepository.deleteById(new ObjectId(templateRequest.getTemplateId()));
        log.info("Deleted Notification Event: {}", templateRequest);
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SuccessResponse sendNotification(Notification notification) {
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request) {

        Page<OpenCDXSMSTemplateModel> all = this.openCDXSMSTemplateRespository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageSize()));

        return SMSTemplateListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllTemplates(all.get()
                        .map(OpenCDXSMSTemplateModel::getProtobufMessage)
                        .toList())
                .build();
    }

    @Override
    public EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request) {
        Page<OpenCDXEmailTemplateModel> all = this.openCDXEmailTemplateRepository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageSize()));
        return EmailTemplateListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .setSortAscending(request.getSortAscending())
                .addAllTemplates(all.get()
                        .map(OpenCDXEmailTemplateModel::getProtobufMessage)
                        .toList())
                .build();
    }

    @Override
    public NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request) {
        Page<OpenCDXNotificationEventModel> all = this.openCDXNotificationEventRepository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageNumber()));
        return NotificationEventListResponse.newBuilder()
                .setPageCount(all.getTotalPages())
                .setPageNumber(request.getPageNumber())
                .setPageSize(request.getPageSize())
                .addAllTemplates(all.get()
                        .map(OpenCDXNotificationEventModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
