package cdx.opencdx.iam.model;

import lombok.Data;

import java.util.List;

@Data
public class ProviderResponse {
    Integer result_count;
    List<OpenCDXIAMProviderModel> results;
}
