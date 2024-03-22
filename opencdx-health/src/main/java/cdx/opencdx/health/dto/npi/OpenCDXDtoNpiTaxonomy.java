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
package cdx.opencdx.health.dto.npi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for NPI Taxonomy
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenCDXDtoNpiTaxonomy {
    private String code;

    @JsonProperty("taxonomy_group")
    private String taxonomyGroup;

    private String desc;
    private String state;
    private String license;

    @JsonProperty("primary")
    private boolean primary;

    /**
     * Default Constructor
     */
    public OpenCDXDtoNpiTaxonomy() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
