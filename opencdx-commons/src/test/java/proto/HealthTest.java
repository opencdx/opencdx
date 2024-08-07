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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.DiagnosisRequest;
import cdx.opencdx.grpc.service.health.SearchMedicationsRequest;
import cdx.opencdx.grpc.service.health.UpdateUserProfileRequest;
import cdx.opencdx.grpc.types.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class HealthTest {
    ObjectMapper mapper;

    ObjectWriter writer;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
        this.writer = mapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    void doctorsNotes() throws JsonProcessingException {
        DoctorNotes doctorNotes = DoctorNotes.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setProviderNumber("Provider Number")
                .addAllTags(List.of("tag1", "tag2"))
                .setNoteDatetime(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setNotes("Notes")
                .setCreated(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setModified(Timestamp.newBuilder().setSeconds(1696733104).build())
                .setCreator("Creator")
                .setModifier("Modifier")
                .build();

        log.info("DoctorNotes: \n {}", this.writer.withDefaultPrettyPrinter().writeValueAsString(doctorNotes));
    }

    @Test
    void searchMedicationsRequest() throws JsonProcessingException {
        SearchMedicationsRequest searchMedicationsRequest = SearchMedicationsRequest.newBuilder()
                .setBrandName("Adipex")
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        log.info("SearchMedicationsRequest: \n {}", this.writer.writeValueAsString(searchMedicationsRequest));
    }

    @Test
    void updateUserProfileRequest() throws JsonProcessingException {
        log.info(
                "UpdateUserProfileRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(UpdateUserProfileRequest.newBuilder()
                                .setUserId(OpenCDXIdentifier.get().toHexString())
                                .setUpdatedProfile(UserProfile.newBuilder()
                                        .setUserId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setFullName(FullName.newBuilder()
                                                .setFirstName("First")
                                                .setMiddleName("Middle")
                                                .setLastName("last")
                                                .build())
                                        .addAllContacts(List.of(ContactInfo.newBuilder()
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .addAllAddresses(List.of(Address.newBuilder()
                                                        .setCity("City")
                                                        .setCountryId(OpenCDXIdentifier.get()
                                                                .toHexString())
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
                                        .setDateOfBirth(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setPlaceOfBirth(PlaceOfBirth.newBuilder()
                                                .setState("CA")
                                                .setCountry("USA")
                                                .setCity("City")
                                                .build())
                                        .addAllAddress(List.of(Address.newBuilder()
                                                .setCity("City")
                                                .setCountryId(
                                                        OpenCDXIdentifier.get().toHexString())
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
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .addAllAddresses(List.of(Address.newBuilder()
                                                        .setCity("City")
                                                        .setCountryId(OpenCDXIdentifier.get()
                                                                .toHexString())
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
                                                        .setPatientId(OpenCDXIdentifier.get()
                                                                .toHexString())
                                                        .setName(FullName.newBuilder()
                                                                .setFirstName("First")
                                                                .setMiddleName("Middle")
                                                                .setLastName("last")
                                                                .setTitle("Mr.")
                                                                .setSuffix("Jr.")
                                                                .build())
                                                        .addAllAddresses(List.of(Address.newBuilder()
                                                                .setCity("City")
                                                                .setCountryId(OpenCDXIdentifier.get()
                                                                        .toHexString())
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
                                                        .setPatientId(OpenCDXIdentifier.get()
                                                                .toHexString())
                                                        .addAllAddresses(List.of(Address.newBuilder()
                                                                .setCity("City")
                                                                .setCountryId(OpenCDXIdentifier.get()
                                                                        .toHexString())
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
                                                .setOnsetDate(Timestamp.newBuilder()
                                                        .setSeconds(1696733104)
                                                        .build())
                                                .setLastOccurrence(Timestamp.newBuilder()
                                                        .setSeconds(1696733104)
                                                        .build())
                                                .setNotes("Christmas Trees")
                                                .build()))
                                        .build())
                                .build()));
    }

    @Test
    void diagnosisRequest() throws JsonProcessingException {
        log.info(
                "DiagnosisRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(DiagnosisRequest.newBuilder()
                                .setDiagnosis(Diagnosis.newBuilder()
                                        .setDiagnosisStatus(DiagnosisStatus.SUSPECTED)
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setDiagnosisDatetime(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setDiagnosisCode(DiagnosisCode.newBuilder()
                                                .setTinkarCode("[TINKAR CODE]")
                                                .putAllValueMap(Map.of("TEST", "JMeter", "Test2", "smoke"))
                                                .setAnfStatement(ANFStatement.newBuilder()
                                                        .setTime(Measure.newBuilder()
                                                                .setUpperBound(120)
                                                                .setLowerBound(90)
                                                                .setIncludeUpperBound(true)
                                                                .setIncludeUpperBound(true)
                                                                .setSemantic(LogicalExpression.newBuilder()
                                                                        .setExpression("mmHg"))
                                                                .setResolution(1)
                                                                .build())
                                                        .setSubjectOfRecord(Participant.newBuilder()
                                                                .setId(UUID.randomUUID()
                                                                        .toString())
                                                                .setPractitionerValue(Reference.newBuilder()
                                                                        .setDisplay("display")
                                                                        .setIdentifier("identifier")
                                                                        .setReference("reference")
                                                                        .setUri("uri")
                                                                        .build())
                                                                .setCode(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient 1"))
                                                                .build())
                                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                                .setId(UUID.randomUUID()
                                                                        .toString())
                                                                .setPractitionerValue(Reference.newBuilder()
                                                                        .setDisplay("display")
                                                                        .setIdentifier("identifier")
                                                                        .setReference("reference")
                                                                        .setUri("uri")
                                                                        .build())
                                                                .setCode(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient 1"))
                                                                .build()))
                                                        .setSubjectOfInformation(LogicalExpression.newBuilder()
                                                                .setExpression(OpenCDXIdentifier.get()
                                                                        .toHexString()))
                                                        .addAllAssociatedStatement(
                                                                List.of(AssociatedStatement.newBuilder()
                                                                        .setId(Reference.newBuilder()
                                                                                .setDisplay("display")
                                                                                .setIdentifier("identifier")
                                                                                .setReference("reference")
                                                                                .setUri("uri")
                                                                                .build())
                                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                                .setExpression("mmHg"))
                                                                        .build()))
                                                        .setTopic(LogicalExpression.newBuilder()
                                                                .setExpression("Blood Pressure"))
                                                        .setType(LogicalExpression.newBuilder()
                                                                .setExpression("Medical Apppointment"))
                                                        .setNarrativeCircumstance(NarrativeCircumstance.newBuilder()
                                                                .setTiming(Measure.newBuilder()
                                                                        .setUpperBound(130)
                                                                        .setLowerBound(130)
                                                                        .setIncludeUpperBound(true)
                                                                        .setIncludeUpperBound(true)
                                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                                .setExpression("mmHg"))
                                                                        .setResolution(1)
                                                                        .build())
                                                                .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient Summary")
                                                                        .build()))
                                                                .setText(
                                                                        "The patient was in good health, and no issues were noted during the examination.")
                                                                .build())
                                                        .build())
                                                .build())
                                        .build())
                                .build()));
    }

    @Test
    void createMedicalHistoryRequest() throws JsonProcessingException {
        log.info(
                "CreateMedicalHistoryRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(CreateMedicalHistoryRequest.newBuilder()
                                .setMedicalHistory(MedicalHistory.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setStartDate(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setEndDate(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setCondition(DiagnosisCode.newBuilder()
                                                .setTinkarCode("[TINKAR CODE]")
                                                .putAllValueMap(Map.of("TEST", "JMeter", "Test2", "smoke"))
                                                .setAnfStatement(ANFStatement.newBuilder()
                                                        .setTime(Measure.newBuilder()
                                                                .setUpperBound(120)
                                                                .setLowerBound(90)
                                                                .setIncludeUpperBound(true)
                                                                .setIncludeUpperBound(true)
                                                                .setSemantic(LogicalExpression.newBuilder()
                                                                        .setExpression("mmHg"))
                                                                .setResolution(1)
                                                                .build())
                                                        .setSubjectOfRecord(Participant.newBuilder()
                                                                .setId(UUID.randomUUID()
                                                                        .toString())
                                                                .setPractitionerValue(Reference.newBuilder()
                                                                        .setDisplay("display")
                                                                        .setIdentifier("identifier")
                                                                        .setReference("reference")
                                                                        .setUri("uri")
                                                                        .build())
                                                                .setCode(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient 1"))
                                                                .build())
                                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                                .setId(UUID.randomUUID()
                                                                        .toString())
                                                                .setPractitionerValue(Reference.newBuilder()
                                                                        .setDisplay("display")
                                                                        .setIdentifier("identifier")
                                                                        .setReference("reference")
                                                                        .setUri("uri")
                                                                        .build())
                                                                .setCode(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient 1"))
                                                                .build()))
                                                        .setSubjectOfInformation(LogicalExpression.newBuilder()
                                                                .setExpression(OpenCDXIdentifier.get()
                                                                        .toHexString()))
                                                        .addAllAssociatedStatement(
                                                                List.of(AssociatedStatement.newBuilder()
                                                                        .setId(Reference.newBuilder()
                                                                                .setDisplay("display")
                                                                                .setIdentifier("identifier")
                                                                                .setReference("reference")
                                                                                .setUri("uri")
                                                                                .build())
                                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                                .setExpression("mmHg"))
                                                                        .build()))
                                                        .setTopic(LogicalExpression.newBuilder()
                                                                .setExpression("Blood Pressure"))
                                                        .setType(LogicalExpression.newBuilder()
                                                                .setExpression("Medical Apppointment"))
                                                        .setNarrativeCircumstance(NarrativeCircumstance.newBuilder()
                                                                .setTiming(Measure.newBuilder()
                                                                        .setUpperBound(130)
                                                                        .setLowerBound(130)
                                                                        .setIncludeUpperBound(true)
                                                                        .setIncludeUpperBound(true)
                                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                                .setExpression("mmHg"))
                                                                        .setResolution(1)
                                                                        .build())
                                                                .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                                        .setExpression("Patient Summary")
                                                                        .build()))
                                                                .setText(
                                                                        "The patient was in good health, and no issues were noted during the examination.")
                                                                .build())
                                                        .build())
                                                .build())
                                        .build())
                                .build()));
    }
}
