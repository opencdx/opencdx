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
package cdx.opencdx.commons.service;


import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.SensitivityLevel;

/**
 * The OpenCDXAuditService interface provides methods for recording various audit events.
 * This interface defines methods to record user login, logout, access changes, password changes,
 * PII and PHI information accesses, creations, updates, and deletions, as well as user communications.
 * @implSpec Current implementation is based on {@link cdx.opencdx.commons.service.OpenCDXMessageService}. This can
 * be changed to other systems if desired. However, this will require that {@link cdx.opencdx.audit} be modified as well.
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
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     *
     */
    void userAccessChange(String actor, AgentType agentType, String purpose, String patientId, String patientNhiId);

    /**
     * Record user password changed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     *
     */
    void passwordChange(String actor, AgentType agentType, String purpose, String patientId, String patientNhiId);

    /**
     * Record User PII information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record User PII information created to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record user PII information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record user PII Information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void piiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information accessed to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiAccessed(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information created ot audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiCreated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record User PHI information updated to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiUpdated(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record user PHI information deleted to audit log.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Configuration Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void phiDeleted(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
            String resource,
            String jsonRecord);

    /**
     * Record User Communication.
     * @param actor Currently logged in user who initiated the actions being recorded.
     * @param agentType type of agent for this request
     * @param purpose purpose of use
     * @param sensitivityLevel type of sensitivity level
     * @param patientId User that data was accessed in the system or modified.
     * @param patientNhiId National Health Id of the patient
     * @param resource Communication Identifier
     * @param jsonRecord JSON used to record communications with Audit.
     *
     */
    void communication(
            String actor,
            AgentType agentType,
            String purpose,
            SensitivityLevel sensitivityLevel,
            String patientId,
            String patientNhiId,
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
}
