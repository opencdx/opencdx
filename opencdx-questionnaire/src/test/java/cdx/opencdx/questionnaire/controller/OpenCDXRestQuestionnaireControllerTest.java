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
package cdx.opencdx.questionnaire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.questionnaire.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestQuestionnaireControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .setUserId(ObjectId.get().toHexString())
                                        .addAllAddresses(List.of(Address.newBuilder()
                                                .setCity("City")
                                                .setCountryId(ObjectId.get().toHexString())
                                                .setState("CA")
                                                .setPostalCode("12345")
                                                .setAddress1("101 Main Street")
                                                .build()))
                                        .addAllEmail(List.of(EmailAddress.newBuilder()
                                                .setEmail("email@email.com")
                                                .setType(EmailType.EMAIL_TYPE_WORK)
                                                .build()))
                                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                .setNumber("1234567890")
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .build()))
                                        .build())
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
    }

    @Test
    void checkMockMvc() throws Exception {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void testGetRuleSets() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/getrulesets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientRulesRequest.newBuilder()
                                .setOrgnizationId(ObjectId.get().toHexString())
                                .setWorkspaceId(ObjectId.get().toHexString())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"ruleSets\":[{\"ruleId\":\"1\",\"type\":\"Business Rule\",\"category\":\"Validation\",\"description\":\"Validate user responses\"},{\"ruleId\":\"2\",\"type\":\"Authorization Rule\",\"category\":\"Access Control\",\"description\":\"Control access based on user responses\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testSubmitQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/submitquestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(
                                        Questionnaire.newBuilder().setId("789").build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed SubmitQuestionnaire operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSubmittedQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/submittedquestionnaire/12345")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(Questionnaire.newBuilder()
                                        .setId("12345")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"description\":\"User Submitted Questionnaire Description\",\"item\":[]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSubmittedQuestionnaireList() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/submittedquestionnaires")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(
                                GetQuestionnaireRequest.newBuilder().build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaires\":[{\"description\":\"User Submitted Questionnaire one Description\",\"item\":[]},{\"description\":\"User Submitted Questionnaire two Description\",\"item\":[]}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/questionnaire/deletequestionnaire/639")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("639")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteQuestionnaire operation.\"}",
                mv.getResponse().getContentAsString());
    }

    // System Level Questionnaire
    @Test
    void testCreateSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/system/createquestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireDataRequest.newBuilder()
                                .setQuestionnaireData(QuestionnaireData.newBuilder()
                                        .setId("sys-can")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed CreateQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/system/updatequestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireDataRequest.newBuilder()
                                .setQuestionnaireData(QuestionnaireData.newBuilder()
                                        .setId("sys-can")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed UpdateQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire//system/questionnaire/789")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireRequest.newBuilder()
                                .setId("12345")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSystemQuestionnaires() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/system/questionnaires")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(
                                GetQuestionnaireRequest.newBuilder().build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"},{\"id\":\"2\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/questionnaire/system/deletequestionnaire/789")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("789")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteQuestionnaire operation.\"}",
                mv.getResponse().getContentAsString());
    }

    // Client Level Questionnaire
    @Test
    void testCreateClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/client/createquestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientQuestionnaireDataRequest.newBuilder()
                                .setClientQuestionnaireData(ClientQuestionnaireData.newBuilder()
                                        .setOrgnizationId("org-ind")
                                        .setWorkspaceId("wrk-sos")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed CreateClientQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/client/updatequestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientQuestionnaireDataRequest.newBuilder()
                                .setClientQuestionnaireData(ClientQuestionnaireData.newBuilder()
                                        .setOrgnizationId("org-ind")
                                        .setWorkspaceId("wrk-sos")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed UpdateClientQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/client/questionnaire/789")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireRequest.newBuilder()
                                .setId("12345")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetClientQuestionnaires() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/client/questionnaires")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(
                                GetQuestionnaireRequest.newBuilder().build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"},{\"id\":\"2\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/questionnaire/client/deletequestionnaire/789")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("789")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteClientQuestionnaire operation.\"}",
                mv.getResponse().getContentAsString());
    }

    // User Level Questionnaire
    @Test
    void testCreateUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/user/createquestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(UserQuestionnaireDataRequest.newBuilder()
                                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                                        .setUserId("usr777")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed CreateUserQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/user/updatequestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(UserQuestionnaireDataRequest.newBuilder()
                                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                                        .setUserId("usr777")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed UpdateUserQuestionnaireData operation.\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/user/questionnaire/777")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireRequest.newBuilder()
                                .setId("12345")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetUserQuestionnaires() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/user/questionnaires")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(
                                GetQuestionnaireRequest.newBuilder().build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"},{\"id\":\"2\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/questionnaire/user/deletequestionnaire/789")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("789")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteUserQuestionnaire operation.\"}",
                mv.getResponse().getContentAsString());
    }
}
