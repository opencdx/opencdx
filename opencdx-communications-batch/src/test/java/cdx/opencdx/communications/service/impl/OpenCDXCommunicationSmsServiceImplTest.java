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
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import cdx.opencdx.communications.service.OpenCDXCommunicationSmsTemplateService;
import cdx.opencdx.grpc.data.SMSTemplate;
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
class OpenCDXCommunicationSmsTemplateServiceImplTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @Mock
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    OpenCDXCommunicationSmsTemplateService openCDXCommunicationSmsTemplateService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXNotificationEventRepository = Mockito.mock(OpenCDXNotificationEventRepository.class);
        this.openCDXSMSTemplateRespository = Mockito.mock(OpenCDXSMSTemplateRespository.class);
        this.openCDXCommunicationSmsTemplateService = Mockito.mock(OpenCDXCommunicationSmsTemplateService.class);

        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> {
                    OpenCDXNotificationEventModel model = OpenCDXNotificationEventModel.builder()
                            .id(invocation.getArgument(0))
                            .build();
                    return Optional.of(model);
                });
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> {
                    OpenCDXSMSTemplateModel model = OpenCDXSMSTemplateModel.builder()
                            .id(invocation.getArgument(0))
                            .build();
                    return Optional.of(model);
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.openCDXCommunicationSmsTemplateService = new OpenCDXCommunicationSmsTemplateServiceImpl(
                this.openCDXAuditService,
                openCDXNotificationEventRepository,
                openCDXSMSTemplateRespository,
                openCDXCurrentUser,
                objectMapper);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper,
                this.openCDXSMSTemplateRespository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository);
    }

    @Test
    void createSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationSmsTemplateService.createSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationSmsTemplateService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void updateSMSTemplateFail() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        SMSTemplate smsTemplate = SMSTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXCommunicationSmsTemplateService.updateSMSTemplate(smsTemplate);
        });
    }

    @Test
    void deleteSMSTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXCommunicationSmsTemplateService.deleteSMSTemplate(templateRequest);
        });
    }

    @Test
    void deleteSMSTemplateFail() throws JsonProcessingException {
        Mockito.when(this.openCDXNotificationEventRepository.existsBySmsTemplateId(
                        Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        TemplateRequest templateRequest = TemplateRequest.newBuilder(TemplateRequest.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertFalse(this.openCDXCommunicationSmsTemplateService
                .deleteSMSTemplate(templateRequest)
                .getSuccess());
    }
}
