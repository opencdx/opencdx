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
package cdx.opencdx.communications.controller;

import cdx.open_communication.v2alpha.*;
import cdx.opencdx.communications.service.OpenCDXCommunicationService;
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
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestCommunicationsController {

    private final OpenCDXCommunicationService openCDXCommunicationService;

    /**
     * Constructor that takes a OpenCDXCommunicationService
     *
     * @param openCDXCommunicationService service for processing requests.
     */
    @Autowired
    public RestCommunicationsController(OpenCDXCommunicationService openCDXCommunicationService) {
        this.openCDXCommunicationService = openCDXCommunicationService;
    }

    /**
     * Create an EmailTemplate.
     *
     * @param emailTemplate the EmailTemplate to create
     * @return the created EmailTemplate
     */
    @PostMapping(value = "/email")
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        return new ResponseEntity<>(this.openCDXCommunicationService.createEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Gets an EmailTemplate
     *
     * @param id the EmailTemplate ID to retrieve
     * @return the requested EmailTemplate.
     */
    @GetMapping("/email/{id}")
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.getEmailTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the EmailTemplate
     *
     * @param emailTemplate the EmailTemplate to update
     * @return the updated EmailTemplate
     */
    @PutMapping("/email")
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        return new ResponseEntity<>(this.openCDXCommunicationService.updateEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Delete the EmailTemplate with the id.
     *
     * @param id the id of the EmailTemplate to delete
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/email/{id}")
    public ResponseEntity<SuccessResponse> deleteEmailTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.deleteEmailTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Create a SMSTemplate.
     *
     * @param smsTemplate the SMSTemplate to create
     * @return the created SMSTemplate
     */
    @PostMapping("/sms")
    public ResponseEntity<SMSTemplate> createSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        return new ResponseEntity<>(this.openCDXCommunicationService.createSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Gets SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     */
    @GetMapping("/sms/{id}")
    public ResponseEntity<SMSTemplate> getSMSTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.getSMSTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the SMSTemplate
     *
     * @param smsTemplate the SMSTemplate to update
     * @return the updated SMSTemplate.
     */
    @PutMapping("/sms")
    public ResponseEntity<SMSTemplate> updateSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        return new ResponseEntity<>(this.openCDXCommunicationService.updateSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Delete SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to delete.
     * @return a SuccessResponse to indicate if successful.
     */
    @DeleteMapping("/sms/{id}")
    public ResponseEntity<SuccessResponse> deleteSMSTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.deleteSMSTemplate(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Create a NotificationEvent.
     *
     * @param notificationEvent the NotificationEvent to create.
     * @return the created NotificationEvent.
     */
    @PostMapping("/event")
    public ResponseEntity<NotificationEvent> createNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.createNotificationEvent(notificationEvent), HttpStatus.OK);
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
                this.openCDXCommunicationService.getNotificationEvent(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update a NotificationEvent.
     *
     * @param notificationEvent the NotificationEvent to update.
     * @return the updated NotificationEvent
     */
    @PutMapping("/event")
    public ResponseEntity<NotificationEvent> updateNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.updateNotificationEvent(notificationEvent), HttpStatus.OK);
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
                this.openCDXCommunicationService.deleteNotificationEvent(
                        TemplateRequest.newBuilder().setTemplateId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Trigger a NotificationEvent to send messages
     *
     * @param notification the Notification for the event.
     * @return a SuccessResponse indicating if successful.
     */
    @PostMapping("/notification")
    public ResponseEntity<SuccessResponse> sendNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(this.openCDXCommunicationService.sendNotification(notification), HttpStatus.OK);
    }

    /**
     * List SMSTemplates
     *
     * @param smsTemplateListRequest request for SMSTemplates
     * @return the requested SMSTemplates.
     */
    @PostMapping("/sms/list")
    public ResponseEntity<SMSTemplateListResponse> listSMSTemplates(
            @RequestBody SMSTemplateListRequest smsTemplateListRequest) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.listSMSTemplates(smsTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List EmailTemplates
     *
     * @param emailTemplateListRequest request for EmailTemplates.
     * @return the requested EmailTemplates.
     */
    @PostMapping("/email/list")
    public ResponseEntity<EmailTemplateListResponse> listEmailTemplates(
            @RequestBody EmailTemplateListRequest emailTemplateListRequest) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.listEmailTemplates(emailTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param notificationEventListRequest request for NotificationEvents.
     * @return the requested NotificationEvents.
     */
    @PostMapping("/event/list")
    public ResponseEntity<NotificationEventListResponse> listNotificationEvents(
            @RequestBody NotificationEventListRequest notificationEventListRequest) {
        return new ResponseEntity<>(
                this.openCDXCommunicationService.listNotificationEvents(notificationEventListRequest), HttpStatus.OK);
    }
}
