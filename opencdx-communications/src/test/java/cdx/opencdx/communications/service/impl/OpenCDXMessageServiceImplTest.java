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
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.communications.model.OpenCDXMessageTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXMessageRepository;
import cdx.opencdx.communications.repository.OpenCDXMessageTemplateRepository;
import cdx.opencdx.grpc.data.MessageTemplate;
import cdx.opencdx.grpc.service.communications.TemplateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMessageServiceImplTest {

    @Mock
    OpenCDXMessageServiceImpl openCDXMessageService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository;

    @Mock
    OpenCDXMessageRepository openCDXMessageRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXMessageTemplateRepository = Mockito.mock(OpenCDXMessageTemplateRepository.class);
        this.openCDXMessageRepository = Mockito.mock(OpenCDXMessageRepository.class);
        this.objectMapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXMessageService = new OpenCDXMessageServiceImpl(
                openCDXDocumentValidator,
                this.openCDXAuditService,
                objectMapper,
                openCDXMessageTemplateRepository,
                openCDXMessageRepository,
                openCDXCurrentUser);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper
                // this.openCDXEmailTemplateRepository,
                //                this.openCDXNotificationEventRepository
                );
        //  this.openCDXSMSTemplateRespository);
    }

    @Test
    void createMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.createMessageTemplate(messageTemplate);
        });
    }

    @Test
    void createMessageTemplateNoException() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        MessageTemplate messageTemplate = MessageTemplate.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXMessageService.createMessageTemplate(messageTemplate);
        });
    }

    @Test
    void getMessageTemplate() {
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Mockito.when(openCDXMessageTemplateRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXMessageTemplateModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build()));
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXMessageService.getMessageTemplate(messageTemplate);
        });
    }

    @Test
    void getMessageTemplateException() {
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Mockito.when(openCDXMessageTemplateRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXMessageService.getMessageTemplate(messageTemplate));
    }

    @Test
    void updateMessageTemplateOpenCDXFailedPrecondition() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXMessageService.updateMessageTemplate(messageTemplate);
        });
    }

    @Test
    void updateMessageTemplateException() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.updateMessageTemplate(messageTemplate);
        });
    }

    @Test
    void updateMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        MessageTemplate messageTemplate = MessageTemplate.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.updateMessageTemplate(messageTemplate));
    }

    @Test
    void deleteMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.deleteMessageTemplate(messageTemplate));
    }

    @Test
    void deleteMessageTemplateException() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.deleteMessageTemplate(messageTemplate);
        });
    }

    @Test
    void deleteMessageTemplateSuccess() {
        Mockito.when(openCDXMessageTemplateRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.deleteMessageTemplate(messageTemplate));
    }
}
