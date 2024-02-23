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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.connected.test.service.impl.OpenCDXCountryServiceImpl;
import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.CountryListRequest;
import cdx.opencdx.grpc.inventory.CountryListResponse;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
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
class OpenCDXGrpcCountryControllerTest {

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

    OpenCDXCountryService openCDXCountryService;

    OpenCDXGrpcCountryController openCDXGrpcCountryController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
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
        this.openCDXGrpcCountryController = new OpenCDXGrpcCountryController(this.openCDXCountryService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getCountryById() {
        StreamObserver<Country> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXCountryModel openCDXCountryModel =
                OpenCDXCountryModel.builder().id(ObjectId.get()).name("USA").build();
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXCountryRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXCountryModel));
        CountryIdRequest countryIdRequest = CountryIdRequest.newBuilder()
                .setCountryId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcCountryController.getCountryById(countryIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXCountryModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addCountry() {
        StreamObserver<Country> responseObserver = Mockito.mock(StreamObserver.class);

        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Country country = Country.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setName("USA")
                .build();
        this.openCDXGrpcCountryController.addCountry(country, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(country);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateCountry() {
        StreamObserver<Country> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Country country = Country.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setName("USA")
                .build();
        this.openCDXGrpcCountryController.updateCountry(country, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(country);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteCountry() {

        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXCountryModel openCDXCountryModel =
                OpenCDXCountryModel.builder().id(ObjectId.get()).name("USA").build();
        Mockito.when(this.openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXCountryRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXCountryModel));
        CountryIdRequest countryIdRequest = CountryIdRequest.newBuilder()
                .setCountryId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcCountryController.deleteCountry(countryIdRequest, responseObserver);

        String message = "Country ID: " + countryIdRequest.getCountryId() + " deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listCountries() {
        StreamObserver<CountryListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXCountryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXCountryModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));

        CountryListRequest countryListRequest = CountryListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcCountryController.listCountries(countryListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
