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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.grpc.communication.EmailTemplate;
import health.safe.api.opencdx.grpc.communication.NotificationEvent;
import health.safe.api.opencdx.grpc.communication.SMSTemplate;
import health.safe.api.opencdx.grpc.communication.TemplateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    CommunicationService communicationService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.communicationService = new CommunicationServiceImpl(this.openCDXAuditService, objectMapper);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void createEmailTemplate() {
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplate() {
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void deleteEmailTemplate() {
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteEmailTemplate(templateRequest);
        });
    }

    @Test
    void createSMSTemplate() {
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplate() {
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void deleteSMSTemplate() {
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteSMSTemplate(templateRequest);
        });
    }

    @Test
    void createNotificationEvent() {
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.createNotificationEvent(notificationEvent);
        });
    }

    @Test
    void updateNotificationEvent() {
        NotificationEvent notificationEvent = NotificationEvent.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.updateNotificationEvent(notificationEvent);
        });
    }

    @Test
    void deleteNotificationEvent() {
        TemplateRequest templateRequest = TemplateRequest.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.communicationService.deleteNotificationEvent(templateRequest);
        });
    }
}