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
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXHtmlSanitizer;
import cdx.opencdx.commons.service.impl.OwaspHtmlSanitizerImpl;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
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
public class OpenCDXCommunicationSmsServiceImpl implements OpenCDXCommunicationSmsService {
    private OpenCDXHtmlSanitizer openCDXHtmlSanitizer = new OwaspHtmlSanitizerImpl();

    private static final String DOMAIN = "OpenCDXNotificationServiceImpl";
    private static final String OBJECT = "Object";
    private static final String FAILED_TO_CONVERT_TEMPLATE_REQUEST = "Failed to convert TemplateRequest";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXNotificationEventRepository openCDXNotificationEventRepository;
    private final OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    /**
     * Constructor taking some repositoroes
     *
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     * @param openCDXNotificationEventRepository Repository for saving Notification Events
     * @param openCDXSMSTemplateRespository      Repository for saving SMS Templates
     * @param openCDXCurrentUser                 Current User Service to access information.
     * @param objectMapper                       ObjectMapper used for converting messages for the audit system.
     */
    @Autowired
    public OpenCDXCommunicationSmsServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXSMSTemplateRespository = openCDXSMSTemplateRespository;
        this.openCDXNotificationEventRepository = openCDXNotificationEventRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate rawSmsTemplate) throws OpenCDXNotAcceptable {
        String sanity = openCDXHtmlSanitizer.sanitize(rawSmsTemplate.getMessage());
        SMSTemplate smsTemplate =
                SMSTemplate.newBuilder(rawSmsTemplate).setMessage(sanity).build();
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
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

    @CacheEvict(value = "sms_templates", key = "#rawSmsTemplate.templateId")
    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate rawSmsTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        String sanity = openCDXHtmlSanitizer.sanitize(rawSmsTemplate.getMessage());
        SMSTemplate smsTemplate =
                SMSTemplate.newBuilder(rawSmsTemplate).setMessage(sanity).build();
        if (!smsTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 2, "Update method called without template id");
        }
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
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
        if (this.openCDXNotificationEventRepository.existsBySmsTemplateId(
                new ObjectId(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
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
}