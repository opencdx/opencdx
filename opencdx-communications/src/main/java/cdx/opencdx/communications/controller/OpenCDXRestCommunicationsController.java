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
package cdx.opencdx.communications.controller;

import cdx.opencdx.communications.service.OpenCDXCommunicationEmailService;
import cdx.opencdx.communications.service.OpenCDXCommunicationSmsService;
import cdx.opencdx.communications.service.OpenCDXMessageService;
import cdx.opencdx.communications.service.OpenCDXNotificationService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.communications.*;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /greeting api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestCommunicationsController {

    private final OpenCDXNotificationService openCDXNotificationService;
    private final OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    private final OpenCDXCommunicationEmailService openCDXCommunicationEmailService;
    private final OpenCDXMessageService openCDXMessageService;

    /**
     * Constructor that takes a OpenCDXNotificationService, OpenCDXCommunicationSmsService, OpenCDXCommunicationEmailService
     *  @param openCDXNotificationService       service for processing requests.
     * @param openCDXCommunicationSmsService   service for processing SMS requests.
     * @param openCDXCommunicationEmailService service for processing email requests.
     * @param openCDXMessageService        service for processing message requests.
     */
    @Autowired
    public OpenCDXRestCommunicationsController(
            OpenCDXNotificationService openCDXNotificationService,
            OpenCDXCommunicationSmsService openCDXCommunicationSmsService,
            OpenCDXCommunicationEmailService openCDXCommunicationEmailService,
            OpenCDXMessageService openCDXMessageService) {
        log.info("OpenCDXRestCommunicationsController created");
        this.openCDXNotificationService = openCDXNotificationService;
        this.openCDXCommunicationSmsService = openCDXCommunicationSmsService;
        this.openCDXCommunicationEmailService = openCDXCommunicationEmailService;
        this.openCDXMessageService = openCDXMessageService;
    }

    /**
     * Create an EmailTemplate.
     *
     * @param emailTemplate the EmailTemplate to create
     * @return the created EmailTemplate
     */
    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        log.trace("Creating EmailTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationEmailService.createEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Gets an EmailTemplate
     *
     * @param id the EmailTemplate ID to retrieve
     * @return the requested EmailTemplate.
     */
    @GetMapping("/email/{id}")
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable String id) {
        log.trace("Getting EmailTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationEmailService.getEmailTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the EmailTemplate
     *
     * @param emailTemplate the EmailTemplate to update
     * @return the updated EmailTemplate
     */
    @PutMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        log.trace("Updating EmailTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationEmailService.updateEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Delete the EmailTemplate with the id.
     *
     * @param id the id of the EmailTemplate to delete
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/email/{id}")
    public ResponseEntity<SuccessResponse> deleteEmailTemplate(@PathVariable String id) {
        log.trace("Deleting EmailTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationEmailService.deleteEmailTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Create a SMSTemplate.
     *
     * @param smsTemplate the SMSTemplate to create
     * @return the created SMSTemplate
     */
    @PostMapping(value = "/sms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SMSTemplate> createSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        log.trace("Creating SMSTemplate");
        return new ResponseEntity<>(this.openCDXCommunicationSmsService.createSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Gets SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     */
    @GetMapping("/sms/{id}")
    public ResponseEntity<SMSTemplate> getSMSTemplate(@PathVariable String id) {
        log.trace("Getting SMSTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationSmsService.getSMSTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the SMSTemplate
     *
     * @param smsTemplate the SMSTemplate to update
     * @return the updated SMSTemplate.
     */
    @PutMapping(value = "/sms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SMSTemplate> updateSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        log.trace("Updating SMSTemplate");
        return new ResponseEntity<>(this.openCDXCommunicationSmsService.updateSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Delete SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to delete.
     * @return a SuccessResponse to indicate if successful.
     */
    @DeleteMapping("/sms/{id}")
    public ResponseEntity<SuccessResponse> deleteSMSTemplate(@PathVariable String id) {
        log.trace("Deleting SMSTemplate");
        return new ResponseEntity<>(
                this.openCDXCommunicationSmsService.deleteSMSTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Create a NotificationEvent.
     *
     * @param notificationEvent the NotificationEvent to create.
     * @return the created NotificationEvent.
     */
    @PostMapping(value = "/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationEvent> createNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.openCDXNotificationService.createNotificationEvent(notificationEvent), HttpStatus.OK);
    }

    /**
     * Gets NotificationEvent with this id.
     *
     * @param id the id of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     */
    @GetMapping("/event/{id}")
    public ResponseEntity<NotificationEvent> getNotificationEvent(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXNotificationService.getNotificationEvent(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update a NotificationEvent.
     *
     * @param notificationEvent the NotificationEvent to update.
     * @return the updated NotificationEvent
     */
    @PutMapping(value = "/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationEvent> updateNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.openCDXNotificationService.updateNotificationEvent(notificationEvent), HttpStatus.OK);
    }

    /**
     * Delete NotificationEvent with id.
     *
     * @param id the id of the NotificationEvent to delete.
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/event/{id}")
    public ResponseEntity<SuccessResponse> deleteNotificationEvent(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXNotificationService.deleteNotificationEvent(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Trigger a NotificationEvent to send messages
     *
     * @param notification the Notification for the event.
     * @return a SuccessResponse indicating if successful.
     */
    @PostMapping(value = "/notification", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> sendNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(this.openCDXNotificationService.sendNotification(notification), HttpStatus.OK);
    }

    /**
     * List SMSTemplates
     *
     * @param smsTemplateListRequest request for SMSTemplates
     * @return the requested SMSTemplates.
     */
    @PostMapping(value = "/sms/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SMSTemplateListResponse> listSMSTemplates(
            @RequestBody SMSTemplateListRequest smsTemplateListRequest) {
        return new ResponseEntity<>(
                this.openCDXCommunicationSmsService.listSMSTemplates(smsTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List EmailTemplates
     *
     * @param emailTemplateListRequest request for EmailTemplates.
     * @return the requested EmailTemplates.
     */
    @PostMapping(value = "/email/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailTemplateListResponse> listEmailTemplates(
            @RequestBody EmailTemplateListRequest emailTemplateListRequest) {
        return new ResponseEntity<>(
                this.openCDXCommunicationEmailService.listEmailTemplates(emailTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param notificationEventListRequest request for NotificationEvents.
     * @return the requested NotificationEvents.
     */
    @PostMapping(value = "/event/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationEventListResponse> listNotificationEvents(
            @RequestBody NotificationEventListRequest notificationEventListRequest) {
        return new ResponseEntity<>(
                this.openCDXNotificationService.listNotificationEvents(notificationEventListRequest), HttpStatus.OK);
    }

    /**
     * Create Message Template
     *
     * @param messageTemplate request for MessageTemplate.
     * @return the requested MessageTemplate.
     */
    @PostMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTemplate> createMessageTemplate(@RequestBody MessageTemplate messageTemplate) {
        return new ResponseEntity<>(this.openCDXMessageService.createMessageTemplate(messageTemplate), HttpStatus.OK);
    }

    /**
     * Get message template
     *
     * @param templateRequest request for TemplateRequest.
     * @return the requested MessageTemplate.
     */
    @GetMapping("/messageTemplate")
    public ResponseEntity<MessageTemplate> getMessageTemplate(@RequestBody TemplateRequest templateRequest) {
        return new ResponseEntity<>(this.openCDXMessageService.getMessageTemplate(templateRequest), HttpStatus.OK);
    }

    /**
     * Update a messageTemplate.
     *
     * @param messageTemplate the MessageTemplate
     * @return the updated MessageTemplate
     */
    @PutMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTemplate> updateMessageTemplate(@RequestBody MessageTemplate messageTemplate) {
        return new ResponseEntity<>(this.openCDXMessageService.updateMessageTemplate(messageTemplate), HttpStatus.OK);
    }

    /**
     * Delete Message with id.
     *
     * @param id the id of the MessageTemplate
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/message/{id}")
    public ResponseEntity<SuccessResponse> deleteMessageTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMessageService.deleteMessageTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List message templates
     *
     * @param pagination request for Pagination.
     * @return the requested MessageTemplateListResponse.
     */
    @PostMapping(value = "/message/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTemplateListResponse> listMessageTemplates(@RequestBody Pagination pagination) {
        return new ResponseEntity<>(this.openCDXMessageService.listMessageTemplates(pagination), HttpStatus.OK);
    }

    /**
     * Gets Messages with this id.
     *
     * @param getMessagesRequest GetMessagesRequest
     * @return the GetMessagesResponse
     */
    @PostMapping("/messages")
    public ResponseEntity<GetMessagesResponse> getMessages(@RequestBody GetMessagesRequest getMessagesRequest) {
        return new ResponseEntity<>(this.openCDXMessageService.getMessages(getMessagesRequest), HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param markMessagesAsReadRequest MarkMessagesAsReadRequest
     * @return the MarkMessagesAsReadResponse
     */
    @PostMapping(value = "/message/read", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarkMessagesAsReadResponse> markMessageAsRead(
            @RequestBody MarkMessagesAsReadRequest markMessagesAsReadRequest) {
        return new ResponseEntity<>(
                this.openCDXMessageService.markMessageAsRead(markMessagesAsReadRequest), HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param markMessagesAsUnreadRequest MarkMessagesAsUnreadRequest
     * @return the MarkMessagesAsUnreadResponse
     */
    @PostMapping(value = "/message/unread", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarkMessagesAsUnreadResponse> markMessageAsUnread(
            @RequestBody MarkMessagesAsUnreadRequest markMessagesAsUnreadRequest) {
        return new ResponseEntity<>(
                this.openCDXMessageService.markMessageAsUnread(markMessagesAsUnreadRequest), HttpStatus.OK);
    }
}
