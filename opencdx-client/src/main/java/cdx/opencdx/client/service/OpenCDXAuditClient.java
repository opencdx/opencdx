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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;

/**
 * Audit Service for interfacing to the service. Used for both direct gRPC client
 * and Message based Client.
 */
public interface OpenCDXAuditClient {
    /**
     * Record User Login successful to audit log.
     * @param actor Currently logged-in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void userLoginSucceed(
            String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record User Login failed to audit log.
     * @param actor Currently logged-in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void userLoginFailure(
            String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user Logout to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void userLogout(String actor, AgentType agentType, String purpose, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user acccess changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void userAccessChange(
            String actor,
            AgentType agentType,
            String purpose,
            String auditEntity,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user password changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void passwordChange(
            String actor,
            AgentType agentType,
            String purpose,
            String auditEntity,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record User PII information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record User PII information created to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user PII information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user PII Information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user PHI information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user PHI information created ot audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void phiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record User PHI information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void phiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record user PHI information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record User Communication.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Communication Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Record Configuraiton Change
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @param openCDXCallCredentials Call Credentials to use for send.
     */
    void config(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord,
            OpenCDXCallCredentials openCDXCallCredentials);
}
