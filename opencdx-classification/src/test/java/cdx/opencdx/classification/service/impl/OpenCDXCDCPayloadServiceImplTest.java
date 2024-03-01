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

import cdx.opencdx.classification.service.OpenCDXCDCPayloadService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXDeviceClient;
import cdx.opencdx.client.service.OpenCDXManufacturerClient;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.connected.*;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import com.google.protobuf.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
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
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        openCDXCDCPayloadService = new OpenCDXCDCPayloadServiceImpl(
                openCDXConnectedTestClient,
                openCDXDeviceClient,
                openCDXManufacturerClient,
                openCDXProfileRepository,
                openCDXCurrentUser,
                openCDXMessageService);
    }

    @Test
    void testSendCDCPayloadMessage() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

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

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();
        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessage2() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

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

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();
        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessage3() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        OpenCDXProfileModel openCDXProfileModel = createUser2(patientId);
        openCDXProfileModel.setGender(null);
        openCDXProfileModel.setActive(true);
        openCDXProfileModel.setAddresses(null);
        openCDXProfileModel.setId(new ObjectId(patientId));

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
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(openCDXProfileModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXCurrentUser).getCurrentUserAccessToken();
        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXManufacturerClient)
                .getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessagePatientNotFound() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find patient: " + patientId);

        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
    }

    @Test
    void testSendCDCPayloadMessageTestNotFound() {
        String testId = ObjectId.get().toHexString();
        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenThrow(OpenCDXClientException.class);

        Assertions.assertThrows(
                OpenCDXClientException.class, () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId));
    }

    @Test
    void testSendCDCPayloadMessageDeviceNotFound() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();

        OpenCDXProfileModel patient = createUser3(patientId);
        patient.setFullName(null);
        patient.setPrimaryContactInfo(null);
        patient.setId(new ObjectId(patientId));

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(patient));
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenThrow(OpenCDXClientException.class);

        Assertions.assertThrows(
                OpenCDXClientException.class, () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId));

        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
        verify(openCDXDeviceClient)
                .getDeviceById(Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
    }

    @Test
    void testSendCDCPayloadMessageManufacturerNotFound() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

        ConnectedTest connectedTest = createTest(testId, patientId, deviceId);

        when(openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(connectedTest);

        Device deviceInfo = createDevice(deviceId, manufacturerId, vendorId, countryId);

        OpenCDXProfileModel openCDXProfileModel = createUser1(patientId);
        openCDXProfileModel.setActive(true);

        when(openCDXProfileRepository.findById(new ObjectId(patientId)))
                .thenReturn(Optional.of(createUser4(patientId)));
        when(openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(deviceInfo);
        when(openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenThrow(OpenCDXClientException.class);

        Assertions.assertThrows(
                OpenCDXClientException.class, () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId));

        verify(openCDXConnectedTestClient)
                .getTestDetailsById(Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
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
        openCDXProfileModel.setGender(Gender.GENDER_MALE);
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
        openCDXProfileModel.setGender(Gender.GENDER_MALE);
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
        openCDXProfileModel.setGender(Gender.GENDER_MALE);
        openCDXProfileModel.setAddresses(Collections.emptyList());

        return openCDXProfileModel;
    }

    private OpenCDXProfileModel createUser4(String userId) {
        OpenCDXProfileModel openCDXProfileModel = new OpenCDXProfileModel();
        openCDXProfileModel.setUserId(new ObjectId(userId));
        openCDXProfileModel.setId(new ObjectId(userId));
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
        openCDXProfileModel.setGender(Gender.GENDER_MALE);

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
