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
class OpenCDXMedicationClientImplTest {

    @Mock
    MedicationServiceGrpc.MedicationServiceBlockingStub medicationServiceBlockingStub;

    OpenCDXMedicationClientImpl openCDXMedicationClient;

    @BeforeEach
    void setUp() {
        this.medicationServiceBlockingStub = Mockito.mock(MedicationServiceGrpc.MedicationServiceBlockingStub.class);
        this.openCDXMedicationClient = new OpenCDXMedicationClientImpl(this.medicationServiceBlockingStub);
        Mockito.when(medicationServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.medicationServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.medicationServiceBlockingStub);
    }

    @Test
    void prescribing() {
        Mockito.when(this.medicationServiceBlockingStub.prescribing(Mockito.any(Medication.class)))
                .thenReturn(Medication.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Medication.getDefaultInstance(),
                this.openCDXMedicationClient.prescribing(Medication.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void prescribingException() {
        Mockito.when(this.medicationServiceBlockingStub.prescribing(Mockito.any(Medication.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Medication request = Medication.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationClient.prescribing(request, openCDXCallCredentials));
    }

    @Test
    void ending() {
        Mockito.when(this.medicationServiceBlockingStub.ending(Mockito.any(EndMedicationRequest.class)))
                .thenReturn(Medication.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Medication.getDefaultInstance(),
                this.openCDXMedicationClient.ending(EndMedicationRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void endingException() {
        Mockito.when(this.medicationServiceBlockingStub.ending(Mockito.any(EndMedicationRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        EndMedicationRequest request = EndMedicationRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationClient.ending(request, openCDXCallCredentials));
    }

    @Test
    void listAllMedications() {
        Mockito.when(this.medicationServiceBlockingStub.listAllMedications(Mockito.any(ListMedicationsRequest.class)))
                .thenReturn(ListMedicationsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMedicationsResponse.getDefaultInstance(),
                this.openCDXMedicationClient.listAllMedications(
                        ListMedicationsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listAllMedicationsException() {
        Mockito.when(this.medicationServiceBlockingStub.listAllMedications(Mockito.any(ListMedicationsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMedicationsRequest request = ListMedicationsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationClient.listAllMedications(request, openCDXCallCredentials));
    }

    @Test
    void listCurrentMedications() {
        Mockito.when(this.medicationServiceBlockingStub.listCurrentMedications(
                        Mockito.any(ListMedicationsRequest.class)))
                .thenReturn(ListMedicationsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMedicationsResponse.getDefaultInstance(),
                this.openCDXMedicationClient.listCurrentMedications(
                        ListMedicationsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listCurrentMedicationsException() {
        Mockito.when(this.medicationServiceBlockingStub.listCurrentMedications(
                        Mockito.any(ListMedicationsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMedicationsRequest request = ListMedicationsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationClient.listCurrentMedications(request, openCDXCallCredentials));
    }

    @Test
    void searchMedications() {
        Mockito.when(this.medicationServiceBlockingStub.searchMedications(Mockito.any(SearchMedicationsRequest.class)))
                .thenReturn(ListMedicationsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMedicationsResponse.getDefaultInstance(),
                this.openCDXMedicationClient.searchMedications(
                        SearchMedicationsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void searchMedicationsException() {
        Mockito.when(this.medicationServiceBlockingStub.searchMedications(Mockito.any(SearchMedicationsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        SearchMedicationsRequest request = SearchMedicationsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicationClient.searchMedications(request, openCDXCallCredentials));
    }
}
