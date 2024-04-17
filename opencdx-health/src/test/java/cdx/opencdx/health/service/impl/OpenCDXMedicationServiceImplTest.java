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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.EndMedicationRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXMedicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
class OpenCDXMedicationServiceImplTest {

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
        Mockito.when(this.openCDXMedicationRepository.findAllByPatientIdAndEndDateIsNull(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
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
    }

    @Test
    void prescribing_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        Medication medication = Medication.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setMedicationName("medication")
                .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationService.prescribing(medication));
    }

    @Test
    void ending_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(OpenCDXIdentifier.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationService.ending(endMedicationRequest));
    }

    @Test
    void listAllMedications_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXMedicationService.listAllMedications(listMedicationsRequest));
    }

    @Test
    void listCurrentMedications_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXMedicationService.listCurrentMedications(listMedicationsRequest));
    }

    @Test
    void prescribing_profileRepository() throws JsonProcessingException {
        this.openCDXProfileRepository = mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        Medication medication = Medication.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setMedicationName("medication")
                .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationService.prescribing(medication));
    }

    @Test
    void ending_profileRepository() throws JsonProcessingException {
        this.openCDXProfileRepository = mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(OpenCDXIdentifier.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationService.ending(endMedicationRequest));
    }

    @Test
    void ending_medicationRepository() throws JsonProcessingException {
        this.openCDXMedicationRepository = mock(OpenCDXMedicationRepository.class);
        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationRepository,
                this.openCDXProfileRepository,
                this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(OpenCDXIdentifier.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXMedicationService.ending(endMedicationRequest));
    }
}
