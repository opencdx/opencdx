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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.service.OpenCDXCDCPayloadService;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.connected.TestIdRequest;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.profile.ContactInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenCDXCDCPayloadServiceImpl implements OpenCDXCDCPayloadService {

    private static final String LOINC_URL = "https://loinc.org";

    private static final String DOMAIN = "OpenCDXCDCPayloadServiceImpl";

    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    private final OpenCDXDeviceRepository openCDXDeviceRepository;

    public OpenCDXCDCPayloadServiceImpl (
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository) {
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
    }

    public void getCDCPayload(String testId) {

        // retrieve the connected test using the refId
        ConnectedTest connectedTest = this.openCDXConnectedTestRepository
                .findById(new ObjectId(testId))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 3, "Failed to find connected test: " + testIdRequest.getTestId()))
                .getProtobufMessage();

        // Create Device
        Device device = createDevice(connectedTest);

        // Create Observation
        Observation observation = createObservation(connectedTest, device.getId());

        // Create DiagnosticReport
        DiagnosticReport diagnosticReport = createDiagnosticReport(connectedTest, observation.getId());

        // Retrieve Patient
        String patientId = connectedTest.getBasicInfo().getUserId();
        Patient patient = getPatientInfo(patientId);

        // Create Bundle
        Bundle bundle = createBundle(patient, device, observation, diagnosticReport);
        // log.debug(parser.encodeResourceToString(bundle));
        log.debug(bundle.toString());

        // Send to CDC
        sendToCDC(bundle);
    }

    private Device createDevice(ConnectedTest connectedTest) throws IOException, URISyntaxException {
        // retrieve the connected test using the refId
        Device device = this.openCDXDeviceRepository
                .findById(new ObjectId(connectedTest.getTestDetails().getDeviceIdentifier()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 3, "Failed to find device: " + connectedTest.getTestDetails().getDeviceIdentifier()))
                .getProtobufMessage();
        return null;
    }

    private Observation createObservation(ConnectedTest entity, String deviceId)
            throws IOException, URISyntaxException {
        String input = createResource(entity, "Observation", deviceId);
        // return parser.parseResource(Observation.class, input);
        return null;
    }

    private DiagnosticReport createDiagnosticReport(ConnectedTest entity, String observationId)
            throws IOException, URISyntaxException {
        String input = createResource(entity, "DiagnosticReport", observationId);
        // return parser.parseResource(DiagnosticReport.class, input);
        return null;
    }

    private String createResource(ConnectedTest entity, String resourceType, String refId)
            throws IOException, URISyntaxException {

        // retrieve the connected test using the refId
        //    OpenCDXCallCredentials credential = new
        // OpenCDXCallCredentials(this.jwtTokenUtil.generateAccessToken(model)));
        //    TestIdRequest request = TestIdRequest.newBuilder().setTestId(refId).build();
        //    openCDXConnectedTestClient.getTestDetailsById(request, credential);
        return "";
    }

    private Patient getPatientInfo(String patientId) {

        // Retrieve the patient Info from the database
        OpenCDXIAMUserModel user = this.openCDXIAMUserRepository
                .findById(new ObjectId(patientId))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, "Failed to find patient: " + patientId));

        Patient patient = new Patient();

        patient.setId(user.getId().toHexString());
        patient.setActive(user.getStatus() == IamUserStatus.IAM_USER_STATUS_ACTIVE);

        HumanName name = new HumanName();
        name.setFamily(user.getFullName().getLastName());
        List<StringType> givenList = Arrays.asList(
                new StringType(user.getFullName().getFirstName()),
                new StringType(" "),
                new StringType(user.getFullName().getMiddleName()));
        name.setGiven(givenList);
        name.setSuffix(List.of(new StringType(user.getFullName().getSuffix())));
        patient.setName(List.of(name));

        ContactInfo primaryContact = user.getPrimaryContactInfo();
        List<ContactPoint> telecomList = new ArrayList<>();
        telecomList.add(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setUse(ContactPoint.ContactPointUse.MOBILE)
                .setValue(primaryContact.getMobileNumber().getNumber())
                .setRank(1));
        telecomList.add(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setUse(ContactPoint.ContactPointUse.HOME)
                .setValue(primaryContact.getHomeNumber().getNumber())
                .setRank(2));
        telecomList.add(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setUse(ContactPoint.ContactPointUse.WORK)
                .setValue(primaryContact.getWorkNumber().getNumber())
                .setRank(3));
        telecomList.add(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.FAX)
                .setValue(primaryContact.getFaxNumber().getNumber())
                .setRank(4));
        telecomList.add(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                .setValue(primaryContact.getEmail()));
        patient.setTelecom(telecomList);

        return patient;
    }

    private StringEntity createResourceJson(ConnectedTest entity, String resourceType, String refId)
            throws UnsupportedEncodingException {
        return null;
        //    return switch (resourceType) {
        //      case "DiagnosticReport" -> new
        // StringEntity(parser.encodeResourceToString(extractDiagnosticReport(entity, refId)));
        //      case "Observation" -> new StringEntity(parser.encodeResourceToString(extractObservation(entity,
        // refId)));
        //      case "Device" -> new StringEntity(parser.encodeResourceToString(extractDevice(entity)));
        //      default -> null;
        //    };
    }

    private String extractId(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        return obj.getString("id");
    }

    private DiagnosticReport extractDiagnosticReport(ConnectedTest entity, String observationId) {

        //
        // ExternalConnectedTest data = entity.get

        // Create an DiagnosticReport instance
        DiagnosticReport diagnosticReport = new DiagnosticReport();

        // Set the category for the report
        CodeableConcept codeableConcept = diagnosticReport.addCategory();
        codeableConcept.setText("Report Category");

        // Give the diagnostic report a code
        Coding coding = diagnosticReport.getCode().addCoding();
        // coding.setCode(data.getLoincCode().getCode()).setSystem(LOINC_URL).setDisplay(data.getLoincCode().getDisplay());

        // Set the ID
        // diagnosticReport.setId(entity.getExternalEvent().getId());

        // Set the Identifiers
        Identifier identifier = diagnosticReport.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        // identifier.setValue(entity.getExternalEvent().getId());
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        // typeCoding.setDisplay(entity.getExternalEvent().getId());

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
        // subject.setDisplay("PATIENT NAME");
        // subject.setReference("Patient/" + data.getPatientId());

        return diagnosticReport;
    }

    private Observation extractObservation(ConnectedTest entity, String deviceId) {

        // ExternalConnectedTest data = entity.getExternalEvent().getData();

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
        // coding.setCode(data.getLoincCode().getCode()).setSystem(LOINC_URL).setDisplay(data.getLoincCode().getDisplay());

        // Set the ID
        // observation.setId(entity.getExternalEvent().getId());

        // Set the Identifiers
        Identifier identifier = observation.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        // identifier.setValue(entity.getExternalEvent().getId());
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        // typeCoding.setDisplay(entity.getExternalEvent().getId());

        // Set the value
        // observation.setValue(new StringType(data.getResult().getOutcome().toString()));

        // Set the result interpretation
        //    CodeableConcept interpretation = observation.addInterpretation();
        //    interpretation.setText(data.getResult().getOutcome().toString());
        //    Coding interpretationCoding = interpretation.addCoding();
        //    interpretationCoding.setCode("Positive");
        //    interpretationCoding.setDisplay("Positive");
        //
        // interpretationCoding.setSystem("https://terminology.hl7.org/ValueSet/v3-ObservationInterpretationDetected");

        // Set the Performer
        //    Reference reference = observation.addPerformer();
        //    //reference.setDisplay("PERFORMER NAME");
        //    reference.setReference("Practitioner/" + data.getProviderId());

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
        // subject.setDisplay("PATIENT NAME");
        // subject.setReference("Patient/" + data.getPatientId());

        // Set the Device
        Reference deviceReference = observation.getDevice();
        deviceReference.setReference(deviceId);

        return observation;
    }

    private Device extractDevice(ConnectedTest entity) {

        // ExternalConnectedTest data = entity.getExternalEvent().getData();

        // Create a Device instance
        Device device = new Device();

        // Set the category for the report
        Device.DeviceDeviceNameComponent deviceName = device.addDeviceName();
        // deviceName.setName(data.getDevice().getName());
        deviceName.setType(Device.DeviceNameType.USERFRIENDLYNAME);

        // device.setDistinctIdentifier();
        device.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1L)));
        //    device.setLotNumber(data.getDevice().getLotNumber());
        //    device.setSerialNumber(data.getDevice().getSerialNumber());
        //    device.setManufacturerElement(new StringType(data.getDevice().getManufacturer()));
        //    device.setModelNumber(data.getDevice().getModelNumber());
        device.setStatus(Device.FHIRDeviceStatus.ACTIVE);

        // Set the patient
        Reference subject = device.getPatient();
        // subject.setDisplay("PATIENT NAME");
        // subject.setReference("Patient/" + data.getPatientId());

        // Set the Type
        Coding coding = device.getType().addCoding();
        // coding.setDisplay(data.getDevice().getName());
        // device.getType().setText(data.getDevice().getShortDescription());

        // Set the meta attribute
        Meta meta = device.getMeta();
        meta.setLastUpdated(new Date());

        // Set the Identifiers
        Identifier identifier = device.addIdentifier();
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        // identifier.setValue(entity.getExternalEvent().getId());
        Coding typeCoding = identifier.getType().addCoding();
        typeCoding.setCode("FILL");
        // typeCoding.setDisplay(entity.getExternalEvent().getId());

        // Give the report a status
        device.setStatus(Device.FHIRDeviceStatus.ACTIVE);

        return device;
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

    private void sendToCDC(Bundle bundle) throws URISyntaxException, IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String uri = "https://staging.prime.cdc.gov/api/reports";

            URIBuilder uriBuilder = new URIBuilder(uri);
            // StringEntity requestEntity = new StringEntity(parser.encodeResourceToString(bundle).replaceAll("\n",
            // ""));
            StringEntity requestEntity = new StringEntity("");

            HttpUriRequest request = RequestBuilder.post()
                    .setUri(uriBuilder.build())
                    .setEntity(requestEntity)
                    .addHeader("Accept", "application/fhir+ndjson")
                    .addHeader("Content-Type", "application/fhir+ndjson")
                    .addHeader("client", "connectathon.CON_FULL_ELR_SENDER")
                    .addHeader("x-functions-key", "CMVUUt4ySvpmasN55_Kz4Mu1SgzlaETrZbdxU41Si1NmAzFuCwiFLQ==")
                    .build();

            // Execute the request and process the results.
            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                log.error(
                        "Exception creating FHIR resource: {}\n",
                        response.getStatusLine().toString());
                responseEntity.writeTo(System.err);
                throw new RuntimeException();
            }
            log.debug("{} FHIR resource bundle sent.");
            log.debug(EntityUtils.toString(responseEntity));
        } catch (Exception e) {
            log.error("Failed sending request", e);
            throw e;
        }
    }
}
