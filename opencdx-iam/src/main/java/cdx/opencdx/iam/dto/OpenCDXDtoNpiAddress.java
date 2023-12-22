package cdx.opencdx.iam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
}
