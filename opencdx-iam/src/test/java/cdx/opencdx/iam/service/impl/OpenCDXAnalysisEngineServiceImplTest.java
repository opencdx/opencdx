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
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.AnalysisEngine;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.iam.*;
import cdx.opencdx.iam.model.OpenCDXAnalysisEngineModel;
import cdx.opencdx.iam.repository.OpenCDXAnalysisEngineRepository;
import cdx.opencdx.iam.service.OpenCDXAnalysisEngineService;
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

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXAnalysisEngineServiceImplTest {

    OpenCDXAnalysisEngineService openCDXAnalysisEngineService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXAnalysisEngineRepository openCDXAnalysisEngineRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXAnalysisEngineRepository.save(Mockito.any(OpenCDXAnalysisEngineModel.class)))
                .thenAnswer(new Answer<OpenCDXAnalysisEngineModel>() {
                    @Override
                    public OpenCDXAnalysisEngineModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXAnalysisEngineModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXAnalysisEngineModel>>() {
                    @Override
                    public Optional<OpenCDXAnalysisEngineModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXAnalysisEngineModel.builder()
                                .id(argument)
                                .organizationId(OpenCDXIdentifier.get())
                                .workspaceId(OpenCDXIdentifier.get())
                                .name("defaultName")
                                .build());
                    }
                });
        Mockito.when(this.openCDXAnalysisEngineRepository.findAllByOrganizationId(
                        Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAnalysisEngineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .organizationId((OpenCDXIdentifier.get()))
                                .workspaceId(OpenCDXIdentifier.get())
                                .name("default")
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

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXAnalysisEngineService = new OpenCDXAnalysisEngineServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXAnalysisEngineRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createAnalysisEngine() {
        CreateAnalysisEngineRequest request = CreateAnalysisEngineRequest.newBuilder()
                .setAnalysisEngine(AnalysisEngine.newBuilder()
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXAnalysisEngineService.createAnalysisEngine(request));
    }

    @Test
    void getAnalysisEngineOpenCDXNotFound() {
        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        GetAnalysisEngineRequest request = GetAnalysisEngineRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXAnalysisEngineService.getAnalysisEngine(request));
    }

    @Test
    void getAnalysisEngineOpenCDXNotAcceptable() {
        GetAnalysisEngineRequest request = GetAnalysisEngineRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXAnalysisEngineService.getAnalysisEngine(request));
    }

    @Test
    void updateAnalysisEngine() {
        UpdateAnalysisEngineRequest request = UpdateAnalysisEngineRequest.newBuilder()
                .setAnalysisEngine(AnalysisEngine.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXAnalysisEngineService.updateAnalysisEngine(request));
    }

    @Test
    void updateAnalysisEngineOpenCDXNotFound() {
        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        UpdateAnalysisEngineRequest request = UpdateAnalysisEngineRequest.newBuilder()
                .setAnalysisEngine(AnalysisEngine.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXAnalysisEngineService.updateAnalysisEngine(request));
    }

    @Test
    void deleteAnalysisEngineOpenCDXNotFound() {
        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteAnalysisEngineRequest request = DeleteAnalysisEngineRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXAnalysisEngineService.deleteAnalysisEngine(request));
    }

    @Test
    void deleteAnalysisEngineOpenCDXNotAcceptable() {
        DeleteAnalysisEngineRequest request = DeleteAnalysisEngineRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXAnalysisEngineService.deleteAnalysisEngine(request));
    }

    @Test
    void listAnalysisEnginesOpenCDXNotAcceptable() {
        Mockito.when(this.openCDXAnalysisEngineRepository.findAllByOrganizationId(
                        Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAnalysisEngineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .organizationId(OpenCDXIdentifier.get())
                                .workspaceId(OpenCDXIdentifier.get())
                                .name("defaultName")
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        ListAnalysisEnginesRequest request = ListAnalysisEnginesRequest.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXAnalysisEngineService.listAnalysisEngines(request));
    }
    /*
       @Test
       void listAnalysisEnginesSortNotAscending() {
           ListAnalysisEnginesRequest request = ListAnalysisEnginesRequest.newBuilder()
                   .setPagination(Pagination.newBuilder()
                           .setPageNumber(1)
                           .setPageSize(10)
                           .setSortAscending(false)
                           .setSort("id")
                           .build())
                   .build();
           Assertions.assertDoesNotThrow(() -> this.openCDXAnalysisEngineService.listAnalysisEngines(request));
       }
    */
}
