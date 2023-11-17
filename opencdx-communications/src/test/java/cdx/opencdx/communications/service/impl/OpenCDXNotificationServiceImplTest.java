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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
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
import cdx.opencdx.communications.service.*;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.*;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXNotificationServiceImplTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXHTMLProcessor openCDXHTMLProcessor;

    @Autowired
    OpenCDXSMSService openCDXSMSService;

    @Autowired
    OpenCDXEmailService openCDXEmailService;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    @Mock
    OpenCDXNotificaitonRepository openCDXNotificaitonRepository;

    @Mock
    OpenCDXCommunicationEmailService openCDXCommunicationEmailService;

    @Mock
    OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    @Mock
    OpenCDXNotificationService openCDXNotificationService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() throws JsonProcessingException {
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

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.openCDXNotificationService = new OpenCDXNotificationServiceImpl(
                this.openCDXAuditService,
                openCDXNotificationEventRepository,
                openCDXNotificaitonRepository,
                openCDXEmailService,
                openCDXSMSService,
                openCDXHTMLProcessor,
                openCDXCurrentUser,
                openCDXCommunicationSmsService,
                openCDXCommunicationEmailService,
                objectMapper);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper,
                // this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository);
        //  this.openCDXSMSTemplateRespository);
    }

    @Test
    void createNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXNotificationService.createNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXNotificationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEventFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXNotificationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void deleteNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXNotificationService.deleteNotificationEvent(templateRequest);
        });
    }

    @Test
    void deleteNotificationEventFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificaitonRepository.existsByEventId(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXNotificationService
                .deleteNotificationEvent(templateRequest)
                .getSuccess());
    }

    @Test
    void sendNotificationFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationEventModel()));

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessToEmail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));
        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllToEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessCCEmail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllCcEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessBccEmail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllBccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessSMS() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllToEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessFailSend() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessFail() throws JsonProcessingException {

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void processOpenCDXNotification_1() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_FAILED)
                .setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_FAILED)
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllBccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void processOpenCDXNotification_2() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setEmailRetry(1);
        eventModel.setSmsRetry(1);
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .setSubject("test")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllBccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void processOpenCDXNotification_3() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setEmailRetry(0);
        eventModel.setSmsRetry(0);
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .addAllToPhoneNumber(List.of("123-456-7890", "098-765-4321"))
                .addAllBccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.sendNotification(notification);
        });
    }

    @Test
    void processOpenCDXNotification_4() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setEventDescription("This is a test object");
        eventModel.setEmailRetry(1);
        eventModel.setSmsRetry(1);

        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS Dear [[${firstName}]] [[${lastName}]],\n" + "\n"
                        + "                        To verify your email : [[${email}]] click the link below :\n"
                        + "                        <a th:href=\"@{|${verification_server}/${user_id}|}\" target=\"_blank\">[[${verification_server}]]/[[${user_id}]]</a>\n"
                        + "\n"
                        + "                        Thank you!")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");
        variablesMap.put("firstName", "FNAME");
        variablesMap.put("lastName", "LNAME");
        variablesMap.put("email", "EMAIL");
        variablesMap.put("verification_server", "VERIFICATION-SERVER");
        variablesMap.put("user_id", "USER-ID");

        OpenCDXNotificationModel notification = OpenCDXNotificationModel.builder()
                .eventId(ObjectId.get())
                .id(ObjectId.get())
                .smsStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .emailStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .timestamp(Instant.now())
                .customData(Collections.emptyMap())
                .phoneNumbers(List.of("123-456-7890", "098-765-4321"))
                .bccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .emailFailCount(2)
                .smsFailCount(2)
                .variables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXNotificationService.processOpenCDXNotification(notification);
        });
    }

    @Test
    void failedSMS() throws JsonProcessingException {
        OpenCDXSMSService openCDXSMSService = Mockito.mock(OpenCDXSMSService.class);
        Mockito.when(openCDXSMSService.sendSMS(Mockito.any(), Mockito.anyList()))
                .thenReturn(false);

        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setEmailTemplateId(ObjectId.get());
        eventModel.setEventDescription("This is a test object");
        eventModel.setEmailRetry(1);
        eventModel.setSmsRetry(1);

        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setMessage("This is a test string for SMS")
                .addAllVariables((List.of("A", "B", "C")))
                .build();

        Mockito.when(this.openCDXCommunicationSmsService.getSMSTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(smsTemplate);

        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setContent("This is a test string for SMS Dear [[${firstName}]] [[${lastName}]],\n" + "\n"
                        + "                        To verify your email : [[${email}]] click the link below :\n"
                        + "                        <a th:href=\"@{|${verification_server}/${user_id}|}\" target=\"_blank\">[[${verification_server}]]/[[${user_id}]]</a>\n"
                        + "\n"
                        + "                        Thank you!")
                .addAllVariables(List.of("A", "B", "C"))
                .build();

        Mockito.when(this.openCDXCommunicationEmailService.getEmailTemplate(Mockito.any(TemplateRequest.class)))
                .thenReturn(emailTemplate);

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");
        variablesMap.put("firstName", "FNAME");
        variablesMap.put("lastName", "LNAME");
        variablesMap.put("email", "EMAIL");
        variablesMap.put("verification_server", "VERIFICATION-SERVER");
        variablesMap.put("user_id", "USER-ID");

        OpenCDXNotificationModel notification = OpenCDXNotificationModel.builder()
                .eventId(ObjectId.get())
                .id(ObjectId.get())
                .smsStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .emailStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .timestamp(Instant.now())
                .customData(Collections.emptyMap())
                .phoneNumbers(List.of("123-456-7890", "098-765-4321"))
                .bccEmail(List.of("test1@opencdx.org", "test2@opencdx.org"))
                .emailFailCount(2)
                .smsFailCount(2)
                .variables(variablesMap)
                .build();

        OpenCDXNotificationService openCDXNotificationService = new OpenCDXNotificationServiceImpl(
                this.openCDXAuditService,
                this.openCDXNotificationEventRepository,
                this.openCDXNotificaitonRepository,
                this.openCDXEmailService,
                openCDXSMSService,
                this.openCDXHTMLProcessor,
                this.openCDXCurrentUser,
                this.openCDXCommunicationSmsService,
                this.openCDXCommunicationEmailService,
                this.objectMapper);

        Assertions.assertDoesNotThrow(() -> {
            openCDXNotificationService.processOpenCDXNotification(notification);
        });
    }
}
