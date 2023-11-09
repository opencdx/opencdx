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
package cdx.opencdx.commons.service;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;

/**
 * Integrated Audit service to use the Audit Aspect.
 */
public interface OpenCDXAuditService {
    /**
     * Record User Login successful to audit log.
     * @param actor Currently logged-in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     *
     */
    void userLoginSucceed(String actor, AgentType agentType, String purpose);

    /**
     * Record User Login failed to audit log.
     * @param actor Currently logged-in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     *
     */
    void userLoginFailure(String actor, AgentType agentType, String purpose);

    /**
     * Record user Logout to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     *
     */
    void userLogout(String actor, AgentType agentType, String purpose);

    /**
     * Record user acccess changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     *
     */
    void userAccessChange(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record user password changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param auditEntity User that data was accessed in the system or modified.
     *
     */
    void passwordChange(String actor, AgentType agentType, String purpose, String auditEntity);

    /**
     * Record User PII information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record User PII information created to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record user PII information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record user PII Information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information created ot audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record User PHI information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record User Communication.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param auditEntity User that data was accessed in the system or modified.
     * @param resource Communication Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String auditEntity,
            String resource,
            String jsonRecord);

    /**
     * Record Configuraiton Change
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void config(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String resource,
            String jsonRecord);

    /**
     * Record User Login successful to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void userLoginSucceed(AgentType agentType, String purpose) throws OpenCDXBadRequest;

    /**
     * Record User Login failed to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void userLoginFailure(AgentType agentType, String purpose) throws OpenCDXBadRequest;

    /**
     * Record user Logout to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void userLogout(AgentType agentType, String purpose) throws OpenCDXBadRequest;

    /**
     * Record user acccess changed to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void userAccessChange(AgentType agentType, String purpose) throws OpenCDXBadRequest;

    /**
     * Record user password changed to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void passwordChange(AgentType agentType, String purpose) throws OpenCDXBadRequest;

    /**
     * Record User PII information accessed to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void piiAccessed(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record User PII information created to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void piiCreated(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record user PII information updated to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void piiUpdated(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record user PII Information deleted to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void piiDeleted(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record user PHI information accessed to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void phiAccessed(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record user PHI information created ot audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void phiCreated(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record User PHI information updated to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void phiUpdated(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record user PHI information deleted to audit log.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void phiDeleted(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record User Communication.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Communication Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void communication(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;

    /**
     * Record Configuration Change
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel sensitivity level for this request
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     * @exception OpenCDXBadRequest Thrown if audit information is not captured.
     */
    void config(
            AgentType agentType, String purpose, SensitivityLevel sensitivityLevel, String resource, String jsonRecord)
            throws OpenCDXBadRequest;
}
