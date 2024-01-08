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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.provider.DeleteProviderRequest;
import cdx.opencdx.grpc.provider.GetProviderRequest;
import cdx.opencdx.grpc.provider.LoadProviderRequest;
import cdx.opencdx.iam.model.OpenCDXIAMProviderModel;
import cdx.opencdx.iam.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.iam.service.OpenCDXIAMProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.io.IOException;
import java.net.*;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

class OpenCDXIAMProviderServiceImplTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    OpenCDXIAMProviderService openCDXIAMProviderService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @MockBean
    OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    @MockBean
    Connection connection;

    @MockBean
    AuthenticationManager authenticationManager;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        this.objectMapper = mock(ObjectMapper.class);
        when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
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
                this.openCDXCountryRepository);
    }

    @Test
    void getProviderByNumberElse() {
        GetProviderRequest request = GetProviderRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.getProviderByNumber(request));
    }

    @Test
    void deleteProviderElse() throws JsonProcessingException {
        when(this.openCDXIAMProviderRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMProviderModel.builder()
                        .userId(ObjectId.get().toHexString())
                        .build()));
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        this.objectMapper1 = mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProviderService = new OpenCDXIAMProviderServiceImpl(
                this.openCDXIAMProviderRepository,
                this.openCDXAuditService,
                this.objectMapper1,
                this.openCDXCountryRepository);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void deleteProviderElse2() {
        DeleteProviderRequest request = DeleteProviderRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProviderService.deleteProvider(request));
    }

    @Test
    void loadProviderTest() throws IOException {
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
                    NullPointerException.class,
                    () -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest1));
        }
    }

    @Test
    void loadProviderTestExcept() {
        LoadProviderRequest loadProviderRequest =
                LoadProviderRequest.newBuilder().setUserId("2431").build();
        Assertions.assertThrows(
                NullPointerException.class, () -> this.openCDXIAMProviderService.loadProvider(loadProviderRequest));
    }
}
