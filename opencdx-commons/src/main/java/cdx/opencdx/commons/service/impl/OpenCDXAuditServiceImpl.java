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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.audit.*;
import com.google.protobuf.Timestamp;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * OpenCDX-Audit based implementation of OpenCDXOpenCDXAuditService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAuditServiceImpl implements OpenCDXAuditService {

    @Value("${spring.application.name}")
    private String applicationName;

    private OpenCDXMessageService messageService;

    /**
     * Constructor based on the OpenCDXMessageService
     * @param messageService Messaging Service to use,
     * @param applicationName Applicaiton name to set.
     */
    public OpenCDXAuditServiceImpl(
            OpenCDXMessageService messageService, @Value("${spring.application.name}") String applicationName) {
        this.applicationName = applicationName;
        this.messageService = messageService;
    }

    @Override
    public void userLoginSucceed(String actor, AgentType agentType, String purpose) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_SYSTEM_LOGIN_SUCCEEDED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void userLoginFailure(String actor, AgentType agentType, String purpose) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_FAIL)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void userLogout(String actor, AgentType agentType, String purpose) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_LOG_OUT)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void userAccessChange(String actor, AgentType agentType, String purpose, String auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void passwordChange(String actor, AgentType agentType, String purpose, String auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_COMMUNICATION)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .setPurposeOfUse(purpose)
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .build());
    }

    @Override
    public void config(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_CONFIG_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setPurposeOfUse(purpose)
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .build());
    }

    private Timestamp getTimeStamp(Instant time) {
        return Timestamp.newBuilder()
                .setSeconds(time.getEpochSecond())
                .setNanos(time.getNano())
                .build();
    }

    private AuditSource getAuditSource(String applicationName) {
        return AuditSource.newBuilder().setSystemInfo(applicationName).build();
    }

    private Actor getActor(String actor, AgentType agentType) {
        return Actor.newBuilder().setIdentity(actor).setAgentType(agentType).build();
    }

    private AuditEntity getAuditEntity(String auditEntity) {
        return AuditEntity.newBuilder().setPatientIdentifier(auditEntity).build();
    }

    private DataObject getDataObject(String jsonRecord, String resource, SensitivityLevel sensitivityLevel) {
        return DataObject.newBuilder()
                .setResource(resource)
                .setData(jsonRecord)
                .setSensitivity(sensitivityLevel)
                .build();
    }

    private AuditStatus sendMessage(AuditEvent event) {
        log.info("Sending Audit Event: {}", event.getEventType());
        this.messageService.send(OpenCDXMessageService.AUDIT_MESSAGE_SUBJECT, event);
        return AuditStatus.newBuilder().setSuccess(true).build();
    }
}
