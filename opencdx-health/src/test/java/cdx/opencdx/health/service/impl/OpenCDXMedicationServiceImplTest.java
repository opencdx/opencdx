package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.medication.EndMedicationRequest;
import cdx.opencdx.grpc.health.medication.ListMedicationsRequest;
import cdx.opencdx.grpc.health.medication.Medication;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXMedicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
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
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.save(Mockito.any(OpenCDXMedicationModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicationModel>() {
                    @Override
                    public OpenCDXMedicationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(Mockito.any(ObjectId.class),Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationRepository.findAllByNationalHealthId(Mockito.any(String.class),Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXMedicationRepository.findAllByPatientIdAndEndDateIsNull(Mockito.any(ObjectId.class),Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationRepository.findAllByNationalHealthIdAndEndDateIsNull(Mockito.any(String.class),Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);
    }

    @Test
    void prescribing_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        Medication medication = Medication.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setMedicationName("medication")
                .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.prescribing(medication));
    }

    @Test
    void ending_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(ObjectId.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.ending(endMedicationRequest));
    }

    @Test
    void listAllMedications_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.listAllMedications(listMedicationsRequest));
    }

    @Test
    void listCurrentMedications_objectMapper() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        ListMedicationsRequest listMedicationsRequest = ListMedicationsRequest.newBuilder()
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.listCurrentMedications(listMedicationsRequest));
    }

    @Test
    void prescribing_profileRepository() throws JsonProcessingException {
        this.openCDXProfileRepository = mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        Medication medication = Medication.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setMedicationName("medication")
                .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.prescribing(medication));
    }

    @Test
    void ending_profileRepository() throws JsonProcessingException {
        this.openCDXProfileRepository = mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(ObjectId.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.ending(endMedicationRequest));
    }

    @Test
    void ending_medicationRepository() throws JsonProcessingException {
        this.openCDXMedicationRepository = mock(OpenCDXMedicationRepository.class);
        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        this.openCDXMedicationService = new OpenCDXMedicationServiceImpl(
                this.objectMapper,this.openCDXAuditService, this.openCDXCurrentUser,this.openCDXMedicationRepository,this.openCDXProfileRepository,this.openCDXApiFDA);

        EndMedicationRequest endMedicationRequest = EndMedicationRequest.newBuilder()
                .setMedicationId(ObjectId.get().toHexString())
                .setEndDate(Timestamp.newBuilder().setSeconds(1696933104).build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () ->this.openCDXMedicationService.ending(endMedicationRequest));
    }
}