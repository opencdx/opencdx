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
import cdx.opencdx.client.service.OpenCDXIAMOrganizationClient;
import cdx.opencdx.grpc.service.iam.*;
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
class OpenCDXIAMOrganizationClientImplTest {

    @Mock
    OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub;

    OpenCDXIAMOrganizationClient openCDXIAMOrganizationClient;

    @BeforeEach
    void setUp() {
        this.organizationServiceBlockingStub =
                Mockito.mock(OrganizationServiceGrpc.OrganizationServiceBlockingStub.class);
        this.openCDXIAMOrganizationClient = new OpenCDXIAMOrganizationClientImpl(this.organizationServiceBlockingStub);
        Mockito.when(organizationServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.organizationServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.organizationServiceBlockingStub);
    }

    @Test
    void createOrganization() {
        Mockito.when(this.organizationServiceBlockingStub.createOrganization(
                        Mockito.any(CreateOrganizationRequest.class)))
                .thenReturn(CreateOrganizationResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateOrganizationResponse.getDefaultInstance(),
                this.openCDXIAMOrganizationClient.createOrganization(
                        CreateOrganizationRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createOrganizationException() {
        Mockito.when(this.organizationServiceBlockingStub.createOrganization(
                        Mockito.any(CreateOrganizationRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateOrganizationRequest request = CreateOrganizationRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMOrganizationClient.createOrganization(request, openCDXCallCredentials));
    }

    @Test
    void getOrganizationDetailsById() {
        Mockito.when(this.organizationServiceBlockingStub.getOrganizationDetailsById(
                        Mockito.any(GetOrganizationDetailsByIdRequest.class)))
                .thenReturn(GetOrganizationDetailsByIdResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetOrganizationDetailsByIdResponse.getDefaultInstance(),
                this.openCDXIAMOrganizationClient.getOrganizationDetailsById(
                        GetOrganizationDetailsByIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getOrganizationDetailsByIdException() {
        Mockito.when(this.organizationServiceBlockingStub.getOrganizationDetailsById(
                        Mockito.any(GetOrganizationDetailsByIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetOrganizationDetailsByIdRequest request = GetOrganizationDetailsByIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMOrganizationClient.getOrganizationDetailsById(request, openCDXCallCredentials));
    }

    @Test
    void updateOrganization() {
        Mockito.when(this.organizationServiceBlockingStub.updateOrganization(
                        Mockito.any(UpdateOrganizationRequest.class)))
                .thenReturn(UpdateOrganizationResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateOrganizationResponse.getDefaultInstance(),
                this.openCDXIAMOrganizationClient.updateOrganization(
                        UpdateOrganizationRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateOrganizationException() {
        Mockito.when(this.organizationServiceBlockingStub.updateOrganization(
                        Mockito.any(UpdateOrganizationRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateOrganizationRequest request = UpdateOrganizationRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMOrganizationClient.updateOrganization(request, openCDXCallCredentials));
    }

    @Test
    void listOrganizations() {
        Mockito.when(this.organizationServiceBlockingStub.listOrganizations(Mockito.any(ListOrganizationsRequest.class)))
                .thenReturn(ListOrganizationsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListOrganizationsResponse.getDefaultInstance(),
                this.openCDXIAMOrganizationClient.listOrganizations(
                        ListOrganizationsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listOrganizationsException() {
        Mockito.when(this.organizationServiceBlockingStub.listOrganizations(Mockito.any(ListOrganizationsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListOrganizationsRequest request = ListOrganizationsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMOrganizationClient.listOrganizations(request, openCDXCallCredentials));
    }
}
