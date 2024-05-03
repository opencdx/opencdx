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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.EndMedicationRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsResponse;
import cdx.opencdx.grpc.service.health.SearchMedicationsRequest;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXMedicationService;
import cdx.opencdx.health.service.impl.OpenCDXMedicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
class OpenCDXMedicationGrpcControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXApiFDA openCDXApiFDA;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXMedicationRepository openCDXMedicationRepository;

    OpenCDXMedicationService openCDXMedicationService;

    OpenCDXMedicationGrpcController openCDXMedicationGrpcController;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
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

        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
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
        Mockito.when(this.openCDXMedicationRepository.findAllByPatientIdAndEndDateIsNull(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationRepository.findAllByNationalHealthIdAndEndDateIsNull(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        this.openCDXMedicationGrpcController = new OpenCDXMedicationGrpcController(this.openCDXMedicationService);
    }

    @Test
    void prescribing() {
        StreamObserver<Medication> responseObserver = Mockito.mock(StreamObserver.class);

        Medication medication = Medication.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setMedicationName("medication")
                .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXMedicationGrpcController.prescribing(medication, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void ending() {
        StreamObserver<Medication> responseObserver = Mockito.mock(StreamObserver.class);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(OpenCDXIdentifier.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXMedicationGrpcController.ending(endMedicationRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listAllMedications() {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMedicationGrpcController.listAllMedications(listMedicationsRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listAllMedications_NHI() {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMedicationGrpcController.listAllMedications(listMedicationsRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listCurrentMedications() {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMedicationGrpcController.listCurrentMedications(listMedicationsRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listCurrentMedications_NHI() {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMedicationGrpcController.listCurrentMedications(listMedicationsRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tylenol", "Adipex", "Guanfacine"})
    void searchMedications(String brandName) {
        StreamObserver<ListMedicationsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        SearchMedicationsRequest searchMedicationsRequest = SearchMedicationsRequest.newBuilder()
                .setBrandName(brandName)
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() ->
                this.openCDXMedicationGrpcController.searchMedications(searchMedicationsRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
