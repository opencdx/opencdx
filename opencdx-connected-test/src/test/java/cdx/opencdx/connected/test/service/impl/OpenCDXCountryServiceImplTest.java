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
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
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
class OpenCDXCountryServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXCountryService openCDXCountryService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXVendorRepository openCDXVendorRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXCountryService = new OpenCDXCountryServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXCountryRepository,
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getCountryByIdElse() {

        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXCountryRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        CountryIdRequest countryIdRequest = CountryIdRequest.newBuilder()
                .setCountryId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXCountryService.getCountryById(countryIdRequest));
    }

    @Test
    void addCountry() throws JsonProcessingException {
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Country country = Country.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setName("USA")
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXCountryModel.class)))
                .thenThrow(JsonProcessingException.class);
        OpenCDXCountryServiceImpl openCDXCountryService1 = new OpenCDXCountryServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXCountryRepository,
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXCountryService1.addCountry(country));
    }

    @Test
    void updateCountry() throws JsonProcessingException {
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Country country = Country.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setName("USA")
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXCountryModel.class)))
                .thenThrow(JsonProcessingException.class);
        OpenCDXCountryServiceImpl openCDXCountryService1 = new OpenCDXCountryServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXCountryRepository,
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                openCDXCurrentUser,
                mapper,
                this.openCDXAuditService);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXCountryService1.updateCountry(country));
    }

    @Test
    void deleteCountry() {
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXCountryRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        Mockito.when(this.openCDXDeviceRepository.existsByVendorId(Mockito.any()))
                .thenReturn(true);

        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerCountryId(Mockito.any()))
                .thenReturn(true);

        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(true);

        Mockito.when(this.openCDXVendorRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(true);

        CountryIdRequest countryIdRequest = CountryIdRequest.newBuilder()
                .setCountryId(ObjectId.get().toHexString())
                .build();
        Assertions.assertEquals(
                "Country ID: " + countryIdRequest.getCountryId() + " in use.",
                this.openCDXCountryService.deleteCountry(countryIdRequest).getMessage());

        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXVendorRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(true);
        Assertions.assertEquals(
                "Country ID: " + countryIdRequest.getCountryId() + " in use.",
                this.openCDXCountryService.deleteCountry(countryIdRequest).getMessage());

        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXVendorRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerCountryId(Mockito.any()))
                .thenReturn(true);
        Assertions.assertEquals(
                "Country ID: " + countryIdRequest.getCountryId() + " in use.",
                this.openCDXCountryService.deleteCountry(countryIdRequest).getMessage());

        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXVendorRepository.existsByAddress_CountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerCountryId(Mockito.any()))
                .thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByVendorCountryId(Mockito.any()))
                .thenReturn(true);
        Assertions.assertEquals(
                "Country ID: " + countryIdRequest.getCountryId() + " in use.",
                this.openCDXCountryService.deleteCountry(countryIdRequest).getMessage());
    }
}
