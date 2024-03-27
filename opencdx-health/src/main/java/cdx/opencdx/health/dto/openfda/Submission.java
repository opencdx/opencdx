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
package cdx.opencdx.health.dto.openfda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Submission class
 */
@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("java:S116")
public class Submission {
    private String submission_type;
    private String submission_number;
    private String submission_status;
    private String submission_status_date;
    private String review_priority;
    private String submission_class_code;
    private String submission_class_code_description;
    private List<ApplicationDoc> application_docs;

    /**
     * Default Constructor
     */
    public Submission() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
