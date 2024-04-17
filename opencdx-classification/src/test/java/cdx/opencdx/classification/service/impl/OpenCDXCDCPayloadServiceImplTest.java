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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.service.OpenCDXCDCPayloadService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXDeviceClient;
import cdx.opencdx.client.service.OpenCDXManufacturerClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.TestIdRequest;
import cdx.opencdx.grpc.service.logistics.DeviceIdRequest;
import cdx.opencdx.grpc.service.logistics.ManufacturerIdRequest;
import cdx.opencdx.grpc.types.AddressPurpose;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.Gender;
import cdx.opencdx.grpc.types.PhoneType;
import com.google.protobuf.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXCDCPayloadServiceImplTest {

    @Mock
    OpenCDXConnectedTestClient openCDXConnectedTestClient;

    @Mock
    OpenCDXDeviceClient openCDXDeviceClient;

    @Mock
    OpenCDXManufacturerClient openCDXManufacturerClient;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMessageService openCDXMessageService;

    private OpenCDXCDCPayloadService openCDXCDCPayloadService;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        openCDXCDCPayloadService = new OpenCDXCDCPayloadServiceImpl(
                openCDXDeviceClient, openCDXManufacturerClient, openCDXCurrentUser, openCDXMessageService);
    }

    @Test
    void testSendCDCPayloadMessage() {
        String testId = OpenCDXIdentifier.get().toHexString();
        String patientId = OpenCDXIdentifier.get().toHexString();
        String deviceId = OpenCDXIdentifier.get().toHexString();
        String manufacturerId = OpenCDXIdentifier.get().toHexString();
        String vendorId = OpenCDXIdentifier.get().toHexString();
        String countryId = OpenCDXIdentifier.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        OpenCDXProfileModel openCDXProfileModel = createUser1(patientId);
        openCDXProfileModel.setActive(true);

        Device deviceInfo = createDevice(deviceId, manufacturerId, vendorId, countryId);
        Manufacturer manufacturerInfo = Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build();
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("ACCESS_TOKEN");
        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(deviceInfo);
        when(openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(manufacturerInfo);

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setConnectedTest(connectedTest);
        model.setPatient(openCDXProfileModel);

        openCDXCDCPayloadService.sendCDCPayloadMessage(model);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessage2() {
        String testId = OpenCDXIdentifier.get().toHexString();
        String patientId = OpenCDXIdentifier.get().toHexString();
        String deviceId = OpenCDXIdentifier.get().toHexString();
        String manufacturerId = OpenCDXIdentifier.get().toHexString();
        String vendorId = OpenCDXIdentifier.get().toHexString();
        String countryId = OpenCDXIdentifier.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        OpenCDXProfileModel openCDXProfileModel = createUser2(patientId);
        openCDXProfileModel.setActive(true);

        Device deviceInfo = createDevice(deviceId, manufacturerId, vendorId, countryId);
        Manufacturer manufacturerInfo = Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build();

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(deviceInfo);
        when(openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(manufacturerInfo);

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setConnectedTest(connectedTest);
        model.setPatient(openCDXProfileModel);

        openCDXCDCPayloadService.sendCDCPayloadMessage(model);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessage3() {
        String testId = OpenCDXIdentifier.get().toHexString();
        String patientId = OpenCDXIdentifier.get().toHexString();
        String deviceId = OpenCDXIdentifier.get().toHexString();
        String manufacturerId = OpenCDXIdentifier.get().toHexString();
        String vendorId = OpenCDXIdentifier.get().toHexString();
        String countryId = OpenCDXIdentifier.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        OpenCDXProfileModel openCDXProfileModel = createUser2(patientId);
        openCDXProfileModel.setActive(true);
        openCDXProfileModel.setAddresses(null);
        openCDXProfileModel.setId(new OpenCDXIdentifier(patientId));

        Device deviceInfo = createDevice(deviceId, manufacturerId, vendorId, countryId);
        // deviceInfo.
        Manufacturer manufacturerInfo = Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build();

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(deviceInfo);
        when(openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(manufacturerInfo);
        when(openCDXProfileRepository.findById(new OpenCDXIdentifier(patientId)))
                .thenReturn(Optional.of(openCDXProfileModel));

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setConnectedTest(connectedTest);
        model.setPatient(openCDXProfileModel);

        openCDXCDCPayloadService.sendCDCPayloadMessage(model);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();

        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessageDeviceNotFound() {
        String testId = OpenCDXIdentifier.get().toHexString();
        String patientId = OpenCDXIdentifier.get().toHexString();
        String deviceId = OpenCDXIdentifier.get().toHexString();

        OpenCDXProfileModel patient = createUser3(patientId);
        patient.setFullName(null);
        patient.setPrimaryContactInfo(null);
        patient.setId(new OpenCDXIdentifier(patientId));

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXProfileRepository.findById(new OpenCDXIdentifier(patientId)))
                .thenReturn(Optional.of(patient));
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenThrow(OpenCDXClientException.class);

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setConnectedTest(connectedTest);
        model.setPatient(patient);

        Assertions.assertThrows(
                OpenCDXClientException.class, () -> openCDXCDCPayloadService.sendCDCPayloadMessage(model));

        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessageManufacturerNotFound() {
        String testId = OpenCDXIdentifier.get().toHexString();
        String patientId = OpenCDXIdentifier.get().toHexString();
        String deviceId = OpenCDXIdentifier.get().toHexString();
        String manufacturerId = OpenCDXIdentifier.get().toHexString();
        String vendorId = OpenCDXIdentifier.get().toHexString();
        String countryId = OpenCDXIdentifier.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);

        Device deviceInfo = createDevice(deviceId, manufacturerId, vendorId, countryId);

        OpenCDXProfileModel openCDXProfileModel = createUser1(patientId);
        openCDXProfileModel.setActive(true);

        when(openCDXProfileRepository.findById(new OpenCDXIdentifier(patientId)))
                .thenReturn(Optional.of(createUser4(patientId)));
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(deviceInfo);
        when(openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenThrow(OpenCDXClientException.class);

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setConnectedTest(connectedTest);
        model.setPatient(openCDXProfileModel);

        Assertions.assertThrows(
                OpenCDXClientException.class, () -> openCDXCDCPayloadService.sendCDCPayloadMessage(model));

        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    private ConnectedTest createTest(String testId, String userId, String deviceId) {
        return ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId(testId)
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setPatientId(userId)
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(deviceId)
                        .addOrderableTestResults(OrderableTestResult.newBuilder()
                                .setTestResult("POSITIVE")
                                .build())
                        .build())
                .build();
    }

    private OpenCDXProfileModel createUser1(String userId) {
        OpenCDXProfileModel openCDXProfileModel = new OpenCDXProfileModel();
        openCDXProfileModel.setId(new OpenCDXIdentifier(userId));
        openCDXProfileModel.setFullName(FullName.newBuilder()
                .setFirstName("Adam")
                .setMiddleName("Charles")
                .setLastName("Smith")
                .setSuffix("Sr")
                .build());
        openCDXProfileModel.setPrimaryContactInfo(ContactInfo.newBuilder()
                .addAllPhoneNumbers(List.of(
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .setNumber("111-111-1111")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_HOME)
                                .setNumber("222-222-2222")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("333-333-3333")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_FAX)
                                .setNumber("333-333-3333")
                                .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_PERSONAL)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .build());
        openCDXProfileModel.setDemographics(
                Demographics.newBuilder().setGender(Gender.GENDER_FEMALE).build());
        openCDXProfileModel.setAddresses(List.of(Address.newBuilder()
                .setAddress1("123 Main St")
                .setCity("Vienna")
                .setState("VA")
                .setPostalCode("22182")
                .setCountryId("USA")
                .setAddressPurpose(AddressPurpose.PRIMARY)
                .build()));

        return openCDXProfileModel;
    }

    private OpenCDXProfileModel createUser2(String userId) {
        OpenCDXProfileModel openCDXProfileModel = new OpenCDXProfileModel();
        openCDXProfileModel.setId(new OpenCDXIdentifier(userId));
        openCDXProfileModel.setFullName(FullName.newBuilder()
                .setFirstName("Adam")
                .setMiddleName("Charles")
                .setLastName("Smith")
                .setSuffix("Sr")
                .build());
        openCDXProfileModel.setPrimaryContactInfo(ContactInfo.newBuilder()
                .addAllPhoneNumbers(List.of(
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .setNumber("111-111-1111")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_HOME)
                                .setNumber("222-222-2222")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("333-333-3333")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_FAX)
                                .setNumber("333-333-3333")
                                .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_PERSONAL)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .build());
        openCDXProfileModel.setDemographics(
                Demographics.newBuilder().setGender(Gender.GENDER_FEMALE).build());
        openCDXProfileModel.setAddresses(List.of(Address.newBuilder()
                .setAddress1("123 Main St")
                .setCity("Vienna")
                .setState("VA")
                .setPostalCode("22182")
                .setCountryId("USA")
                .setAddressPurpose(AddressPurpose.BILLING)
                .build()));

        return openCDXProfileModel;
    }

    private OpenCDXProfileModel createUser3(String userId) {
        OpenCDXProfileModel openCDXProfileModel = new OpenCDXProfileModel();
        openCDXProfileModel.setFullName(FullName.newBuilder()
                .setFirstName("Adam")
                .setMiddleName("Charles")
                .setLastName("Smith")
                .setSuffix("Sr")
                .build());
        openCDXProfileModel.setPrimaryContactInfo(ContactInfo.newBuilder()
                .addAllPhoneNumbers(List.of(
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .setNumber("111-111-1111")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_HOME)
                                .setNumber("222-222-2222")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("333-333-3333")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_FAX)
                                .setNumber("333-333-3333")
                                .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_PERSONAL)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .build());
        openCDXProfileModel.setDemographics(
                Demographics.newBuilder().setGender(Gender.GENDER_FEMALE).build());
        openCDXProfileModel.setAddresses(Collections.emptyList());

        return openCDXProfileModel;
    }

    private OpenCDXProfileModel createUser4(String userId) {
        OpenCDXProfileModel openCDXProfileModel = new OpenCDXProfileModel();
        openCDXProfileModel.setUserId(new OpenCDXIdentifier(userId));
        openCDXProfileModel.setId(new OpenCDXIdentifier(userId));
        openCDXProfileModel.setFullName(FullName.newBuilder()
                .setFirstName("Adam")
                .setMiddleName("Charles")
                .setLastName("Smith")
                .setSuffix("Sr")
                .build());
        openCDXProfileModel.setPrimaryContactInfo(ContactInfo.newBuilder()
                .addAllPhoneNumbers(List.of(
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .setNumber("111-111-1111")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_HOME)
                                .setNumber("222-222-2222")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("333-333-3333")
                                .build(),
                        PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_FAX)
                                .setNumber("333-333-3333")
                                .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .addAllEmails(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_PERSONAL)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .build());
        openCDXProfileModel.setDemographics(
                Demographics.newBuilder().setGender(Gender.GENDER_FEMALE).build());

        return openCDXProfileModel;
    }

    private Device createDevice(String deviceId, String manufacturerId, String vendorId, String countryId) {
        return Device.newBuilder()
                .setId(deviceId)
                .setManufacturerId(manufacturerId)
                .setManufacturerCountryId(countryId)
                .setVendorCountryId(countryId)
                .setVendorId(vendorId)
                .setExpiryDate(Timestamp.newBuilder().setSeconds(1696732104))
                .build();
    }
}
