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
package health.safe.api.opencdx.communications.tasks;

import static org.junit.jupiter.api.Assertions.*;

import cdx.open_communication.v2alpha.NotificationPriority;
import cdx.open_communication.v2alpha.NotificationStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import health.safe.api.opencdx.communications.service.impl.OpenCDXCommunicationServiceImpl;
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
class OpenCDXNotificationProcessorTasksTest {
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

    OpenCDXNotificationProcessorTasks openCDXNotificationProcessorTasks;

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

        Mockito.when(this.openCDXNotificaitonRepository.findAllByPriorityAndSmsStatusOrderByTimestampAsc(
                        Mockito.any(NotificationPriority.class), Mockito.any(NotificationStatus.class)))
                .thenReturn(List.of(notification));
        Mockito.when(this.openCDXNotificaitonRepository.findAllByPriorityAndEmailStatusOrderByTimestampAsc(
                        Mockito.any(NotificationPriority.class), Mockito.any(NotificationStatus.class)))
                .thenReturn(List.of(notification));

        this.openCDXNotificationProcessorTasks = new OpenCDXNotificationProcessorTasks(
                this.openCDXCommunicationService, this.openCDXNotificaitonRepository);
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
    void highPriorityNotifications() {
        Assertions.assertDoesNotThrow(() -> this.openCDXNotificationProcessorTasks.highPriorityNotifications());
    }

    @Test
    void mediumPriorityNotifications() {
        Assertions.assertDoesNotThrow(() -> this.openCDXNotificationProcessorTasks.mediumPriorityNotifications());
    }

    @Test
    void lowPriorityNotifications() {
        Assertions.assertDoesNotThrow(() -> this.openCDXNotificationProcessorTasks.lowPriorityNotifications());
    }
}
