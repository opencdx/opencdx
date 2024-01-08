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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OpenCDXNotificationServiceImpl implements OpenCDXNotificationService {

    private static final String DOMAIN = "OpenCDXNotificationServiceImpl";
    private static final String OBJECT = "Object";
    private static final String FAILED_TO_CONVERT_TEMPLATE_REQUEST = "Failed to convert TemplateRequest";
    private static final String NOTIFICATION_EVENT = "NOTIFICATION-EVENT: ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXNotificationEventRepository openCDXNotificationEventRepository;
    private final OpenCDXNotificaitonRepository openCDXNotificaitonRepository;
    private final OpenCDXEmailService openCDXEmailService;
    private final OpenCDXSMSService openCDXSMSService;
    private final OpenCDXHTMLProcessor openCDXHTMLProcessor;

    private final OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    private final OpenCDXCommunicationEmailService openCDXCommunicationEmailService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    /**
     * Constructor taking some repositoroes
     *
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     * @param openCDXNotificationEventRepository Repository for saving Notification Events
     * @param openCDXNotificaitonRepository      Repository for saving Notificaitons
     * @param openCDXEmailService                Email service for sending emails
     * @param openCDXSMSService                  SMS Service for sending SMS
     * @param openCDXHTMLProcessor               HTML Process for processing HTML Templates.
     * @param openCDXCurrentUser                 Current User Service to access information.
     * @param openCDXCommunicationSmsService     SMS Service to use for handling SMS
     * @param openCDXCommunicationEmailService   Email Service to use for handling Email
     * @param objectMapper                       ObjectMapper used for converting messages for the audit system.
     * @param openCDXDocumentValidator           Document Validator for validating documents.
     */
    @Autowired
    public OpenCDXNotificationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository,
            OpenCDXNotificaitonRepository openCDXNotificaitonRepository,
            OpenCDXEmailService openCDXEmailService,
            OpenCDXSMSService openCDXSMSService,
            OpenCDXHTMLProcessor openCDXHTMLProcessor,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXCommunicationSmsService openCDXCommunicationSmsService,
            OpenCDXCommunicationEmailService openCDXCommunicationEmailService,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXNotificationEventRepository = openCDXNotificationEventRepository;
        this.openCDXNotificaitonRepository = openCDXNotificaitonRepository;
        this.openCDXEmailService = openCDXEmailService;
        this.openCDXSMSService = openCDXSMSService;
        this.openCDXHTMLProcessor = openCDXHTMLProcessor;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXCommunicationSmsService = openCDXCommunicationSmsService;
        this.openCDXCommunicationEmailService = openCDXCommunicationEmailService;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) throws OpenCDXNotAcceptable {
        if (notificationEvent.hasEmailTemplateId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "email-template", new ObjectId(notificationEvent.getEmailTemplateId()));
        }
        if (notificationEvent.hasSmsTemplateId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "sms-template", new ObjectId(notificationEvent.getSmsTemplateId()));
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    NOTIFICATION_EVENT + notificationEvent.getEventId(),
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
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    NOTIFICATION_EVENT + notificationEvent.getEventId(),
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
        if (this.openCDXNotificaitonRepository.existsByEventId(new ObjectId(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting Notification Event",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    NOTIFICATION_EVENT + templateRequest.getTemplateId(),
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
        SMSTemplate smsTemplate = this.openCDXCommunicationSmsService.getSMSTemplate(TemplateRequest.newBuilder()
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
        EmailTemplate emailTemplate =
                this.openCDXCommunicationEmailService.getEmailTemplate(TemplateRequest.newBuilder()
                        .setTemplateId(notificationEvent.getEmailTemplateId())
                        .build());
        String subject = this.processHTML(
                emailTemplate.getSubject(),
                emailTemplate.getVariablesList().stream().toList(),
                objectVariableMap);
        String message = this.processHTML(
                emailTemplate.getContent(),
                emailTemplate.getVariablesList().stream().toList(),
                objectVariableMap);

        if (this.openCDXEmailService.sendEmail(
                subject,
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
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.communication(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    notificationEvent.getEventDescription(),
                    notificationEvent.getSensitivity(),
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    NOTIFICATION_EVENT + ": " + notificationEvent.getEventId(),
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

        OpenCDXNotificationModel openCDXNotificationModel = new OpenCDXNotificationModel(notification);
        openCDXNotificationModel.setPriority(notificationEvent.getPriority());
        openCDXNotificationModel = this.openCDXNotificaitonRepository.save(openCDXNotificationModel);
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
    public NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request) {
        Page<OpenCDXNotificationEventModel> all = this.openCDXNotificationEventRepository.findAll(PageRequest.of(
                request.getPagination().getPageNumber(), request.getPagination().getPageSize()));
        return NotificationEventListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllTemplates(all.get()
                        .map(OpenCDXNotificationEventModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
