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
package health.safe.api.opencdx.communications.service.impl;

import cdx.open_communication.v2alpha.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.model.OpenCDXEmailTemplateModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationEventModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationModel;
import health.safe.api.opencdx.communications.model.OpenCDXSMSTemplateModel;
import health.safe.api.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import health.safe.api.opencdx.communications.service.OpenCDXCommunicationService;
import health.safe.api.opencdx.communications.service.OpenCDXEmailService;
import health.safe.api.opencdx.communications.service.OpenCDXHTMLProcessor;
import health.safe.api.opencdx.communications.service.OpenCDXSMSService;
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

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXCommunicationServiceImplTest {
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

    OpenCDXCommunicationService openCDXCommunicationService;

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

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.openCDXCommunicationService = new OpenCDXCommunicationServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
                openCDXSMSTemplateRespository,
                openCDXNotificaitonRepository,
                openCDXEmailService,
                openCDXSMSService,
                openCDXHTMLProcessor,
                objectMapper);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper,
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository);
    }

    @Test
    void createEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.createEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void deleteEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.deleteEmailTemplate(templateRequest);
        });
    }

    @Test
    void deleteEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificationEventRepository.existsByEmailTemplateId(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXCommunicationService
                .deleteEmailTemplate(templateRequest)
                .getSuccess());
    }

    @Test
    void createSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.createSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void deleteSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.deleteSMSTemplate(templateRequest);
        });
    }

    @Test
    void deleteSMSTemplateFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificationEventRepository.existsBySmsTemplateId(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXCommunicationService
                .deleteSMSTemplate(templateRequest)
                .getSuccess());
    }

    @Test
    void createNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.createNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEventFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void deleteNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationService.deleteNotificationEvent(templateRequest);
        });
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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXCommunicationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessFail() throws JsonProcessingException {

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(ObjectId.get());
        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(ObjectId.get().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

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
            this.openCDXCommunicationService.sendNotification(notification);
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

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        OpenCDXEmailTemplateModel emailModel = new OpenCDXEmailTemplateModel();
        emailModel.setContent("This is a test string for SMS");
        emailModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(emailModel));

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

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
            this.openCDXCommunicationService.processOpenCDXNotification(notification);
        });
    }
}
