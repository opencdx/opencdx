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
import cdx.opencdx.grpc.data.BPM;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.ArmUsed;
import cdx.opencdx.grpc.types.BPMUnits;
import cdx.opencdx.grpc.types.CuffSize;
import cdx.opencdx.health.model.OpenCDXBPMModel;
import cdx.opencdx.health.repository.OpenCDXBPMRepository;
import cdx.opencdx.health.service.OpenCDXBPMService;
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
class OpenCDXBPMServiceImplTest {

    OpenCDXBPMService openCDXBPMService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXBPMRepository openCDXBPMRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXBPMRepository.save(Mockito.any(OpenCDXBPMModel.class)))
                .thenAnswer(new Answer<OpenCDXBPMModel>() {
                    @Override
                    public OpenCDXBPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXBPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXBPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXBPMModel>>() {
                    @Override
                    public Optional<OpenCDXBPMModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXBPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
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

        this.openCDXBPMService = new OpenCDXBPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXBPMRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createBPM() {
        CreateBPMRequest request = CreateBPMRequest.newBuilder()
                .setBpmMeasurement(BPM.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXBPMService.createBPMMeasurement(request));
    }

    @Test
    void getBPMOpenCDXNotFound() {
        Mockito.when(this.openCDXBPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetBPMRequest request = GetBPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXBPMService.getBPMMeasurement(request));
    }

    @Test
    void getBPMOpenCDXNotAcceptable() {
        GetBPMRequest request = GetBPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXBPMService.getBPMMeasurement(request));
    }

    @Test
    void updateBPM() {
        UpdateBPMRequest request = UpdateBPMRequest.newBuilder()
                .setBpmMeasurement(BPM.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXBPMService.updateBPMMeasurement(request));
    }

    @Test
    void deleteBPMOpenCDXNotFound() {
        Mockito.when(this.openCDXBPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteBPMRequest request = DeleteBPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXBPMService.deleteBPMMeasurement(request));
    }

    @Test
    void deleteBPMOpenCDXNotAcceptable() {
        DeleteBPMRequest request = DeleteBPMRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXBPMService.deleteBPMMeasurement(request));
    }

    @Test
    void listBPMsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXBPMRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXBPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListBPMRequest request = ListBPMRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXBPMService.listBPMMeasurements(request));
    }

    @Test
    void listBPMsSortNotAscending() {
        ListBPMRequest request = ListBPMRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXBPMService.listBPMMeasurements(request));
    }
}
