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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
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
import cdx.opencdx.grpc.iam.IamUser;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.profile.Gender;
import com.google.protobuf.Timestamp;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXCDCPayloadServiceImplTest {

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXMessageService openCDXMessageService;

    private OpenCDXCDCPayloadService openCDXCDCPayloadService;

    @BeforeEach
    void setUp() {
        openCDXCDCPayloadService = new OpenCDXCDCPayloadServiceImpl(
                openCDXConnectedTestRepository,
                openCDXIAMUserRepository,
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
        OpenCDXIAMUserModel openCDXIAMUserModel = createUser(patientId);
        openCDXIAMUserModel.setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE);
        OpenCDXDeviceModel openCDXDeviceModel = createDevice(deviceId, manufacturerId, vendorId, countryId);
        OpenCDXManufacturerModel openCDXManufacturerModel = new OpenCDXManufacturerModel(Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build());

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        when(openCDXIAMUserRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(openCDXIAMUserModel));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.of(openCDXDeviceModel));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.of(openCDXManufacturerModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXIAMUserRepository).findById(new ObjectId(patientId));
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
        OpenCDXIAMUserModel openCDXIAMUserModel = createUser(patientId);
        openCDXIAMUserModel.setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE);
        openCDXIAMUserModel.setAddresses(null);
        OpenCDXDeviceModel openCDXDeviceModel = createDevice(deviceId, manufacturerId, vendorId, countryId);
        openCDXDeviceModel.setExpiryDate(null);
        OpenCDXManufacturerModel openCDXManufacturerModel = new OpenCDXManufacturerModel(Manufacturer.newBuilder()
                .setId(manufacturerId)
                .setName("ABC Devices Inc")
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .build());

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        when(openCDXIAMUserRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(openCDXIAMUserModel));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.of(openCDXDeviceModel));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.of(openCDXManufacturerModel));

        openCDXCDCPayloadService.sendCDCPayloadMessage(testId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXIAMUserRepository).findById(new ObjectId(patientId));
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
        when(openCDXIAMUserRepository.findById(new ObjectId(patientId))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find patient: " + patientId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXIAMUserRepository).findById(new ObjectId(patientId));
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

        OpenCDXIAMUserModel patient = createUser(patientId);
        patient.setFullName(null);
        patient.setPrimaryContactInfo(null);

        when(openCDXConnectedTestRepository.findById(new ObjectId(testId)))
                .thenReturn(Optional.of(createTest(testId, patientId, deviceId)));
        when(openCDXIAMUserRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(patient));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find device: " + deviceId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXIAMUserRepository).findById(new ObjectId(patientId));
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
        when(openCDXIAMUserRepository.findById(new ObjectId(patientId))).thenReturn(Optional.of(createUser(patientId)));
        when(openCDXDeviceRepository.findById(new ObjectId(deviceId)))
                .thenReturn(Optional.of(createDevice(deviceId, manufacturerId, vendorId, countryId)));
        when(openCDXManufacturerRepository.findById(new ObjectId(manufacturerId)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXCDCPayloadService.sendCDCPayloadMessage(testId),
                "Failed to find manufacturer: " + manufacturerId);

        verify(openCDXConnectedTestRepository).findById(new ObjectId(testId));
        verify(openCDXIAMUserRepository).findById(new ObjectId(patientId));
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

    private OpenCDXIAMUserModel createUser(String userId) {
        OpenCDXIAMUserModel openCDXIAMUserModel =
                new OpenCDXIAMUserModel(IamUser.newBuilder().setId(userId).build());
        openCDXIAMUserModel.setFullName(FullName.newBuilder()
                .setFirstName("Adam")
                .setMiddleName("Charles")
                .setLastName("Smith")
                .setSuffix("Sr")
                .build());
        openCDXIAMUserModel.setPrimaryContactInfo(ContactInfo.newBuilder()
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
                .addAllEmail(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .addAllEmail(List.of(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_PERSONAL)
                        .setEmail("contact@opencdx.org")
                        .build()))
                .build());
        openCDXIAMUserModel.setGender(Gender.GENDER_MALE);
        openCDXIAMUserModel.setAddresses(List.of(Address.newBuilder()
                .setAddress1("123 Main St")
                .setCity("Vienna")
                .setState("VA")
                .setPostalCode("22182")
                .setCountryId("USA")
                .setAddressPurpose(AddressPurpose.PRIMARY)
                .build()));

        return openCDXIAMUserModel;
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
