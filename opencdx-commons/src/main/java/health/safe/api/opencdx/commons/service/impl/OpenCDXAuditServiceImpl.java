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
package health.safe.api.opencdx.commons.service.impl;

import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditStatus;
import health.safe.api.opencdx.client.service.impl.OpenCDXAuditServiceAbstract;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * OpenCDX-Audit based implementation of OpenCDXOpenCDXAuditService
 */
@Slf4j
@Service
public class OpenCDXAuditServiceImpl extends OpenCDXAuditServiceAbstract {

    private OpenCDXMessageService messageService;

    /**
     * Constructor based on the OpenCDXMessageService
     * @param messageService Messaging Service to use,
     * @param applicationName Applicaiton name to set.
     */
    public OpenCDXAuditServiceImpl(
            OpenCDXMessageService messageService, @Value("${spring.application.name}") String applicationName) {
        super(applicationName);
        this.messageService = messageService;
    }

    protected AuditStatus sendMessage(AuditEvent event) {
        this.messageService.send(OpenCDXMessageService.AUDIT_MESSAGE_SUBJECT, event);
        return AuditStatus.newBuilder().setSuccess(true).build();
    }
}
