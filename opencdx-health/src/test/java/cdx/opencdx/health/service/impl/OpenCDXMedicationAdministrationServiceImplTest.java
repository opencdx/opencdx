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
package cdx.opencdx.health.service.impl;

import static org.mockito.ArgumentMatchers.any;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.MedicationAdministration;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.GetMedicationByIdRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.health.model.OpenCDXMedicationAdministrationModel;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationAdministrationRepository;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
class OpenCDXMedicationAdministrationServiceImplTest {

    OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;

    @Mock
    OpenCDXMedicationRepository openCDXMedicationRepository;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXMedicationAdministrationRepository.save(
                        Mockito.any(OpenCDXMedicationAdministrationModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicationAdministrationModel>() {
                    @Override
                    public OpenCDXMedicationAdministrationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicationAdministrationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.save(Mockito.any(OpenCDXMedicationModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicationModel>() {
                    @Override
                    public OpenCDXMedicationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicationAdministrationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationAdministrationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationAdministrationModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationAdministrationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationAdministrationService = new OpenCDXMedicationAdministrationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationAdministrationRepository,
                this.openCDXMedicationRepository,
                this.openCDXDocumentValidator);
    }

    @Test
    void trackMedicationAdministration() {
        MedicationAdministration medicationAdministration = MedicationAdministration.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setPatientId(ObjectId.get().toHexString())
                .setMedicationId(ObjectId.get().toHexString())
                .setNationalHealthId(ObjectId.get().toHexString())
                .setAdministratedBy("Doctor")
                .setAdministrationNotes("notes")
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> openCDXMedicationAdministrationService.trackMedicationAdministration(medicationAdministration));
    }

    @Test
    void getMedicationById() {
        GetMedicationByIdRequest request = GetMedicationByIdRequest.newBuilder()
                .setMedicationId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> openCDXMedicationAdministrationService.getMedicationById(request));
    }

    @Test
    void listMedicationsSortNotAscending() {
        ListMedicationsRequest request = ListMedicationsRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> openCDXMedicationAdministrationService.listMedications(request));
    }

    @Test
    void listMedicationsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListMedicationsRequest request = ListMedicationsRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationAdministrationService.listMedications(request));
    }

    @Test
    void getMedicationById_OpenCDXNotFound() {
        Mockito.when(this.openCDXMedicationAdministrationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationAdministrationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationAdministrationModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.empty();
                    }
                });
        GetMedicationByIdRequest request = GetMedicationByIdRequest.newBuilder()
                .setMedicationId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> openCDXMedicationAdministrationService.getMedicationById(request));
    }

    @Test
    void listMedicationsSortNotAscendingNoSort() {
        ListMedicationsRequest request = ListMedicationsRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> openCDXMedicationAdministrationService.listMedications(request));
    }
}
