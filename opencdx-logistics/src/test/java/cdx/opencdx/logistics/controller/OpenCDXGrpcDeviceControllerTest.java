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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Device;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.service.logistics.DeviceIdRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListResponse;
import cdx.opencdx.logistics.model.OpenCDXDeviceModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.logistics.repository.OpenCDXVendorRepository;
import cdx.opencdx.logistics.service.OpenCDXDeviceService;
import cdx.opencdx.logistics.service.impl.OpenCDXDeviceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcDeviceControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXVendorRepository openCDXVendorRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXDeviceService openCDXDeviceService;

    OpenCDXGrpcDeviceController openCDXGrpcDeviceController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXDeviceService = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        this.openCDXGrpcDeviceController = new OpenCDXGrpcDeviceController(this.openCDXDeviceService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getDeviceById() {
        StreamObserver<Device> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXDeviceModel openCDXDeviceModel = OpenCDXDeviceModel.builder()
                .id(OpenCDXIdentifier.get())
                .model("model")
                .build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        DeviceIdRequest deviceIdRequest = DeviceIdRequest.newBuilder()
                .setDeviceId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcDeviceController.getDeviceById(deviceIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXDeviceModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addDevice() {
        StreamObserver<Device> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXDeviceModel openCDXDeviceModel = OpenCDXDeviceModel.builder()
                .id(OpenCDXIdentifier.get())
                .model("model")
                .build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        Device device = Device.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setBatchNumber("10")
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerCountryId(OpenCDXIdentifier.get().toHexString())
                .setVendorCountryId(OpenCDXIdentifier.get().toHexString())
                .setVendorId(OpenCDXIdentifier.get().toHexString())
                .setLidrId("")
                .build();
        this.openCDXGrpcDeviceController.addDevice(device, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(device);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateDevice() {
        StreamObserver<Device> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXDeviceModel openCDXDeviceModel = OpenCDXDeviceModel.builder()
                .id(OpenCDXIdentifier.get())
                .model("model")
                .manufacturerId(OpenCDXIdentifier.get())
                .vendorId(OpenCDXIdentifier.get())
                .build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        Device device = Device.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setBatchNumber("10")
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerCountryId(OpenCDXIdentifier.get().toHexString())
                .setVendorCountryId(OpenCDXIdentifier.get().toHexString())
                .setVendorId(OpenCDXIdentifier.get().toHexString())
                .setLidrId("")
                .build();
        this.openCDXGrpcDeviceController.updateDevice(device, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(device);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteDevice() {
        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXDeviceModel openCDXDeviceModel = OpenCDXDeviceModel.builder()
                .id(OpenCDXIdentifier.get())
                .model("model")
                .build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        DeviceIdRequest deviceIdRequest = DeviceIdRequest.newBuilder()
                .setDeviceId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcDeviceController.deleteDevice(deviceIdRequest, responseObserver);
        String message = "Device: " + deviceIdRequest.getDeviceId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listDevices() {
        StreamObserver<DeviceListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXDeviceRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDeviceModel.builder()
                                .manufacturerId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        DeviceListRequest deviceListRequest = DeviceListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcDeviceController.listDevices(deviceListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listDevicesManufacturer() {
        StreamObserver<DeviceListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXDeviceRepository.findAllByManufacturerId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDeviceModel.builder()
                                .manufacturerId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        DeviceListRequest deviceListRequest = DeviceListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcDeviceController.listDevices(deviceListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listDevicesVendor() {
        StreamObserver<DeviceListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXDeviceRepository.findAllByVendorId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDeviceModel.builder()
                                .manufacturerId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        DeviceListRequest deviceListRequest = DeviceListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .build())
                .setVendorId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcDeviceController.listDevices(deviceListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
