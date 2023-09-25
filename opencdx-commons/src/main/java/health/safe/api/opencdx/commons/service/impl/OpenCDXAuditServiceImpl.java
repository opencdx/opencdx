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

import cdx.open_audit.v2alpha.AgentType;
import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditStatus;
import cdx.open_audit.v2alpha.SensitivityLevel;
import health.safe.api.opencdx.client.service.impl.OpenCDXAuditClientAbstract;
import health.safe.api.opencdx.commons.aspects.AuditAspect;
import health.safe.api.opencdx.commons.dto.*;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * OpenCDX-Audit based implementation of OpenCDXOpenCDXAuditService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAuditServiceImpl extends OpenCDXAuditClientAbstract implements OpenCDXAuditService {

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

    @Override
    public void userLoginSucceed(AgentType agentType, String purpose) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.userLoginSucceed(info.getActor(), agentType, purpose);
    }

    @Override
    public void userLoginFailure(AgentType agentType, String purpose) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.userLoginFailure(info.getActor(), agentType, purpose);
    }

    @Override
    public void userLogout(AgentType agentType, String purpose) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.userLogout(info.getActor(), agentType, purpose);
    }

    @Override
    public void userAccessChange(AgentType agentType, String purpose) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.userAccessChange(info.getActor(), agentType, purpose, info.getPatient());
    }

    @Override
    public void passwordChange(AgentType agentType, String purpose) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.passwordChange(info.getActor(), agentType, purpose, info.getPatient());
    }

    @Override
    public void piiAccessed(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.piiAccessed(
                info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void piiCreated(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.piiCreated(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void piiUpdated(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.piiUpdated(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void piiDeleted(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.piiDeleted(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void phiAccessed(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.phiAccessed(
                info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void phiCreated(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.phiCreated(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void phiUpdated(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.phiUpdated(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void phiDeleted(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.phiDeleted(info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void communication(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.communication(
                info.getActor(), agentType, purpose, sensitivityLevel, info.getPatient(), resource, jsonRecord);
    }

    @Override
    public void config(
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        RequestActorAttributes info = AuditAspect.getCurrentThreadInfo();
        this.config(info.getActor(), agentType, purpose, sensitivityLevel, resource, jsonRecord);
    }
}
