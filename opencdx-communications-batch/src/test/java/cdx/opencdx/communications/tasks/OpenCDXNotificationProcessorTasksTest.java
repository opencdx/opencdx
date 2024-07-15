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
package cdx.opencdx.communications.tasks;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.*;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.communications.service.impl.OpenCDXNotificationServiceImpl;
import cdx.opencdx.grpc.data.EmailTemplate;
import cdx.opencdx.grpc.data.SMSTemplate;
import cdx.opencdx.grpc.service.communications.TemplateRequest;
import cdx.opencdx.grpc.types.NotificationPriority;
import cdx.opencdx.grpc.types.NotificationStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.*;
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
class OpenCDXNotificationProcessorTasksTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXHTMLProcessor openCDXHTMLProcessor;

    @Autowired
    OpenCDXSMSService openCDXSMSService;

    @Autowired
    OpenCDXEmailService openCDXEmailService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

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
    OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    @Mock
    OpenCDXCommunicationEmailService openCDXCommunicationEmailService;

    OpenCDXNotificationService openCDXNotificationService;

    OpenCDXNotificationProcessorTasks openCDXNotificationProcessorTasks;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXMessageRepository openCDXMessageRepository;

    @Mock
    OpenCDXSystemMessageService openCDXMessageService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXEmailTemplateRepository = Mockito.mock(OpenCDXEmailTemplateRepository.class);
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXSMSTemplateRespository = Mockito.mock(OpenCDXSMSTemplateRespository.class);
        this.openCDXNotificaitonRepository = Mockito.mock(OpenCDXNotificaitonRepository.class);
        this.openCDXCommunicationEmailService = Mockito.mock(OpenCDXCommunicationEmailService.class);
        this.openCDXCommunicationSmsService = Mockito.mock(OpenCDXCommunicationSmsService.class);

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build()));

        Mockito.when(this.openCDXNotificaitonRepository.save(Mockito.any(OpenCDXNotificationModel.class)))
                .thenAnswer(new Answer<OpenCDXNotificationModel>() {
                    @Override
                    public OpenCDXNotificationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXNotificationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
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
                openCDXDocumentValidator,
                openCDXProfileRepository,
                openCDXMessageRepository);

        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");

        OpenCDXNotificationEventModel eventModel = new OpenCDXNotificationEventModel();
        eventModel.setSmsTemplateId(OpenCDXIdentifier.get());
        eventModel.setEmailTemplateId(OpenCDXIdentifier.get());
        eventModel.setEventDescription("This is a test object");
        eventModel.setEmailRetry(1);
        eventModel.setSmsRetry(1);

        eventModel.setPriority(NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
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

        OpenCDXNotificationModel notification = OpenCDXNotificationModel.builder()
                .eventId(OpenCDXIdentifier.get())
                .id(OpenCDXIdentifier.get())
                .patientId(OpenCDXIdentifier.get())
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
                this.openCDXNotificationService, this.openCDXNotificaitonRepository, this.openCDXCurrentUser);
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
