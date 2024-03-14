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
package cdx.opencdx.communications.service;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.communication.*;

/**
 * Interface for the OpenCDXMessageService
 */
public interface OpenCDXMessageService {

    /**
     * Create a MessageTemplate
     * @param messageTemplate MessageTemplate to create.
     * @return the created MessageTemplate.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    MessageTemplate createMessageTemplate(MessageTemplate messageTemplate) throws OpenCDXNotAcceptable;

    /**
     * Get a TemplateRequest
     * @param templateRequest Request ID of the MessageTemplate to retrieve.
     * @return the requested MessageTemplate.
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    MessageTemplate getMessageTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound, OpenCDXNotAcceptable;

    /**
     * Update MessageTemplate
     * @param messageTemplate MessageTemplate to update.
     * @return the updated MessageTemplate.
     * @exception OpenCDXFailedPrecondition Missing event id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    MessageTemplate updateMessageTemplate(MessageTemplate messageTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable;

    /**
     * Delete TemplateRequest
     * @param templateRequest Request ID of TemplateRequest to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteMessageTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable;

    /**
     * Send Pagination
     * @param pagination Pagination information to trigger.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotFound Template not found
     * @exception OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    MessageTemplateListResponse listMessageTemplates(Pagination pagination)
            throws OpenCDXFailedPrecondition, OpenCDXNotFound, OpenCDXNotAcceptable;

    /**
     * GetMessagesRequest
     * @param request Request GetMessagesRequest
     * @return requested Messages
     */
    GetMessagesResponse getMessages(GetMessagesRequest request);

    /**
     * MarkMessagesAsReadRequest
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    MarkMessagesAsReadResponse markMessageAsRead(MarkMessagesAsReadRequest request);
    /**
     * MarkMessagesAsUnReadRequest
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    MarkMessagesAsUnreadResponse markMessageAsUnread(MarkMessagesAsUnreadRequest request);
}
