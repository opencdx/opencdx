package cdx.opencdx.iam.model;

import lombok.Data;

@Data
public class BasicInfoResponse {
    String first_name;
    String last_name;
    String credential;
    String sole_proprietor;
    String gender;
    String enumeration_date;
    String last_updated;
    String status;
    String name_prefix;
    String name_suffix;
}
