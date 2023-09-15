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

import health.safe.api.opencdx.grpc.audit.AgentType;
import java.util.UUID;

public interface OpenCDXAuditService {
    /**
     * Record User Login successful to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     */
    void userLoginSucceed(UUID actor, AgentType agentType, String purpose);

    /**
     * Record User Login failed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     */
    void userLoginFailure(UUID actor, AgentType agentType, String purpose);

    /**
     * Record user Logout to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     */
    void userLogout(UUID actor, AgentType agentType, String purpose);

    /**
     * Record user acccess changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void userAccessChange(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user password changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void passwordChange(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record User PII information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiAccessed(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record User PII information created to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiCreated(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user PII information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiUpdated(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user PII Information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void piiDeleted(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user PHI information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiAccessed(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user PHI information created ot audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiCreated(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record User PHI information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiUpdated(UUID actor, AgentType agentType, String purpose, UUID auditEntity);

    /**
     * Record user PHI information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     */
    void phiDeleted(UUID actor, AgentType agentType, String purpose, UUID auditEntity);
}
