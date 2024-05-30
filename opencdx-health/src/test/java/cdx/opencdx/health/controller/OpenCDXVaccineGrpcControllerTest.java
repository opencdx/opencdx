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
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.GetVaccineByIdRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesResponse;
import cdx.opencdx.health.model.OpenCDXVaccineModel;
import cdx.opencdx.health.repository.OpenCDXVaccineRepository;
import cdx.opencdx.health.service.OpenCDXVaccineService;
import cdx.opencdx.health.service.impl.OpenCDXVaccineServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXVaccineGrpcControllerTest {

    OpenCDXVaccineService openCDXVaccineService;
    OpenCDXVaccineGrpcController openCDXVaccineGrpcController;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXVaccineRepository openCDXVaccineRepository;

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

        Mockito.when(this.openCDXVaccineRepository.save(Mockito.any(OpenCDXVaccineModel.class)))
                .thenAnswer(new Answer<OpenCDXVaccineModel>() {
                    @Override
                    public OpenCDXVaccineModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXVaccineModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXVaccineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXVaccineModel>>() {
                    @Override
                    public Optional<OpenCDXVaccineModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXVaccineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .fips("fips")
                                .healthDistrict("district")
                                .doseNumber(2)
                                .build());
                    }
                });

        Mockito.when(this.openCDXVaccineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXVaccineModel>>() {
                    @Override
                    public Optional<OpenCDXVaccineModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXVaccineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .fips("fips")
                                .healthDistrict("district")
                                .doseNumber(2)
                                .facilityType("facility")
                                .vaccine(Medication.getDefaultInstance().getDefaultInstanceForType())
                                .build());
                    }
                });

        Mockito.when(this.openCDXVaccineRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXVaccineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .fips("fips")
                                .healthDistrict("district")
                                .doseNumber(2)
                                .facilityType("facility")
                                .vaccine(Medication.getDefaultInstance().getDefaultInstanceForType())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXVaccineRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXVaccineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.openCDXVaccineService = new OpenCDXVaccineServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXVaccineRepository,
                this.openCDXDocumentValidator);
        this.openCDXVaccineGrpcController = new OpenCDXVaccineGrpcController(openCDXVaccineService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXVaccineRepository);
    }

    @Test
    void trackVaccineAdministration() {
        StreamObserver<Vaccine> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXVaccineGrpcController.trackVaccineAdministration(
                Vaccine.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setNationalHealthId(ObjectId.get().toHexString())
                        .setFips("fips")
                        .setHealthDistrict("district")
                        .setDoseNumber(2)
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Vaccine.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateVaccine() {
        StreamObserver<Vaccine> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXVaccineGrpcController.updateVaccine(
                Vaccine.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setNationalHealthId(ObjectId.get().toHexString())
                        .setFips("fips")
                        .setHealthDistrict("district")
                        .setDoseNumber(2)
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Vaccine.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getVaccineById() {
        StreamObserver<Vaccine> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXVaccineGrpcController.getVaccineById(
                GetVaccineByIdRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Vaccine.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listVaccines() {
        StreamObserver<ListVaccinesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXVaccineGrpcController.listVaccines(
                ListVaccinesRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .setSort("id")
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListVaccinesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
