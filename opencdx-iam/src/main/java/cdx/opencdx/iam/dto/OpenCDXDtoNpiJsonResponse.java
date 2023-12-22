package cdx.opencdx.iam.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenCDXDtoNpiJsonResponse {
    @JsonProperty("result_count")
    private int resultCount;

    private List<OpenCDXDtoNpiResult> results;
}
