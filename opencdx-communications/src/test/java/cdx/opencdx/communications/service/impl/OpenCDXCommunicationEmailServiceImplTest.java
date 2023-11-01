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
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXCommunicationEmailServiceImplTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    OpenCDXCommunicationEmailService openCDXCommunicationEmailService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXEmailTemplateRepository = Mockito.mock(OpenCDXEmailTemplateRepository.class);
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXCommunicationEmailService = Mockito.mock(OpenCDXCommunicationEmailService.class);
        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.openCDXCommunicationEmailService = new OpenCDXCommunicationEmailServiceImpl(
                this.openCDXAuditService,
                openCDXEmailTemplateRepository,
                openCDXNotificationEventRepository,
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
                .setTemplateId(ObjectId.get().toHexString())
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
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationEmailService.deleteEmailTemplate(templateRequest);
        });
    }

    @Test
    void deleteEmailTemplateFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificationEventRepository.existsByEmailTemplateId(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXCommunicationEmailService
                .deleteEmailTemplate(templateRequest)
                .getSuccess());
    }
}
