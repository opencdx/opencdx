package cdx.opencdx.iam.model;

import lombok.Data;

@Data
@SuppressWarnings({"java:S116"})
public class TaxonomiesResponse {
    String code;
    String taxonomy_group;
    String desc;
    String state;
    String license;
    Boolean primary;
}
