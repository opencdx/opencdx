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
package cdx.opencdx.connected.test.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import cdx.opencdx.connected.test.model.OpenCDXManufacturerModel;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.connected.test.service.OpenCDXCDCPayloadService;
import cdx.opencdx.grpc.common.*;
import io.micrometer.observation.annotation.Observed;
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

    private static final String DOMAIN = "OpenCDXCDCPayloadServiceImpl";

    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    private final OpenCDXDeviceRepository openCDXDeviceRepository;

    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;

    private final OpenCDXProfileRepository openCDXProfileRepository;

    private final OpenCDXMessageService openCDXMessageService;

    /**
     * Constructor with OpenCDXCDCPayloadServiceImpl
     *
     * @param openCDXConnectedTestRepository Mongo Repository for OpenCDXConnectedTest
     * @param openCDXDeviceRepository        Mongo repository to look up device info
     * @param openCDXManufacturerRepository  Mongo repository to look up manufacturer info
     * @param openCDXMessageService          Message Service for sending CDC message
     * @param openCDXProfileRepository       Mongo repository to look up profile info
     */
    public OpenCDXCDCPayloadServiceImpl(
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXMessageService openCDXMessageService) {
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXMessageService = openCDXMessageService;
    }

    public void sendCDCPayloadMessage(String testId) {

        log.info(testId);

        // retrieve the connected test using the refId
        OpenCDXConnectedTestModel connectedTestModel = this.openCDXConnectedTestRepository
                .findById(new ObjectId(testId))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, "Failed to find connected test: " + testId));

        // Retrieve Patient
        String patientId = connectedTestModel.getBasicInfo().getUserId();
        Patient patient = getPatientInfo(patientId);

        // Create Device
        Device device = createDevice(connectedTestModel, patientId);

        // Create Observation
        Observation observation = createObservation(connectedTestModel, device.getId(), patientId);

        // Create DiagnosticReport
        DiagnosticReport diagnosticReport = createDiagnosticReport(connectedTestModel, observation.getId(), patientId);

        // Create Bundle
        Bundle bundle = createBundle(patient, device, observation, diagnosticReport);

        // Send to CDC
        sendMessage(bundle);
    }

    private Device createDevice(OpenCDXConnectedTestModel connectedTestModel, String patientId) {
        // retrieve the device for the connected test
        OpenCDXDeviceModel deviceModel = this.openCDXDeviceRepository
                .findById(new ObjectId(connectedTestModel.getTestDetails().getDeviceIdentifier()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        3,
                        "Failed to find device: "
                                + connectedTestModel.getTestDetails().getDeviceIdentifier()));

        // Create a Device instance
        Device device = new Device();

        // Set the category for the report
        Device.DeviceDeviceNameComponent deviceName = device.addDeviceName();
        deviceName.setName(deviceModel.getName());
        deviceName.setType(Device.DeviceNameType.USERFRIENDLYNAME);

        if (deviceModel.getExpiryDate() != null) {
            device.setExpirationDate(Date.from(deviceModel.getExpiryDate()));
        }
        device.setLotNumber(deviceModel.getBatchNumber());
        device.setSerialNumber(deviceModel.getSerialNumber());
        device.setModelNumber(deviceModel.getModel());

        OpenCDXManufacturerModel manufacturerModel = this.openCDXManufacturerRepository
                .findById(new ObjectId(String.valueOf(deviceModel.getManufacturerId())))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 3, "Failed to find manufacturer: " + deviceModel.getManufacturerId()));
        device.setManufacturerElement(new StringType(manufacturerModel.getName()));
        device.setManufactureDate(Date.from(manufacturerModel.getCreated()));

        // Set the patient
        Reference subject = device.getPatient();
        setPatientReference(patientId, subject);

        // Set the Type
        Coding coding = device.getType().addCoding();
        coding.setDisplay(deviceModel.getName());
        device.getType().setText(deviceModel.getShortDescription());

        // Set the meta attribute
        Meta meta = device.getMeta();
        meta.setLastUpdated(new Date());

        // Set the Identifiers
        Identifier identifier = device.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        typeCoding.setDisplay(String.valueOf(deviceModel.getId()));

        // Give the report a status
        device.setStatus(Device.FHIRDeviceStatus.ACTIVE);

        return device;
    }

    private Observation createObservation(
            OpenCDXConnectedTestModel connectedTestModel, String deviceId, String patientId) {
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
        coding.setCode(connectedTestModel.getTestDetails().getLoincCode().getCode())
                .setSystem(LOINC_URL)
                .setDisplay(connectedTestModel.getTestDetails().getLoincCode().getDisplay());

        // Set the ID
        observation.setId(String.valueOf(connectedTestModel.getId()));

        // Set the Identifiers
        Identifier identifier = observation.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        identifier.setValue(String.valueOf(connectedTestModel.getId()));
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        typeCoding.setDisplay(String.valueOf(connectedTestModel.getId()));

        // Set the value
        if (!connectedTestModel.getTestDetails().getOrderableTestResultsList().isEmpty()) {
            observation.setValue(new StringType(connectedTestModel
                    .getTestDetails()
                    .getOrderableTestResults(0)
                    .getTestResult()));
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
        setPatientReference(patientId, subject);

        // Set the Device
        Reference deviceReference = observation.getDevice();
        deviceReference.setReference(deviceId);

        return observation;
    }

    private DiagnosticReport createDiagnosticReport(
            OpenCDXConnectedTestModel connectedTestModel, String observationId, String patientId) {
        // Create an DiagnosticReport instance
        DiagnosticReport diagnosticReport = new DiagnosticReport();

        // Set the category for the report
        CodeableConcept codeableConcept = diagnosticReport.addCategory();
        codeableConcept.setText("Report Category");

        // Give the diagnostic report a code
        Coding coding = diagnosticReport.getCode().addCoding();
        coding.setCode(connectedTestModel.getTestDetails().getLoincCode().getCode())
                .setSystem(LOINC_URL)
                .setDisplay(connectedTestModel.getTestDetails().getLoincCode().getDisplay());

        // Set the ID
        diagnosticReport.setId(String.valueOf(connectedTestModel.getId()));

        // Set the Identifiers
        Identifier identifier = diagnosticReport.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        identifier.setValue(String.valueOf(connectedTestModel.getId()));
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        typeCoding.setDisplay(String.valueOf(connectedTestModel.getId()));

        // Set the meta attribute
        Meta meta = diagnosticReport.getMeta();
        meta.setLastUpdated(new Date());

        // Set the observation reference
        Reference reference = diagnosticReport.addResult();
        reference.setReference(observationId);

        // Give the report a status
        diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.FINAL);

        // Set the subject
        Reference subject = diagnosticReport.getSubject();
        setPatientReference(patientId, subject);

        return diagnosticReport;
    }

    private Patient getPatientInfo(String patientId) {

        // Retrieve the patient Info from the database
        OpenCDXProfileModel user = this.openCDXProfileRepository
                .findById(new ObjectId(patientId))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, "Failed to find patient: " + patientId));

        Patient patient = new Patient();

        patient.setId(user.getId().toHexString());
        patient.setActive(user.isActive());

        FullName fullName = user.getFullName();
        if (fullName != null) {
            HumanName name = new HumanName();
            name.setFamily(user.getFullName().getLastName());
            List<StringType> givenList = Arrays.asList(
                    new StringType(user.getFullName().getFirstName()),
                    new StringType(" "),
                    new StringType(user.getFullName().getMiddleName()));
            name.setGiven(givenList);
            name.setSuffix(List.of(new StringType(user.getFullName().getSuffix())));
            patient.setName(List.of(name));
        }

        ContactInfo primaryContact = user.getPrimaryContactInfo();
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

        if (user.getGender() != null) {
            patient.setGender(Enumerations.AdministrativeGender.fromCode(
                    user.getGender().name().replace("GENDER_", "").toLowerCase()));
        }

        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
            user.getAddresses().forEach(a -> {
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
        log.info("Sending CDC Payload Event: {}", cdcPayload);
        this.openCDXMessageService.send(OpenCDXMessageService.CDC_MESSAGE_SUBJECT, cdcPayload);
    }
}
