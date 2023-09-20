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
}
