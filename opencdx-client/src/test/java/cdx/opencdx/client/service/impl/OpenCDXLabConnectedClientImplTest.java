package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXLabConnectedClient;
import cdx.opencdx.grpc.lab.connected.*;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXLabConnectedClientImplTest {

    @Mock
    ConnectedLabServiceGrpc.ConnectedLabServiceBlockingStub connectedLabServiceBlockingStub;;
    OpenCDXLabConnectedClient openCDXLabConnectedClient;

    @BeforeEach
    void setUp() {
        this.connectedLabServiceBlockingStub =
                Mockito.mock(ConnectedLabServiceGrpc.ConnectedLabServiceBlockingStub.class);
        this.openCDXLabConnectedClient = new OpenCDXLabConnectedClientImpl(this.connectedLabServiceBlockingStub);
        Mockito.when(connectedLabServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.connectedLabServiceBlockingStub);
    }


    @AfterEach
    void tearDown() {
        Mockito.reset(this.connectedLabServiceBlockingStub);
    }

    @Test
    void submitLabFindings() {
        Mockito.when(this.connectedLabServiceBlockingStub.submitLabFindings(Mockito.any(LabFindings.class)))
                .thenReturn(LabFindingsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LabFindingsResponse.getDefaultInstance(),
                this.openCDXLabConnectedClient.submitLabFindings(
                        LabFindings.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createConnectedLab() {
        Mockito.when(this.connectedLabServiceBlockingStub.createConnectedLab(Mockito.any(CreateConnectedLabRequest.class)))
                .thenReturn(CreateConnectedLabResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateConnectedLabResponse.getDefaultInstance(),
                this.openCDXLabConnectedClient.createConnectedLab(
                        CreateConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getConnectedLab() {
        Mockito.when(this.connectedLabServiceBlockingStub.getConnectedLab(Mockito.any(GetConnectedLabRequest.class)))
                .thenReturn(GetConnectedLabResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetConnectedLabResponse.getDefaultInstance(),
                this.openCDXLabConnectedClient.getConnectedLab(
                        GetConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateConnectedLab() {
        Mockito.when(this.connectedLabServiceBlockingStub.updateConnectedLab(Mockito.any(UpdateConnectedLabRequest.class)))
                .thenReturn(UpdateConnectedLabResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateConnectedLabResponse.getDefaultInstance(),
                this.openCDXLabConnectedClient.updateConnectedLab(
                        UpdateConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteConnectedLab() {
        Mockito.when(this.connectedLabServiceBlockingStub.deleteConnectedLab(Mockito.any(DeleteConnectedLabRequest.class)))
                .thenReturn(DeleteConnectedLabResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteConnectedLabResponse.getDefaultInstance(),
                this.openCDXLabConnectedClient.deleteConnectedLab(
                        DeleteConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }


    @Test
    void submitLabFindingsException() {
        Mockito.when(this.connectedLabServiceBlockingStub.submitLabFindings(Mockito.any(LabFindings.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXLabConnectedClient.submitLabFindings(
                        LabFindings.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createConnectedLabException() {
        Mockito.when(this.connectedLabServiceBlockingStub.createConnectedLab(Mockito.any(CreateConnectedLabRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXLabConnectedClient.createConnectedLab(
                        CreateConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getConnectedLabException() {
        Mockito.when(this.connectedLabServiceBlockingStub.getConnectedLab(Mockito.any(GetConnectedLabRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXLabConnectedClient.getConnectedLab(
                        GetConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateConnectedLabException() {
        Mockito.when(this.connectedLabServiceBlockingStub.updateConnectedLab(Mockito.any(UpdateConnectedLabRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () ->  this.openCDXLabConnectedClient.updateConnectedLab(
                        UpdateConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteConnectedLabException() {
        Mockito.when(this.connectedLabServiceBlockingStub.deleteConnectedLab(Mockito.any(DeleteConnectedLabRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXLabConnectedClient.deleteConnectedLab(
                        DeleteConnectedLabRequest.getDefaultInstance(), openCDXCallCredentials));
    }
}