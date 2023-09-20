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

import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditServiceGrpc;
import cdx.open_audit.v2alpha.AuditStatus;

/**
 * gRPC Client implementation of the Audit System.
 */
public class OpenCDXAuditServiceImpl extends OpenCDXAuditServiceAbstract {

    private final AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub;

    /**
     * OpenCDXAuditService Implementation for the audit system based on shared client code.
     * @param applicationName Name of the application
     * @param auditServiceBlockingStub gRPC Client stub
     */
    public OpenCDXAuditServiceImpl(
            String applicationName, AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub) {
        super(applicationName);
        this.auditServiceBlockingStub = auditServiceBlockingStub;
    }

    @Override
    protected AuditStatus sendMessage(AuditEvent event) {
        return this.auditServiceBlockingStub.event(event);
    }
}
