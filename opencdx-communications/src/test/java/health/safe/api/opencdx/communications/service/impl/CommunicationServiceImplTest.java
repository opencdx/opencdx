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
import health.safe.api.opencdx.communications.model.OpenCDXSMSTemplateModel;
import health.safe.api.opencdx.communications.repository.OpenCDXConnectedTestsRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import health.safe.api.opencdx.communications.service.CommunicationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class CommunicationServiceImplTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;
    @Mock
    OpenCDXConnectedTestsRepository openCDXConnectedTestsRepository;

    CommunicationService communicationService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXEmailTemplateRepository = Mockito.mock(OpenCDXEmailTemplateRepository.class);
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXSMSTemplateRespository = Mockito.mock(OpenCDXSMSTemplateRespository.class);
        this.openCDXConnectedTestsRepository = Mockito.mock(OpenCDXConnectedTestsRepository.class);

        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.communicationService = new CommunicationServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
                openCDXSMSTemplateRespository,
                openCDXConnectedTestsRepository,
                objectMapper);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper,
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository,
                this.openCDXConnectedTestsRepository);
    }

    @Test
    void createEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setTemplateId(new ObjectId().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.communicationService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void deleteEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteEmailTemplate(templateRequest);
        });
    }

    @Test
    void createSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setTemplateId(new ObjectId().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.communicationService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void deleteSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteSMSTemplate(templateRequest);
        });
    }

    @Test
    void createNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.newBuilder()
                .setEventId(new ObjectId().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEventFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.communicationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void deleteNotificationEvent() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteNotificationEvent(templateRequest);
        });
    }

    @Test
    void sendNotificationFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationEventModel()));

        Notification notification = Notification.newBuilder()
                .setEventId(new ObjectId().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcess() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(new ObjectId());

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(eventModel));

        OpenCDXSMSTemplateModel smsModel = new OpenCDXSMSTemplateModel();
        smsModel.setMessage("This is a test string for SMS");
        smsModel.setVariables(List.of("A", "B", "C"));

        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(smsModel));

        Map<String, String> variablesMap = new HashMap<>();
        variablesMap.put("A", "Alpha");
        variablesMap.put("B", "Beta");
        variablesMap.put("C", "Gnarly");

        Notification notification = Notification.newBuilder()
                .setEventId(new ObjectId().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.communicationService.sendNotification(notification);
        });
    }

    @Test
    void sendNotificationHtmlProcessFail() throws JsonProcessingException {

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(new ObjectId());

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
                .setEventId(new ObjectId().toHexString())
                .putAllVariables(variablesMap)
                .build();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.communicationService.sendNotification(notification);
        });
    }
}
