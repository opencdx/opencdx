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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXHtmlSanitizer;
import cdx.opencdx.commons.service.impl.OwaspHtmlSanitizerImpl;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import cdx.opencdx.communications.service.OpenCDXCommunicationSmsService;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.SMSTemplate;
import cdx.opencdx.grpc.service.communications.SMSTemplateListRequest;
import cdx.opencdx.grpc.service.communications.SMSTemplateListResponse;
import cdx.opencdx.grpc.service.communications.SuccessResponse;
import cdx.opencdx.grpc.service.communications.TemplateRequest;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for processing Communications Requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCommunicationSmsServiceImpl implements OpenCDXCommunicationSmsService {
    private static final String SMS_TEMPLATE = "SMS-TEMPLATE: ";
    private final OpenCDXHtmlSanitizer openCDXHtmlSanitizer = new OwaspHtmlSanitizerImpl();

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
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    SMS_TEMPLATE + smsTemplate.getTemplateId(),
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
        log.trace("Created SMS template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @Cacheable(value = "sms_templates", key = "#templateRequest.templateId")
    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound {
        return this.openCDXSMSTemplateRespository
                .findById(new OpenCDXIdentifier(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find sms template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    @CacheEvict(value = "sms_templates", key = "#rawSmsTemplate.templateId")
    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate rawSmsTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        if (!rawSmsTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 2, "Update method called without template id");
        }
        String sanity = openCDXHtmlSanitizer.sanitize(rawSmsTemplate.getMessage());
        SMSTemplate smsTemplate =
                SMSTemplate.newBuilder(rawSmsTemplate).setMessage(sanity).build();
        OpenCDXSMSTemplateModel model = this.openCDXSMSTemplateRespository
                .findById(new OpenCDXIdentifier(smsTemplate.getTemplateId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 3, "Failed to find sms template: " + smsTemplate.getTemplateId()));

        model = this.openCDXSMSTemplateRespository.save(model.update(smsTemplate));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    SMS_TEMPLATE + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 5, "Failed to convert SMSTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }

        log.trace("Updated SMS Template: {}", model.getId());
        return model.getProtobufMessage();
    }

    @CacheEvict(value = "sms_templates", key = "#templateRequest.templateId")
    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable {
        if (!this.openCDXNotificationEventRepository.existsBySmsTemplateId(
                new OpenCDXIdentifier(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting SMS Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    SMS_TEMPLATE + templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 8, FAILED_TO_CONVERT_TEMPLATE_REQUEST, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXSMSTemplateRespository.deleteById(new OpenCDXIdentifier(templateRequest.getTemplateId()));
        log.trace("Deleted SMS Template: {}", templateRequest.getTemplateId());
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request) {
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }

        Page<OpenCDXSMSTemplateModel> all = this.openCDXSMSTemplateRespository.findAll(pageable);

        return SMSTemplateListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllTemplates(all.get()
                        .map(OpenCDXSMSTemplateModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
