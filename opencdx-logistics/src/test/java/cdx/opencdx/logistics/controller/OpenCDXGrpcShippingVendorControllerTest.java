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
package cdx.opencdx.logistics.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.PhoneType;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import cdx.opencdx.logistics.repository.OpenCDXShippingRepository;
import cdx.opencdx.logistics.service.OpenCDXShippingVendorService;
import cdx.opencdx.logistics.service.impl.OpenCDXShippingVendorServiceImpl;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class OpenCDXGrpcShippingVendorControllerTest {

    OpenCDXGrpcShippingVendorController openCDXGrpcShippingVendorController;
    OpenCDXShippingVendorService openCDXShippingVendorService;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Mock
    OpenCDXShippingRepository openCDXShippingRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void beforeEach() {
        this.openCDXShippingVendorService = new OpenCDXShippingVendorServiceImpl(
                openCDXShippingRepository, openCDXCommunicationService, openCDXProfileRepository);
        this.openCDXGrpcShippingVendorController =
                new OpenCDXGrpcShippingVendorController(openCDXShippingVendorService);
    }

    @Test
    void getShippingVendors() {
        StreamObserver<ShippingVendorResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcShippingVendorController.getShippingVendors(
                ShippingRequest.newBuilder(ShippingRequest.getDefaultInstance()).build(), responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ShippingVendorResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void shipPackage() {
        StreamObserver<ShippingResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcShippingVendorController.shipPackage(
                Shipping.newBuilder(Shipping.getDefaultInstance())
                        .setShippingVendorId("fedex")
                        .setPackageDetails(Order.newBuilder().build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ShippingResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testCreateDeliveryTracking() {
        StreamObserver<DeliveryTrackingResponse> responseObserver = Mockito.mock(StreamObserver.class);
        OpenCDXShippingModel shippingModel = Mockito.mock(OpenCDXShippingModel.class);
        Order order = Mockito.mock(Order.class);
        Mockito.when(shippingModel.getPackageDetails()).thenReturn(order);
        Mockito.when(order.getTestCaseId()).thenReturn("testCaseId");
        Mockito.when(shippingModel.getTrackingNumber()).thenReturn("trackingNumber");
        Mockito.when(order.getPatientId()).thenReturn("60f1e6b1f075a911a94d3762");
        Mockito.when(this.openCDXShippingRepository.findByTrackingNumber(Mockito.anyString()))
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
        this.openCDXGrpcShippingVendorController.createDeliveryTracking(
                DeliveryTrackingRequest.newBuilder(deliveryTrackingRequest).build(), responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeliveryTrackingResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetDeliveryTracking() {
        StreamObserver<DeliveryTrackingResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcShippingVendorController.getDeliveryTracking(
                DeliveryTrackingRequest.newBuilder()
                        .setDeliveryTracking(DeliveryTracking.newBuilder()
                                .setTrackingId("789")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeliveryTrackingResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
