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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXHtmlSanitizer;
import cdx.opencdx.commons.service.impl.OwaspHtmlSanitizerImpl;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
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
 * Service for processing Email Communications Requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCommunicationEmailServiceImpl implements OpenCDXCommunicationEmailService {
    private OpenCDXHtmlSanitizer openCDXHtmlSanitizer = new OwaspHtmlSanitizerImpl();

    private static final String DOMAIN = "OpenCDXNotificationServiceImpl";
    private static final String OBJECT = "Object";
    private static final String FAILED_TO_CONVERT_TEMPLATE_REQUEST = "Failed to convert TemplateRequest";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;
    private final OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    private final ObjectMapper objectMapper;
    /**
     * Constructor taking some repositories
     *
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     * @param openCDXEmailTemplateRepository     Repository for saving Email Templates
     * @param openCDXNotificationEventRepository Repository for saving Notification Events
     * @param objectMapper                       ObjectMapper used for converting messages for the audit system.
     */
    @Autowired
    public OpenCDXCommunicationEmailServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository,
            ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXEmailTemplateRepository = openCDXEmailTemplateRepository;
        this.openCDXNotificationEventRepository = openCDXNotificationEventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate rawEmailTemplate) throws OpenCDXNotAcceptable {
        String sanity = openCDXHtmlSanitizer.sanitize(rawEmailTemplate.getContent());
        EmailTemplate emailTemplate =
                EmailTemplate.newBuilder(rawEmailTemplate).setContent(sanity).build();
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

    @CacheEvict(value = "email_templates", key = "#rawEmailTemplate.templateId")
    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate rawEmailTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        String sanity = openCDXHtmlSanitizer.sanitize(rawEmailTemplate.getContent());
        EmailTemplate emailTemplate =
                EmailTemplate.newBuilder(rawEmailTemplate).setContent(sanity).build();
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

        if (this.openCDXNotificationEventRepository.existsByEmailTemplateId(
                new ObjectId(templateRequest.getTemplateId()))) {
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
}
