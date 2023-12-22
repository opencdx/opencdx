package cdx.opencdx.iam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenCDXDtoNpiTaxonomy {
    private String code;

    @JsonProperty("taxonomy_group")
    private String taxonomyGroup;

    private String desc;
    private String state;
    private String license;

    @JsonProperty("primary")
    private boolean primary;
}
