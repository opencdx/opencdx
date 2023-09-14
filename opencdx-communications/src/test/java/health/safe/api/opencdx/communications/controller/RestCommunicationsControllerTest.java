package health.safe.api.opencdx.communications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.grpc.communication.EmailTemplate;
import health.safe.api.opencdx.grpc.communication.Notification;
import health.safe.api.opencdx.grpc.communication.NotificationEvent;
import health.safe.api.opencdx.grpc.communication.SMSTemplate;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                        .content(this.objectMapper.writeValueAsString(EmailTemplate.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void getEmailTemplate() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(get("/communications/email/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertTrue(content.contains(uuid));
    }

    @Test
    void updateEmailTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/communications/email")
                        .content(this.objectMapper.writeValueAsString(EmailTemplate.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void deleteEmailTemplate() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(delete("/communications/email/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertEquals("{\"success\":true}", content);
    }

    @Test
    void createSMSTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/communications/sms")
                        .content(this.objectMapper.writeValueAsString(SMSTemplate.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void getSMSTemplate() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(get("/communications/sms/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertTrue(content.contains(uuid));

    }

    @Test
    void updateSMSTemplate() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/communications/sms")
                        .content(this.objectMapper.writeValueAsString(SMSTemplate.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void deleteSMSTemplate() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(delete("/communications/sms/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertEquals("{\"success\":true}", content);
    }

    @Test
    void createNotificationEvent() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/communications/event")
                        .content(this.objectMapper.writeValueAsString(NotificationEvent.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void getNotificationEvent() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(get("/communications/event/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertTrue(content.contains(uuid));

    }

    @Test
    void updateNotificationEvent() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/communications/event")
                        .content(this.objectMapper.writeValueAsString(NotificationEvent.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void deleteNotificationEvent() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MvcResult result = this.mockMvc
                .perform(delete("/communications/event/"+uuid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
        Assertions.assertEquals("{\"success\":true}", content);
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
        log.info("Received\n {}",content);

    }

    @Test
    void listSMSTemplates() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/communications/sms/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void listEmailTemplates() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/communications/email/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }

    @Test
    void listNotificationEvents() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/communications/event/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}",content);
    }
}