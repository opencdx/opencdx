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

import static org.mockito.Mockito.mock;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
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
import cdx.opencdx.health.service.impl.OpenCDXAllergyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
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
class OpenCDXAllergyGrpcControllerTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXAllergyRepository openCDXAllergyRepository;

    OpenCDXAllergyService openCDXAllergyService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXAllergyGrpcController openCDXAllergyGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXAllergyRepository = mock(OpenCDXAllergyRepository.class);

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
                                .id(argument)
                                .patientId(argument)
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate("onSet")
                                .lastOccurrence("lastOccurrence")
                                .notes("notes")
                                .build());
                    }
                });

        Mockito.when(this.openCDXAllergyRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate("onSet")
                                .lastOccurrence("lastOccurrence")
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXAllergyRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate("onSet")
                                .lastOccurrence("lastOccurrence")
                                .notes("notes")
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

        this.openCDXAllergyService = new OpenCDXAllergyServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXAllergyRepository);
        this.openCDXAllergyGrpcController = new OpenCDXAllergyGrpcController(this.openCDXAllergyService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXAllergyRepository);
    }

    @Test
    void createAllergy() {
        StreamObserver<CreateAllergyResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAllergyGrpcController.createAllergy(
                CreateAllergyRequest.newBuilder()
                        .setKnownAllergy(KnownAllergy.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateAllergyResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getAllergy() {
        StreamObserver<GetAllergyResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAllergyGrpcController.getAllergy(
                GetAllergyRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetAllergyResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateAllergy() {
        StreamObserver<UpdateAllergyResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAllergyGrpcController.updateAllergy(
                UpdateAllergyRequest.newBuilder()
                        .setKnownAllergy(KnownAllergy.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateAllergyResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteAllergy() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAllergyGrpcController.deleteAllergy(
                DeleteAllergyRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listAllergies() {
        StreamObserver<ListAllergyResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAllergyGrpcController.listAllergies(
                ListAllergyRequest.newBuilder()
                        .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListAllergyResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
