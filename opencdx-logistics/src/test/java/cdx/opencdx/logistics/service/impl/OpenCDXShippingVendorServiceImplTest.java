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
package cdx.opencdx.logistics.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.logistics.DeliveryTrackingRequest;
import cdx.opencdx.grpc.service.logistics.DeliveryTrackingResponse;
import cdx.opencdx.grpc.service.logistics.ShippingRequest;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.PhoneType;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import cdx.opencdx.logistics.repository.OpenCDXShippingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXShippingVendorServiceImplTest {

    OpenCDXShippingVendorServiceImpl openCDXShippingVendorServiceImpl;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Mock
    OpenCDXShippingRepository openCDXShippingRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void beforeEach() {
        openCDXShippingVendorServiceImpl = new OpenCDXShippingVendorServiceImpl(
                openCDXShippingRepository, openCDXCommunicationService, openCDXProfileRepository);
    }

    @RepeatedTest(100)
    void getShippingVendors() {
        ShippingRequest shippingRequest = ShippingRequest.newBuilder()
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();
        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.getShippingVendors(shippingRequest));
    }

    @ParameterizedTest
    @ValueSource(strings = {"fedex", "ups", "usps", "doordash"})
    void testShippingVendor(String vendorID) {
        Shipping shipping = Shipping.newBuilder()
                .setShippingVendorId(vendorID)
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setShippingCost(Math.random() * 100)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();
        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.shipPackage(shipping));
    }

    @Test
    void createDeliveryTrackingShippingRepo() {
        Mockito.when(this.openCDXShippingRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(DeliveryTracking.newBuilder()
                        .setTrackingId("60f1e6b1f075a901a94d3762")
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXShippingVendorServiceImpl.createDeliveryTracking(deliveryTrackingRequest));
    }

    @Test
    void createDeliveryTrackingProfileRepo() {
        OpenCDXShippingModel shippingModel = Mockito.mock(OpenCDXShippingModel.class);
        Order order = Mockito.mock(Order.class);
        Mockito.when(shippingModel.getPackageDetails()).thenReturn(order);
        Mockito.when(order.getPatientId()).thenReturn("60f1e6b1f075a911a94d3762");
        Mockito.when(this.openCDXShippingRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(shippingModel));
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(DeliveryTracking.newBuilder()
                        .setTrackingId("60f1e6b1f075a901a94d3762")
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> openCDXShippingVendorServiceImpl.createDeliveryTracking(deliveryTrackingRequest));
    }

    @Test
    void createDeliveryTrackingNotification() {
        OpenCDXShippingModel shippingModel = Mockito.mock(OpenCDXShippingModel.class);
        Order order = Mockito.mock(Order.class);
        Mockito.when(shippingModel.getPackageDetails()).thenReturn(order);
        Mockito.when(order.getTestCaseId()).thenReturn("testCaseId");
        Mockito.when(shippingModel.getTrackingNumber()).thenReturn("trackingNumber");
        Mockito.when(order.getPatientId()).thenReturn("60f1e6b1f075a911a94d3762");
        Mockito.when(this.openCDXShippingRepository.findByTrackingNumber("60f1e6b1f075a901a94d3762"))
                .thenReturn(Optional.of(shippingModel));
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.ofNullable(OpenCDXProfileModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .fullName(FullName.newBuilder()
                                .setFirstName("first")
                                .setLastName("last")
                                .build())
                        .addresses(List.of(Address.newBuilder()
                                .setAddress1("address1")
                                .setAddress2("address2")
                                .setAddress3("address3")
                                .setCity("address3")
                                .setPostalCode("address4")
                                .setState("address5")
                                .setCountryId("countryId")
                                .build()))
                        .primaryContactInfo(ContactInfo.newBuilder()
                                .addAllEmails(List.of(EmailAddress.newBuilder()
                                        .setType(EmailType.EMAIL_TYPE_WORK)
                                        .setEmail("ab@safehealth.me")
                                        .build()))
                                .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                        .setType(PhoneType.PHONE_TYPE_MOBILE)
                                        .setNumber("1234567890")
                                        .build()))
                                .build())
                        .build()));
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(DeliveryTracking.newBuilder()
                        .setTrackingId("60f1e6b1f075a901a94d3762")
                        .build())
                .build();

        Assertions.assertEquals(
                DeliveryTrackingResponse.newBuilder()
                        .setDeliveryTracking(deliveryTrackingRequest.getDeliveryTracking())
                        .build(),
                openCDXShippingVendorServiceImpl.createDeliveryTracking(deliveryTrackingRequest));
    }

    @Test
    void getDeliveryTracking() {
        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setTrackingId("789").build())
                .build();

        DeliveryTrackingResponse response =
                openCDXShippingVendorServiceImpl.getDeliveryTracking(deliveryTrackingRequest);

        Assertions.assertEquals("789", response.getDeliveryTracking().getTrackingId());
    }
}
