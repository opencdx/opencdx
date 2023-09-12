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

import com.google.protobuf.Timestamp;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import health.safe.api.opencdx.grpc.audit.*;
import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * OpenCDX-Audit based implementation of OpenCDXAuditService
 */
@Slf4j
@Service
public class OpenCDXAuditServiceImpl implements OpenCDXAuditService {

    private OpenCDXMessageService messageService;

    private String applicationName;
    /**
     * Constructor based on the OpenCDXMessageService
     * @param messageService Messaging Service to use,
     * @param applicationName Applicaiton name to set.
     */
    public OpenCDXAuditServiceImpl(
            OpenCDXMessageService messageService, @Value("${spring.application.name}") String applicationName) {
        this.messageService = messageService;
        this.applicationName = applicationName;
    }

    @Override
    public void userLoginSucceed(UUID actor, AgentType agentType) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.USER_LOGIN_SUCCEEDED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .build());
    }

    @Override
    public void userLoginFailure(UUID actor, AgentType agentType) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.USER_LOGIN_FAIL)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .build());
    }

    @Override
    public void userLogout(UUID actor, AgentType agentType) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.USER_LOGOUT)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .build());
    }

    @Override
    public void userAccessChange(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.USER_ACCESS_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void passwordChange(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.USER_PASSWORD_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void piiAccessed(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PII_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void piiCreated(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PII_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void piiUpdated(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PII_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void piiDeleted(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PII_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void phiAccessed(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PHI_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void phiCreated(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PHI_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void phiUpdated(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PHI_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    @Override
    public void phiDeleted(UUID actor, AgentType agentType, UUID auditEntity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.PHI_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(this.getAuditEntity(auditEntity))
                .build());
    }

    private void sendMessage(AuditEvent event) {
        this.messageService.send(OpenCDXAuditService.AUDIT_MESSAGE_SUBJECT, event);
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

    private Actor getActor(UUID actor, AgentType agentType) {
        return Actor.newBuilder()
                .setIdentity(actor.toString())
                .setAgentType(agentType)
                .build();
    }

    private AuditEntity getAuditEntity(UUID auditEntity) {
        return AuditEntity.newBuilder()
                .setPatientIdentifier(auditEntity.toString())
                .build();
    }
}
