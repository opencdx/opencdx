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
import cdx.opencdx.grpc.data.KnownAllergy;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXAllergyModel;
import cdx.opencdx.health.repository.OpenCDXAllergyRepository;
import cdx.opencdx.health.service.OpenCDXAllergyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
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
class OpenCDXAllergyServiceImplTest {

    OpenCDXAllergyService openCDXAllergyService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXAllergyRepository openCDXAllergyRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXAllergyRepository.save(Mockito.any(OpenCDXAllergyModel.class)))
                .thenAnswer(new Answer<OpenCDXAllergyModel>() {
                    @Override
                    public OpenCDXAllergyModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXAllergyModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXAllergyRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXAllergyModel>>() {
                    @Override
                    public Optional<OpenCDXAllergyModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate(Instant.now())
                                .lastOccurrence(Instant.now())
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

        this.openCDXAllergyService = new OpenCDXAllergyServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXAllergyRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createAllergy() {
        CreateAllergyRequest request = CreateAllergyRequest.newBuilder()
                .setKnownAllergy(KnownAllergy.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXAllergyService.createAllergy(request));
    }

    @Test
    void getAllergyOpenCDXNotFound() {
        Mockito.when(this.openCDXAllergyRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetAllergyRequest request = GetAllergyRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXAllergyService.getAllergy(request));
    }

    @Test
    void getBPMOpenCDXNotAcceptable() {
        GetAllergyRequest request = GetAllergyRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXAllergyService.getAllergy(request));
    }

    @Test
    void updateBPM() {
        UpdateAllergyRequest request = UpdateAllergyRequest.newBuilder()
                .setKnownAllergy(KnownAllergy.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXAllergyService.updateAllergy(request));
    }

    @Test
    void deleteBPMOpenCDXNotFound() {
        Mockito.when(this.openCDXAllergyRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteAllergyRequest request = DeleteAllergyRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXAllergyService.deleteAllergy(request));
    }

    @Test
    void deleteBPMOpenCDXNotAcceptable() {
        DeleteAllergyRequest request = DeleteAllergyRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXAllergyService.deleteAllergy(request));
    }

    @Test
    void listBPMsOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXAllergyRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate(Instant.now())
                                .lastOccurrence(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListAllergyRequest request = ListAllergyRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXAllergyService.listAllergies(request));
    }

    @Test
    void listBPMsSortNotAscending() {
        ListAllergyRequest request = ListAllergyRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXAllergyService.listAllergies(request));
    }
}
