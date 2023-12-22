package cdx.opencdx.iam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
}
