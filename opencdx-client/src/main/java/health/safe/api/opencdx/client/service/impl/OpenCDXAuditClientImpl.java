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
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * gRPC Client implementation of the Audit System.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXAuditClientImpl extends OpenCDXAuditClientAbstract {

    private final AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub;

    /**
     * OpenCDXAuditClient Implementation for the audit system based on shared client code.
     * @param applicationName Name of the application
     * @param auditServiceBlockingStub gRPC Client stub
     */
    public OpenCDXAuditClientImpl(
            String applicationName, AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub) {
        super(applicationName);
        this.auditServiceBlockingStub = auditServiceBlockingStub;
    }

    @Override
    protected AuditStatus sendMessage(AuditEvent event) {
        log.info("Sending Audit Event: {}", event.getEventType());
        return this.auditServiceBlockingStub.event(event);
    }
}
