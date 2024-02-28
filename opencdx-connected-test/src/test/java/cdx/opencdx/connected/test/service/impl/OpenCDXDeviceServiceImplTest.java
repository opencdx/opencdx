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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.connected.test.controller.OpenCDXGrpcDeviceController;
import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.repository.OpenCDXVendorRepository;
import cdx.opencdx.connected.test.service.OpenCDXDeviceService;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXDeviceServiceImplTest {

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
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXDeviceService = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getDeviceById() {
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        DeviceIdRequest deviceIdRequest = DeviceIdRequest.newBuilder()
                .setDeviceId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXDeviceService.getDeviceById(deviceIdRequest));
    }

    @Test
    void addDevice() throws JsonProcessingException {
        OpenCDXDeviceModel openCDXDeviceModel =
                OpenCDXDeviceModel.builder().id(ObjectId.get()).model("model").build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        Device device = Device.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setBatchNumber("10")
                .setManufacturerId(ObjectId.get().toHexString())
                .setManufacturerCountryId(ObjectId.get().toHexString())
                .setVendorCountryId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXDeviceModel.class)))
                .thenThrow(JsonProcessingException.class);
        OpenCDXDeviceServiceImpl openCDXDeviceService1 = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXDeviceService1.addDevice(device));
    }

    @Test
    void updateDevice() throws JsonProcessingException {
        OpenCDXDeviceModel openCDXDeviceModel =
                OpenCDXDeviceModel.builder().id(ObjectId.get()).model("model").build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        Device device = Device.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setBatchNumber("10")
                .setManufacturerId(ObjectId.get().toHexString())
                .setManufacturerCountryId(ObjectId.get().toHexString())
                .setVendorCountryId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXDeviceModel.class)))
                .thenThrow(JsonProcessingException.class);
        OpenCDXDeviceServiceImpl openCDXDeviceService1 = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXDeviceService1.updateDevice(device));
    }

    @Test
    void deleteDeviceOpenCDXNotFound() {
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        DeviceIdRequest device = DeviceIdRequest.newBuilder()
                .setDeviceId(ObjectId.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        OpenCDXDeviceServiceImpl openCDXDeviceService1 = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotFound.class, () -> openCDXDeviceService1.deleteDevice(device));
    }

    @Test
    void deleteDeviceOpenCDXNotAcceptable() throws JsonProcessingException {
        OpenCDXDeviceModel openCDXDeviceModel =
                OpenCDXDeviceModel.builder().id(ObjectId.get()).model("model").build();
        Mockito.when(this.openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXDeviceModel));
        DeviceIdRequest device = DeviceIdRequest.newBuilder()
                .setDeviceId(ObjectId.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXDeviceModel.class)))
                .thenThrow(JsonProcessingException.class);
        OpenCDXDeviceServiceImpl openCDXDeviceService1 = new OpenCDXDeviceServiceImpl(
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXDeviceService1.deleteDevice(device));
    }
}
