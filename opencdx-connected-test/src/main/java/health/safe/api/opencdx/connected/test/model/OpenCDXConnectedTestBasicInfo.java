package health.safe.api.opencdx.connected.test.model;

import cdx.open_connected_test.v2alpha.BasicInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXConnectedTestBasicInfo {

    /**
     *               Creation timestamp of the test record
     */
    private String created;
    /**
     *             User or entity that created the test
     */
    private String creator;
    /**
     *              Timestamp when the test was last modified
     */
    private String modified;
    /**
     *               User or entity that last modified the test
     */
    private String modifier;
    /**
     *       ID referencing the test within the vendor's system
     */
    private String vendorLabTestId;
    /**
     *                Class or type of the test
     */
    private String classType;
    /**
     *                 ID of the user associated with the test
     */
    private String userId;
    /**
     *     National health ID of the user
     */
    private int nationalHealthId;
    /**
     *      // ID of the health service provider
     */
    private String healthServiceId;
    /**
     *              ID of the tenant (in multi-tenant systems)
     */
    private String tenantId;
    /**
     *              // Source or origin of the test data
     */
    private String source;


    public OpenCDXConnectedTestBasicInfo(BasicInfo basicInfo) {
        this.created = basicInfo.getCreated();
        this.creator = basicInfo.getCreator();
        this.modified = basicInfo.getModified();
        this.modifier = basicInfo.getModifier();
        this.vendorLabTestId = basicInfo.getVendorLabTestId();
        this.classType = basicInfo.getClass_();
        this.userId = basicInfo.getUserId();
        this.nationalHealthId = basicInfo.getNationalHealthId();
        this.healthServiceId = basicInfo.getHealthServiceId();
        this.tenantId = basicInfo.getTenantId();
        this.source = basicInfo.getSource();
    }

    public BasicInfo getProtobufMessage(String id) {
        return BasicInfo.newBuilder()
                .setId(id)
                .setCreated(this.created)
                .setCreator(this.creator)
                .setModified(this.modified)
                .setModifier(this.modifier)
                .setVendorLabTestId(this.vendorLabTestId)
                .setClass_(this.classType)
                .setUserId(this.userId)
                .setNationalHealthId(this.nationalHealthId)
                .setHealthServiceId(this.healthServiceId)
                .setTenantId(this.tenantId)
                .setSource(this.source)
                .build();
    }
}
