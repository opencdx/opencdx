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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.service.OpenCDXCommunicationEmailTemplateService;
import cdx.opencdx.grpc.data.EmailTemplate;
import cdx.opencdx.grpc.service.communications.TemplateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXCommunicationEmailTemplateServiceImplTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    OpenCDXCommunicationEmailTemplateService openCDXCommunicationEmailService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXEmailTemplateRepository = Mockito.mock(OpenCDXEmailTemplateRepository.class);
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXCommunicationEmailService = Mockito.mock(OpenCDXCommunicationEmailTemplateService.class);

        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> {
                    OpenCDXEmailTemplateModel model = OpenCDXEmailTemplateModel.builder()
                            .id(invocation.getArgument(0))
                            .build();
                    return Optional.of(model);
                });

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> {
                    OpenCDXNotificationEventModel model = OpenCDXNotificationEventModel.builder()
                            .id(invocation.getArgument(0))
                            .build();
                    return Optional.of(model);
                });

        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.openCDXCommunicationEmailService = new OpenCDXCommunicationEmailTemplateServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
                openCDXCurrentUser,
                objectMapper);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper, this.openCDXEmailTemplateRepository, this.openCDXNotificationEventRepository);
    }

    @Test
    void createEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationEmailService.createEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationEmailService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void updateEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        EmailTemplate emailTemplate = EmailTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationEmailService.updateEmailTemplate(emailTemplate);
        });
    }

    @Test
    void deleteEmailTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationEmailService.deleteEmailTemplate(templateRequest);
        });
    }

    @Test
    void deleteEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificationEventRepository.existsByEmailTemplateId(
                        Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXCommunicationEmailService
                .deleteEmailTemplate(templateRequest)
                .getSuccess());
    }
}
