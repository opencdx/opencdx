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
package cdx.opencdx.communications.controller;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import cdx.opencdx.communications.service.OpenCDXCommunicationService;
import cdx.opencdx.communications.service.OpenCDXEmailService;
import cdx.opencdx.communications.service.OpenCDXHTMLProcessor;
import cdx.opencdx.communications.service.OpenCDXSMSService;
import cdx.opencdx.communications.service.impl.OpenCDXCommunicationServiceImpl;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Collections;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class GrpcCommunicationsControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXHTMLProcessor openCDXHTMLProcessor;

    @Autowired
    OpenCDXSMSService openCDXSMSService;

    @Autowired
    OpenCDXEmailService openCDXEmailService;

    @Mock
    OpenCDXNotificaitonRepository openCDXNotificaitonRepository;

    @Mock
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    OpenCDXCommunicationService openCDXCommunicationService;

    GrpcCommunicationsController grpcCommunicationsController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        this.openCDXEmailTemplateRepository = Mockito.mock(OpenCDXEmailTemplateRepository.class);
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXSMSTemplateRespository = Mockito.mock(OpenCDXSMSTemplateRespository.class);
        this.openCDXNotificaitonRepository = Mockito.mock(OpenCDXNotificaitonRepository.class);

        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificaitonRepository.save(Mockito.any(OpenCDXNotificationModel.class)))
                .thenAnswer(new Answer<OpenCDXNotificationModel>() {
                    @Override
                    public OpenCDXNotificationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXNotificationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXEmailTemplateModel()));
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXSMSTemplateModel()));

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setSmsTemplateId(ObjectId.get());

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        Mockito.when(this.openCDXEmailTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXSMSTemplateRespository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXNotificationEventRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUserType()).thenReturn(AgentType.AGENT_TYPE_HUMAN_USER);

        this.openCDXCommunicationService = new OpenCDXCommunicationServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
                openCDXSMSTemplateRespository,
                openCDXNotificaitonRepository,
                openCDXEmailService,
                openCDXSMSService,
                openCDXHTMLProcessor,
                openCDXCurrentUser,
                objectMapper);
        this.grpcCommunicationsController = new GrpcCommunicationsController(this.openCDXCommunicationService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository);
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
                .setTemplateId(ObjectId.get().toHexString())
                .build();
    }

    @Test
    void getEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXEmailTemplateModel()));
        this.grpcCommunicationsController.getEmailTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getEmailTemplateFail() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        TemplateRequest templateRequest = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.grpcCommunicationsController.getEmailTemplate(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
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
                        .setTemplateId(ObjectId.get().toHexString())
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
                .setTemplateId(ObjectId.get().toHexString())
                .build();
    }

    @Test
    void getSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXSMSTemplateModel()));
        this.grpcCommunicationsController.getSMSTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getSMSTemplateFail() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        TemplateRequest templateRequest = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.grpcCommunicationsController.getSMSTemplate(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
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
                        .setTemplateId(ObjectId.get().toHexString())
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
                .setEventId(ObjectId.get().toHexString())
                .setEmailTemplateId(ObjectId.get().toHexString())
                .setSmsTemplateId(ObjectId.get().toHexString())
                .build();
    }

    @Test
    void getNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationEventModel()));
        this.grpcCommunicationsController.getNotificationEvent(
                TemplateRequest.newBuilder()
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getNotificationEventFail() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        TemplateRequest templateRequest = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.grpcCommunicationsController.getNotificationEvent(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
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
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void sendNotification() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .build();
        this.grpcCommunicationsController.sendNotification(notification, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listSMSTemplates() {
        StreamObserver<SMSTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplateListRequest request = SMSTemplateListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .build();
        this.grpcCommunicationsController.listSMSTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listEmailTemplates() {
        StreamObserver<EmailTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplateListRequest request = EmailTemplateListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .build();
        this.grpcCommunicationsController.listEmailTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listNotificationEvents() {
        StreamObserver<NotificationEventListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEventListRequest request = NotificationEventListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .build();
        this.grpcCommunicationsController.listNotificationEvents(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
