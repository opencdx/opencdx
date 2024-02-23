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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import cdx.opencdx.grpc.connected.BasicInfo;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.connected.OrderableTestResult;
import cdx.opencdx.grpc.connected.TestDetails;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.Manufacturer;
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
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXMessageService openCDXMessageService;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    private OpenCDXCDCPayloadService openCDXCDCPayloadService;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
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
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        openCDXCDCPayloadService = new OpenCDXCDCPayloadServiceImpl(
                openCDXConnectedTestRepository,
                openCDXProfileRepository,
                openCDXDeviceRepository,
                openCDXManufacturerRepository,
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

        OpenCDXConnectedTestModel openCDXConnectedTestModel = createTest(testId, patientId, deviceId);
        OpenCDXProfileModel openCDXProfileModel = createUser1(patientId);
        openCDXProfileModel.setActive(true);
        OpenCDXDeviceModel openCDXDeviceModel = createDevice(deviceId, manufacturerId, vendorId, countryId);
        OpenCDXManufacturerModel openCDXManufacturerModel = new OpenCDXManufacturerModel(Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build());

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));

        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.of(openCDXDeviceModel));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.of(openCDXManufacturerModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXDeviceRepository).findById(new ObjectId(deviceId));
        verify(openCDXManufacturerRepository).findById(new ObjectId(manufacturerId));
    }

    @Test
    void testSendCDCPayloadMessage_2() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

        OpenCDXConnectedTestModel openCDXConnectedTestModel = createTest(testId, patientId, deviceId);
        OpenCDXProfileModel openCDXProfileModel = createUser2(patientId);
        openCDXProfileModel.setActive(true);
        OpenCDXDeviceModel openCDXDeviceModel = createDevice(deviceId, manufacturerId, vendorId, countryId);
        OpenCDXManufacturerModel openCDXManufacturerModel = new OpenCDXManufacturerModel(Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build());

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.of(openCDXDeviceModel));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.of(openCDXManufacturerModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXDeviceRepository).findById(new ObjectId(deviceId));
        verify(openCDXManufacturerRepository).findById(new ObjectId(manufacturerId));
    }

    @Test
    void testSendCDCPayloadMessage2() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();

        OpenCDXConnectedTestModel openCDXConnectedTestModel = createTest(testId, patientId, deviceId);
        openCDXConnectedTestModel.setTestDetails(
                TestDetails.newBuilder().setDeviceIdentifier(deviceId).build());
        OpenCDXProfileModel openCDXProfileModel = createUser2(patientId);
        openCDXProfileModel.setGender(null);
        openCDXProfileModel.setActive(true);
        openCDXProfileModel.setAddresses(null);
        openCDXProfileModel.setId(new ObjectId(patientId));
        OpenCDXDeviceModel openCDXDeviceModel = createDevice(deviceId, manufacturerId, vendorId, countryId);
        openCDXDeviceModel.setExpiryDate(null);
        OpenCDXManufacturerModel openCDXManufacturerModel = new OpenCDXManufacturerModel(Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build());

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(openCDXProfileModel));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.of(openCDXDeviceModel));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.of(openCDXManufacturerModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXDeviceRepository).findById(new ObjectId(deviceId));
        verify(openCDXManufacturerRepository).findById(new ObjectId(manufacturerId));
    }

    @Test
    void testSendCDCPayloadMessagePatientNotFound() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(createTest(testId, patientId, deviceId)));
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find patient: " + patientId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
    }

    @Test
    void testSendCDCPayloadMessageTestNotFound() {
        String testId = ObjectId.get().toHexString();
        when(openCDXConnectedTestRepository.findById(new ObjectId(testId))).thenReturn(Optional.empty());
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find connected test: " + testId);
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

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(createTest(testId, patientId, deviceId)));
        when(openCDXProfileRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(patient));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find device: " + deviceId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
        verify(openCDXDeviceRepository).findById(new ObjectId(deviceId));
    }

    @Test
    void testSendCDCPayloadMessageManufacturerNotFound() {
        String testId = ObjectId.get().toHexString();
        String patientId = ObjectId.get().toHexString();
        String deviceId = ObjectId.get().toHexString();
        String manufacturerId = ObjectId.get().toHexString();
        String vendorId = ObjectId.get().toHexString();
        String countryId = ObjectId.get().toHexString();
        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(createTest(testId, patientId, deviceId)));
        when(openCDXProfileRepository.findById(new ObjectId(patientId)))
                .thenReturn(Optional.of(createUser4(patientId)));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId)))
                .thenReturn(Optional.of(createDevice(deviceId, manufacturerId, vendorId, countryId)));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find manufacturer: " + manufacturerId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXProfileRepository).findById(new ObjectId(patientId));
        verify(openCDXDeviceRepository).findById(new ObjectId(deviceId));
        verify(openCDXManufacturerRepository).findById(new ObjectId(manufacturerId));
    }

    private OpenCDXConnectedTestModel createTest(String testId, String userId, String deviceId) {
        return new OpenCDXConnectedTestModel(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId(testId)
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setUserId(userId)
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(deviceId)
                        .addOrderableTestResults(OrderableTestResult.newBuilder()
                                .setTestResult("POSITIVE")
                                .build())
                        .build())
                .build());
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

    private OpenCDXDeviceModel createDevice(String deviceId, String manufacturerId, String vendorId, String countryId) {
        return new OpenCDXDeviceModel(Device.newBuilder()
                .setId(deviceId)
                .setManufacturerId(manufacturerId)
                .setManufacturerCountryId(countryId)
                .setVendorCountryId(countryId)
                .setVendorId(vendorId)
                .setExpiryDate(Timestamp.newBuilder().setSeconds(1696732104))
                .build());
    }
}
