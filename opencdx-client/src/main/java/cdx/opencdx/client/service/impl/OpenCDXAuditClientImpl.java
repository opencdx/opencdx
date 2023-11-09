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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXAuditClient;
import cdx.opencdx.grpc.audit.*;
import com.google.protobuf.Timestamp;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.observation.annotation.Observed;
import java.io.InputStream;
import java.time.Instant;
import javax.net.ssl.SSLException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * gRPC Client implementation of the Audit System.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@ConditionalOnProperty(prefix = "opencdx.client", name = "audit", havingValue = "true")
public class OpenCDXAuditClientImpl implements OpenCDXAuditClient {

    @Value("${spring.application.name}")
    private String applicationName;

    private final AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @throws SSLException if issue with connection
     */
    @Generated
    public OpenCDXAuditClientImpl() throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress("audit", 9090)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();

        this.auditServiceBlockingStub = AuditServiceGrpc.newBlockingStub(channel);
    }
    /**
     * OpenCDXAuditClient Implementation for the audit system based on shared client code.
     * @param applicationName Name of the application
     * @param auditServiceBlockingStub gRPC Client stub
     */
    public OpenCDXAuditClientImpl(
            String applicationName, AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub) {
        this.applicationName = applicationName;
        this.auditServiceBlockingStub = auditServiceBlockingStub;
    }
    /**
     * Abstract class that allows for replacement of communication system
     * @param event Event to send to Audit Service
     * @return indicates if successfully sent.
     */
    private AuditStatus sendMessage(AuditEvent event, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            log.info("Sending Audit Event: {}", event.getEventType());
            return this.auditServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .event(event);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXAuditClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    @Override
    public void userLoginSucceed(
            String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_SYSTEM_LOGIN_SUCCEEDED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void userLoginFailure(
            String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_FAIL)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void userLogout(
            String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_LOG_OUT)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void userAccessChange(
            String actor,
            AgentType agentType,
            String purpose,
            String auditEntity,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void passwordChange(
            String actor,
            AgentType agentType,
            String purpose,
            String auditEntity,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_CREATED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_UPDATED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_DELETED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_ACCESSED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void phiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void phiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_UPDATED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_DELETED)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_COMMUNICATION)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setAuditEntity(this.getAuditEntity(auditEntity))
                        .setPurposeOfUse(purpose)
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .build(),
                openCDXCallCredentials);
    }

    @Override
    public void config(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials) {
        this.sendMessage(
                AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_CONFIG_CHANGE)
                        .setCreated(this.getTimeStamp(Instant.now()))
                        .setAuditSource(this.getAuditSource(this.applicationName))
                        .setActor(this.getActor(actor, agentType))
                        .setPurposeOfUse(purpose)
                        .setDataObject(this.getDataObject(jsonRecord, resource, sensitivityLevel))
                        .build(),
                openCDXCallCredentials);
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
}
