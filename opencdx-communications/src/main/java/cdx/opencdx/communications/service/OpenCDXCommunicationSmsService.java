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
import cdx.opencdx.grpc.data.SMSTemplate;
import cdx.opencdx.grpc.service.communications.SMSTemplateListRequest;
import cdx.opencdx.grpc.service.communications.SMSTemplateListResponse;
import cdx.opencdx.grpc.service.communications.SuccessResponse;
import cdx.opencdx.grpc.service.communications.TemplateRequest;

/**
 * Interface for the OpenCDXCommunicationSmsService
 */
public interface OpenCDXCommunicationSmsService {

    /**
     * Create an SMS Template
     * @param smsTemplate SMSTemplate to create
     * @return the created SMSTemplate.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXNotAcceptable;

    /**
     * Get an SMS Template
     * @param templateRequest Request ID of the SMSTemplate to retrieve.
     * @return the requested SMSTemplate
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotFound, OpenCDXNotAcceptable;

    /**
     * Update SMS Template
     * @param smsTemplate SMSTemplate to update.
     * @return the updated SMSTemplate.
     * @exception OpenCDXFailedPrecondition Missing template id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable;

    /**
     * Delete SMS Template
     * @param templateRequest Request ID of the SMSTemplate to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) throws OpenCDXNotAcceptable;

    /**
     * List of all SMSTemplates
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested SMSTemplates with page, sorting, and page size
     */
    SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request);
}
