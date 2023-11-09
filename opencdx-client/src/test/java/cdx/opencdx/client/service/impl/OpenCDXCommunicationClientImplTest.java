/*
 * Copyright 2023 Safe Health Systems, Inc.
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
import cdx.opencdx.client.service.OpenCDXCommunicationClient;
import cdx.opencdx.grpc.communication.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXCommunicationClientImplTest {

    // public static final TemplateRequest templateRequest = template;
    @Mock
    CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub;

    OpenCDXCommunicationClient client;

    EmailTemplate emailTemplate;
    SMSTemplate smsTemplate;
    NotificationEvent notificationEvent;
    TemplateRequest templateRequest;
    SuccessResponse successResponse;

    @BeforeEach
    void setUp() {
        this.blockingStub = Mockito.mock(CommunicationServiceGrpc.CommunicationServiceBlockingStub.class);
        Mockito.when(this.blockingStub.withCallCredentials(Mockito.any())).thenReturn(this.blockingStub);
        this.client = new OpenCDXCommunicationClientImpl(this.blockingStub);
        this.smsTemplate = SMSTemplate.newBuilder().build();
        this.emailTemplate = EmailTemplate.newBuilder().build();
        this.templateRequest = TemplateRequest.newBuilder().build();
        this.successResponse = SuccessResponse.newBuilder().build();
        this.notificationEvent = NotificationEvent.newBuilder().build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.blockingStub);
    }

    @Test
    void createEmailTemplateTest() {
        Mockito.when(this.blockingStub.createEmailTemplate(Mockito.any(EmailTemplate.class)))
                .thenReturn(emailTemplate);
        ;
        Assertions.assertNotNull(client.createEmailTemplate(emailTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void createEmailTemplateWithException() {
        Mockito.when(this.blockingStub.createEmailTemplate(Mockito.any(EmailTemplate.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.createEmailTemplate(emailTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getEmailTemplate() {
        Mockito.when(this.blockingStub.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);
        Assertions.assertNotNull(this.client.getEmailTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getEmailTemplateWithException() {
        Mockito.when(this.blockingStub.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.getEmailTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateEmailTemplate() {
        Mockito.when(this.blockingStub.updateEmailTemplate(Mockito.any(EmailTemplate.class)))
                .thenReturn(emailTemplate);
        Assertions.assertNotNull(this.client.updateEmailTemplate(emailTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateEmailTemplateWithException() {
        Mockito.when(this.blockingStub.updateEmailTemplate(Mockito.any(EmailTemplate.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.updateEmailTemplate(emailTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteEmailTemplate() {
        Mockito.when(this.blockingStub.deleteEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(successResponse);
        Assertions.assertNotNull(client.deleteEmailTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteEmailTemplateWithException() {
        Mockito.when(this.blockingStub.deleteEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.deleteEmailTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void createSMSTemplateTest() {
        Mockito.when(this.blockingStub.createSMSTemplate(Mockito.any(SMSTemplate.class)))
                .thenReturn(smsTemplate);
        ;
        Assertions.assertNotNull(client.createSMSTemplate(smsTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void createSMSTemplateWithException() {
        Mockito.when(this.blockingStub.createSMSTemplate(Mockito.any(SMSTemplate.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.createSMSTemplate(smsTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getSMSTemplate() {
        Mockito.when(this.blockingStub.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);
        Assertions.assertNotNull(this.client.getSMSTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getSMSTemplateWithException() {
        Mockito.when(this.blockingStub.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.getSMSTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateSMSTemplate() {
        Mockito.when(this.blockingStub.updateSMSTemplate(Mockito.any(SMSTemplate.class)))
                .thenReturn(smsTemplate);
        Assertions.assertNotNull(this.client.updateSMSTemplate(smsTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateSMSTemplateWithException() {
        Mockito.when(this.blockingStub.updateSMSTemplate(Mockito.any(SMSTemplate.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.updateSMSTemplate(smsTemplate, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteSMSTemplate() {
        Mockito.when(this.blockingStub.deleteSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(successResponse);
        Assertions.assertNotNull(client.deleteSMSTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteSMSTemplateWithException() {
        Mockito.when(this.blockingStub.deleteSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.deleteSMSTemplate(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void createNotificationEvent() {
        Mockito.when(this.blockingStub.createNotificationEvent(Mockito.any(NotificationEvent.class)))
                .thenReturn(notificationEvent);
        Assertions.assertNotNull(
                this.client.createNotificationEvent(notificationEvent, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void createNotificationEventWithException() {
        Mockito.when(this.blockingStub.createNotificationEvent(Mockito.any(NotificationEvent.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.createNotificationEvent(notificationEvent, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getNotificationEvent() {
        Mockito.when(this.blockingStub.getNotificationEvent(Mockito.any(TemplateRequest.class)))
                .thenReturn(notificationEvent);
        Assertions.assertNotNull(
                this.client.getNotificationEvent(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getNotificationEventWithException() {
        Mockito.when(this.blockingStub.getNotificationEvent(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.getNotificationEvent(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateNotificationEvent() {
        Mockito.when(this.blockingStub.updateNotificationEvent(Mockito.any(NotificationEvent.class)))
                .thenReturn(notificationEvent);
        Assertions.assertNotNull(
                this.client.updateNotificationEvent(notificationEvent, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void updateNotificationEventWithException() {
        Mockito.when(this.blockingStub.updateNotificationEvent(Mockito.any(NotificationEvent.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.updateNotificationEvent(notificationEvent, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteNotificationEvent() {
        Mockito.when(this.blockingStub.deleteNotificationEvent(Mockito.any(TemplateRequest.class)))
                .thenReturn(successResponse);
        Assertions.assertNotNull(
                this.client.deleteNotificationEvent(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void deleteNotificationEventWithException() {
        Mockito.when(this.blockingStub.deleteNotificationEvent(Mockito.any(TemplateRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.deleteNotificationEvent(templateRequest, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void sendNotification() {
        Mockito.when(this.blockingStub.sendNotification(Mockito.any(Notification.class)))
                .thenReturn(successResponse);
        Assertions.assertNotNull(
                this.client.sendNotification(Notification.newBuilder().build(), new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void sendNotificationWithException() {
        Mockito.when(this.blockingStub.sendNotification(Mockito.any(Notification.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Notification notification = Notification.newBuilder().build();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.client.sendNotification(notification, openCDXCallCredentials));
    }

    @Test
    void getEmailList() {
        EmailTemplateListRequest request = EmailTemplateListRequest.newBuilder().build();
        EmailTemplateListResponse response =
                EmailTemplateListResponse.newBuilder().build();
        Mockito.when(this.blockingStub.listEmailTemplates(Mockito.any(EmailTemplateListRequest.class)))
                .thenReturn(response);
        Assertions.assertNotNull(this.client.listEmailTemplates(request, new OpenCDXCallCredentials("Bearer")));

        Mockito.when(this.blockingStub.listEmailTemplates(Mockito.any(EmailTemplateListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Notification notification = Notification.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.listEmailTemplates(request, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getSMSList() {
        SMSTemplateListRequest request = SMSTemplateListRequest.newBuilder().build();
        SMSTemplateListResponse response = SMSTemplateListResponse.newBuilder().build();
        Mockito.when(this.blockingStub.listSMSTemplates(Mockito.any(SMSTemplateListRequest.class)))
                .thenReturn(response);
        Assertions.assertNotNull(this.client.listSMSTemplates(request, new OpenCDXCallCredentials("Bearer")));

        Mockito.when(this.blockingStub.listSMSTemplates(Mockito.any(SMSTemplateListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Notification notification = Notification.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.listSMSTemplates(request, new OpenCDXCallCredentials("Bearer")));
    }

    @Test
    void getNotificationList() {
        NotificationEventListRequest request =
                NotificationEventListRequest.newBuilder().build().newBuilder().build();
        NotificationEventListResponse response =
                NotificationEventListResponse.newBuilder().build();
        Mockito.when(this.blockingStub.listNotificationEvents(Mockito.any(NotificationEventListRequest.class)))
                .thenReturn(response);
        Assertions.assertNotNull(this.client.listNotificationEvents(request, new OpenCDXCallCredentials("Bearer")));

        Mockito.when(this.blockingStub.listNotificationEvents(Mockito.any(NotificationEventListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Notification notification = Notification.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.client.listNotificationEvents(request, new OpenCDXCallCredentials("Bearer")));
    }
}
