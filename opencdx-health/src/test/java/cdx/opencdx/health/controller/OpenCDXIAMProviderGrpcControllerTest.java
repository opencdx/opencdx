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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.dto.npi.*;
import cdx.opencdx.health.feign.OpenCDXNpiRegistryClient;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import cdx.opencdx.health.service.impl.OpenCDXIAMProviderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    ObjectMapper objectMapper1;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OpenCDXNpiRegistryClient openCDXNpiRegistryClient;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    OpenCDXIAMProviderService openCDXIAMProviderService;
    OpenCDXIAMProviderService openCDXIAMProviderService1;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

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
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMProviderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMProviderModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXIAMProviderRepository.findByNpiNumber(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMProviderModel.builder()
                                .npiNumber(argument)
                                .id(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXIAMProviderRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);

        when(this.openCDXCountryRepository.findByName(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.of(OpenCDXCountryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);

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
                        .setProviderNumber("1245356781")
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
                        .setProviderId("1245356781")
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
                                .setProviderNumber("1245356781")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LoadProviderResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void loadProviderOpenCDXNotAcceptableAndOpenCDXServiceUnavailable() throws IOException {
        StreamObserver<LoadProviderResponse> responseObserver = mock(StreamObserver.class);
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        OpenCDXDtoNpiJsonResponse openCDXDtoNpiJsonResponse = Mockito.mock(OpenCDXDtoNpiJsonResponse.class);
        OpenCDXDtoNpiResult openCDXDtoNpiResult = Mockito.mock(OpenCDXDtoNpiResult.class);
        when(openCDXDtoNpiResult.getNumber()).thenReturn(OpenCDXIdentifier.get().toHexString());
        OpenCDXDtoNpiBasicInfo openCDXDtoNpiBasicInfo = Mockito.mock(OpenCDXDtoNpiBasicInfo.class);
        when(openCDXDtoNpiBasicInfo.getFirstName()).thenReturn("first");
        when(openCDXDtoNpiBasicInfo.getLastName()).thenReturn("last");
        when(openCDXDtoNpiBasicInfo.getCredential()).thenReturn("cred");
        when(openCDXDtoNpiBasicInfo.getSoleProprietor()).thenReturn("sole");
        when(openCDXDtoNpiBasicInfo.getGender()).thenReturn("M");
        when(openCDXDtoNpiBasicInfo.getEnumerationDate()).thenReturn("enum");
        when(openCDXDtoNpiBasicInfo.getNamePrefix()).thenReturn("namePre");
        when(openCDXDtoNpiBasicInfo.getNameSuffix()).thenReturn("nameSuf");
        when(openCDXDtoNpiResult.getBasic()).thenReturn(openCDXDtoNpiBasicInfo);
        OpenCDXDtoNpiTaxonomy openCDXDtoNpiTaxonomy = Mockito.mock(OpenCDXDtoNpiTaxonomy.class);
        when(openCDXDtoNpiResult.getTaxonomies()).thenReturn(List.of(openCDXDtoNpiTaxonomy));
        OpenCDXDtoNpiIdentifier openCDXDtoNpiIdentifier = Mockito.mock(OpenCDXDtoNpiIdentifier.class);
        when(openCDXDtoNpiIdentifier.getCode()).thenReturn("code");
        when(openCDXDtoNpiIdentifier.getDesc()).thenReturn("desc");
        when(openCDXDtoNpiIdentifier.getIdentifier()).thenReturn("identifier");
        when(openCDXDtoNpiIdentifier.getState()).thenReturn("state");
        when(openCDXDtoNpiResult.getIdentifiers()).thenReturn(List.of(openCDXDtoNpiIdentifier));
        when(openCDXDtoNpiJsonResponse.getResults()).thenReturn(List.of(openCDXDtoNpiResult));
        Mockito.when(this.objectMapper1.readValue(anyString(), Mockito.eq(OpenCDXDtoNpiJsonResponse.class)))
                .thenReturn(openCDXDtoNpiJsonResponse);
        Mockito.when(openCDXCurrentUser1.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser1.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMProviderService1 = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser1,
                this.openCDXNpiRegistryClient);
        openCDXIAMProviderGrpcController = new OpenCDXIAMProviderGrpcController(this.openCDXIAMProviderService1);
        LoadProviderRequest request = LoadProviderRequest.newBuilder(
                        LoadProviderRequest.newBuilder().setUserId("1245356781").build())
                .build();
        Assertions.assertThrows(
                OpenCDXServiceUnavailable.class,
                () -> openCDXIAMProviderGrpcController.loadProvider(request, responseObserver));
    }
}
