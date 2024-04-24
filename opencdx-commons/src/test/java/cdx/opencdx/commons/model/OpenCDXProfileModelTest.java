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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OpenCDXProfileModelTest {

    @Test
    void test() {
        UserProfile userProfile = UserProfile.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setFullName(FullName.newBuilder()
                        .setFirstName("First")
                        .setMiddleName("Middle")
                        .setLastName("last")
                        .build())
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(1696733104).build())
                .addAllContacts(List.of(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setState("CA")
                                .setPostalCode("12345")
                                .setAddress1("101 Main Street")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setEmail("email@email.com")
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setNumber("1234567890")
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .build()))
                        .build()))
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setPlaceOfBirth(PlaceOfBirth.newBuilder()
                        .setState("CA")
                        .setCountry("USA")
                        .setCity("City")
                        .build())
                .setPhoto(ByteString.copyFromUtf8("photo"))
                .addAllAddress(List.of(Address.newBuilder()
                        .setCity("City")
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .setState("CA")
                        .setPostalCode("12345")
                        .setAddress1("101 Main Street")
                        .build()))
                .setDemographics(Demographics.newBuilder()
                        .setGender(Gender.GENDER_MALE)
                        .setMaritalStatus(MaritalStatus.MARRIED)
                        .setEthnicity(Ethnicity.ETHNICITY_CAUCASIAN)
                        .setLanguage("English")
                        .setIncomeLevel(IncomeLevel.INCOME_LEVEL_MIDDLE_CLASS)
                        .setNationality("USA")
                        .setRace("Race")
                        .setEducation(Education.newBuilder()
                                .addAllEntries(List.of(EducationEntry.newBuilder()
                                        .setDegree("BA")
                                        .setInstitution("University")
                                        .setStartDate("1992/08/01")
                                        .setCompletionDate("1996/05/30")
                                        .build()))
                                .build())
                        .build())
                .setEmployeeIdentity(EmployeeIdentity.newBuilder()
                        .setEmployeeId("employeeID")
                        .setStatus("Full Time")
                        .setIdentityVerified(true)
                        .setWorkspaceId("WorkspaceID")
                        .setOrganizationId("OrganizationId")
                        .build())
                .setPrimaryContactInfo(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setState("CA")
                                .setPostalCode("12345")
                                .setAddress1("101 Main Street")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setEmail("email@email.com")
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setNumber("1234567890")
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .build()))
                        .build())
                .setEmergencyContact(EmergencyContact.newBuilder()
                        .setContactInfo(ContactInfo.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .setName(FullName.newBuilder()
                                        .setFirstName("First")
                                        .setMiddleName("Middle")
                                        .setLastName("last")
                                        .setTitle("Mr.")
                                        .setSuffix("Jr.")
                                        .build())
                                .addAllAddresses(List.of(Address.newBuilder()
                                        .setCity("City")
                                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                                        .setState("CA")
                                        .setPostalCode("12345")
                                        .setAddress1("101 Main Street")
                                        .build()))
                                .addAllEmails(List.of(EmailAddress.newBuilder()
                                        .setEmail("email@email.com")
                                        .setType(EmailType.EMAIL_TYPE_WORK)
                                        .build()))
                                .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                        .setNumber("1234567890")
                                        .setType(PhoneType.PHONE_TYPE_MOBILE)
                                        .build()))
                                .build())
                        .setRelationship("Emergency Contact")
                        .build())
                .setPharmacyDetails(Pharmacy.newBuilder()
                        .setPharmacyAddress(Address.newBuilder()
                                .setCity("City")
                                .setCountryId("USA")
                                .setState("CA")
                                .setPostalCode("12345")
                                .setAddress1("101 Main Street")
                                .build())
                        .setPharmacyName("Pharmacy Name")
                        .setPharmacyContact(ContactInfo.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .addAllAddresses(List.of(Address.newBuilder()
                                        .setCity("City")
                                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                                        .setState("CA")
                                        .setPostalCode("12345")
                                        .setAddress1("101 Main Street")
                                        .build()))
                                .addAllEmails(List.of(EmailAddress.newBuilder()
                                        .setEmail("email@email.com")
                                        .setType(EmailType.EMAIL_TYPE_WORK)
                                        .build()))
                                .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                        .setNumber("1234567890")
                                        .setType(PhoneType.PHONE_TYPE_MOBILE)
                                        .build()))
                                .build())
                        .build())
                .addAllDependentId(List.of(
                        OpenCDXIdentifier.get().toHexString(),
                        OpenCDXIdentifier.get().toHexString()))
                .addAllKnownAllergies(List.of(KnownAllergy.newBuilder()
                        .setAllergen("Evergreen Trees")
                        .setReaction("Respiratory Distress")
                        .setIsSevere(true)
                        .setOnsetDate("1975/12/20")
                        .setLastOccurrence("1976/12/25")
                        .setNotes("Christmas Trees")
                        .build()))
                .setPlaceOfBirth(PlaceOfBirth.newBuilder()
                        .setState("CA")
                        .setCountry("USA")
                        .setCity("City")
                        .build())
                .setCreated(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setModified(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXProfileModel model = new OpenCDXProfileModel(userProfile);
        assertNotNull(model.getUserProfileProtobufMessage());
    }
}
