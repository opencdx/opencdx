/*
 * Copyright 2024 Safe Health Systems, Inc.
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

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.ContactInfo;
import cdx.opencdx.grpc.common.FullName;
import cdx.opencdx.grpc.common.Gender;
import cdx.opencdx.grpc.profile.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("profiles")
public class OpenCDXProfileModel {

    @Id
    private ObjectId id;

    private ObjectId userId;

    private FullName fullName;
    private String nationalHealthId;

    private List<ContactInfo> contactInfo;
    private Gender gender;
    private Instant dateOfBirth;
    private PlaceOfBirth placeOfBirth;
    private List<Address> addresses;
    private byte[] photo;
    private Preferences communication;
    private Demographics demographics;
    private Education education;
    private EmployeeIdentity employeeIdentity;
    private ContactInfo primaryContactInfo;
    private EmergencyContact emergencyContact;
    private Pharmacy pharmacyDetails;
    private boolean isActive;

    private List<Vaccine> vaccines;
    private List<ObjectId> dependents;
    private List<KnownAllergy> allergies;
    private List<Medication> medications;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Method to update the data with a protobuf UserProfile
     * @param userProfile UserProfile to update data from
     * @return reference to itself.
     */
    public OpenCDXProfileModel update(UserProfile userProfile) {
        log.trace("Updating user profile for user");
        this.nationalHealthId = userProfile.getNationalHealthId();
        this.fullName = userProfile.getFullName();
        this.contactInfo = userProfile.getContactsList();
        this.gender = userProfile.getGender();

        if (userProfile.hasUserId()) {
            this.userId = new ObjectId(userProfile.getUserId());
        }

        if (userProfile.hasDateOfBirth()) {
            this.dateOfBirth = Instant.ofEpochSecond(
                    userProfile.getDateOfBirth().getSeconds(),
                    userProfile.getDateOfBirth().getNanos());
        }
        this.isActive = userProfile.getIsActive();
        this.addresses = userProfile.getAddressList();
        this.photo = userProfile.getPhoto().toByteArray();
        this.communication = userProfile.getCommunication();
        this.demographics = userProfile.getDemographics();
        this.education = userProfile.getEducation();
        this.employeeIdentity = userProfile.getEmployeeIdentity();
        this.primaryContactInfo = userProfile.getPrimaryContactInfo();
        this.emergencyContact = userProfile.getEmergencyContact();
        this.pharmacyDetails = userProfile.getPharmacyDetails();
        this.vaccines = userProfile.getVaccineAdministeredList();
        this.allergies = userProfile.getKnownAllergiesList();
        this.medications = userProfile.getCurrentMedicationsList();

        if (userProfile.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    userProfile.getCreated().getSeconds(),
                    userProfile.getCreated().getNanos());
        }
        if (userProfile.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    userProfile.getModified().getSeconds(),
                    userProfile.getModified().getNanos());
        }
        if (userProfile.hasCreator()) {
            this.creator = new ObjectId(userProfile.getCreator());
        }
        if (userProfile.hasModifier()) {
            this.modifier = new ObjectId(userProfile.getModifier());
        }

        return this;
    }

    /**
     * Method to return a gRPC UserProfile Message
     * @return gRPC UserProfile Message
     */
    @SuppressWarnings("java:S3776")
    public UserProfile getUserProfileProtobufMessage() {
        log.trace("Creating user profile from user");
        UserProfile.Builder builder = UserProfile.newBuilder();

        builder.setUserId(this.id.toHexString());

        if (this.userId != null) {
            builder.setUserId(this.userId.toHexString());
        }

        builder.setIsActive(this.isActive);

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

            builder.setDateOfBirth(Timestamp.newBuilder()
                    .setSeconds(this.dateOfBirth.getEpochSecond())
                    .setNanos(this.dateOfBirth.getNano())
                    .build());
        }
        if (this.placeOfBirth != null) {
            builder.setPlaceOfBirth(this.placeOfBirth);
        }
        if (this.addresses != null) {
            builder.addAllAddress(this.addresses);
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
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }
}
