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
package cdx.opencdx.classification.model;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.grpc.data.TestKit;
import cdx.opencdx.grpc.types.ClassificationType;
import lombok.Data;

/**
 * Model for Rule Result
 */
@Data
@ExcludeFromJacocoGeneratedReport
public class RuleResult {
    /**
     * Constructor for RuleResult
     */
    public RuleResult() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    private String furtherActions = "";

    private boolean notifyCDC = false;

    private ClassificationType type;

    private TestKit testKit = null;
}
