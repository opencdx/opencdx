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
import cdx.opencdx.connected.test.controller.GrpcManufacturerController;
import cdx.opencdx.connected.test.model.OpenCDXManufacturerModel;
import cdx.opencdx.connected.test.repository.OpenCDXCountryRepository;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
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
class OpenCDXManufacturerServiceImplTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXManufacturerService openCDXManufacturerService;

    GrpcManufacturerController grpcManufacturerController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUserType()).thenReturn(AgentType.AGENT_TYPE_HUMAN_USER);
        this.openCDXManufacturerService = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getManufacturerById() {
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXManufacturerService.getManufacturerById(manufacturerIdRequest));
    }

    @Test
    void addManufacturer() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer =
                Manufacturer.newBuilder().setId(ObjectId.get().toHexString()).build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> manufacturerService1.addManufacturer(manufacturer));
    }

    @Test
    void updateManufacturer() throws JsonProcessingException {
        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer =
                Manufacturer.newBuilder().setId(ObjectId.get().toHexString()).build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXManufacturerServiceImpl manufacturerService1 = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> manufacturerService1.updateManufacturer(manufacturer));
    }

    @Test
    void deleteManufacturer() {
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(true);
        Mockito.when(this.openCDXTestCaseRepository.existsByManufacturerId(Mockito.any()))
                .thenReturn(true);
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(ObjectId.get().toHexString())
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
}