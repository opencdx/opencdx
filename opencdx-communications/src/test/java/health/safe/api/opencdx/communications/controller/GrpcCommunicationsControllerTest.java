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
package health.safe.api.opencdx.communications.controller;

import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.communications.service.impl.CommunicationServiceImpl;
import health.safe.api.opencdx.grpc.communication.*;
import io.grpc.stub.StreamObserver;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class GrpcCommunicationsControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    CommunicationService communicationService;

    GrpcCommunicationsController grpcCommunicationsController;

    @BeforeEach
    void setUp() {
        this.communicationService = new CommunicationServiceImpl(this.openCDXAuditService);
        this.grpcCommunicationsController = new GrpcCommunicationsController(this.communicationService);
    }

    @Test
    void createEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.grpcCommunicationsController.createEmailTemplate(emailTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static EmailTemplate getTestEmailTemplate() {
        return EmailTemplate.newBuilder()
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.getEmailTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.grpcCommunicationsController.updateEmailTemplate(emailTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(emailTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteEmailTemplate() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.deleteEmailTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplate smsTemplate = getTestSMSTemplate();
        this.grpcCommunicationsController.createSMSTemplate(smsTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static SMSTemplate getTestSMSTemplate() {
        return SMSTemplate.newBuilder()
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.getSMSTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplate smsTemplate = getTestSMSTemplate();
        this.grpcCommunicationsController.updateSMSTemplate(smsTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(smsTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteSMSTemplate() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.deleteSMSTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEvent notificationEvent = getTestNotificationEvent();
        this.grpcCommunicationsController.createNotificationEvent(notificationEvent, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static NotificationEvent getTestNotificationEvent() {
        return NotificationEvent.newBuilder()
                .setEventId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.getNotificationEvent(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEvent notificationEvent = getTestNotificationEvent();
        this.grpcCommunicationsController.updateNotificationEvent(notificationEvent, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(notificationEvent);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteNotificationEvent() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcCommunicationsController.deleteNotificationEvent(
                TemplateRequest.newBuilder()
                        .setTemplateId(UUID.randomUUID().toString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void sendNotification() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Notification notification = Notification.newBuilder().build();
        this.grpcCommunicationsController.sendNotification(notification, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listSMSTemplates() {
        StreamObserver<SMSTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplateListRequest request = SMSTemplateListRequest.getDefaultInstance();
        this.grpcCommunicationsController.listSMSTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listEmailTemplates() {
        StreamObserver<EmailTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplateListRequest request = EmailTemplateListRequest.getDefaultInstance();
        this.grpcCommunicationsController.listEmailTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listNotificationEvents() {
        StreamObserver<NotificationEventListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEventListRequest request = NotificationEventListRequest.getDefaultInstance();
        this.grpcCommunicationsController.listNotificationEvents(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
