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
package cdx.opencdx.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXANFClient;
import cdx.opencdx.grpc.anf.ANFServiceGrpc;
import cdx.opencdx.grpc.anf.AnfStatement;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXANFClientImplTest {

    @Mock
    ANFServiceGrpc.ANFServiceBlockingStub anfServiceBlockingStub;

    OpenCDXANFClient openCDXANFClient;

    @BeforeEach
    void setUp() {
        this.anfServiceBlockingStub = Mockito.mock(ANFServiceGrpc.ANFServiceBlockingStub.class);
        this.openCDXANFClient = new OpenCDXANFClientImpl(this.anfServiceBlockingStub);
        Mockito.when(anfServiceBlockingStub.withCallCredentials(Mockito.any())).thenReturn(this.anfServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.anfServiceBlockingStub);
    }

    @Test
    void createANFStatement() {
        Mockito.when(this.anfServiceBlockingStub.createANFStatement(Mockito.any(AnfStatement.ANFStatement.class)))
                .thenReturn(AnfStatement.Identifier.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                AnfStatement.Identifier.getDefaultInstance(),
                this.openCDXANFClient.createANFStatement(
                        AnfStatement.ANFStatement.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createANFStatementException() {
        Mockito.when(this.anfServiceBlockingStub.createANFStatement(Mockito.any(AnfStatement.ANFStatement.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AnfStatement.ANFStatement request = AnfStatement.ANFStatement.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXANFClient.createANFStatement(request, openCDXCallCredentials));
    }

    @Test
    void getANFStatement() {
        Mockito.when(this.anfServiceBlockingStub.getANFStatement(Mockito.any(AnfStatement.Identifier.class)))
                .thenReturn(AnfStatement.ANFStatement.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                AnfStatement.ANFStatement.getDefaultInstance(),
                this.openCDXANFClient.getANFStatement(
                        AnfStatement.Identifier.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getANFStatementException() {
        Mockito.when(this.anfServiceBlockingStub.getANFStatement(Mockito.any(AnfStatement.Identifier.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AnfStatement.Identifier request = AnfStatement.Identifier.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXANFClient.getANFStatement(request, openCDXCallCredentials));
    }

    @Test
    void updateANFStatement() {
        Mockito.when(this.anfServiceBlockingStub.updateANFStatement(Mockito.any(AnfStatement.ANFStatement.class)))
                .thenReturn(AnfStatement.Identifier.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                AnfStatement.Identifier.getDefaultInstance(),
                this.openCDXANFClient.updateANFStatement(
                        AnfStatement.ANFStatement.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateANFStatementException() {
        Mockito.when(this.anfServiceBlockingStub.updateANFStatement(Mockito.any(AnfStatement.ANFStatement.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AnfStatement.ANFStatement request = AnfStatement.ANFStatement.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXANFClient.updateANFStatement(request, openCDXCallCredentials));
    }

    @Test
    void deleteANFStatement() {
        Mockito.when(this.anfServiceBlockingStub.deleteANFStatement(Mockito.any(AnfStatement.Identifier.class)))
                .thenReturn(AnfStatement.Identifier.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                AnfStatement.Identifier.getDefaultInstance(),
                this.openCDXANFClient.deleteANFStatement(
                        AnfStatement.Identifier.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteANFStatementException() {
        Mockito.when(this.anfServiceBlockingStub.deleteANFStatement(Mockito.any(AnfStatement.Identifier.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AnfStatement.Identifier request = AnfStatement.Identifier.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXANFClient.deleteANFStatement(request, openCDXCallCredentials));
    }
}
