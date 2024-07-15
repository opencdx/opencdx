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
package cdx.opencdx.communications.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.communications.model.*;
import cdx.opencdx.communications.repository.*;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.communications.*;
import cdx.opencdx.grpc.types.MessageStatus;
import cdx.opencdx.grpc.types.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestCommunicationsControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository;

    @MockBean
    OpenCDXNotificationEventRepository openCDXNotificationEventRepository;

    @MockBean
    OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository;

    @MockBean
    OpenCDXNotificaitonRepository openCDXNotificaitonRepository;

    @MockBean
    OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository;

    @MockBean
    OpenCDXMessageRepository openCDXMessageRepository;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    public void setup() {
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
        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(new OpenCDXEmailTemplateModel()));

        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(new OpenCDXSMSTemplateModel()));

        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationEventModel()));

        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXMessageTemplateRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(new OpenCDXMessageTemplateModel()));

        Mockito.when(this.openCDXMessageRepository.saveAll(Mockito.anyList()))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXMessageRepository.findAllById(Mockito.anyList()))
                .thenReturn(List.of(OpenCDXMessageModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .patientId(OpenCDXIdentifier.get())
                        .title("title")
                        .message("message")
                        .messageType(MessageType.INFO)
                        .messageStatus(MessageStatus.READ)
                        .build()));
        Mockito.when(this.openCDXMessageRepository.findAllByPatientId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(List.of(OpenCDXMessageModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .patientId(OpenCDXIdentifier.get())
                        .title("title")
                        .message("message")
                        .messageType(MessageType.INFO)
                        .messageStatus(MessageStatus.READ)
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
        Mockito.when(this.openCDXNotificaitonRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationModel()));

        Mockito.when(this.openCDXEmailTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXSMSTemplateRespository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXNotificationEventRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXMessageTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        Mockito.when(openCDXMessageTemplateRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.connection,
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository,
                this.openCDXMessageTemplateRepository,
                this.openCDXMessageRepository);
    }

    @Test
    void checkMockMvc() throws Exception { // Assertions.assertNotNull(greetingController);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void createEmailTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/email")
                        .content(this.objectMapper.writeValueAsString(
                                EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                                        .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/email/", "/sms/", "/event/"})
    void testGets(String url) throws Exception {
        String uuid = OpenCDXIdentifier.get().toHexString();
        MvcResult result = this.mockMvc
                .perform(get(url + uuid).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
        Assertions.assertNotNull(content);
    }

    @Test
    void updateEmailTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/email")
                        .content(this.objectMapper.writeValueAsString(
                                EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                                        .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/email/", "/sms/", "/event/"})
    void testDeletes(String url) throws Exception {
        String uuid = OpenCDXIdentifier.get().toHexString();
        MvcResult result = this.mockMvc
                .perform(delete(url + uuid).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
        Assertions.assertEquals("{\"success\":true}", content);
    }

    @Test
    void createSMSTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/sms")
                        .content(this.objectMapper.writeValueAsString(
                                SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                                        .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @Test
    void updateSMSTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/sms")
                        .content(this.objectMapper.writeValueAsString(
                                SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                                        .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @Test
    void createNotificationEvent() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/event")
                        .content(this.objectMapper.writeValueAsString(NotificationEvent.newBuilder(
                                        NotificationEvent.getDefaultInstance())
                                .setEventId(OpenCDXIdentifier.get().toHexString())
                                .setEmailTemplateId(OpenCDXIdentifier.get().toHexString())
                                .setSmsTemplateId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @Test
    void updateNotificationEvent() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/event")
                        .content(this.objectMapper.writeValueAsString(NotificationEvent.newBuilder(
                                        NotificationEvent.getDefaultInstance())
                                .setEventId(OpenCDXIdentifier.get().toHexString())
                                .setEmailTemplateId(OpenCDXIdentifier.get().toHexString())
                                .setSmsTemplateId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @Test
    void sendNotification() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/notification")
                        .content(this.objectMapper.writeValueAsString(Notification.newBuilder()
                                .setEventId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n {}", this.objectMapper.writeValueAsString(Notification.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listSMSTemplates() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/sms/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(SMSTemplateListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(SMSTemplateListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listEmailTemplates() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/email/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(EmailTemplateListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(EmailTemplateListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listNotificationEvents() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/event/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(NotificationEventListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(NotificationEventListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void createMessageTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(
                                MessageTemplate.newBuilder().build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(MessageTemplate.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void getMessageTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/messageTemplate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(TemplateRequest.newBuilder()
                                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(TemplateRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void updateMessageTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/message")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(MessageTemplate.newBuilder()
                                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(MessageTemplate.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void deleteMessageTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/message/" + OpenCDXIdentifier.get().toHexString())
                        .content(this.objectMapper.writeValueAsString(MessageTemplate.newBuilder()
                                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                //  .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(TemplateRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listMessageTemplates() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/message/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(NotificationEventListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void getMessages() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetMessagesRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(GetMessagesRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void markMessageAsRead() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/message/read")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(MarkMessagesAsReadRequest.newBuilder()
                                .addAllId(List.of(OpenCDXIdentifier.get().toHexString()))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(MarkMessagesAsReadRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void markMessageAsUnread() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/message/unread")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(MarkMessagesAsUnreadRequest.newBuilder()
                                .addAllId(List.of(OpenCDXIdentifier.get().toHexString()))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(MarkMessagesAsUnreadRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }
}
