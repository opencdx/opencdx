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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.model.OpenCDXQuestionnaireModel;
import cdx.opencdx.questionnaire.model.OpenCDXRuleSet;
import cdx.opencdx.questionnaire.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.questionnaire.repository.OpenCDXQuestionnaireRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXRuleSetRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXUserQuestionnaireRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @MockBean
    OpenCDXQuestionnaireRepository openCDXQuestionnaireRepository;

    @MockBean
    OpenCDXUserQuestionnaireRepository openCDXUserQuestionnaireRepository;

    @MockBean
    OpenCDXRuleSetRepository openCDXRuleSetRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(openCDXRuleSetRepository.save(Mockito.any(OpenCDXRuleSet.class)))
                .thenAnswer(new Answer<OpenCDXRuleSet>() {
                    @Override
                    public OpenCDXRuleSet answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXRuleSet argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXRuleSetRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXRuleSet>>() {
                    @Override
                    public Optional<OpenCDXRuleSet> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXRuleSet.builder()
                                .id(argument)
                                .category("category")
                                .type("type")
                                .description("description")
                                .rule("rule")
                                .status(QuestionnaireStatus.draft)
                                .build());
                    }
                });

        Mockito.when(this.openCDXRuleSetRepository.findAll())
                .thenReturn(List.of(
                        new OpenCDXRuleSet(
                                ObjectId.get(),
                                "Business Rule",
                                "Validation",
                                "Validate user responses",
                                null,
                                QuestionnaireStatus.active),
                        new OpenCDXRuleSet(
                                ObjectId.get(),
                                "Authorization Rule",
                                "Access Control",
                                "Control access based on user responses",
                                null,
                                QuestionnaireStatus.active)));
        Mockito.when(this.openCDXUserQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .userId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(openCDXUserQuestionnaireRepository.save(Mockito.any(OpenCDXUserQuestionnaireModel.class)))
                .thenAnswer(new Answer<OpenCDXUserQuestionnaireModel>() {
                    @Override
                    public OpenCDXUserQuestionnaireModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXUserQuestionnaireModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXUserQuestionnaireModel>>() {
                    @Override
                    public Optional<OpenCDXUserQuestionnaireModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(argument)
                                .userId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build());
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);

        Mockito.when(this.openCDXQuestionnaireRepository.save(Mockito.any(OpenCDXQuestionnaireModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(openCDXQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXQuestionnaireModel>>() {
                    @Override
                    public Optional<OpenCDXQuestionnaireModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXQuestionnaireModel.builder().id(argument).build());
                    }
                });
        Mockito.when(openCDXQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

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
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
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
                .perform(post("/getrulesets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientRulesRequest.newBuilder()
                                .setOrganizationId(ObjectId.get().toHexString())
                                .setWorkspaceId(ObjectId.get().toHexString())
                                .build())))
                .andReturn();
        Assertions.assertNotNull(mv.getResponse().getContentAsString());
    }

    @Test
    void testSubmitQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(Questionnaire.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertNotNull(mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(put("/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(Questionnaire.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertNotNull(mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSubmittedQuestionnaire() throws Exception {
        String id = ObjectId.get().toHexString();
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/" + id).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals(
                "{\"id\":\"" + id + "\",\"item\":[],\"ruleQuestionId\":[]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSubmittedQuestionnaireList() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageSize(10)
                                        .setPageNumber(1)
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertNotNull(mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/questionnaire/639")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("639")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteQuestionnaire operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    // System Level Questionnaire
    @Test
    void testCreateSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/system/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireDataRequest.newBuilder()
                                .setQuestionnaireData(
                                        QuestionnaireData.newBuilder().build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed CreateQuestionnaireData operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(put("/system/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireDataRequest.newBuilder()
                                .setQuestionnaireData(QuestionnaireData.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed UpdateQuestionnaireData operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/system/questionnaire/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals(
                "{\"questionnaireData\":[{\"id\":\"1\",\"state\":\"Active\"}]}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testDeleteSystemQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/system/questionnaire/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("789")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteQuestionnaire operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    // Client Level Questionnaire
    @Test
    void testCreateClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/client/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientQuestionnaireDataRequest.newBuilder()
                                .setClientQuestionnaireData(ClientQuestionnaireData.newBuilder()
                                        .setOrganizationId("org-ind")
                                        .setWorkspaceId("wrk-sos")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed CreateClientQuestionnaireData operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testUpdateClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(put("/client/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClientQuestionnaireDataRequest.newBuilder()
                                .setClientQuestionnaireData(ClientQuestionnaireData.newBuilder()
                                        .setOrganizationId("org-ind")
                                        .setWorkspaceId("wrk-sos")
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed UpdateClientQuestionnaireData operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/client/questionnaire/789")
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
    void testDeleteClientQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(delete("/client/questionnaire/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeleteQuestionnaireRequest.newBuilder()
                                .setId("789")
                                .build())))
                .andReturn();
        Assertions.assertEquals(
                "{\"success\":true,\"message\":\"Executed DeleteClientQuestionnaire operation.\",\"id\":\"\"}",
                mv.getResponse().getContentAsString());
    }

    // User Level Questionnaire
    @Test
    void testCreateUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/user/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(UserQuestionnaireDataRequest.newBuilder()
                                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                                        .setUserId(ObjectId.get().toHexString())
                                        .build())
                                .build())))
                .andReturn();
        Assertions.assertEquals(200, mv.getResponse().getStatus());
    }

    @Test
    void testGetUserQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/user/questionnaire/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireRequest.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(0)
                                        .setPageSize(10)
                                        .build())
                                .setId(ObjectId.get().toHexString())
                                .build())))
                .andReturn();
        Assertions.assertEquals(200, mv.getResponse().getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user/questionnaire/list", "/system/questionnaire/list", "/client/questionnaire/list"})
    void testGetUserQuestionnaires(String url) throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(GetQuestionnaireListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(0)
                                        .setPageSize(10)
                                        .build())
                                .setId(ObjectId.get().toHexString())
                                .build())))
                .andReturn();
        Assertions.assertFalse(mv.getResponse().getContentAsString().contains("cause"));
    }

    @Test
    void createRuleSet() throws Exception {

        CreateRuleSetRequest request = CreateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setCategory("category")
                        .setType("type")
                        .setDescription("description")
                        .setRule("rule")
                        .setStatus(QuestionnaireStatus.draft)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/ruleset")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateRuleSet() throws Exception {

        UpdateRuleSetRequest request = UpdateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setRuleId(ObjectId.get().toHexString())
                        .setCategory("category")
                        .setType("type")
                        .setDescription("description")
                        .setRule("rule")
                        .setStatus(QuestionnaireStatus.draft)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/ruleset")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getRuleSet() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/ruleset/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteRuleSet() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/ruleset/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getRuleSets() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/ruleset/list").content("{}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
