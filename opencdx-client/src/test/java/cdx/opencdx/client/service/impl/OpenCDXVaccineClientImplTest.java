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

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.grpc.health.medication.*;
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
class OpenCDXVaccineClientImplTest {

    @Mock
    VaccineServiceGrpc.VaccineServiceBlockingStub vaccineServiceBlockingStub;

    OpenCDXVaccineClientImpl openCDXVaccineClient;

    @BeforeEach
    void setUp() {
        this.vaccineServiceBlockingStub = Mockito.mock(VaccineServiceGrpc.VaccineServiceBlockingStub.class);
        this.openCDXVaccineClient = new OpenCDXVaccineClientImpl(this.vaccineServiceBlockingStub);
        Mockito.when(vaccineServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.vaccineServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.vaccineServiceBlockingStub);
    }

    @Test
    void trackVaccineAdministration() {
        Mockito.when(this.vaccineServiceBlockingStub.trackVaccineAdministration(Mockito.any(Vaccine.class)))
                .thenReturn(Vaccine.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Vaccine.getDefaultInstance(),
                this.openCDXVaccineClient.trackVaccineAdministration(
                        Vaccine.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void trackMedicationAdministrationException() {
        Mockito.when(this.vaccineServiceBlockingStub.trackVaccineAdministration(Mockito.any(Vaccine.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Vaccine request = Vaccine.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVaccineClient.trackVaccineAdministration(request, openCDXCallCredentials));
    }

    @Test
    void getVaccineById() {
        Mockito.when(this.vaccineServiceBlockingStub.getVaccineById(Mockito.any(GetVaccineByIdRequest.class)))
                .thenReturn(Vaccine.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Vaccine.getDefaultInstance(),
                this.openCDXVaccineClient.getVaccineById(
                        GetVaccineByIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getVaccineByIdException() {
        Mockito.when(this.vaccineServiceBlockingStub.getVaccineById(Mockito.any(GetVaccineByIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetVaccineByIdRequest request = GetVaccineByIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVaccineClient.getVaccineById(request, openCDXCallCredentials));
    }

    @Test
    void listVaccines() {
        Mockito.when(this.vaccineServiceBlockingStub.listVaccines(Mockito.any(ListVaccinesRequest.class)))
                .thenReturn(ListVaccinesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListVaccinesResponse.getDefaultInstance(),
                this.openCDXVaccineClient.listVaccines(
                        ListVaccinesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listVaccinesException() {
        Mockito.when(this.vaccineServiceBlockingStub.listVaccines(Mockito.any(ListVaccinesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListVaccinesRequest request = ListVaccinesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXVaccineClient.listVaccines(request, openCDXCallCredentials));
    }
}
