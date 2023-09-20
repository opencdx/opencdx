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
package health.safe.api.opencdx.client.service;

import cdx.open_audit.v2alpha.AgentType;

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
     */
    void userLoginSucceed(String actor, AgentType agentType, String purpose);

    /**
     * Record User Login failed to audit log.
     * @param actor Currently logged-in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     */
    void userLoginFailure(String actor, AgentType agentType, String purpose);

    /**
     * Record user Logout to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     */
    void userLogout(String actor, AgentType agentType, String purpose);

    /**
     * Record user acccess changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void userAccessChange(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user password changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void passwordChange(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record User PII information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiAccessed(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record User PII information created to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiCreated(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user PII information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiUpdated(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user PII Information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiDeleted(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user PHI information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiAccessed(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user PHI information created ot audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiCreated(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record User PHI information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiUpdated(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user PHI information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiDeleted(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record User Communication.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Communication Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     */
    void communication(
            String actor, AgentType agentType, String purpose, String auditEntity, String resource, String jsonRecord);

    /**
     * Record Configuraiton Change
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     */
    void config(String actor, AgentType agentType, String purpose, String resource, String jsonRecord);
}
