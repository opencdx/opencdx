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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.communication.*;

/**
 * Interface for the Open CDX Communication Client.
 */
public interface OpenCDXCommunicationClient {

    /**
     * ID of the verify email notification event
     */
    String VERIFY_EMAIL_USER = "60f1e6b1f075a361a94d3760";
    /**
     * ID of the Change password Notification Event.
     */
    String CHANGE_PASSWORD = "60f1e6b1f075a361a94d3750";
    /**
     * ID of the welcome email notification event.
     */
    String WELCOME_EMAIL_USER = "60f1e6b1f075a361a94d373e";

    /**
     * Create an Email Template
     * @param emailTemplate EmailTemplate to create.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the created EmailTemplate.
     * @exception OpenCDXClientException Failed to convert to JSON
     */
    EmailTemplate createEmailTemplate(EmailTemplate emailTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Retrieve an Email Template
     * @param templateRequest Request ID of email template to retrieve.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the requested EmailTemplate.
     * @exception OpenCDXClientException Template with requested ID not found.
     */
    EmailTemplate getEmailTemplate(TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Update an Email Template
     * @param emailTemplate Updated EmailTemplate.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return updated EmailTemplate.
     * @exception OpenCDXClientException OpenCDXFailedPrecondition Missing template id.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Delete an Email Template
     * @param templateRequest Request ID of the email template to delete
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Create an SMS Template
     * @param smsTemplate SMSTemplate to create
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the created SMSTemplate.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    SMSTemplate createSMSTemplate(SMSTemplate smsTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Get an SMS Template
     * @param templateRequest Request ID of the SMSTemplate to retrieve.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the requested SMSTemplate
     * @exception OpenCDXClientException OpenCDXNotFound Template with requested ID not found.
     */
    SMSTemplate getSMSTemplate(TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Update SMS Template
     * @param smsTemplate SMSTemplate to update.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the updated SMSTemplate.
     * @exception OpenCDXClientException OpenCDXFailedPrecondition Missing template id.
     * @exception OpenCDXClientException Failed to convert to JSON
     */
    SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Delete SMS Template
     * @param templateRequest Request ID of the SMSTemplate to delete.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Create a Notification Event
     * @param notificationEvent NotificationEvent to create.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the created NotificationEvent.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent createNotificationEvent(
            NotificationEvent notificationEvent, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Get a Notification Event
     * @param templateRequest Request ID of the NotificationEvent to retrieve.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the requested NotificationEvent.
     * @exception OpenCDXClientException OpenCDXNotFound Template with requested ID not found.
     */
    NotificationEvent getNotificationEvent(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Update Notification Event
     * @param notificationEvent NotificationEvent to update.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the updated NotificationEvent.
     * @exception OpenCDXClientException OpenCDXFailedPrecondition Missing event id.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent updateNotificationEvent(
            NotificationEvent notificationEvent, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Delete Notification Event
     * @param templateRequest Request ID of NotificationEvent to delete.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteNotificationEvent(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Send Notification
     * @param notification Notification information to trigger.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXClientException OpenCDXNotFound Template not found
     * @exception OpenCDXClientException OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse sendNotification(Notification notification, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * List of all SMSTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return requested SMSTemplates with page, sorting, and page size
     */
    SMSTemplateListResponse listSMSTemplates(
            SMSTemplateListRequest request, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * List of all EmailTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return requested EmailTemplates with page, sorting, and page size
     */
    EmailTemplateListResponse listEmailTemplates(
            EmailTemplateListRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * List of all NotificationEvent
     * @param request Request indicating pagination, sorting, and page size.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    NotificationEventListResponse listNotificationEvents(
            NotificationEventListRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
