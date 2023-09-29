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
package health.safe.api.opencdx.communications.service;

import cdx.open_communication.v2alpha.*;
import health.safe.api.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotFound;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationModel;

/**
 * Interface for the OpenCDXCommunicationService
 */
public interface OpenCDXCommunicationService {

    /**
     * Create an Email Template
     * @param emailTemplate EmailTemplate to create.
     * @return the created EmailTemplate.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXNotAcceptable;

    /**
     * Retrieve an Email Template
     * @param templateRequest Request ID of email template to retrieve.
     * @return the requested EmailTemplate.
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    EmailTemplate getEmailTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound;

    /**
     * Update an Email Template
     * @param emailTemplate Updated EmailTemplate.
     * @return updated EmailTemplate.
     * @exception OpenCDXFailedPrecondition Missing template id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXFailedPrecondition;

    /**
     * Delete an Email Template
     * @param templateRequest Request ID of the email template to delete
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest);

    /**
     * Create an SMS Template
     * @param smsTemplate SMSTemplate to create
     * @return the created SMSTemplate.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SMSTemplate createSMSTemplate(SMSTemplate smsTemplate);

    /**
     * Get an SMS Template
     * @param templateRequest Request ID of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound;

    /**
     * Update SMS Template
     * @param smsTemplate SMSTemplate to update.
     * @return the updated SMSTemplate.
     * @exception OpenCDXFailedPrecondition Missing template id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXFailedPrecondition;

    /**
     * Delete SMS Template
     * @param templateRequest Request ID of the SMSTemplate to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest);

    /**
     * Create a Notification Event
     * @param notificationEvent NotificationEvent to create.
     * @return the created NotificationEvent.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent createNotificationEvent(NotificationEvent notificationEvent);

    /**
     * Get a Notification Event
     * @param templateRequest Request ID of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    NotificationEvent getNotificationEvent(TemplateRequest templateRequest) throws OpenCDXNotFound;

    /**
     * Update Notification Event
     * @param notificationEvent NotificationEvent to update.
     * @return the updated NotificationEvent.
     * @exception OpenCDXFailedPrecondition Missing event id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent) throws OpenCDXFailedPrecondition;

    /**
     * Delete Notification Event
     * @param templateRequest Request ID of NotificationEvent to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest);

    /**
     * Send Notification
     * @param notification Notification information to trigger.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotFound Template not found
     * @exception OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse sendNotification(Notification notification) throws OpenCDXFailedPrecondition, OpenCDXNotFound;

    /**
     * List of all SMSTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested SMSTemplates with page, sorting, and page size
     */
    SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request);

    /**
     * List of all EmailTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested EmailTemplates with page, sorting, and page size
     */
    EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request);
    /**
     * List of all NotificationEvent
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request);

    /**
     * Process and send the Notificaiton
     * @param openCDXNotificationModel OpenCDXNotificaitonModel to process.
     */
    void processOpenCDXNotification(OpenCDXNotificationModel openCDXNotificationModel);
}
