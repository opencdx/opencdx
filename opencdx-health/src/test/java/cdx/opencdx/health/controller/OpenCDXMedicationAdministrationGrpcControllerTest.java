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
package cdx.opencdx.health.controller;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.medication.*;
import cdx.opencdx.health.model.OpenCDXMedicationAdministrationModel;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationAdministrationRepository;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
import cdx.opencdx.health.service.impl.OpenCDXMedicationAdministrationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
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
class OpenCDXMedicationAdministrationGrpcControllerTest {

    OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;
    OpenCDXMedicationAdministrationGrpcController openCDXMedicationAdministrationGrpcController;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
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
                            argument.setPatientId(OpenCDXIdentifier.get());
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
                                .medicationId(OpenCDXIdentifier.get())
                                .administratedBy("Doctor")
                                .administrationNotes("notes")
                                .nationalHealthId(ObjectId.get().toHexString())
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

        //        this.objectMapper = Mockito.mock(ObjectMapper.class);
        //        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationAdministrationService = new OpenCDXMedicationAdministrationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationAdministrationRepository,
                this.openCDXMedicationRepository,
                this.openCDXDocumentValidator);
        this.openCDXMedicationAdministrationGrpcController =
                new OpenCDXMedicationAdministrationGrpcController(openCDXMedicationAdministrationService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXMedicationAdministrationRepository);
        Mockito.reset(this.openCDXMedicationRepository);
    }

    @Test
    void trackMedicationAdministration() {
        StreamObserver<MedicationAdministration> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicationAdministrationGrpcController.trackMedicationAdministration(
                MedicationAdministration.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setMedicationId(ObjectId.get().toHexString())
                        .setNationalHealthId(ObjectId.get().toHexString())
                        .setAdministratedBy("Doctor")
                        .setAdministrationNotes("notes")
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(MedicationAdministration.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedicationById() {
        StreamObserver<Medication> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicationAdministrationGrpcController.getMedicationById(
                GetMedicationByIdRequest.newBuilder()
                        .setMedicationId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Medication.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedicationsByPatientId() {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicationAdministrationGrpcController.listMedications(
                ListMedicationsRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .setSort("id")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListMedicationsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
