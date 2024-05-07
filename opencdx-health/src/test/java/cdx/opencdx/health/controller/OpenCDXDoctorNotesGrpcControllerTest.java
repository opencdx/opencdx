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

import static org.mockito.Mockito.mock;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.DoctorNotes;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXDoctorNotesModel;
import cdx.opencdx.health.repository.OpenCDXDoctorNotesRepository;
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
import cdx.opencdx.health.service.impl.OpenCDXDoctorNotesServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
class OpenCDXDoctorNotesGrpcControllerTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;

    OpenCDXDoctorNotesService openCDXDoctorNotesService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXDoctorNotesGrpcController openCDXDoctorNotesGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXDoctorNotesRepository = mock(OpenCDXDoctorNotesRepository.class);

        Mockito.when(this.openCDXDoctorNotesRepository.save(Mockito.any(OpenCDXDoctorNotesModel.class)))
                .thenAnswer(new Answer<OpenCDXDoctorNotesModel>() {
                    @Override
                    public OpenCDXDoctorNotesModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXDoctorNotesModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXDoctorNotesRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXDoctorNotesModel>>() {
                    @Override
                    public Optional<OpenCDXDoctorNotesModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXDoctorNotesModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build());
                    }
                });

        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTags(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTagsContainingAndNoteDatetimeBetween(
                        Mockito.any(OpenCDXIdentifier.class),
                        Mockito.any(String.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndNoteDatetimeBetween(
                        Mockito.any(OpenCDXIdentifier.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXDoctorNotesService = new OpenCDXDoctorNotesServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXDoctorNotesRepository);
        this.openCDXDoctorNotesGrpcController = new OpenCDXDoctorNotesGrpcController(this.openCDXDoctorNotesService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXDoctorNotesRepository);
    }

    @Test
    void createDoctorNotes() {
        StreamObserver<CreateDoctorNotesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXDoctorNotesGrpcController.createDoctorNotes(
                CreateDoctorNotesRequest.newBuilder()
                        .setDoctorNotes(DoctorNotes.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateDoctorNotesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getDoctorNotes() {
        StreamObserver<GetDoctorNotesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXDoctorNotesGrpcController.getDoctorNotes(
                GetDoctorNotesRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetDoctorNotesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateDoctorNotes() {
        StreamObserver<UpdateDoctorNotesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXDoctorNotesGrpcController.updateDoctorNotes(
                UpdateDoctorNotesRequest.newBuilder()
                        .setDoctorNotes(DoctorNotes.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateDoctorNotesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteDoctorNotes() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXDoctorNotesGrpcController.deleteDoctorNotes(
                DeleteDoctorNotesRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listAllergies() {
        StreamObserver<ListDoctorNotesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXDoctorNotesGrpcController.listAllByPatientId(
                ListDoctorNotesRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListDoctorNotesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
