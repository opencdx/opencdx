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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.AuditEventType;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.google.protobuf.Timestamp;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class is an implementation of the OpenCDXAuditService interface.
 * It provides methods for auditing user actions in the OpenCDX system.
 * The class uses an OpenCDXMessageService to send audit events.
 * <p>
 * The class is annotated with the @Service annotation to indicate that it is a service component.
 * It is also annotated with the @Observed(name = "opencdx") annotation to specify the name of the observer for the audit events.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAuditServiceImpl implements OpenCDXAuditService {

    @Value("${spring.application.name}")
    private String applicationName;

    private final OpenCDXMessageService messageService;

    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor based on the OpenCDXMessageService
     * @param messageService Messaging Service to use,
     * @param applicationName Application name to set.
     * @param openCDXDocumentValidator Document validator to validate the document.
     */
    public OpenCDXAuditServiceImpl(
            OpenCDXMessageService messageService,
            @Value("${spring.application.name}") String applicationName,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.applicationName = applicationName;
        this.messageService = messageService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
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
    public void userAccessChange(String actor, AgentType agentType, String purpose, AuditEntity entity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void passwordChange(String actor, AgentType agentType, String purpose, AuditEntity entity) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_ACCESSED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
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
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
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
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_UPDATED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_DELETED)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
                .setPurposeOfUse(purpose)
                .build());
    }

    @Override
    public void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            AuditEntity entity,
            String resource,
            String jsonRecord) {
        this.sendMessage(AuditEvent.newBuilder()
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_COMMUNICATION)
                .setCreated(this.getTimeStamp(Instant.now()))
                .setAuditSource(this.getAuditSource(this.applicationName))
                .setActor(this.getActor(actor, agentType))
                .setAuditEntity(entity)
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

    private DataObject getDataObject(String jsonRecord, String resource, SensitivityLevel sensitivityLevel) {
        return DataObject.newBuilder()
                .setResource(resource)
                .setData(jsonRecord)
                .setSensitivity(sensitivityLevel)
                .build();
    }

    private void sendMessage(AuditEvent event) {
        log.info("Sending Audit Event: {}", event.getEventType());
        openCDXDocumentValidator.validateDocumentOrLog(
                "users", new OpenCDXIdentifier(event.getActor().getIdentity()));
        if (event.hasAuditEntity()) {
            if (event.getAuditEntity().hasNationalHealthId()
                    && !openCDXDocumentValidator.profileWithNationalHealthId(
                            event.getAuditEntity().getNationalHealthId())) {
                log.warn(
                        "Profile with National Health ID: {} does not exist in collection profiles",
                        event.getAuditEntity().getNationalHealthId());
            }
            if (event.getAuditEntity().hasUserId()) {
                openCDXDocumentValidator.validateDocumentOrLog(
                        "users", new OpenCDXIdentifier(event.getAuditEntity().getUserId()));
            }
            if (event.getAuditEntity().hasPatientId()) {
                openCDXDocumentValidator.validateDocumentOrLog(
                        "profiles", new OpenCDXIdentifier(event.getAuditEntity().getPatientId()));
            }
        }
        this.messageService.send(OpenCDXMessageService.AUDIT_MESSAGE_SUBJECT, event);
    }
}
