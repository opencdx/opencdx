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
package cdx.opencdx.communications.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.communications.model.OpenCDXEmailTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationEventModel;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.communications.model.OpenCDXSMSTemplateModel;
import cdx.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import cdx.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import cdx.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import cdx.opencdx.grpc.communication.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXEmailTemplateRepository.save(Mockito.any(OpenCDXEmailTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXEmailTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXEmailTemplateModel()));

        Mockito.when(this.openCDXSMSTemplateRespository.save(Mockito.any(OpenCDXSMSTemplateModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXSMSTemplateRespository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXSMSTemplateModel()));

        Mockito.when(this.openCDXNotificationEventRepository.save(Mockito.any(OpenCDXNotificationEventModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXNotificationEventRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationEventModel()));

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
        Mockito.when(this.openCDXNotificaitonRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new OpenCDXNotificationModel()));

        Mockito.when(this.openCDXEmailTemplateRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXSMSTemplateRespository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        Mockito.when(this.openCDXNotificationEventRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.connection,
                this.openCDXEmailTemplateRepository,
                this.openCDXNotificationEventRepository,
                this.openCDXSMSTemplateRespository);
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
                                        .setTemplateId(ObjectId.get().toHexString())
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
        String uuid = ObjectId.get().toHexString();
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
                                        .setTemplateId(ObjectId.get().toHexString())
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
        String uuid = ObjectId.get().toHexString();
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
                                        .setTemplateId(ObjectId.get().toHexString())
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
                                        .setTemplateId(ObjectId.get().toHexString())
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
                        .content(this.objectMapper.writeValueAsString(
                                NotificationEvent.newBuilder(NotificationEvent.getDefaultInstance())
                                        .setEventId(ObjectId.get().toHexString())
                                        .setEmailTemplateId(ObjectId.get().toHexString())
                                        .setSmsTemplateId(ObjectId.get().toHexString())
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
                        .content(this.objectMapper.writeValueAsString(
                                NotificationEvent.newBuilder(NotificationEvent.getDefaultInstance())
                                        .setEventId(ObjectId.get().toHexString())
                                        .setEmailTemplateId(ObjectId.get().toHexString())
                                        .setSmsTemplateId(ObjectId.get().toHexString())
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
                                .setEventId(ObjectId.get().toHexString())
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
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
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
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
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
}
