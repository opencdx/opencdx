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

import health.safe.api.opencdx.grpc.communication.*;

/**
 * Interface for the CommunicationService
 */
public interface CommunicationService {

    /**
     * Create an Email Template
     * @param emailTemplate EmailTemplate to create.
     * @return the created EmailTemplate.
     */
    EmailTemplate createEmailTemplate(EmailTemplate emailTemplate);

    /**
     * Retrieve an Email Template
     * @param templateRequest Request ID of email template to retrieve.
     * @return the requested EmailTemplate.
     */
    EmailTemplate getEmailTemplate(TemplateRequest templateRequest);

    /**
     * Update an Email Template
     * @param emailTemplate Updated EmailTemplate.
     * @return updated EmailTemplate.
     */
    EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate);

    /**
     * Delete an Email Template
     * @param templateRequest Request ID of the email template to delete
     * @return SuccessResponse indicating if the action was successful.
     */
    SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest);

    /**
     * Create an SMS Template
     * @param smsTemplate SMSTemplate to create
     * @return the created SMSTemplate.
     */
    SMSTemplate createSMSTemplate(SMSTemplate smsTemplate);

    /**
     * Get an SMS Template
     * @param templateRequest Request ID of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     */
    SMSTemplate getSMSTemplate(TemplateRequest templateRequest);

    /**
     * Update SMS Template
     * @param smsTemplate SMSTemplate to update.
     * @return the updated SMSTemplate.
     */
    SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate);

    /**
     * Delete SMS Template
     * @param templateRequest Request ID of the SMSTemplate to delete.
     * @return SuccessResponse indicating if the action was successsful.
     */
    SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest);

    /**
     * Create a Notification Event
     * @param notificationEvent NotificationEvent to create.
     * @return the created NotificationEvent.
     */
    NotificationEvent createNotificationEvent(NotificationEvent notificationEvent);

    /**
     * Get a Notification Event
     * @param templateRequest Request ID of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     */
    NotificationEvent getNotificationEvent(TemplateRequest templateRequest);

    /**
     * Update Notification Event
     * @param notificationEvent NotificationEvent to update.
     * @return the updated NotificationEvent.
     */
    NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent);

    /**
     * Delete Notification Event
     * @param templateRequest Request ID of NotificationEvent to delete.
     * @return SuccessResponse indicating if the action was successful.
     */
    SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest);

    /**
     * Send Notification
     * @param notification Notification information to trigger.
     * @return SuccessResponse indicating if the action was successful.
     */
    SuccessResponse sendNotification(Notification notification);

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
    NotificationEventListResponse listNotificationEvents(NotificaitonEventListRequest request);
}
