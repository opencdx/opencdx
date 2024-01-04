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
package proto;

import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.profile.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class ProfileTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void updateUserProfileRequest() throws JsonProcessingException {
        log.info(
                "UpdateUserProfileRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(UpdateUserProfileRequest.newBuilder()
                                .setUserId(ObjectId.get().toHexString())
                                .setUpdatedProfile(UserProfile.newBuilder()
                                        .setUserId(ObjectId.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setFullName(FullName.newBuilder()
                                                .setFirstName("First")
                                                .setMiddleName("Middle")
                                                .setLastName("last")
                                                .build())
                                        .addAllContacts(List.of(ContactInfo.newBuilder()
                                                .setUserId(ObjectId.get().toHexString())
                                                .addAllAddresses(List.of(Address.newBuilder()
                                                        .setCity("City")
                                                        .setCountryId(
                                                                ObjectId.get().toHexString())
                                                        .setState("CA")
                                                        .setPostalCode("12345")
                                                        .setAddress1("101 Main Street")
                                                        .build()))
                                                .addAllEmail(List.of(EmailAddress.newBuilder()
                                                        .setEmail("email@email.com")
                                                        .setType(EmailType.EMAIL_TYPE_WORK)
                                                        .build()))
                                                .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                        .setNumber("1234567890")
                                                        .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                        .build()))
                                                .build()))
                                        .setGender(Gender.GENDER_MALE)
                                        .setDateOfBirth(DateOfBirth.newBuilder()
                                                .setDate("1900/01/01")
                                                .build())
                                        .setPlaceOfBirth(PlaceOfBirth.newBuilder()
                                                .setState("CA")
                                                .setCountry("USA")
                                                .setCity("City")
                                                .build())
                                        .addAllAddress(List.of(Address.newBuilder()
                                                .setCity("City")
                                                .setCountryId(ObjectId.get().toHexString())
                                                .setState("CA")
                                                .setPostalCode("12345")
                                                .setAddress1("101 Main Street")
                                                .build()))
                                        .setCommunication(Preferences.newBuilder()
                                                .setPreferred(true)
                                                .setLanguage("EN")
                                                .setTimeZone("EST")
                                                .build())
                                        .setDemographics(Demographics.newBuilder()
                                                .setEthnicity("Ethnicity")
                                                .setGender(Gender.GENDER_MALE)
                                                .setNationality("USA")
                                                .setRace("Race")
                                                .build())
                                        .setEducation(Education.newBuilder()
                                                .addAllEntries(List.of(EducationEntry.newBuilder()
                                                        .setDegree("BA")
                                                        .setInstitution("University")
                                                        .setStartDate("1992/08/01")
                                                        .setCompletionDate("1996/05/30")
                                                        .build()))
                                                .build())
                                        .setEmployeeIdentity(EmployeeIdentity.newBuilder()
                                                .setEmployeeId("employeeID")
                                                .setStatus("Full Time")
                                                .setIdentityVerified(true)
                                                .setWorkspaceId("WorkspaceID")
                                                .setOrganizationId("OrganizationId")
                                                .build())
                                        .setPrimaryContactInfo(ContactInfo.newBuilder()
                                                .setUserId(ObjectId.get().toHexString())
                                                .addAllAddresses(List.of(Address.newBuilder()
                                                        .setCity("City")
                                                        .setCountryId(
                                                                ObjectId.get().toHexString())
                                                        .setState("CA")
                                                        .setPostalCode("12345")
                                                        .setAddress1("101 Main Street")
                                                        .build()))
                                                .addAllEmail(List.of(EmailAddress.newBuilder()
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
                                                        .setUserId(
                                                                ObjectId.get().toHexString())
                                                        .setName(FullName.newBuilder()
                                                                .setFirstName("First")
                                                                .setMiddleName("Middle")
                                                                .setLastName("last")
                                                                .setTitle("Mr.")
                                                                .setSuffix("Jr.")
                                                                .build())
                                                        .addAllAddresses(List.of(Address.newBuilder()
                                                                .setCity("City")
                                                                .setCountryId(ObjectId.get()
                                                                        .toHexString())
                                                                .setState("CA")
                                                                .setPostalCode("12345")
                                                                .setAddress1("101 Main Street")
                                                                .build()))
                                                        .addAllEmail(List.of(EmailAddress.newBuilder()
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
                                                        .setUserId(
                                                                ObjectId.get().toHexString())
                                                        .addAllAddresses(List.of(Address.newBuilder()
                                                                .setCity("City")
                                                                .setCountryId(ObjectId.get()
                                                                        .toHexString())
                                                                .setState("CA")
                                                                .setPostalCode("12345")
                                                                .setAddress1("101 Main Street")
                                                                .build()))
                                                        .addAllEmail(List.of(EmailAddress.newBuilder()
                                                                .setEmail("email@email.com")
                                                                .setType(EmailType.EMAIL_TYPE_WORK)
                                                                .build()))
                                                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                                .setNumber("1234567890")
                                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                                .build()))
                                                        .build())
                                                .build())
                                        .addAllVaccineAdministered(List.of(Vaccine.newBuilder()
                                                .setAdministrationDate("2021/06/01")
                                                .setFips("12345")
                                                .setLocality("CA")
                                                .setHealthDistrict("District")
                                                .setFacilityType("Clinic")
                                                .setManufacturer(ObjectId.get().toHexString())
                                                .setDoseNumber(20)
                                                .setVaccineType("COVID-19")
                                                .build()))
                                        .addAllDependentId(List.of(
                                                ObjectId.get().toHexString(),
                                                ObjectId.get().toHexString()))
                                        .addAllKnownAllergies(List.of(KnownAllergy.newBuilder()
                                                .setAllergen("Evergreen Trees")
                                                .setReaction("Respiratory Distress")
                                                .setIsSevere(true)
                                                .setOnsetDate("1975/12/20")
                                                .setLastOccurrence("1976/12/25")
                                                .setNotes("Christmas Trees")
                                                .build()))
                                        .addAllCurrentMedications(List.of(Medication.newBuilder()
                                                .setName("Singular")
                                                .setDosage("5mg")
                                                .setInstructions("Take 1 pill at night")
                                                .setRouteOfAdministration("Oral")
                                                .setFrequency("Daily")
                                                .setStartDate("1976/12/26")
                                                .setEndDate("EOL")
                                                .setPrescribingDoctor("Dr. OpenCDX")
                                                .setPharmacy("Pharmacy")
                                                .setIsPrescription(true)
                                                .build()))
                                        .build())
                                .build()));
    }
}
