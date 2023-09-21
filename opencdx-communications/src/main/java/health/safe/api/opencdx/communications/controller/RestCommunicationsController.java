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
package health.safe.api.opencdx.communications.controller;

import cdx.open_communication.v2alpha.*;
import health.safe.api.opencdx.communications.service.CommunicationService;
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
@RequestMapping(
        value = "/communications",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestCommunicationsController {

    private final CommunicationService communicationService;

    /**
     * Constructor that takes a CommunicationService
     *
     * @param communicationService service for processing requests.
     */
    @Autowired
    public RestCommunicationsController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    /**
     * Create an EmailTemplate.
     *
     * @param emailTemplate the EmailTemplate to create
     * @return the created EmailTemplate
     */
    @PostMapping(value = "/email")
    @Observed(name = "opencdx-communications.rest.createEmailTemplate")
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        return new ResponseEntity<>(this.communicationService.createEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Gets an EmailTemplate
     *
     * @param id the EmailTemplate ID to retrieve
     * @return the requested EmailTemplate.
     */
    @GetMapping("/email/{id}")
    @Observed(name = "opencdx-communications.rest.getEmailTemplate")
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.getEmailTemplate(
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
    @Observed(name = "opencdx-communications.rest.updateEmailTemplate")
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        return new ResponseEntity<>(this.communicationService.updateEmailTemplate(emailTemplate), HttpStatus.OK);
    }

    /**
     * Delete the EmailTemplate with the id.
     *
     * @param id the id of the EmailTemplate to delete
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/email/{id}")
    @Observed(name = "opencdx-communications.rest.deleteEmailTemplate")
    public ResponseEntity<SuccessResponse> deleteEmailTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.deleteEmailTemplate(
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
    @Observed(name = "opencdx-communications.rest.createSMSTemplate")
    public ResponseEntity<SMSTemplate> createSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        return new ResponseEntity<>(this.communicationService.createSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Gets SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     */
    @GetMapping("/sms/{id}")
    @Observed(name = "opencdx-communications.rest.getSMSTemplate")
    public ResponseEntity<SMSTemplate> getSMSTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.getSMSTemplate(
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
    @Observed(name = "opencdx-communications.rest.updateSMSTemplate")
    public ResponseEntity<SMSTemplate> updateSMSTemplate(@RequestBody SMSTemplate smsTemplate) {
        return new ResponseEntity<>(this.communicationService.updateSMSTemplate(smsTemplate), HttpStatus.OK);
    }

    /**
     * Delete SMSTemplate with the id.
     *
     * @param id the id of the SMSTemplate to delete.
     * @return a SuccessResponse to indicate if successful.
     */
    @DeleteMapping("/sms/{id}")
    @Observed(name = "opencdx-communications.rest.deleteSMSTemplate")
    public ResponseEntity<SuccessResponse> deleteSMSTemplate(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.deleteSMSTemplate(
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
    @Observed(name = "opencdx-communications.rest.createNotificationEvent")
    public ResponseEntity<NotificationEvent> createNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.communicationService.createNotificationEvent(notificationEvent), HttpStatus.OK);
    }

    /**
     * Gets NotificationEvent with this id.
     *
     * @param id the id of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     */
    @GetMapping("/event/{id}")
    @Observed(name = "opencdx-communications.rest.getNotificationEvent")
    public ResponseEntity<NotificationEvent> getNotificationEvent(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.getNotificationEvent(
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
    @Observed(name = "opencdx-communications.rest.updateNotificationEvent")
    public ResponseEntity<NotificationEvent> updateNotificationEvent(@RequestBody NotificationEvent notificationEvent) {
        return new ResponseEntity<>(
                this.communicationService.updateNotificationEvent(notificationEvent), HttpStatus.OK);
    }

    /**
     * Delete NotificationEvent with id.
     *
     * @param id the id of the NotificationEvent to delete.
     * @return a SuccessResponse indicating if successful.
     */
    @DeleteMapping("/event/{id}")
    @Observed(name = "opencdx-communications.rest.deleteNotificationEvent")
    public ResponseEntity<SuccessResponse> deleteNotificationEvent(@PathVariable String id) {
        return new ResponseEntity<>(
                this.communicationService.deleteNotificationEvent(
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
    @Observed(name = "opencdx-communications.rest.sendNotification")
    public ResponseEntity<SuccessResponse> sendNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(this.communicationService.sendNotification(notification), HttpStatus.OK);
    }

    /**
     * List SMSTemplates
     *
     * @param smsTemplateListRequest request for SMSTemplates
     * @return the requested SMSTemplates.
     */
    @PostMapping("/sms/list")
    @Observed(name = "opencdx-communications.rest.listSMSTemplates")
    public ResponseEntity<SMSTemplateListResponse> listSMSTemplates(
            @RequestBody SMSTemplateListRequest smsTemplateListRequest) {
        return new ResponseEntity<>(this.communicationService.listSMSTemplates(smsTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List EmailTemplates
     *
     * @param emailTemplateListRequest request for EmailTemplates.
     * @return the requested EmailTemplates.
     */
    @PostMapping("/email/list")
    @Observed(name = "opencdx-communications.rest.listEmailTemplates")
    public ResponseEntity<EmailTemplateListResponse> listEmailTemplates(
            @RequestBody EmailTemplateListRequest emailTemplateListRequest) {
        return new ResponseEntity<>(
                this.communicationService.listEmailTemplates(emailTemplateListRequest), HttpStatus.OK);
    }

    /**
     * List NotificationEvents
     *
     * @param notificationEventListRequest request for NotificationEvents.
     * @return the requested NotificationEvents.
     */
    @PostMapping("/event/list")
    @Observed(name = "opencdx-communications.rest.listNotificationEvents")
    public ResponseEntity<NotificationEventListResponse> listNotificationEvents(
            @RequestBody NotificationEventListRequest notificationEventListRequest) {
        return new ResponseEntity<>(
                this.communicationService.listNotificationEvents(notificationEventListRequest), HttpStatus.OK);
    }
}
