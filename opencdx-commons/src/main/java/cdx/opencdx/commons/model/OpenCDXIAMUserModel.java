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
package cdx.opencdx.commons.model;

import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.iam.IamUser;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.iam.IamUserType;
import cdx.opencdx.grpc.iam.SignUpRequest;
import cdx.opencdx.grpc.profile.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Record for IAM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("users")
public class OpenCDXIAMUserModel {
    @Id
    private ObjectId id;

    private Instant createdAt;
    private Instant updatedAt;
    private FullName fullName;
    private String username;
    private String systemName;

    @Builder.Default
    private Boolean emailVerified = false;

    private IamUserStatus status;
    private IamUserType type;
    private String password;
    private String nationalHealthId;

    @Builder.Default
    private boolean accountExpired = false;

    @Builder.Default
    private boolean credentialsExpired = false;

    @Builder.Default
    private boolean accountLocked = false;

    private List<ContactInfo> contactInfo;
    private Gender gender;
    private DateOfBirth dateOfBirth;
    private PlaceOfBirth placeOfBirth;
    private Address primaryAddress;
    private byte[] photo;
    private Preferences communication;
    private Demographics demographics;
    private Education education;
    private EmployeeIdentity employeeIdentity;
    private ContactInfo primaryContactInfo;
    private Address billingAddress;
    private Address shippingAddress;
    private EmergencyContact emergencyContact;
    private Pharmacy pharmacyDetails;

    private List<Vaccine> vaccines;
    private List<ObjectId> dependents;
    private List<KnownAllergy> allergies;
    private List<Medication> medications;
    /**
     * Method to identify AgentType for Audit
     * @return AgentType corresponding to IamUser.
     */
    public AgentType getAgentType() {
        if (this.type == null) {
            return AgentType.AGENT_TYPE_UNSPECIFIED;
        }
        return switch (type) {
            case IAM_USER_TYPE_TRIAL, IAM_USER_TYPE_REGULAR -> AgentType.AGENT_TYPE_HUMAN_USER;
            case IAM_USER_TYPE_SYSTEM -> AgentType.AGENT_TYPE_SYSTEM;
            default -> AgentType.AGENT_TYPE_UNSPECIFIED;
        };
    }

    /**
     * Method to update the data with a protobuf UserProfile
     * @param userProfile UserProfile to update data from
     * @return reference to itself.
     */
    public OpenCDXIAMUserModel update(UserProfile userProfile) {
        this.nationalHealthId = userProfile.getNationalHealthId();
        this.fullName = userProfile.getFullName();
        this.contactInfo = userProfile.getContactsList();
        this.gender = userProfile.getGender();
        this.dateOfBirth = userProfile.getDateOfBirth();
        this.primaryAddress = userProfile.getPrimaryAddress();
        this.photo = userProfile.getPhoto().toByteArray();
        this.communication = userProfile.getCommunication();
        this.demographics = userProfile.getDemographics();
        this.education = userProfile.getEducation();
        this.employeeIdentity = userProfile.getEmployeeIdentity();
        this.primaryContactInfo = userProfile.getPrimaryContactInfo();
        this.billingAddress = userProfile.getBillingAddress();
        this.shippingAddress = userProfile.getShippingAddress();
        this.emergencyContact = userProfile.getEmergencyContact();
        this.pharmacyDetails = userProfile.getPharmacyDetails();
        this.vaccines = userProfile.getVaccineAdministeredList();
        this.allergies = userProfile.getKnownAllergiesList();
        this.medications = userProfile.getCurrentMedicationsList();
        return this;
    }

    /**
     * Constructor from a SignUpRequest
     * @param request SingUpRequest to create from.
     */
    public OpenCDXIAMUserModel(SignUpRequest request) {

        this.fullName = FullName.newBuilder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .build();
        this.systemName = request.getSystemName();
        this.username = request.getUsername();
        this.status = IamUserStatus.IAM_USER_STATUS_ACTIVE;
        this.type = request.getType();
    }
    /**
     * Constructor to convert in an IamUser
     * @param iamUser IamUser to read in.
     */
    public OpenCDXIAMUserModel(IamUser iamUser) {
        if (iamUser.hasId()) {
            this.id = new ObjectId(iamUser.getId());
        }
        this.systemName = iamUser.getSystemName();
        if (iamUser.hasCreatedAt()) {
            this.createdAt = Instant.ofEpochSecond(
                    iamUser.getCreatedAt().getSeconds(), iamUser.getCreatedAt().getNanos());
        } else {
            this.createdAt = Instant.now();
        }
        if (iamUser.hasUpdatedAt()) {
            this.updatedAt = Instant.ofEpochSecond(
                    iamUser.getUpdatedAt().getSeconds(), iamUser.getUpdatedAt().getNanos());
        } else {
            this.updatedAt = Instant.now();
        }

        this.username = iamUser.getUsername();
        this.emailVerified = iamUser.getEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
    }

    /**
     * Method to return a gRPC IamUser Message
     * @return gRPC IamUser Message
     */
    public IamUser getIamUserProtobufMessage() {
        IamUser.Builder builder = IamUser.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.createdAt != null) {
            builder.setCreatedAt(Timestamp.newBuilder()
                    .setSeconds(this.createdAt.getEpochSecond())
                    .setNanos(this.createdAt.getNano())
                    .build());
        }
        if (this.updatedAt != null) {
            builder.setUpdatedAt(Timestamp.newBuilder()
                    .setSeconds(this.updatedAt.getEpochSecond())
                    .setNanos(this.updatedAt.getNano())
                    .build());
        }

        if (this.username != null) {
            builder.setUsername(this.username);
        }
        if (this.systemName != null) {
            builder.setSystemName(this.systemName);
        }
        if (this.emailVerified != null) {
            builder.setEmailVerified(this.emailVerified);
        }
        if (this.status != null) {
            builder.setStatus(this.status);
        }
        if (this.type != null) {
            builder.setType(this.type);
        }
        return builder.build();
    }

    /**
     * Method to return a gRPC UserProfile Message
     * @return gRPC UserProfile Message
     */
    @SuppressWarnings("java:S3776")
    public UserProfile getUserProfileProtobufMessage() {
        UserProfile.Builder builder = UserProfile.newBuilder();

        if (this.id != null) {
            builder.setUserId(this.id.toHexString());
        }

        builder.setIsActive(this.status != null && this.status.equals(IamUserStatus.IAM_USER_STATUS_ACTIVE));

        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }

        if (this.fullName != null) {
            builder.setFullName(this.fullName);
        }
        if (this.contactInfo != null) {
            builder.addAllContacts(this.contactInfo);
        }
        if (this.gender != null) {
            builder.setGender(this.gender);
        }
        if (this.dateOfBirth != null) {
            builder.setDateOfBirth(this.dateOfBirth);
        }
        if (this.placeOfBirth != null) {
            builder.setPlaceOfBirth(this.placeOfBirth);
        }
        if (this.primaryAddress != null) {
            builder.setPrimaryAddress(this.primaryAddress);
        }
        if (this.photo != null && this.photo.length > 0) {
            builder.setPhoto(ByteString.copyFrom(this.photo));
        }
        if (this.communication != null) {
            builder.setCommunication(this.communication);
        }
        if (this.demographics != null) {
            builder.setDemographics(this.demographics);
        }
        if (this.education != null) {
            builder.setEducation(this.education);
        }
        if (this.employeeIdentity != null) {
            builder.setEmployeeIdentity(this.employeeIdentity);
        }
        if (this.primaryContactInfo != null) {
            builder.setPrimaryContactInfo(this.primaryContactInfo);
        }
        if (this.billingAddress != null) {
            builder.setBillingAddress(this.billingAddress);
        }
        if (this.shippingAddress != null) {
            builder.setShippingAddress(this.shippingAddress);
        }
        if (this.emergencyContact != null) {
            builder.setEmergencyContact(this.emergencyContact);
        }
        if (this.pharmacyDetails != null) {
            builder.setPharmacyDetails(this.pharmacyDetails);
        }
        if (this.vaccines != null) {
            builder.addAllVaccineAdministered(this.vaccines);
        }
        if (this.dependents != null) {
            builder.addAllDependentId(
                    this.dependents.stream().map(ObjectId::toHexString).toList());
        }
        if (this.allergies != null) {
            builder.addAllKnownAllergies(this.allergies);
        }
        if (this.medications != null) {
            builder.addAllCurrentMedications(this.medications);
        }

        return builder.build();
    }
}
