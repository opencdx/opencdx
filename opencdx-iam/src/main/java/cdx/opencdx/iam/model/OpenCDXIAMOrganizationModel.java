/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.iam.model;

import cdx.opencdx.grpc.organization.ContactInfo;
import cdx.opencdx.grpc.organization.Organization;
import cdx.opencdx.grpc.organization.Workspace;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Organization in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("organization")
public class OpenCDXIAMOrganizationModel {

    @Id
    private ObjectId organizationId;

    private String name;
    private String description;
    private String foundingDate;
    private String address;
    private String website;
    private String industry;
    private double revenue;
    private String logoUrl;
    private List<String> socialMediaLinks;
    private String missionStatement;
    private String visionStatement;
    private List<ContactInfo> contactInfo;
    private List<Workspace> workspace;

    /**
     * Constructor from protobuf message Organization
     * @param organization Protobuf message to generate from
     */
    public OpenCDXIAMOrganizationModel(Organization organization) {
        if (organization.hasOrganizationId()) {
            this.organizationId = new ObjectId(organization.getOrganizationId());
        }
        this.name = organization.getName();
        this.description = organization.getDescription();
        if (organization.hasFoundingDate()) {
            this.foundingDate = organization.getFoundingDate();
        }
        this.address = organization.getAddress();
        this.website = organization.getWebsite();
        this.industry = organization.getIndustry();
        if (organization.hasRevenue()) {
            this.revenue = organization.getRevenue();
        }
        this.logoUrl = organization.getLogoUrl();
        this.socialMediaLinks = organization.getSocialMediaLinksList();
        this.missionStatement = organization.getMissionStatement();
        this.visionStatement = organization.getVisionStatement();
        this.contactInfo = organization.getContactsList();
        this.workspace = organization.getWorkspaceList();
    }

    /**
     * Method to get the protobuf organization object
     * @return protobuf organization object
     */
    public Organization getProtobufMessage() {
        Organization.Builder builder = Organization.newBuilder();

        if (this.organizationId != null) {
            builder.setOrganizationId(this.organizationId.toHexString());
        }
        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.description != null) {
            builder.setDescription(this.description);
        }
        if (this.foundingDate != null) {
            builder.setFoundingDate(this.foundingDate);
        }
        if (this.address != null) {
            builder.setAddress(this.address);
        }
        if (this.website != null) {
            builder.setWebsite(this.website);
        }
        if (this.industry != null) {
            builder.setIndustry(this.industry);
        }

        builder.setRevenue(this.revenue);

        if (this.logoUrl != null) {
            builder.setLogoUrl(this.logoUrl);
        }
        if (this.socialMediaLinks != null) {
            builder.addAllSocialMediaLinks(this.socialMediaLinks);
        }
        if (this.missionStatement != null) {
            builder.setMissionStatement(this.missionStatement);
        }
        if (this.visionStatement != null) {
            builder.setVisionStatement(this.visionStatement);
        }
        if (this.contactInfo != null) {
            builder.addAllContacts(this.contactInfo);
        }
        if (this.workspace != null) {
            builder.addAllWorkspace(this.workspace);
        }

        return builder.build();
    }
}
