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
package cdx.opencdx.commons.annotations;

import cdx.open_audit.v2alpha.SensitivityLevel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to exclude the class or method from being calculated into
 * the JaCoCo coverage report.  Should only be used sparingly.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OpenCDXAuditUser {
    /**
     * The actor ID
     * @return the actor ID.
     */
    String actor() default "";

    /**
     * The patient ID
     * @return the patient ID.
     */
    String patient() default "";

    /**
     * The SensitivityLevel
     * @return the sensitivity level
     */
    SensitivityLevel sensitivityLevel() default SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED;
}
