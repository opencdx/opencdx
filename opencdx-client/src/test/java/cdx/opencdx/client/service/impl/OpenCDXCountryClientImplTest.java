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
package cdx.opencdx.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXCountryClient;
import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXCountryClientImplTest {

    @Mock
    CountryServiceGrpc.CountryServiceBlockingStub countryServiceBlockingStub;

    OpenCDXCountryClient openCDXCountryClient;

    @BeforeEach
    void setUp() {
        this.countryServiceBlockingStub = Mockito.mock(CountryServiceGrpc.CountryServiceBlockingStub.class);
        this.openCDXCountryClient = new OpenCDXCountryClientImpl(this.countryServiceBlockingStub);
        Mockito.when(countryServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.countryServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.countryServiceBlockingStub);
    }

    @Test
    void getCountryById() {
        Mockito.when(this.countryServiceBlockingStub.getCountryById(Mockito.any(CountryIdRequest.class)))
                .thenReturn(Country.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Country.getDefaultInstance(),
                this.openCDXCountryClient.getCountryById(
                        CountryIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getCountryByIdException() {
        Mockito.when(this.countryServiceBlockingStub.getCountryById(Mockito.any(CountryIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CountryIdRequest request = CountryIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXCountryClient.getCountryById(request, openCDXCallCredentials));
    }

    @Test
    void addCountry() {
        Mockito.when(this.countryServiceBlockingStub.addCountry(Mockito.any(Country.class)))
                .thenReturn(Country.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Country.getDefaultInstance(),
                this.openCDXCountryClient.addCountry(Country.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void addCountryException() {
        Mockito.when(this.countryServiceBlockingStub.addCountry(Mockito.any(Country.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Country request = Country.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXCountryClient.addCountry(request, openCDXCallCredentials));
    }

    @Test
    void updateCountry() {
        Mockito.when(this.countryServiceBlockingStub.updateCountry(Mockito.any(Country.class)))
                .thenReturn(Country.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Country.getDefaultInstance(),
                this.openCDXCountryClient.updateCountry(Country.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateCountryException() {
        Mockito.when(this.countryServiceBlockingStub.updateCountry(Mockito.any(Country.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Country request = Country.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXCountryClient.updateCountry(request, openCDXCallCredentials));
    }

    @Test
    void deleteCountry() {
        Mockito.when(this.countryServiceBlockingStub.deleteCountry(Mockito.any(CountryIdRequest.class)))
                .thenReturn(DeleteResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteResponse.getDefaultInstance(),
                this.openCDXCountryClient.deleteCountry(CountryIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteCountryException() {
        Mockito.when(this.countryServiceBlockingStub.deleteCountry(Mockito.any(CountryIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CountryIdRequest request = CountryIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXCountryClient.deleteCountry(request, openCDXCallCredentials));
    }

    @Test
    void listCountries() {
        Mockito.when(this.countryServiceBlockingStub.listCountries(Mockito.any(CountryListRequest.class)))
                .thenReturn(CountryListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CountryListResponse.getDefaultInstance(),
                this.openCDXCountryClient.listCountries(
                        CountryListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listCountriesException() {
        Mockito.when(this.countryServiceBlockingStub.listCountries(Mockito.any(CountryListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CountryListRequest request = CountryListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXCountryClient.listCountries(request, openCDXCallCredentials));
    }
}
