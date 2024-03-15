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
package cdx.opencdx.communications.controller;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.communications.model.*;
import cdx.opencdx.communications.repository.*;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.communications.service.OpenCDXEmailService;
import cdx.opencdx.communications.service.OpenCDXHTMLProcessor;
import cdx.opencdx.communications.service.OpenCDXSMSService;
import cdx.opencdx.communications.service.impl.OpenCDXCommunicationEmailServiceImpl;
import cdx.opencdx.communications.service.impl.OpenCDXCommunicationSmsServiceImpl;
import cdx.opencdx.communications.service.impl.OpenCDXMessageServiceImpl;
import cdx.opencdx.communications.service.impl.OpenCDXNotificationServiceImpl;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
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
class OpenCDXGrpcCommunicationsControllerTest {

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

    @Autowired
    OpenCDXMessageService openCDXMessageService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXNotificaitonRepository openCDXNotificaitonRepository;

    @Mock
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    @Mock
    OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository;

    OpenCDXNotificationService openCDXNotificationService;

    OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    OpenCDXCommunicationEmailService openCDXCommunicationEmailService;

    OpenCDXGrpcCommunicationsController openCDXGrpcCommunicationsController;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXMessageRepository openCDXMessageRepository;

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
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenAnswer(new Answer<OpenCDXMessageTemplateModel>() {
                    @Override
                    public OpenCDXMessageTemplateModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMessageTemplateModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(
                        OpenCDXIAMUserModel.builder().id(ObjectId.get()).build()));
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

        this.openCDXCommunicationEmailService = new OpenCDXCommunicationEmailServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
                openCDXCurrentUser,
                objectMapper);
        this.openCDXCommunicationSmsService = new OpenCDXCommunicationSmsServiceImpl(
                this.openCDXAuditService,
                openCDXNotificationEventRepository,
                openCDXSMSTemplateRespository,
                openCDXCurrentUser,
                objectMapper);
        this.openCDXNotificationService = new OpenCDXNotificationServiceImpl(
                this.openCDXAuditService,
                openCDXNotificationEventRepository,
                openCDXNotificaitonRepository,
                openCDXEmailService,
                openCDXSMSService,
                openCDXHTMLProcessor,
                openCDXMessageService,
                openCDXCurrentUser,
                openCDXCommunicationSmsService,
                openCDXCommunicationEmailService,
                objectMapper,
                this.openCDXDocumentValidator,
                openCDXProfileRepository,
                openCDXMessageRepository);
        this.openCDXMessageService = new OpenCDXMessageServiceImpl(
                this.openCDXDocumentValidator,
                this.openCDXAuditService,
                objectMapper,
                openCDXMessageTemplateRepository,
                openCDXMessageRepository,
                openCDXCurrentUser);
        this.openCDXGrpcCommunicationsController = new OpenCDXGrpcCommunicationsController(
                this.openCDXNotificationService,
                openCDXCommunicationEmailService,
                openCDXCommunicationSmsService,
                this.openCDXMessageService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository,
                this.openCDXMessageRepository);
    }

    @Test
    void createEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.openCDXGrpcCommunicationsController.createEmailTemplate(emailTemplate, responseObserver);

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
        this.openCDXGrpcCommunicationsController.getEmailTemplate(
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
                () -> this.openCDXGrpcCommunicationsController.getEmailTemplate(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
    }

    @Test
    void updateEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.openCDXGrpcCommunicationsController.updateEmailTemplate(emailTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(emailTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteEmailTemplate() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcCommunicationsController.deleteEmailTemplate(
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
        this.openCDXGrpcCommunicationsController.createSMSTemplate(smsTemplate, responseObserver);

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
        this.openCDXGrpcCommunicationsController.getSMSTemplate(
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
                () -> this.openCDXGrpcCommunicationsController.getSMSTemplate(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
    }

    @Test
    void updateSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplate smsTemplate = getTestSMSTemplate();
        this.openCDXGrpcCommunicationsController.updateSMSTemplate(smsTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(smsTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteSMSTemplate() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcCommunicationsController.deleteSMSTemplate(
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
        this.openCDXGrpcCommunicationsController.createNotificationEvent(notificationEvent, responseObserver);

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
        this.openCDXGrpcCommunicationsController.getNotificationEvent(
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
                () -> this.openCDXGrpcCommunicationsController.getNotificationEvent(templateRequest, responseObserver));

        Mockito.verify(responseObserver, Mockito.times(0)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(0)).onCompleted();
    }

    @Test
    void updateNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEvent notificationEvent = getTestNotificationEvent();
        this.openCDXGrpcCommunicationsController.updateNotificationEvent(notificationEvent, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(notificationEvent);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteNotificationEvent() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXGrpcCommunicationsController.deleteNotificationEvent(
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
                .setPatientId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcCommunicationsController.sendNotification(notification, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listSMSTemplates() {
        StreamObserver<SMSTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplateListRequest request = SMSTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listSMSTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listEmailTemplates() {
        StreamObserver<EmailTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplateListRequest request = EmailTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listEmailTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listNotificationEvents() {
        StreamObserver<NotificationEventListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEventListRequest request = NotificationEventListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listNotificationEvents(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listSMSTemplates_2() {
        StreamObserver<SMSTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplateListRequest request = SMSTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("message")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listSMSTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listEmailTemplates_2() {
        StreamObserver<EmailTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplateListRequest request = EmailTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("subject")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listEmailTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listNotificationEvents_2() {
        StreamObserver<NotificationEventListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEventListRequest request = NotificationEventListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("eventName")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listNotificationEvents(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listSMSTemplates_3() {
        StreamObserver<SMSTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplateListRequest request = SMSTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("message")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listSMSTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listEmailTemplates_3() {
        StreamObserver<EmailTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplateListRequest request = EmailTemplateListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("subject")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listEmailTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listNotificationEvents_3() {
        StreamObserver<NotificationEventListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEventListRequest request = NotificationEventListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("eventName")
                        .build())
                .build();
        this.openCDXGrpcCommunicationsController.listNotificationEvents(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createMessageTemplate() {
        StreamObserver<MessageTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        MessageTemplate messageTemplate = MessageTemplate.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcCommunicationsController.createMessageTemplate(messageTemplate, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMessageTemplate() {
        StreamObserver<MessageTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXMessageTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXMessageTemplateModel()));
        this.openCDXGrpcCommunicationsController.getMessageTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateMessageTemplate() {
        StreamObserver<MessageTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        MessageTemplate notificationEvent = MessageTemplate.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcCommunicationsController.updateMessageTemplate(notificationEvent, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(notificationEvent);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteMessageTemplate() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(openCDXMessageTemplateRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        this.openCDXGrpcCommunicationsController.deleteMessageTemplate(
                TemplateRequest.newBuilder()
                        .setTemplateId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listMessageTemplates() {
        StreamObserver<MessageTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(openCDXMessageTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMessageTemplateModel.builder().build()), PageRequest.of(1, 10), 1));
        Pagination request = Pagination.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .build();
        this.openCDXGrpcCommunicationsController.listMessageTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

        Pagination request2 = Pagination.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .setSort("id")
                .build();
        this.openCDXGrpcCommunicationsController.listMessageTemplates(request2, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(2)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(2)).onCompleted();
    }

    @Test
    void listMessageTemplatesIf() {
        StreamObserver<MessageTemplateListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(openCDXMessageTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMessageTemplateModel.builder().build()), PageRequest.of(1, 10), 1));
        Pagination request = Pagination.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(false)
                .setSort("id")
                .build();
        this.openCDXGrpcCommunicationsController.listMessageTemplates(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void markMessageAsRead() {
        StreamObserver<MarkMessagesAsReadResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        MarkMessagesAsReadRequest templateRequest = MarkMessagesAsReadRequest.newBuilder()
                .addId(ObjectId.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcCommunicationsController.markMessageAsRead(templateRequest, responseObserver));
    }

    @Test
    void markMessageAsReadCondition() {
        StreamObserver<MarkMessagesAsReadResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        MarkMessagesAsReadRequest templateRequest =
                MarkMessagesAsReadRequest.newBuilder().build();
        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcCommunicationsController.markMessageAsRead(templateRequest, responseObserver));
    }

    @Test
    void markMessageAsUnread() {
        StreamObserver<MarkMessagesAsUnreadResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        MarkMessagesAsUnreadRequest templateRequest = MarkMessagesAsUnreadRequest.newBuilder()
                .addId(ObjectId.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcCommunicationsController.markMessageAsUnread(templateRequest, responseObserver));
    }

    @Test
    void markMessageAsUnreadCondition() {
        StreamObserver<MarkMessagesAsUnreadResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        MarkMessagesAsUnreadRequest templateRequest =
                MarkMessagesAsUnreadRequest.newBuilder().build();
        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcCommunicationsController.markMessageAsUnread(templateRequest, responseObserver));
    }

    @Test
    void getMessages() {
        StreamObserver<GetMessagesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXMessageRepository.findAllByPatientId(Mockito.any(ObjectId.class)))
                .thenReturn(List.of(OpenCDXMessageModel.builder()
                        .id(ObjectId.get())
                        .patientId(ObjectId.get())
                        .title("title")
                        .message("message")
                        .messageType(MessageType.INFO)
                        .messageStatus(MessageStatus.READ)
                        .created(Instant.now())
                        .modified(Instant.now())
                        .modifier(ObjectId.get())
                        .creator(ObjectId.get())
                        .build()));
        GetMessagesRequest templateRequest = GetMessagesRequest.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcCommunicationsController.getMessages(templateRequest, responseObserver));
    }
}
