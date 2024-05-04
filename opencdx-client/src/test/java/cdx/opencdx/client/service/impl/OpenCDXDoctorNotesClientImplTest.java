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
import cdx.opencdx.client.service.OpenCDXDoctorNotesClient;
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
class OpenCDXDoctorNotesClientImplTest {

    @Mock
    DoctorNotesServiceGrpc.DoctorNotesServiceBlockingStub doctorNotesServiceBlockingStub;

    OpenCDXDoctorNotesClient openCDXDoctorNotesClient;

    @BeforeEach
    void setUp() {
        this.doctorNotesServiceBlockingStub = Mockito.mock(DoctorNotesServiceGrpc.DoctorNotesServiceBlockingStub.class);
        this.openCDXDoctorNotesClient = new OpenCDXDoctorNotesClientImpl(this.doctorNotesServiceBlockingStub);
        Mockito.when(doctorNotesServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.doctorNotesServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.doctorNotesServiceBlockingStub);
    }

    @Test
    void createDoctorNotes() {
        Mockito.when(this.doctorNotesServiceBlockingStub.createDoctorNotes(Mockito.any(CreateDoctorNotesRequest.class)))
                .thenReturn(CreateDoctorNotesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateDoctorNotesResponse.getDefaultInstance(),
                this.openCDXDoctorNotesClient.createDoctorNotes(
                        CreateDoctorNotesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createDoctorNotesException() {
        Mockito.when(this.doctorNotesServiceBlockingStub.createDoctorNotes(Mockito.any(CreateDoctorNotesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateDoctorNotesRequest request = CreateDoctorNotesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDoctorNotesClient.createDoctorNotes(request, openCDXCallCredentials));
    }

    @Test
    void getDoctorNotes() {
        Mockito.when(this.doctorNotesServiceBlockingStub.getDoctorNotes(Mockito.any(GetDoctorNotesRequest.class)))
                .thenReturn(GetDoctorNotesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetDoctorNotesResponse.getDefaultInstance(),
                this.openCDXDoctorNotesClient.getDoctorNotes(
                        GetDoctorNotesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getDoctorNotesException() {
        Mockito.when(this.doctorNotesServiceBlockingStub.getDoctorNotes(Mockito.any(GetDoctorNotesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetDoctorNotesRequest request = GetDoctorNotesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDoctorNotesClient.getDoctorNotes(request, openCDXCallCredentials));
    }

    @Test
    void updateDoctorNotes() {
        Mockito.when(this.doctorNotesServiceBlockingStub.updateDoctorNotes(Mockito.any(UpdateDoctorNotesRequest.class)))
                .thenReturn(UpdateDoctorNotesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateDoctorNotesResponse.getDefaultInstance(),
                this.openCDXDoctorNotesClient.updateDoctorNotes(
                        UpdateDoctorNotesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateDoctorNotesException() {
        Mockito.when(this.doctorNotesServiceBlockingStub.updateDoctorNotes(Mockito.any(UpdateDoctorNotesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateDoctorNotesRequest request = UpdateDoctorNotesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDoctorNotesClient.updateDoctorNotes(request, openCDXCallCredentials));
    }

    @Test
    void deleteDoctorNotes() {
        Mockito.when(this.doctorNotesServiceBlockingStub.deleteDoctorNotes(Mockito.any(DeleteDoctorNotesRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXDoctorNotesClient.deleteDoctorNotes(
                        DeleteDoctorNotesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteDoctorNotesException() {
        Mockito.when(this.doctorNotesServiceBlockingStub.deleteDoctorNotes(Mockito.any(DeleteDoctorNotesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteDoctorNotesRequest request = DeleteDoctorNotesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDoctorNotesClient.deleteDoctorNotes(request, openCDXCallCredentials));
    }

    @Test
    void listAllergies() {
        Mockito.when(this.doctorNotesServiceBlockingStub.listAllByPatientId(Mockito.any(ListDoctorNotesRequest.class)))
                .thenReturn(ListDoctorNotesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListDoctorNotesResponse.getDefaultInstance(),
                this.openCDXDoctorNotesClient.listAllByPatientId(
                        ListDoctorNotesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listAllergiesException() {
        Mockito.when(this.doctorNotesServiceBlockingStub.listAllByPatientId(Mockito.any(ListDoctorNotesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListDoctorNotesRequest request = ListDoctorNotesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDoctorNotesClient.listAllByPatientId(request, openCDXCallCredentials));
    }
}
