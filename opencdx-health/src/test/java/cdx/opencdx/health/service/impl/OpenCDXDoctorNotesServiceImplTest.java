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
import cdx.opencdx.grpc.data.DoctorNotes;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXDoctorNotesModel;
import cdx.opencdx.health.repository.OpenCDXDoctorNotesRepository;
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
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
class OpenCDXDoctorNotesServiceImplTest {
    OpenCDXDoctorNotesService openCDXDoctorNotesService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

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

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXDoctorNotesService = new OpenCDXDoctorNotesServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXDoctorNotesRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createDoctorNotes() {
        CreateDoctorNotesRequest request = CreateDoctorNotesRequest.newBuilder()
                .setDoctorNotes(DoctorNotes.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.createDoctorNotes(request));
    }

    @Test
    void getDoctorNotesOpenCDXNotFound() {
        Mockito.when(this.openCDXDoctorNotesRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetDoctorNotesRequest request = GetDoctorNotesRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXDoctorNotesService.getDoctorNotes(request));
    }

    @Test
    void getDoctorNotesOpenCDXNotAcceptable() {
        GetDoctorNotesRequest request = GetDoctorNotesRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.getDoctorNotes(request));
    }

    @Test
    void updateDoctorNotes() {
        UpdateDoctorNotesRequest request = UpdateDoctorNotesRequest.newBuilder()
                .setDoctorNotes(DoctorNotes.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.updateDoctorNotes(request));
    }

    @Test
    void deleteDoctorNotesOpenCDXNotFound() {
        Mockito.when(this.openCDXDoctorNotesRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteDoctorNotesRequest request = DeleteDoctorNotesRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXDoctorNotesService.deleteDoctorNotes(request));
    }

    @Test
    void deleteDoctorNotesOpenCDXNotAcceptable() throws JsonProcessingException {
        Mockito.when(this.openCDXDoctorNotesRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXDoctorNotesModel>>() {
                    @Override
                    public Optional<OpenCDXDoctorNotesModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build());
                    }
                });

        DeleteDoctorNotesRequest request = DeleteDoctorNotesRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXDoctorNotesService = new OpenCDXDoctorNotesServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXDoctorNotesRepository);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.deleteDoctorNotes(request));
    }

    @Test
    void listDoctorNotessOpenCDXNotAcceptable() {
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }

    @Test
    void listDoctorNotesSortNotAscending() {
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setStartDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .setEndDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }

    @Test
    void listDoctorNotesSortNotAscendingTagandDate() {
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTags("tags")
                .setStartDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .setEndDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }

    @Test
    void listDoctorNotesSortNotAscendingOnlyTag() {
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTags(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(), Mockito.any(Pageable.class)))
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTags("tags")
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }

    @Test
    void listDoctorNotesSortNotAscendingWithTagAndStartDateOnly() {
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTags(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(), Mockito.any(Pageable.class)))
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTags("tags")
                .setStartDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }

    @Test
    void listDoctorNotesSortNotAscendingWithStartDateOnly() {
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
        ListDoctorNotesRequest request = ListDoctorNotesRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setStartDate(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXDoctorNotesService.listAllByPatientId(request));
    }
}
