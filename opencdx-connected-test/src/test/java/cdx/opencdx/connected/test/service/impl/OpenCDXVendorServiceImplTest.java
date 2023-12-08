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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.controller.OpenCDXGrpcVendorController;
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;
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
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXVendorServiceImplTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXVendorRepository openCDXVendorRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    OpenCDXVendorService openCDXVendorService;

    OpenCDXGrpcVendorController openCDXGrpcVendorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXVendorService = new OpenCDXVendorServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getVendorById() {
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        VendorIdRequest vendorIdRequest = VendorIdRequest.newBuilder()
                .setVendorId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXVendorService.getVendorById(vendorIdRequest));
    }

    @Test
    void addVendor() throws JsonProcessingException {
        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        Vendor vendor = Vendor.newBuilder().setId(ObjectId.get().toHexString()).build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXVendorModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXVendorServiceImpl openCDXVendorService1 = new OpenCDXVendorServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXVendorService1.addVendor(vendor));
    }

    @Test
    void updateVendor() throws JsonProcessingException {
        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        Vendor vendor = Vendor.newBuilder().setId(ObjectId.get().toHexString()).build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXVendorModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXVendorServiceImpl openCDXVendorService1 = new OpenCDXVendorServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXVendorService1.updateVendor(vendor));
    }

    @Test
    void deleteVendor() {
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        Mockito.when(this.openCDXDeviceRepository.existsByVendorId(Mockito.any()))
                .thenReturn(true);

        Mockito.when(this.openCDXTestCaseRepository.existsByVendorId(Mockito.any()))
                .thenReturn(true);

        VendorIdRequest vendorIdRequest = VendorIdRequest.newBuilder()
                .setVendorId(ObjectId.get().toHexString())
                .build();
        Assertions.assertEquals(
                "Vendor: " + vendorIdRequest.getVendorId() + " is in use.",
                this.openCDXVendorService.deleteVendor(vendorIdRequest).getMessage());

        Mockito.when(this.openCDXDeviceRepository.existsByVendorId(Mockito.any()))
                .thenReturn(false);

        Mockito.when(this.openCDXTestCaseRepository.existsByVendorId(Mockito.any()))
                .thenReturn(true);
        Assertions.assertEquals(
                "Vendor: " + vendorIdRequest.getVendorId() + " is in use.",
                this.openCDXVendorService.deleteVendor(vendorIdRequest).getMessage());
    }
}
