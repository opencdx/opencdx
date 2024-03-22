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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.provider.DeleteProviderRequest;
import cdx.opencdx.grpc.provider.GetProviderRequest;
import cdx.opencdx.grpc.provider.ListProvidersRequest;
import cdx.opencdx.grpc.provider.LoadProviderRequest;
import cdx.opencdx.health.feign.OpenCDXNpiRegistryClient;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());


        when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProviderRepository = mock(OpenCDXIAMProviderRepository.class);
        when(this.openCDXIAMProviderRepository.save(any(OpenCDXIAMProviderModel.class)))
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
                .setProviderNumber(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    @Test
    void getProviderByNumberElseJsonException() throws JsonProcessingException {
        Mockito.when(this.openCDXIAMProviderRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMProviderModel>>() {
                    @Override
                    public Optional<OpenCDXIAMProviderModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMProviderModel.builder().id(argument).build());
                    }
                });
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        GetProviderRequest request = GetProviderRequest.newBuilder()
                .setProviderNumber(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    @Test
    void deleteProviderElse() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMProviderModel.builder()
                        .userId(ObjectId.get())
                        .build()));
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setProviderId(ObjectId.get().toHexString())
                .build();
        this.objectMapper1 = mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository,
                this.openCDXCurrentUser,
                this.openCDXNpiRegistryClient);
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void deleteProviderElse2() {
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setProviderId(ObjectId.get().toHexString())
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
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest));
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
            Assertions.assertThrows(
                    OpenCDXServiceUnavailable.class,
                    () -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest1));
        }
    }

    @Test
    void listProvidersTestJsonException() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findAll())
                .thenReturn(List.of(OpenCDXIAMProviderModel.builder()
                        .userId(ObjectId.get())
                        .id(ObjectId.get())
                        .npiNumber(ObjectId.get().toHexString())
                        .build()));
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
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
                        .userId(ObjectId.get())
                        .id(ObjectId.get())
                        .npiNumber(ObjectId.get().toHexString())
                        .build()));
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any(OpenCDXIAMProviderModel.class)))
                .thenReturn("model");
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
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
}
