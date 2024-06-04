package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.TemperatureMeasurement;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXTemperatureMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXTemperatureMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXTemperatureMeasurementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXTemperatureMeasurementServiceImplTest {

    OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXTemperatureMeasurementRepository.save(Mockito.any(OpenCDXTemperatureMeasurementModel.class)))
                .thenAnswer(new Answer<OpenCDXTemperatureMeasurementModel>() {
                    @Override
                    public OpenCDXTemperatureMeasurementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXTemperatureMeasurementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXTemperatureMeasurementModel>>() {
                    @Override
                    public Optional<OpenCDXTemperatureMeasurementModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXTemperatureMeasurementModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
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

        this.openCDXTemperatureMeasurementService = new OpenCDXTemperatureMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXTemperatureMeasurementRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createTemperatureMeasurement() {
        CreateTemperatureMeasurementRequest request = CreateTemperatureMeasurementRequest.newBuilder()
                .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXTemperatureMeasurementService.createTemperatureMeasurement(request));
    }

    @Test
    void getTemperatureMeasurementOpenCDXNotFound() {
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetTemperatureMeasurementRequest request = GetTemperatureMeasurementRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXTemperatureMeasurementService.getTemperatureMeasurement(request));
    }

    @Test
    void getTemperatureMeasurementOpenCDXNotAcceptable() {
        GetTemperatureMeasurementRequest request = GetTemperatureMeasurementRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXTemperatureMeasurementService.getTemperatureMeasurement(request));
    }

    @Test
    void updateTemperatureMeasurement() {
        UpdateTemperatureMeasurementRequest request = UpdateTemperatureMeasurementRequest.newBuilder()
                .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXTemperatureMeasurementService.updateTemperatureMeasurement(request));
    }

    @Test
    void updateTemperatureMeasurementOpenCDXNotFound() {
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        UpdateTemperatureMeasurementRequest request = UpdateTemperatureMeasurementRequest.newBuilder()
                .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXTemperatureMeasurementService.updateTemperatureMeasurement(request));
    }

    @Test
    void deleteTemperatureMeasurementOpenCDXNotFound() {
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteTemperatureMeasurementRequest request = DeleteTemperatureMeasurementRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXTemperatureMeasurementService.deleteTemperatureMeasurement(request));
    }

    @Test
    void deleteTemperatureMeasurementOpenCDXNotAcceptable() {
        DeleteTemperatureMeasurementRequest request = DeleteTemperatureMeasurementRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXTemperatureMeasurementService.deleteTemperatureMeasurement(request));
    }

    @Test
    void listTemperatureMeasurementsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTemperatureMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                        .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                        .build()),
                                PageRequest.of(1, 10),
                                1));
        ListTemperatureMeasurementsRequest request = ListTemperatureMeasurementsRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXTemperatureMeasurementService.listTemperatureMeasurements(request));
    }

    @Test
    void listTemperatureMeasurementsSortNotAscending() {
        ListTemperatureMeasurementsRequest request = ListTemperatureMeasurementsRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXTemperatureMeasurementService.listTemperatureMeasurements(request));
    }
}