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
package cdx.opencdx.questionnaire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.profile.ContactInfo;
import cdx.opencdx.grpc.profile.FullName;
import cdx.opencdx.grpc.profile.PhoneNumber;
import cdx.opencdx.grpc.profile.PhoneType;
import cdx.opencdx.grpc.questionnaire.Questionnaire;
import cdx.opencdx.grpc.questionnaire.QuestionnaireRequest;
import cdx.opencdx.grpc.questionnaire.SubmissionResponse;
import cdx.opencdx.grpc.routine.DeliveryTracking;
import cdx.opencdx.grpc.routine.DeliveryTrackingRequest;
import cdx.opencdx.grpc.routine.Routine;
import cdx.opencdx.grpc.routine.RoutineRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                                        .setEmail("ab@safehealth.me")
                                        .setMobileNumber(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build())
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
    void testSubmitQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(post("/questionnaire/submitquestionnaire")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(
                                        Questionnaire.newBuilder().setId("789").build())
                                .build())))
                        .andReturn();
        Assertions.assertEquals("{\"success\":true,\"message\":\"Executed SubmitQuestionnaire operation.\"}", 
                mv.getResponse().getContentAsString());
    }

    @Test
    void testGetSubmittedQuestionnaire() throws Exception {
        MvcResult mv = this.mockMvc
                .perform(get("/questionnaire/submittedquestionnaire/12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(QuestionnaireRequest.newBuilder()
                                .setQuestionnaire(
                                        Questionnaire.newBuilder().setId("12345").build())
                                .build())))
                .andReturn();
        Assertions.assertEquals("{\"description\":\"System Level Questionnaire Description\",\"item\":[]}", 
                mv.getResponse().getContentAsString());
    }

}
