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
package health.safe.api.opencdx.communications.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.grpc.communication.*;
import io.nats.client.Connection;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestCommunicationsControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
    }

    @Test
    void checkMockMvc() throws Exception { // Assertions.assertNotNull(greetingController);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void createEmailTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/communications/email")
                        .content(this.objectMapper.writeValueAsString(
                                EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                                        .setTemplateId(new ObjectId().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/communications/email/", "/communications/sms/", "/communications/event/"})
    void testGets(String url) throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(get(url + uuid).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
        Assertions.assertTrue(content.contains(uuid));
    }

    @Test
    void updateEmailTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/communications/email")
                        .content(this.objectMapper.writeValueAsString(
                                EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                                        .setTemplateId(new ObjectId().toHexString())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/communications/email/", "/communications/sms/", "/communications/event/"})
    void testDeletes(String url) throws Exception {
        String uuid = UUID.randomUUID().toString();
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
                .perform(post("/communications/sms")
                        .content(this.objectMapper.writeValueAsString(
                                SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                                        .setTemplateId(new ObjectId().toHexString())
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
                .perform(put("/communications/sms")
                        .content(this.objectMapper.writeValueAsString(
                                SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                                        .setTemplateId(new ObjectId().toHexString())
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
                .perform(post("/communications/event")
                        .content(this.objectMapper.writeValueAsString(
                                NotificationEvent.newBuilder(NotificationEvent.getDefaultInstance())
                                        .setEventId(new ObjectId().toHexString())
                                        .setEmailTemplateId(new ObjectId().toHexString())
                                        .setSmsTemplateId(new ObjectId().toHexString())
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
                .perform(put("/communications/event")
                        .content(this.objectMapper.writeValueAsString(
                                NotificationEvent.newBuilder(NotificationEvent.getDefaultInstance())
                                        .setEventId(new ObjectId().toHexString())
                                        .setEmailTemplateId(new ObjectId().toHexString())
                                        .setSmsTemplateId(new ObjectId().toHexString())
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
                .perform(post("/communications/notification")
                        .content(this.objectMapper.writeValueAsString(Notification.getDefaultInstance()))
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
                .perform(post("/communications/sms/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(SMSTemplateListRequest.getDefaultInstance()))
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
                .perform(post("/communications/email/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(EmailTemplateListRequest.getDefaultInstance()))
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
                .perform(post("/communications/event/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                this.objectMapper.writeValueAsString(NotificationEventListRequest.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(NotificationEventListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }
}
