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
package health.safe.api.opencdx.commons.aspects;

import cdx.open_audit.v2alpha.AuditEventType;
import health.safe.api.opencdx.commons.annotations.OpenCDXAuditAnnotation;
import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;

public class AuditAspectTestInstance {

    public RequestActorAttributes info;

    public RequestActorAttributes getInfo() {
        return info;
    }

    @OpenCDXAuditUser(actor = "actor", patient = "patient")
    public String testAnnotation(String actor, String patient) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditUser(actor = "attributes.actor", patient = "attributes.patient")
    public String testAnnotationChild(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", attributes.getActor(), attributes.getPatient());
    }

    @OpenCDXAuditUser(actor = "attributes.actor", patient = "patient")
    public String testAnnotationFail(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", attributes.getActor(), attributes.getPatient());
    }

    @OpenCDXAuditUser(actor = "actor", patient = "attributes.patient")
    public String testAnnotationFail2(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", attributes.getActor(), attributes.getPatient());
    }

    @OpenCDXAuditUser(actor = "actor", patient = "attributes.bob")
    public String testAnnotationFail3(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", attributes.getActor(), attributes.getPatient());
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationEventUnspecified(
            String actor, String patient, String data, String purpose, String resource) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_SUCCEEDED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserLoginSucceed(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_SUCCEEDED)
    public String testAuditAnnotationUserLoginSucceedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOG_OUT,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserLogOut(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOG_OUT)
    public String testAuditAnnotationUserLogOutElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_FAIL,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserLoginFail(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_LOGIN_FAIL)
    public String testAuditAnnotationUserLoginFailElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserAccessChange(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE)
    public String testAuditAnnotationUserAccessChangeElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPasswordChange(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE)
    public String testAuditAnnotationUserPasswordChangeElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPIIAccessed(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED)
    public String testAuditAnnotationUserPIIAccessedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_UPDATED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPIIUpdated(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_UPDATED)
    public String testAuditAnnotationUserPIIUpdatedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_CREATED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPIICreated(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_CREATED)
    public String testAuditAnnotationUserPIICreatedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_DELETED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPIIDeleted(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PII_DELETED)
    public String testAuditAnnotationUserPIIDeletedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_ACCESSED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPHIAccessed(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_ACCESSED)
    public String testAuditAnnotationUserPHIAccessedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_UPDATED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPHIUpdated(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_UPDATED)
    public String testAuditAnnotationUserPHIUpdatedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPHICreated(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED)
    public String testAuditAnnotationUserPHICreatedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_DELETED,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserPHIDeleted(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_DELETED)
    public String testAuditAnnotationUserPHIDeletedElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_COMMUNICATION,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationUserCommunication(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_USER_COMMUNICATION)
    public String testAuditAnnotationUserCommunicationElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_CONFIG_CHANGE,
            purpose = "purpose",
            resource = "resource")
    public String testAuditAnnotationConfigChange(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }

    @OpenCDXAuditAnnotation(
            actor = "actor",
            patient = "patient",
            data = "data",
            eventType = AuditEventType.AUDIT_EVENT_TYPE_CONFIG_CHANGE)
    public String testAuditAnnotationConfigChangeElse(
            String actor, String patient, String data, String purpose, String resource) {
        return String.format("Say Hello to %s and %s!", actor, patient);
    }
}
