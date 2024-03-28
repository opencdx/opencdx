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
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import cdx.opencdx.logistics.controller.OpenCDXGrpcManufacturerController;
import cdx.opencdx.logistics.model.OpenCDXManufacturerModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.logistics.service.OpenCDXManufacturerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
class OpenCDXManufacturerServiceImplTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXManufacturerService openCDXManufacturerService;

    OpenCDXGrpcManufacturerController openCDXGrpcManufacturerController;

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

        this.openCDXManufacturerService = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getManufacturerById() {
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXManufacturerService.getManufacturerById(manufacturerIdRequest));
    }

    @Test
    void addManufacturer() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> manufacturerService1.addManufacturer(manufacturer));
    }

    @Test
    void addManufacturer_2() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerAddress(Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> manufacturerService1.addManufacturer(manufacturer));
    }

    @Test
    void updateManufacturer() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> manufacturerService1.updateManufacturer(manufacturer));
    }

    @Test
    void updateManufacturer_2() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerAddress(Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> manufacturerService1.updateManufacturer(manufacturer));
    }

    @Test
    void deleteManufacturer() {
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(true);
        Mockito.when(this.openCDXTestCaseRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(true);
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertEquals(
                "Manufacturer: " + manufacturerIdRequest.getManufacturerId() + " is in use.",
                this.openCDXManufacturerService
                        .deleteManufacturer(manufacturerIdRequest)
                        .getMessage());

        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXTestCaseRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(true);
        Assertions.assertEquals(
                "Manufacturer: " + manufacturerIdRequest.getManufacturerId() + " is in use.",
                this.openCDXManufacturerService
                        .deleteManufacturer(manufacturerIdRequest)
                        .getMessage());
    }

    @Test
    void deleteManufacturerOpenCDXNotAcceptable() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> manufacturerService1.deleteManufacturer(manufacturerIdRequest));
    }

    @Test
    void deleteManufacturerOpenCDXNotFound() throws JsonProcessingException {
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> manufacturerService1.deleteManufacturer(manufacturerIdRequest));
    }
}
