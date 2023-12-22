package cdx.opencdx.iam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

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
    private List<Object> practiceLocations;

    private OpenCDXDtoNpiBasicInfo basic;
    private List<OpenCDXDtoNpiTaxonomy> taxonomies;
    private List<OpenCDXDtoNpiIdentifier> identifiers;
    private List<Object> endpoints;

    @JsonProperty("other_names")
    private List<Object> otherNames;
}
