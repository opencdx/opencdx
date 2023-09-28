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
import cdx.open_audit.v2alpha.SensitivityLevel;
import cdx.open_communication.v2alpha.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotFound;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.model.OpenCDXEmailTemplateModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationEventModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationModel;
import health.safe.api.opencdx.communications.model.OpenCDXSMSTemplateModel;
import health.safe.api.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.communications.service.OpenCDXEmailService;
import health.safe.api.opencdx.communications.service.OpenCDXHTMLProcessor;
import health.safe.api.opencdx.communications.service.OpenCDXSMSService;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for processing Communications Requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class CommunicationServiceImpl implements CommunicationService {

    private static final String DOMAIN = "CommunicationServiceImpl";
    private static final String OBJECT = "Object";
    private static final String FAILED_TO_CONVERT_TEMPLATE_REQUEST = "Failed to convert TemplateRequest";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;
    private final OpenCDXNotificationEventRepository openCDXNotificationEventRepository;
    private final OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;
    private final OpenCDXNotificaitonRepository openCDXNotificaitonRepository;
    private final OpenCDXEmailService openCDXEmailService;
    private final OpenCDXSMSService openCDXSMSService;
    private final OpenCDXHTMLProcessor openCDXHTMLProcessor;

    private final ObjectMapper objectMapper;
    /**
     * Constructor taking a PersonRepository
     *
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     * @param openCDXEmailTemplateRepository     Repository for saving Email Templates
     * @param openCDXNotificationEventRepository Repository for saving Notification Events
     * @param openCDXSMSTemplateRespository      Repository for saving SMS Templates
     * @param openCDXNotificaitonRepository
     * @param openCDXEmailService                Email service for sending emails
     * @param openCDXSMSService                  SMS Service for sending SMS
     * @param openCDXHTMLProcessor               HTML Process for processing HTML Templates.
     * @param objectMapper                       ObjectMapper used for converting messages for the audit system.
     */
    @Autowired
    public CommunicationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificaitonRepository openCDXNotificaitonRepository,
            OpenCDXEmailService openCDXEmailService,
            OpenCDXSMSService openCDXSMSService,
            OpenCDXHTMLProcessor openCDXHTMLProcessor,
            ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXEmailTemplateRepository = openCDXEmailTemplateRepository;
        this.openCDXNotificationEventRepository = openCDXNotificationEventRepository;
        this.openCDXSMSTemplateRespository = openCDXSMSTemplateRespository;
        this.openCDXNotificaitonRepository = openCDXNotificaitonRepository;
        this.openCDXEmailService = openCDXEmailService;
        this.openCDXSMSService = openCDXSMSService;
        this.openCDXHTMLProcessor = openCDXHTMLProcessor;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating Email Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @Cacheable(value = "email_templates", key = "#templateRequest.templateId")
    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXEmailTemplateRepository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find email template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @CacheEvict(value = "email_templates", key = "#emailTemplate.templateId")
    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        if (!emailTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 1, "Update method called without template id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating Email Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @CacheEvict(value = "email_templates", key = "#templateRequest.templateId")
    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable {

        if(this.openCDXNotificationEventRepository.existsByEmailTemplateId(new ObjectId(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }

        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting Email Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @Cacheable(value = "sms_templates", key = "#templateRequest.templateId")
    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXSMSTemplateRespository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find sms template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @CacheEvict(value = "sms_templates", key = "#smsTemplate.templateId")
    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        if (!smsTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 2, "Update method called without template id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @CacheEvict(value = "sms_templates", key = "#templateRequest.templateId")
    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable {
        if(this.openCDXNotificationEventRepository.existsBySmsTemplateId(new ObjectId(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Creating Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @Cacheable(value = "notificaiton_event", key = "#templateRequest.templateId")
    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXNotificationEventRepository
                .findById(new ObjectId(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find event notification: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @CacheEvict(value = "notificaiton_event", key = "#notificationEvent.eventId")
    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        if (!notificationEvent.hasEventId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 3, "Update method called without event id");
        }
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Updating Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    @CacheEvict(value = "notificaiton_event", key = "#templateRequest.templateId")
    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) throws OpenCDXNotAcceptable {
        try {
            this.openCDXAuditService.config(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    "Deleting Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
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

    public void processOpenCDXNotification(OpenCDXNotificationModel openCDXNotificationModel) {
        CommunicationAuditRecord.Builder auditBuilder = CommunicationAuditRecord.newBuilder();
        auditBuilder.setNotification(openCDXNotificationModel.getProtobufMessage());

        Map<String, Object> objectVariableMap = new HashMap<>(openCDXNotificationModel.getVariables());

        NotificationEvent notificationEvent = this.getNotificationEvent(TemplateRequest.newBuilder()
                .setTemplateId(openCDXNotificationModel.getEventId().toHexString())
                .build());

        if (notificationEvent.hasEmailTemplateId()
                && openCDXNotificationModel.getEmailStatus().equals(NotificationStatus.NOTIFICATION_STATUS_PENDING)) {
            sendEmail(openCDXNotificationModel, auditBuilder, objectVariableMap, notificationEvent);
        }

        if (notificationEvent.hasSmsTemplateId()
                && openCDXNotificationModel.getSmsStatus().equals(NotificationStatus.NOTIFICATION_STATUS_PENDING)) {
            sendSMS(openCDXNotificationModel, auditBuilder, objectVariableMap, notificationEvent);
        }

        openCDXNotificaitonRepository.save(openCDXNotificationModel);

        this.recordAudit(auditBuilder.build(), notificationEvent);
    }

    private void sendSMS(
            OpenCDXNotificationModel openCDXNotificationModel,
            CommunicationAuditRecord.Builder auditBuilder,
            Map<String, Object> objectVariableMap,
            NotificationEvent notificationEvent) {
        SMSTemplate smsTemplate = this.getSMSTemplate(TemplateRequest.newBuilder()
                .setTemplateId(notificationEvent.getSmsTemplateId())
                .build());
        String message = this.processHTML(
                smsTemplate.getMessage(),
                smsTemplate.getVariablesList().stream().toList(),
                objectVariableMap);

        if (this.openCDXSMSService.sendSMS(message, openCDXNotificationModel.getPhoneNumbers())) {

            auditBuilder.setSmsContent(message);
            openCDXNotificationModel.setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_SENT);
        } else {
            openCDXNotificationModel.setSmsFailCount(openCDXNotificationModel.getSmsFailCount() + 1);
        }

        if (notificationEvent.getSmsRetry() != 0
                && openCDXNotificationModel.getSmsFailCount() >= notificationEvent.getSmsRetry()) {
            openCDXNotificationModel.setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_FAILED);
        }
    }

    private void sendEmail(
            OpenCDXNotificationModel openCDXNotificationModel,
            CommunicationAuditRecord.Builder auditBuilder,
            Map<String, Object> objectVariableMap,
            NotificationEvent notificationEvent) {
        EmailTemplate emailTemplate = this.getEmailTemplate(TemplateRequest.newBuilder()
                .setTemplateId(notificationEvent.getEmailTemplateId())
                .build());
        String message = this.processHTML(
                emailTemplate.getContent(),
                emailTemplate.getVariablesList().stream().toList(),
                objectVariableMap);

        if (this.openCDXEmailService.sendEmail(
                emailTemplate.getSubject(),
                message,
                openCDXNotificationModel.getToEmail(),
                openCDXNotificationModel.getCcEmail(),
                openCDXNotificationModel.getBccEmail(),
                openCDXNotificationModel.getAttachments())) {

            auditBuilder.setEmailContent(message);
            openCDXNotificationModel.setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_SENT);
        } else {
            openCDXNotificationModel.setEmailFailCount(openCDXNotificationModel.getEmailFailCount() + 1);
        }

        if (notificationEvent.getEmailRetry() != 0
                && openCDXNotificationModel.getEmailFailCount() >= notificationEvent.getEmailRetry()) {
            openCDXNotificationModel.setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_FAILED);
        }
    }

    private void recordAudit(CommunicationAuditRecord auditRecord, NotificationEvent notificationEvent) {

        try {
            this.openCDXAuditService.communication(
                    UUID.randomUUID().toString(),
                    AgentType.AGENT_TYPE_HUMAN_USER,
                    notificationEvent.getEventDescription(),
                    notificationEvent.getSensitivity(),
                    UUID.randomUUID().toString(),
                    notificationEvent.getEventName() + ": " + notificationEvent.getEventId(),
                    this.objectMapper.writeValueAsString(auditRecord));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 10, "Failed to convert AuditRecord", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, auditRecord.toString());
            throw openCDXNotAcceptable;
        }
    }

    @Override
    public SuccessResponse sendNotification(Notification notification)
            throws OpenCDXFailedPrecondition, OpenCDXNotFound, OpenCDXNotAcceptable {

        NotificationEvent notificationEvent = this.getNotificationEvent(TemplateRequest.newBuilder()
                .setTemplateId(notification.getEventId())
                .build());

        OpenCDXNotificationModel openCDXNotificationModel =
                this.openCDXNotificaitonRepository.save(new OpenCDXNotificationModel(notification));
        if (notificationEvent.getPriority().equals(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE)) {
            this.processOpenCDXNotification(openCDXNotificationModel);
        } else {
            this.recordAudit(
                    CommunicationAuditRecord.newBuilder()
                            .setNotification(openCDXNotificationModel.getProtobufMessage())
                            .build(),
                    notificationEvent);
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    private String processHTML(String template, List<String> variables, Map<String, Object> data)
            throws OpenCDXFailedPrecondition {
        for (String variable : variables) {
            if (!data.containsKey(variable)) {
                throw new OpenCDXFailedPrecondition(DOMAIN, 8, "Missing Data from required variable list: " + variable);
            }
        }

        return this.openCDXHTMLProcessor.processHTML(template, data);
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
