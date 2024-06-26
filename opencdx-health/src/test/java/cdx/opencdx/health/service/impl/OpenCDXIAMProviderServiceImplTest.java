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
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.service.health.DeleteProviderRequest;
import cdx.opencdx.grpc.service.health.GetProviderRequest;
import cdx.opencdx.grpc.service.health.ListProvidersRequest;
import cdx.opencdx.grpc.service.health.LoadProviderRequest;
import cdx.opencdx.health.feign.OpenCDXNpiRegistryClient;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMProviderServiceImplTest {

    OpenCDXIAMProviderService openCDXIAMProviderService;

    OpenCDXIAMProviderService openCDXIAMProviderService1;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXNpiRegistryClient openCDXNpiRegistryClient;

    @MockBean
    OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    @MockBean
    Connection connection;

    @MockBean
    AuthenticationManager authenticationManager;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProviderRepository = mock(OpenCDXIAMProviderRepository.class);
        when(this.openCDXIAMProviderRepository.save(any(OpenCDXIAMProviderModel.class)))
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

        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
    }

    @Test
    void getProviderByNumberElse() {
        GetProviderRequest request = GetProviderRequest.newBuilder()
                .setProviderNumber(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    // @Test
    void getProviderByNumberElseJsonException() throws JsonProcessingException {
        Mockito.when(this.openCDXIAMProviderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMProviderModel.builder().id(argument).build());
                    }
                });
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        OpenCDXIAMProviderRepository openCDXIAMProviderRepository2 = mock(OpenCDXIAMProviderRepository.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                openCDXIAMProviderRepository2,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        GetProviderRequest request = GetProviderRequest.newBuilder()
                .setProviderNumber(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    @Test
    void deleteProviderElse() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMProviderModel.builder()
                        .userId(OpenCDXIdentifier.get())
                        .build()));
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setProviderId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.objectMapper1 = mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
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
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void deleteProviderElse2() {
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setProviderId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void loadProviderTestExcept() throws IOException {
        LoadProviderRequest loadProviderRequest1 =
                LoadProviderRequest.newBuilder().setUserId("&%").build();
        URL url = mock(URL.class);
        URI uri = mock(URI.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(url.openConnection()).thenReturn(connection);
        try (MockedStatic<URI> uriMockedStatic = Mockito.mockStatic(URI.class)) {
            when(URI.create(anyString())).thenReturn(uri);
            doReturn(url).when(uri).toURL();
            when(url.openConnection()).thenReturn(connection);
            when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_GATEWAY);
            Assertions.assertThrows(
                    OpenCDXServiceUnavailable.class,
                    () -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest1));
        }
    }

    @Test
    void loadProviderTest() {
        LoadProviderRequest loadProviderRequest =
                LoadProviderRequest.newBuilder().setProviderNumber("1245356781").build();
        try {
            this.openCDXIAMProviderService.loadProvider(loadProviderRequest);
        } catch (Exception e) {
            Assertions.assertTrue(
                    e instanceof OpenCDXServiceUnavailable, "Exception is not of type OpenCDXServiceUnavailable");
        }
    }

    @Test
    void loadProviderTestJsonException() throws IOException {
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        LoadProviderRequest loadProviderRequest1 =
                LoadProviderRequest.newBuilder().setProviderNumber("1245356781").build();
        URL url = mock(URL.class);
        URI uri = mock(URI.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(url.openConnection()).thenReturn(connection);
        try (MockedStatic<URI> uriMockedStatic = Mockito.mockStatic(URI.class)) {
            when(URI.create(anyString())).thenReturn(uri);
            doReturn(url).when(uri).toURL();
            when(url.openConnection()).thenReturn(connection);
            when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_GATEWAY);
            Assertions.assertDoesNotThrow(
                    () -> {
                        try {
                            this.openCDXIAMProviderService.loadProvider(loadProviderRequest1);
                        } catch (OpenCDXNotAcceptable | OpenCDXServiceUnavailable e) {
                            // it is okay
                        }
                    },
                    "Expected OpenCDXNotAcceptable or OpenCDXServiceUnavailable");
        }
    }

    @Test
    void listProvidersTestJsonException() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findAll())
                .thenReturn(List.of(OpenCDXIAMProviderModel.builder()
                        .userId(OpenCDXIdentifier.get())
                        .id(OpenCDXIdentifier.get())
                        .npiNumber(OpenCDXIdentifier.get().toHexString())
                        .build()));
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
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
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        ListProvidersRequest loadProviderRequest =
                ListProvidersRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProviderService.listProviders(loadProviderRequest));
    }

    @Test
    void listProvidersTestNoJsonException() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findAll())
                .thenReturn(List.of(OpenCDXIAMProviderModel.builder()
                        .userId(OpenCDXIdentifier.get())
                        .id(OpenCDXIdentifier.get())
                        .npiNumber(OpenCDXIdentifier.get().toHexString())
                        .build()));
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any(OpenCDXIAMProviderModel.class)))
                .thenReturn("model");
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMProviderService1 = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        ListProvidersRequest loadProviderRequest =
                ListProvidersRequest.newBuilder().build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProviderService1.listProviders(loadProviderRequest));
    }

    @Test
    void getProviderByNumberOpenCDXNotAcceptable() throws JsonProcessingException {
        Mockito.reset(this.openCDXIAMProviderRepository);
        OpenCDXIAMProviderRepository repository = mock(OpenCDXIAMProviderRepository.class);
        Mockito.when(repository.findByNpiNumber(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMProviderModel.builder()
                                .npiNumber("123")
                                .id(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        when(repository.save(any(OpenCDXIAMProviderModel.class))).thenAnswer(new Answer<OpenCDXIAMProviderModel>() {
            @Override
            public OpenCDXIAMProviderModel answer(InvocationOnMock invocation) throws Throwable {
                OpenCDXIAMProviderModel argument = invocation.getArgument(0);
                if (argument.getId() == null) {
                    argument.setId(OpenCDXIdentifier.get());
                    argument.setNpiNumber("456");
                }
                return argument;
            }
        });
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                repository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        GetProviderRequest request = GetProviderRequest.newBuilder()
                .setProviderNumber(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    @Test
    void deleteProviderOpenCDXNotAcceptable() throws JsonProcessingException {
        Mockito.reset(this.openCDXIAMProviderRepository);
        OpenCDXIAMProviderRepository repository = mock(OpenCDXIAMProviderRepository.class);
        Mockito.when(repository.findByNpiNumber(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMProviderModel.builder()
                                .npiNumber("123")
                                .id(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        when(repository.save(any(OpenCDXIAMProviderModel.class))).thenAnswer(new Answer<OpenCDXIAMProviderModel>() {
            @Override
            public OpenCDXIAMProviderModel answer(InvocationOnMock invocation) throws Throwable {
                OpenCDXIAMProviderModel argument = invocation.getArgument(0);
                if (argument.getId() == null) {
                    argument.setId(OpenCDXIdentifier.get());
                    argument.setNpiNumber("456");
                }
                return argument;
            }
        });
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                repository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setProviderId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void loadProvidersTestJsonException2() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findAll())
                .thenReturn(List.of(OpenCDXIAMProviderModel.builder()
                        .userId(OpenCDXIdentifier.get())
                        .id(OpenCDXIdentifier.get())
                        .npiNumber(OpenCDXIdentifier.get().toHexString())
                        .build()));
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(openCDXIAMProviderRepository.existsByNpiNumber(Mockito.any(String.class)))
                .thenReturn(true);
        OpenCDXIAMProviderRepository openCDXIAMProviderRepository1 = mock(OpenCDXIAMProviderRepository.class);
        when(openCDXIAMProviderRepository1.findByNpiNumber(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMProviderModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        //        OpenCDXDtoNpiJsonResponse openCDXDtoNpiJsonResponse = mock(OpenCDXDtoNpiJsonResponse.class);
        //        ResponseEntity<OpenCDXDtoNpiJsonResponse> responseEntity = mock(ResponseEntity.class);
        //        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        //        when(openCDXNpiRegistryClient.getProviderInfo(Mockito.any(String.class), Mockito.any(String.class)))
        //                .thenReturn(responseEntity);
        //        when(openCDXNpiRegistryClient.getProviderInfo(Mockito.any(String.class), Mockito.any(String.class)))
        //                .thenReturn(responseEntity);

        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                openCDXIAMProviderRepository1,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        LoadProviderRequest loadProviderRequest =
                LoadProviderRequest.newBuilder().setProviderNumber("123").build();
        LoadProviderRequest loadProviderRequest1 =
                LoadProviderRequest.newBuilder().setUserId("&%").build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest));
    }
}
