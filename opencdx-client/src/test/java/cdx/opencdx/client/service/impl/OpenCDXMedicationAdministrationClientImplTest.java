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
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.MedicationAdministration;
import cdx.opencdx.grpc.service.health.*;
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
class OpenCDXMedicationAdministrationClientImplTest {

    @Mock
    MedicationAdministrationServiceGrpc.MedicationAdministrationServiceBlockingStub
            medicationAdministrationServiceBlockingStub;

    OpenCDXMedicationAdministrationClientImpl openCDXMedicationAdministrationClient;

    @BeforeEach
    void setUp() {
        this.medicationAdministrationServiceBlockingStub =
                Mockito.mock(MedicationAdministrationServiceGrpc.MedicationAdministrationServiceBlockingStub.class);
        this.openCDXMedicationAdministrationClient =
                new OpenCDXMedicationAdministrationClientImpl(this.medicationAdministrationServiceBlockingStub);
        Mockito.when(medicationAdministrationServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.medicationAdministrationServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.medicationAdministrationServiceBlockingStub);
    }

    @Test
    void trackMedicationAdministration() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.trackMedicationAdministration(
                        Mockito.any(MedicationAdministration.class)))
                .thenReturn(MedicationAdministration.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                MedicationAdministration.getDefaultInstance(),
                this.openCDXMedicationAdministrationClient.trackMedicationAdministration(
                        MedicationAdministration.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void trackMedicationAdministrationException() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.trackMedicationAdministration(
                        Mockito.any(MedicationAdministration.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        MedicationAdministration request = MedicationAdministration.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationAdministrationClient.trackMedicationAdministration(
                        request, openCDXCallCredentials));
    }

    @Test
    void getMedicationById() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.getMedicationById(
                        Mockito.any(GetMedicationByIdRequest.class)))
                .thenReturn(Medication.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Medication.getDefaultInstance(),
                this.openCDXMedicationAdministrationClient.getMedicationById(
                        GetMedicationByIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getMedicationByIdException() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.getMedicationById(
                        Mockito.any(GetMedicationByIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetMedicationByIdRequest request = GetMedicationByIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationAdministrationClient.getMedicationById(request, openCDXCallCredentials));
    }

    @Test
    void listMedications() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.listMedications(
                        Mockito.any(ListMedicationsRequest.class)))
                .thenReturn(ListMedicationsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMedicationsResponse.getDefaultInstance(),
                this.openCDXMedicationAdministrationClient.listMedications(
                        ListMedicationsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listMedicationsException() {
        Mockito.when(this.medicationAdministrationServiceBlockingStub.listMedications(
                        Mockito.any(ListMedicationsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMedicationsRequest request = ListMedicationsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationAdministrationClient.listMedications(request, openCDXCallCredentials));
    }
}
