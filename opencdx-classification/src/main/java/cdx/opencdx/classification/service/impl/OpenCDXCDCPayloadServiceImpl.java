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
package cdx.opencdx.classification.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.service.OpenCDXCDCPayloadService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXDeviceClient;
import cdx.opencdx.client.service.OpenCDXManufacturerClient;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.connected.TestIdRequest;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Address;
import org.springframework.stereotype.Service;

/**
 * Service for creating CDC payloads
 */
@Service
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXCDCPayloadServiceImpl implements OpenCDXCDCPayloadService {

    private static final String LOINC_URL = "https://loinc.org";

    private final OpenCDXDeviceClient openCDXDeviceClient;
    private final OpenCDXManufacturerClient openCDXManufacturerClient;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXMessageService openCDXMessageService;

    /**
     * Constructor with OpenCDXCDCPayloadServiceImpl
     *
     * @param openCDXDeviceClient            Client for Device info
     * @param openCDXManufacturerClient      Client for Manufacturer info
     * @param openCDXCurrentUser             Service for getting current user
     * @param openCDXMessageService          Message Service for sending CDC message
     */
    public OpenCDXCDCPayloadServiceImpl(
            OpenCDXDeviceClient openCDXDeviceClient,
            OpenCDXManufacturerClient openCDXManufacturerClient,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXMessageService openCDXMessageService) {
        this.openCDXDeviceClient = openCDXDeviceClient;
        this.openCDXManufacturerClient = openCDXManufacturerClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXMessageService = openCDXMessageService;
    }

    public void sendCDCPayloadMessage(OpenCDXClassificationModel model) {

        OpenCDXCallCredentials openCDXCallCredentials =
                new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken());

        // retrieve the connected test using the refId
        ConnectedTest connectedTest = model.getConnectedTest();

        // Retrieve Patient
        String patientId = connectedTest.getBasicInfo().getPatientId();
        Patient patient = getPatientInfo(model.getPatient());

        // Create Device
        Device device = createDevice(connectedTest, openCDXCallCredentials);

        // Create Observation
        Observation observation = createObservation(connectedTest);

        // Create DiagnosticReport
        DiagnosticReport diagnosticReport = createDiagnosticReport(connectedTest);

        // Create Bundle
        Bundle bundle = createBundle(patient, device, observation, diagnosticReport);

        // Send to CDC
        sendMessage(bundle);
    }

    private Device createDevice(ConnectedTest connectedTest, OpenCDXCallCredentials openCDXCallCredentials) {

        // retrieve the device for the connected test
        DeviceIdRequest deviceRequest = DeviceIdRequest.newBuilder()
                .setDeviceId(connectedTest.getTestDetails().getDeviceIdentifier())
                .build();
        cdx.opencdx.grpc.inventory.Device deviceInfo =
                this.openCDXDeviceClient.getDeviceById(deviceRequest, openCDXCallCredentials);

        // Create a FHIR Device instance
        Device device = new Device();

        // Set the category for the report
        Device.DeviceDeviceNameComponent deviceName = device.addDeviceName();
        deviceName.setName(deviceInfo.getName());
        deviceName.setType(Device.DeviceNameType.USERFRIENDLYNAME);

        device.setExpirationDate(Date.from(Instant.ofEpochSecond(
                deviceInfo.getCreated().getSeconds(), deviceInfo.getCreated().getNanos())));
        device.setLotNumber(deviceInfo.getBatchNumber());
        device.setSerialNumber(deviceInfo.getSerialNumber());
        device.setModelNumber(deviceInfo.getModel());

        // Retrieve the manufacturer for the Device
        ManufacturerIdRequest manufacturerRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(deviceInfo.getManufacturerId())
                .build();
        Manufacturer manufacturerInfo =
                this.openCDXManufacturerClient.getManufacturerById(manufacturerRequest, openCDXCallCredentials);

        device.setManufacturerElement(new StringType(manufacturerInfo.getName()));
        device.setManufactureDate(Date.from(Instant.ofEpochSecond(
                manufacturerInfo.getCreated().getSeconds(),
                manufacturerInfo.getCreated().getNanos())));

        // Set the patient
        Reference subject = device.getPatient();
        setPatientReference(connectedTest.getBasicInfo().getPatientId(), subject);

        // Set the Type
        Coding coding = device.getType().addCoding();
        coding.setDisplay(deviceInfo.getName());
        device.getType().setText(deviceInfo.getShortDescription());

        // Set the meta attribute
        Meta meta = device.getMeta();
        meta.setLastUpdated(new Date());

        // Set the Identifiers
        Identifier identifier = device.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        typeCoding.setDisplay(deviceInfo.getId());

        // Give the report a status
        device.setStatus(Device.FHIRDeviceStatus.ACTIVE);

        return device;
    }

    private Observation createObservation(ConnectedTest connectedTest) {

        // Create an Observation instance
        Observation observation = new Observation();

        // Give the observation a status
        observation.setStatus(Observation.ObservationStatus.FINAL);

        // Set the category for the observation
        CodeableConcept category = observation.addCategory();
        Coding categoryCoding = category.addCoding();
        categoryCoding.setCode("laboratory");
        categoryCoding.setDisplay("laboratory");
        categoryCoding.setSystem("https://terminology.hl7.org/CodeSystem/observation-category");

        // Give the observation a code
        Coding coding = observation.getCode().addCoding();
        coding.setCode(connectedTest.getTestDetails().getLoincCode().getCode())
                .setSystem(LOINC_URL)
                .setDisplay(connectedTest.getTestDetails().getLoincCode().getDisplay());

        // Set the Identifiers
        observation.addIdentifier(setIdentifier(observation, connectedTest));

        // Set the value
        if (!connectedTest.getTestDetails().getOrderableTestResultsList().isEmpty()) {
            observation.setValue(new StringType(
                    connectedTest.getTestDetails().getOrderableTestResults(0).getTestResult()));
        }

        // Set the reference range
        Observation.ObservationReferenceRangeComponent comp1 = observation.addReferenceRange();
        comp1.setText("Positive");
        Observation.ObservationReferenceRangeComponent comp2 = observation.addReferenceRange();
        comp2.setText("Negative");
        Observation.ObservationReferenceRangeComponent comp3 = observation.addReferenceRange();
        comp3.setText("Indeterminate");
        Observation.ObservationReferenceRangeComponent comp4 = observation.addReferenceRange();
        comp4.setText("Equivocal");

        // Set the meta attribute
        Meta meta = observation.getMeta();
        meta.setLastUpdated(new Date());

        // Give the report a status
        observation.setStatus(Observation.ObservationStatus.FINAL);

        // Set the subject
        Reference subject = observation.getSubject();
        setPatientReference(connectedTest.getBasicInfo().getPatientId(), subject);

        // Set the Device
        Reference deviceReference = observation.getDevice();
        deviceReference.setReference(connectedTest.getTestDetails().getDeviceIdentifier());

        return observation;
    }

    private DiagnosticReport createDiagnosticReport(ConnectedTest connectedTest) {

        // Create an DiagnosticReport instance
        DiagnosticReport diagnosticReport = new DiagnosticReport();

        // Set the category for the report
        CodeableConcept codeableConcept = diagnosticReport.addCategory();
        codeableConcept.setText("Report Category");

        // Give the diagnostic report a code
        Coding coding = diagnosticReport.getCode().addCoding();
        coding.setCode(connectedTest.getTestDetails().getLoincCode().getCode())
                .setSystem(LOINC_URL)
                .setDisplay(connectedTest.getTestDetails().getLoincCode().getDisplay());

        // Set the Identifiers
        diagnosticReport.addIdentifier(setIdentifier(diagnosticReport, connectedTest));

        // Set the meta attribute
        Meta meta = diagnosticReport.getMeta();
        meta.setLastUpdated(new Date());

        // Set the observation reference
        Reference reference = diagnosticReport.addResult();
        reference.setReference(connectedTest.getBasicInfo().getId());

        // Give the report a status
        diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.FINAL);

        // Set the subject
        Reference subject = diagnosticReport.getSubject();
        setPatientReference(connectedTest.getBasicInfo().getPatientId(), subject);

        return diagnosticReport;
    }

    private Patient getPatientInfo( OpenCDXProfileModel patientInfo) {

        Patient patient = new Patient();

        patient.setId(patientInfo.getId().toHexString());
        patient.setActive(patientInfo.isActive());

        FullName fullName = patientInfo.getFullName();
        if (fullName != null) {
            HumanName name = new HumanName();
            name.setFamily(patientInfo.getFullName().getLastName());
            List<StringType> givenList = Arrays.asList(
                    new StringType(patientInfo.getFullName().getFirstName()),
                    new StringType(" "),
                    new StringType(patientInfo.getFullName().getMiddleName()));
            name.setGiven(givenList);
            name.setSuffix(List.of(new StringType(patientInfo.getFullName().getSuffix())));
            patient.setName(List.of(name));
        }

        ContactInfo primaryContact = patientInfo.getPrimaryContactInfo();
        if (primaryContact != null) {
            List<ContactPoint> telecomList = new ArrayList<>();
            AtomicInteger rank = new AtomicInteger(1);
            primaryContact.getPhoneNumbersList().stream()
                    .filter(phone -> phone.getType().equals(PhoneType.PHONE_TYPE_MOBILE))
                    .findFirst()
                    .ifPresent(phone -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.PHONE)
                            .setUse(ContactPoint.ContactPointUse.MOBILE)
                            .setValue(phone.getNumber())
                            .setRank(rank.getAndIncrement())));
            primaryContact.getPhoneNumbersList().stream()
                    .filter(phone -> phone.getType().equals(PhoneType.PHONE_TYPE_HOME))
                    .findFirst()
                    .ifPresent(phone -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.PHONE)
                            .setUse(ContactPoint.ContactPointUse.HOME)
                            .setValue(phone.getNumber())
                            .setRank(rank.getAndIncrement())));

            primaryContact.getPhoneNumbersList().stream()
                    .filter(phone -> phone.getType().equals(PhoneType.PHONE_TYPE_WORK))
                    .findFirst()
                    .ifPresent(phone -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.PHONE)
                            .setUse(ContactPoint.ContactPointUse.WORK)
                            .setValue(phone.getNumber())
                            .setRank(rank.getAndIncrement())));
            primaryContact.getPhoneNumbersList().stream()
                    .filter(phone -> phone.getType().equals(PhoneType.PHONE_TYPE_FAX))
                    .findFirst()
                    .ifPresent(phone -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.FAX)
                            .setValue(phone.getNumber())
                            .setRank(rank.getAndIncrement())));
            primaryContact.getEmailsList().stream()
                    .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_PERSONAL))
                    .findFirst()
                    .ifPresent(email -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                            .setUse(ContactPoint.ContactPointUse.HOME)
                            .setValue(email.getEmail())
                            .setRank(rank.getAndIncrement())));
            primaryContact.getEmailsList().stream()
                    .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_WORK))
                    .findFirst()
                    .ifPresent(email -> telecomList.add(new ContactPoint()
                            .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                            .setUse(ContactPoint.ContactPointUse.WORK)
                            .setValue(email.getEmail())
                            .setRank(rank.getAndIncrement())));
            patient.setTelecom(telecomList);
        }

        if (patientInfo.getGender() != null) {
            patient.setGender(Enumerations.AdministrativeGender.fromCode(
                    patientInfo.getGender().name().replace("GENDER_", "").toLowerCase()));
        }

        if (patientInfo.getAddresses() != null && !patientInfo.getAddresses().isEmpty()) {
            patientInfo.getAddresses().forEach(a -> {
                if (a.getAddressPurpose().equals(AddressPurpose.PRIMARY)) {
                    patient.addAddress(new Address()
                            .setUse(Address.AddressUse.HOME)
                            .setType(Address.AddressType.POSTAL)
                            .setLine(List.of(new StringType(a.getAddress1())))
                            .setCity(a.getCity())
                            .setState(a.getState())
                            .setPostalCode(a.getPostalCode())
                            .setCountry(a.getCountryId()));
                }
            });
        }

        return patient;
    }

    private void setPatientReference(String patientId, Reference reference) {
        // Set the patient
        reference.setReference("Patient/" + patientId);
    }

    private Identifier setIdentifier(DomainResource resource, ConnectedTest connectedTest) {
        // Set the ID
        resource.setId(connectedTest.getBasicInfo().getId());

        // Set the Identifiers
        Identifier identifier = new Identifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        identifier.setValue(connectedTest.getBasicInfo().getId());
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        typeCoding.setDisplay(connectedTest.getBasicInfo().getId());

        return identifier;
    }

    private Bundle createBundle(
            Patient patient, Device device, Observation observation, DiagnosticReport diagnosticReport) {
        Bundle bundle = new Bundle();

        bundle.setId(observation.getId());
        bundle.setType(Bundle.BundleType.MESSAGE);
        bundle.setTimestamp(new Date());

        Meta meta = bundle.getMeta();
        meta.setLastUpdated(new Date());

        Bundle.BundleEntryComponent patientEntry = bundle.addEntry();
        patientEntry.setFullUrl("https://fhir.org/fhir/Patient/" + patient.getId());
        patientEntry.setResource(patient);

        Bundle.BundleEntryComponent deviceEntry = bundle.addEntry();
        deviceEntry.setFullUrl("https://fhir.org/fhir/Device/" + device.getId());
        deviceEntry.setResource(device);

        Bundle.BundleEntryComponent observationEntry = bundle.addEntry();
        observationEntry.setFullUrl("https://fhir.org/fhir/Observation/" + observation.getId());
        observationEntry.setResource(observation);

        Bundle.BundleEntryComponent diagnosticReportEntry = bundle.addEntry();
        diagnosticReportEntry.setFullUrl("https://fhir.org/fhir/DiagnosticReport/" + diagnosticReport.getId());
        diagnosticReportEntry.setResource(diagnosticReport);

        return bundle;
    }

    private void sendMessage(Bundle bundle) {
        IParser parser = FhirContext.forR4().newJsonParser().setPrettyPrint(true);
        String cdcPayload = parser.encodeResourceToString(bundle);
        log.debug("Sending CDC Payload Event: {}", cdcPayload);
        this.openCDXMessageService.send(OpenCDXMessageService.CDC_MESSAGE_SUBJECT, cdcPayload);
    }
}
