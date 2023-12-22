package cdx.opencdx.iam.dto;

import lombok.Data;

@Data
public class OpenCDXDtoNpiIdentifier {
    private String code;
    private String desc;
    private String issuer;
    private String identifier;
    private String state;
}
