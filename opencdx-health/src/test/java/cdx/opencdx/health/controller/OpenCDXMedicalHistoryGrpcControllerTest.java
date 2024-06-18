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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.MedicalHistory;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.Relationship;
import cdx.opencdx.grpc.types.RelationshipFamilyLine;
import cdx.opencdx.health.model.OpenCDXMedicalHistoryModel;
import cdx.opencdx.health.repository.OpenCDXMedicalHistoryRepository;
import cdx.opencdx.health.service.OpenCDXMedicalHistoryService;
import cdx.opencdx.health.service.impl.OpenCDXMedicalHistoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
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
class OpenCDXMedicalHistoryGrpcControllerTest {

    OpenCDXMedicalHistoryService openCDXMedicalHistoryService;
    OpenCDXMedicalHistoryGrpcController openCDXMedicalHistoryGrpcController;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXMedicalHistoryRepository.save(Mockito.any(OpenCDXMedicalHistoryModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicalHistoryModel>() {
                    @Override
                    public OpenCDXMedicalHistoryModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicalHistoryModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicalHistoryRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalHistoryModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalHistoryModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .relationship(Relationship.MOTHER)
                                .relationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicalHistoryRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalHistoryModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalHistoryModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .relationship(Relationship.MOTHER)
                                .relationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicalHistoryRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .relationship(Relationship.MOTHER)
                                .relationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicalHistoryRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.openCDXMedicalHistoryService = new OpenCDXMedicalHistoryServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalHistoryRepository);

        this.openCDXMedicalHistoryGrpcController =
                new OpenCDXMedicalHistoryGrpcController(openCDXMedicalHistoryService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXMedicalHistoryRepository);
    }

    @Test
    void createMedicalHistory() {
        StreamObserver<CreateMedicalHistoryResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalHistoryGrpcController.createMedicalHistory(
                CreateMedicalHistoryRequest.newBuilder()
                        .setMedicalHistory(MedicalHistory.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setPatientId(ObjectId.get().toHexString())
                                .setNationalHealthId(ObjectId.get().toHexString())
                                .setRelationship(Relationship.MOTHER)
                                .setRelationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateMedicalHistoryResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedicalHistoryById() {
        StreamObserver<GetMedicalHistoryResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalHistoryGrpcController.getMedicalHistory(
                GetMedicalHistoryRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetMedicalHistoryResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listMedicalHistories() {
        StreamObserver<ListMedicalHistoriesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalHistoryGrpcController.listMedicalHistories(
                ListMedicalHistoriesRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .setSort("id")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListMedicalHistoriesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
