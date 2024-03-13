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
package cdx.opencdx.health.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for NPI Basic Info
 */
@Data
public class OpenCDXDtoNpiBasicInfo {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String credential;

    @JsonProperty("sole_proprietor")
    private String soleProprietor;

    private String gender;

    @JsonProperty("enumeration_date")
    private String enumerationDate;

    @JsonProperty("last_updated")
    private String lastUpdated;

    private String status;

    @JsonProperty("name_prefix")
    private String namePrefix;

    @JsonProperty("name_suffix")
    private String nameSuffix;

    /**
     * Default Constructor
     */
    public OpenCDXDtoNpiBasicInfo() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
