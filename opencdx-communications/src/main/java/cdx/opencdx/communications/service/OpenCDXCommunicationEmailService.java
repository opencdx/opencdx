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

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.grpc.communication.*;

/**
 * Interface for the OpenCDXCommunicationEmailService
 */
public interface OpenCDXCommunicationEmailService {

    /**
     * Create an Email Template
     * @param emailTemplate EmailTemplate to create.
     * @return the created EmailTemplate.
     * @exception OpenCDXClientException OpenCDXNotAcceptable Failed to convert to JSON
     */
    EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXClientException;

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
    EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable;

    /**
     * Delete an Email Template
     * @param templateRequest Request ID of the email template to delete
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable;

    /**
     * List of all EmailTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested EmailTemplates with page, sorting, and page size
     */
    EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request);
}
