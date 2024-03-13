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
 * DTO for NPI Address
 */
@Data
public class OpenCDXDtoNpiAddress {
    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("address_purpose")
    private String addressPurpose;

    @JsonProperty("address_type")
    private String addressType;

    @JsonProperty("address_1")
    private String address1;

    private String city;
    private String state;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("telephone_number")
    private String telephoneNumber;

    @JsonProperty("fax_number")
    private String faxNumber;

    /**
     * Default Constructor
     */
    public OpenCDXDtoNpiAddress() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
