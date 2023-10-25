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
package cdx.opencdx.commons.aspects;

import cdx.opencdx.commons.annotations.OpenCDXAuditUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import java.lang.annotation.Annotation;

public class AuditAspectServiceTest implements OpenCDXAuditUser {

    private final String actor;
    private final String patient;
    private final SensitivityLevel sensitivityLevel;

    AuditAspectServiceTest(String actor, String patient, SensitivityLevel sensitivityLevel) {
        this.actor = actor;
        this.patient = patient;
        this.sensitivityLevel = sensitivityLevel;
    }

    @Override
    public String actor() {
        return this.actor;
    }

    @Override
    public String patient() {
        return this.patient;
    }

    @Override
    public SensitivityLevel sensitivityLevel() {
        return this.sensitivityLevel;
    }

    /**
     * Returns the annotation interface of this annotation.
     *
     * @return the annotation interface of this annotation
     * @apiNote Implementation-dependent classes are used to provide
     * the implementations of annotations. Therefore, calling {@link
     * Object#getClass getClass} on an annotation will return an
     * implementation-dependent class. In contrast, this method will
     * reliably return the annotation interface of the annotation.
     * @see Enum#getDeclaringClass
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
