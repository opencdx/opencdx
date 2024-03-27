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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXHeightMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXHeightMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXHeightMeasurementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
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
class OpenCDXHeightMeasurementServiceImplTest {

    OpenCDXHeightMeasurementService openCDXHeightMeasurementService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXHeightMeasurementRepository.save(Mockito.any(OpenCDXHeightMeasurementModel.class)))
                .thenAnswer(new Answer<OpenCDXHeightMeasurementModel>() {
                    @Override
                    public OpenCDXHeightMeasurementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXHeightMeasurementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXHeightMeasurementRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXHeightMeasurementModel>>() {
                    @Override
                    public Optional<OpenCDXHeightMeasurementModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXHeightMeasurementModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXHeightMeasurementService = new OpenCDXHeightMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXHeightMeasurementRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createHeightMeasurement() {
        CreateHeightMeasurementRequest request = CreateHeightMeasurementRequest.newBuilder()
                .setHeightMeasurement(HeightMeasurement.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXHeightMeasurementService.createHeightMeasurement(request));
    }

    @Test
    void getHeightMeasurementOpenCDXNotFound() {
        Mockito.when(this.openCDXHeightMeasurementRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        GetHeightMeasurementRequest request = GetHeightMeasurementRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXHeightMeasurementService.getHeightMeasurement(request));
    }

    @Test
    void getHeightMeasurementOpenCDXNotAcceptable() {
        GetHeightMeasurementRequest request = GetHeightMeasurementRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeightMeasurementService.getHeightMeasurement(request));
    }

    @Test
    void updateHeightMeasurement() {
        UpdateHeightMeasurementRequest request = UpdateHeightMeasurementRequest.newBuilder()
                .setHeightMeasurement(HeightMeasurement.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXHeightMeasurementService.updateHeightMeasurement(request));
    }

    @Test
    void deleteHeightMeasurementOpenCDXNotFound() {
        Mockito.when(this.openCDXHeightMeasurementRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        DeleteHeightMeasurementRequest request = DeleteHeightMeasurementRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXHeightMeasurementService.deleteHeightMeasurement(request));
    }

    @Test
    void deleteHeightMeasurementOpenCDXNotAcceptable() {
        DeleteHeightMeasurementRequest request = DeleteHeightMeasurementRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXHeightMeasurementService.deleteHeightMeasurement(request));
    }

    @Test
    void listHeightMeasurementsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXHeightMeasurementRepository.findAllByPatientId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeightMeasurementModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListHeightMeasurementsRequest request = ListHeightMeasurementsRequest.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setPatientId(ObjectId.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeightMeasurementService.listHeightMeasurements(request));
    }

    @Test
    void listHeightMeasurementsSortNotAscending() {
        ListHeightMeasurementsRequest request = ListHeightMeasurementsRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXHeightMeasurementService.listHeightMeasurements(request));
    }
}
