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
package health.safe.api.opencdx.client.service.impl;

import cdx.open_communication.v2alpha.*;
import com.google.rpc.Code;
import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.client.service.OpenCDXCommunicationClient;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;

@SuppressWarnings("java:S1192")
public class OpenCDXCommunicationClientImpl implements OpenCDXCommunicationClient {

    private final CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub;

    /**
     * Constructor for creating the Communication service client implementation.
     * @param blockingStub gRPC Blocking Stub for communications service.
     */
    public OpenCDXCommunicationClientImpl(CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    /**
     * Create an Email Template
     * @param emailTemplate EmailTemplate to create.
     * @return the created EmailTemplate.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.createEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 1, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Retrieve an Email Template
     *
     * @param templateRequest Request ID of email template to retrieve.
     * @return the requested EmailTemplate.
     * @throws OpenCDXClientException Template with requested ID not found.
     */
    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 2, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Update an Email Template
     *
     * @param emailTemplate Updated EmailTemplate.
     * @return updated EmailTemplate.
     * @throws OpenCDXClientException OpenCDXFailedPrecondition Missing template id.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.updateEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 3, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Delete an Email Template
     *
     * @param templateRequest Request ID of the email template to delete
     * @return SuccessResponse indicating if the action was successful.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 3, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Create an SMS Template
     *
     * @param smsTemplate SMSTemplate to create
     * @return the created SMSTemplate.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.createSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 4, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Get an SMS Template
     *
     * @param templateRequest Request ID of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     * @throws OpenCDXClientException OpenCDXNotFound Template with requested ID not found.
     */
    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 5, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Update SMS Template
     *
     * @param smsTemplate SMSTemplate to update.
     * @return the updated SMSTemplate.
     * @throws OpenCDXClientException OpenCDXFailedPrecondition Missing template id.
     * @throws OpenCDXClientException Failed to convert to JSON
     */
    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.updateSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 6, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Delete SMS Template
     *
     * @param templateRequest Request ID of the SMSTemplate to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 7, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Create a Notification Event
     *
     * @param notificationEvent NotificationEvent to create.
     * @return the created NotificationEvent.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXClientException {
        try {
            return blockingStub.createNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 8, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Get a Notification Event
     *
     * @param templateRequest Request ID of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     * @throws OpenCDXClientException OpenCDXNotFound Template with requested ID not found.
     */
    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 9, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Update Notification Event
     *
     * @param notificationEvent NotificationEvent to update.
     * @return the updated NotificationEvent.
     * @throws OpenCDXClientException OpenCDXFailedPrecondition Missing event id.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXClientException {
        try {
            return blockingStub.updateNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 10, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Delete Notification Event
     *
     * @param templateRequest Request ID of NotificationEvent to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 11, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * Send Notification
     *
     * @param notification Notification information to trigger.
     * @return SuccessResponse indicating if the action was successful.
     * @throws OpenCDXClientException OpenCDXNotFound Template not found
     * @throws OpenCDXClientException OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @throws OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    @Override
    public SuccessResponse sendNotification(Notification notification) throws OpenCDXClientException {
        try {
            return blockingStub.sendNotification(notification);
        } catch (StatusRuntimeException e) {
            throw new OpenCDXClientException(
                    Code.INTERNAL, "CommunicationClientImpl", 12, e.getMessage(), new ArrayList<>(), e);
        }
    }

    /**
     * List of all SMSTemplates
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested SMSTemplates with page, sorting, and page size
     */
    @Override
    public SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request) {
        return blockingStub.listSMSTemplates(request);
    }

    /**
     * List of all EmailTemplates
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested EmailTemplates with page, sorting, and page size
     */
    @Override
    public EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request) {
        return blockingStub.listEmailTemplates(request);
    }

    /**
     * List of all NotificationEvent
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    @Override
    public NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request) {
        return blockingStub.listNotificationEvents(request);
    }
}
