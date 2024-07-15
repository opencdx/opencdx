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
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.communications.model.OpenCDXMessageModel;
import cdx.opencdx.communications.model.OpenCDXMessageTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXMessageRepository;
import cdx.opencdx.communications.repository.OpenCDXMessageTemplateRepository;
import cdx.opencdx.communications.service.OpenCDXSystemMessageService;
import cdx.opencdx.grpc.data.MessageTemplate;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.communications.*;
import cdx.opencdx.grpc.types.MessageStatus;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
 * Service for Messages.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXSystemMessageServiceImpl implements OpenCDXSystemMessageService {

    private static final String OBJECT = "Object";
    private static final String DOMAIN = "OpenCDXMessageServiceImpl";

    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository;
    private final OpenCDXMessageRepository openCDXMessageRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Default Constructor
     * @param openCDXDocumentValidator Document Validator
     * @param openCDXAuditService Audit Service
     * @param objectMapper Object Mapper
     * @param openCDXMessageTemplateRepository Message Template Repository
     * @param openCDXMessageRepository Message Repository
     * @param openCDXCurrentUser Current User
     */
    @Autowired
    public OpenCDXSystemMessageServiceImpl(
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository,
            OpenCDXMessageRepository openCDXMessageRepository,
            OpenCDXCurrentUser openCDXCurrentUser) {
        // Explicit declaration to prevent this class from inadvertently being made instantiable

        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXMessageTemplateRepository = openCDXMessageTemplateRepository;
        this.openCDXMessageRepository = openCDXMessageRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    /**
     * Create a Message Template
     *
     * @param messageTemplate Message Template to create.
     * @return the created Message Template.
     * @throws OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public MessageTemplate createMessageTemplate(MessageTemplate messageTemplate) throws OpenCDXNotAcceptable {
        if (messageTemplate.hasTemplateId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "message", new OpenCDXIdentifier(messageTemplate.getTemplateId()));
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Message Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    "Message Template" + messageTemplate.getTemplateId(),
                    this.objectMapper.writeValueAsString(messageTemplate));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert Message template", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, messageTemplate.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXMessageTemplateModel model =
                this.openCDXMessageTemplateRepository.save(new OpenCDXMessageTemplateModel(messageTemplate));
        log.trace("Created Message Template : {}", model.getId());
        return model.getProtobufMessage();
    }

    /**
     * Get a Message Template
     *
     * @param templateRequest Request ID of the MessageTemplate to retrieve.
     * @return the requested MessageTemplate.
     * @throws OpenCDXNotFound Template with requested ID not found.
     */
    @Cacheable(value = "message_template", key = "#templateRequest.templateId")
    @Override
    public MessageTemplate getMessageTemplate(TemplateRequest templateRequest)
            throws OpenCDXNotFound, OpenCDXNotAcceptable {
        return this.openCDXMessageTemplateRepository
                .findById(new OpenCDXIdentifier(templateRequest.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, "Failed to find message template: " + templateRequest.getTemplateId()))
                .getProtobufMessage();
    }

    /**
     * Update Message Template
     *
     * @param messageTemplate Message Template to update.
     * @return the updated Message Template.
     * @throws OpenCDXFailedPrecondition Missing event id.
     * @throws OpenCDXNotAcceptable      Failed to convert to JSON
     */
    @CacheEvict(value = "message_template", key = "#messageTemplate.templateId")
    @Override
    public MessageTemplate updateMessageTemplate(MessageTemplate messageTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable {
        if (!messageTemplate.hasTemplateId()) {
            throw new OpenCDXFailedPrecondition(DOMAIN, 3, "Update method called without event id");
        }

        OpenCDXMessageTemplateModel messageTemplateModel = this.openCDXMessageTemplateRepository
                .findById(new OpenCDXIdentifier(messageTemplate.getTemplateId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 4, "Failed to find message template: " + messageTemplate.getTemplateId()));
        messageTemplateModel = this.openCDXMessageTemplateRepository.save(messageTemplateModel.update(messageTemplate));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Message Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    DOMAIN + messageTemplateModel.getId(),
                    this.objectMapper.writeValueAsString(messageTemplateModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 7, "Failed to convert MessageTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, messageTemplateModel.toString());
            throw openCDXNotAcceptable;
        }
        log.trace("Updated Message Template: {}", messageTemplateModel.getId());
        return messageTemplateModel.getProtobufMessage();
    }

    /**
     * Delete Message Template
     *
     * @param templateRequest Template Request
     * @return SuccessResponse indicating if the action was successful.
     * @throws OpenCDXNotAcceptable Failed to convert to JSON
     */
    @CacheEvict(value = "message_template", key = "#templateRequest.templateId")
    @Override
    public SuccessResponse deleteMessageTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable {
        if (openCDXMessageTemplateRepository.existsById(new OpenCDXIdentifier(templateRequest.getTemplateId()))) {
            return SuccessResponse.newBuilder().setSuccess(false).build();
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting Message Template",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    DOMAIN + templateRequest.getTemplateId(),
                    this.objectMapper.writeValueAsString(templateRequest));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 9, "Failed to convert MessageTemplate", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, templateRequest.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXMessageTemplateRepository.deleteById(new OpenCDXIdentifier(templateRequest.getTemplateId()));
        log.trace("Deleted Notification Event: {}", templateRequest);
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    /**
     * List Message Template
     *
     * @param pagination Message Template information to trigger.
     * @return Message Template Response indicating if the action was successful.
     * @throws OpenCDXNotFound           Template not found
     * @throws OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @throws OpenCDXNotAcceptable      Failed to convert to JSON
     */
    @Override
    public MessageTemplateListResponse listMessageTemplates(Pagination pagination)
            throws OpenCDXFailedPrecondition, OpenCDXNotFound, OpenCDXNotAcceptable {
        Pageable pageable;
        if (pagination.hasSort()) {
            pageable = PageRequest.of(
                    pagination.getPageNumber(),
                    pagination.getPageSize(),
                    pagination.getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    pagination.getSort());
        } else {
            pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        }
        Page<OpenCDXMessageTemplateModel> all = this.openCDXMessageTemplateRepository.findAll(pageable);
        return MessageTemplateListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(pagination)
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllTemplates(all.get()
                        .map(OpenCDXMessageTemplateModel::getProtobufMessage)
                        .toList())
                .build();
    }

    /**
     * Get Messages
     *
     * @param request Request Messages
     * @return requested Messages
     */
    @Override
    public GetMessagesResponse getMessages(GetMessagesRequest request) {
        return GetMessagesResponse.newBuilder()
                .addAllMessages(
                        this.openCDXMessageRepository
                                .findAllByPatientId(new OpenCDXIdentifier(request.getPatientId()))
                                .stream()
                                .map(OpenCDXMessageModel::getProtobufMessage)
                                .toList())
                .build();
    }

    /**
     * Mark a message a Read
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    @Override
    public MarkMessagesAsReadResponse markMessageAsRead(MarkMessagesAsReadRequest request) {
        List<OpenCDXMessageModel> openCDXMessageModelList = Collections.emptyList();
        if (!request.getIdList().isEmpty()) {
            openCDXMessageModelList = openCDXMessageRepository.findAllById(
                    request.getIdList().stream().map(OpenCDXIdentifier::new).toList());
            openCDXMessageModelList.forEach(model -> model.setMessageStatus(MessageStatus.READ));
            openCDXMessageRepository.saveAll(openCDXMessageModelList);
        }
        return MarkMessagesAsReadResponse.newBuilder()
                .addAllMessages(openCDXMessageModelList.stream()
                        .map(OpenCDXMessageModel::getProtobufMessage)
                        .toList())
                .build();
    }

    /**
     * Mark a message as unread
     *
     * @param request Mark Messages As Unread Request
     * @return requested MarkMessages
     */
    @Override
    public MarkMessagesAsUnreadResponse markMessageAsUnread(MarkMessagesAsUnreadRequest request) {
        List<OpenCDXMessageModel> openCDXMessageModelList = Collections.emptyList();
        if (!request.getIdList().isEmpty()) {
            openCDXMessageModelList = openCDXMessageRepository.findAllById(
                    request.getIdList().stream().map(OpenCDXIdentifier::new).toList());
            openCDXMessageModelList.forEach(model -> model.setMessageStatus(MessageStatus.UNREAD));
            openCDXMessageRepository.saveAll(openCDXMessageModelList);
        }
        return MarkMessagesAsUnreadResponse.newBuilder()
                .addAllMessages(openCDXMessageModelList.stream()
                        .map(OpenCDXMessageModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
