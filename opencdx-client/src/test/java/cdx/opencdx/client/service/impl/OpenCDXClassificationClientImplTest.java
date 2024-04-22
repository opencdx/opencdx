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

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXClassificationClient;
import cdx.opencdx.grpc.service.classification.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXClassificationClientImplTest {
    @Mock
    ClassificationServiceGrpc.ClassificationServiceBlockingStub classificationServiceBlockingStub;

    OpenCDXClassificationClient openCDXClassificationClient;

    @BeforeEach
    void setUp() {
        this.classificationServiceBlockingStub =
                Mockito.mock(ClassificationServiceGrpc.ClassificationServiceBlockingStub.class);
        this.openCDXClassificationClient = new OpenCDXClassificationClientImpl(this.classificationServiceBlockingStub);
        Mockito.when(classificationServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.classificationServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.classificationServiceBlockingStub);
    }

    @Test
    void classifyException() {
        Mockito.when(this.classificationServiceBlockingStub.classify(Mockito.any(ClassificationRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClassificationRequest request = ClassificationRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXClassificationClient.classify(request, openCDXCallCredentials));
    }

    @Test
    void classify() {
        Mockito.when(this.classificationServiceBlockingStub.classify(Mockito.any(ClassificationRequest.class)))
                .thenReturn(ClassificationResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ClassificationResponse.getDefaultInstance(),
                this.openCDXClassificationClient.classify(
                        ClassificationRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getRuleSets() {
        Mockito.when(this.classificationServiceBlockingStub.getRuleSets(Mockito.any(RuleSetsRequest.class)))
                .thenReturn(RuleSetsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                RuleSetsResponse.getDefaultInstance(),
                this.openCDXClassificationClient.getRuleSets(
                        RuleSetsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getRuleSetsException() {
        Mockito.when(this.classificationServiceBlockingStub.getRuleSets(Mockito.any(RuleSetsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        RuleSetsRequest request = RuleSetsRequest.getDefaultInstance();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXClassificationClient.getRuleSets(request, openCDXCallCredentials));
    }
}
