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
import java.util.List;
import lombok.Data;

/**
 * DTO for NPI Result
 */
@Data
public class OpenCDXDtoNpiResult {
    @JsonProperty("created_epoch")
    private String createdEpoch;

    @JsonProperty("enumeration_type")
    private String enumerationType;

    @JsonProperty("last_updated_epoch")
    private String lastUpdatedEpoch;

    private String number;
    private List<OpenCDXDtoNpiAddress> addresses;

    @JsonProperty("practiceLocations")
    private List<String> practiceLocations;

    private OpenCDXDtoNpiBasicInfo basic;
    private List<OpenCDXDtoNpiTaxonomy> taxonomies;
    private List<OpenCDXDtoNpiIdentifier> identifiers;
    private List<Object> endpoints;

    @JsonProperty("other_names")
    private List<Object> otherNames;

    /**
     * Default Constructor
     */
    public OpenCDXDtoNpiResult() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
