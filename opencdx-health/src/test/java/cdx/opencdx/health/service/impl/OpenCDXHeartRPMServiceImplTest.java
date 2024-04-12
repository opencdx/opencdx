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
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXHeartRPMModel;
import cdx.opencdx.health.repository.OpenCDXHeartRPMRepository;
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
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
class OpenCDXHeartRPMServiceImplTest {
    OpenCDXHeartRPMService openCDXHeartRPMService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXHeartRPMRepository openCDXHeartRPMRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXHeartRPMRepository.save(Mockito.any(OpenCDXHeartRPMModel.class)))
                .thenAnswer(new Answer<OpenCDXHeartRPMModel>() {
                    @Override
                    public OpenCDXHeartRPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXHeartRPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXHeartRPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXHeartRPMModel>>() {
                    @Override
                    public Optional<OpenCDXHeartRPMModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXHeartRPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
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

        this.openCDXHeartRPMService = new OpenCDXHeartRPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXHeartRPMRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createHeartRPM() {
        CreateHeartRPMRequest request = CreateHeartRPMRequest.newBuilder()
                .setHeartRpmMeasurement(HeartRPM.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeartRPMService.createHeartRPMMeasurement(request));
    }

    @Test
    void getHeartRPMOpenCDXNotFound() {
        Mockito.when(this.openCDXHeartRPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetHeartRPMRequest request = GetHeartRPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXHeartRPMService.getHeartRPMMeasurement(request));
    }

    @Test
    void getHeartRPMOpenCDXNotAcceptable() {
        GetHeartRPMRequest request = GetHeartRPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeartRPMService.getHeartRPMMeasurement(request));
    }

    @Test
    void updateHeartRPM() {
        UpdateHeartRPMRequest request = UpdateHeartRPMRequest.newBuilder()
                .setHeartRpmMeasurement(HeartRPM.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeartRPMService.updateHeartRPMMeasurement(request));
    }

    @Test
    void deleteHeartRPMOpenCDXNotFound() {
        Mockito.when(this.openCDXHeartRPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteHeartRPMRequest request = DeleteHeartRPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXHeartRPMService.deleteHeartRPMMeasurement(request));
    }

    @Test
    void deleteHeartRPMOpenCDXNotAcceptable() {
        DeleteHeartRPMRequest request = DeleteHeartRPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeartRPMService.deleteHeartRPMMeasurement(request));
    }

    @Test
    void listHeartRPMsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXHeartRPMRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeartRPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListHeartRPMRequest request = ListHeartRPMRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXHeartRPMService.listHeartRPMMeasurements(request));
    }

    @Test
    void listHeartRPMsSortNotAscending() {
        ListHeartRPMRequest request = ListHeartRPMRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXHeartRPMService.listHeartRPMMeasurements(request));
    }
}
