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
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.provider.*;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import cdx.opencdx.health.service.impl.OpenCDXIAMProviderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMProviderGrpcControllerTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    OpenCDXIAMProviderService openCDXIAMProviderService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXIAMProviderGrpcController openCDXIAMProviderGrpcController;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @BeforeEach
    void setUp() {
        this.openCDXIAMProviderRepository = mock(OpenCDXIAMProviderRepository.class);
        Mockito.when(this.openCDXIAMProviderRepository.save(Mockito.any(OpenCDXIAMProviderModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMProviderModel>() {
                    @Override
                    public OpenCDXIAMProviderModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMProviderModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMProviderRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMProviderModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXIAMProviderRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);

        when(this.openCDXCountryRepository.findByName(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.of(
                                OpenCDXCountryModel.builder().id(ObjectId.get()).build());
                    }
                });

        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper,
                this.openCDXCountryRepository);

        this.openCDXIAMProviderGrpcController = new OpenCDXIAMProviderGrpcController(this.openCDXIAMProviderService);
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMProviderRepository);
    }

    @Test
    void getProviderByNumber() {
        StreamObserver<GetProviderResponse> responseObserver = mock(StreamObserver.class);
        this.openCDXIAMProviderGrpcController.getProviderByNumber(
                GetProviderRequest.newBuilder(GetProviderRequest.getDefaultInstance())
                        .setProviderNumber(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetProviderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteProvider() {
        StreamObserver<DeleteProviderResponse> responseObserver = mock(StreamObserver.class);
        this.openCDXIAMProviderGrpcController.deleteProvider(
                DeleteProviderRequest.newBuilder()
                        .build()
                        .newBuilder(DeleteProviderRequest.getDefaultInstance())
                        .setProviderId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteProviderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listProviders() {
        StreamObserver<ListProvidersResponse> responseObserver = mock(StreamObserver.class);
        this.openCDXIAMProviderGrpcController.listProviders(
                ListProvidersRequest.newBuilder(ListProvidersRequest.getDefaultInstance())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListProvidersResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void loadProvider() {
        StreamObserver<LoadProviderResponse> responseObserver = mock(StreamObserver.class);
        this.openCDXIAMProviderGrpcController.loadProvider(
                LoadProviderRequest.newBuilder(LoadProviderRequest.newBuilder()
                                .setUserId("1679736037")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LoadProviderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
