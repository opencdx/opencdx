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
package cdx.opencdx.classification.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.repository.OpenCDXClassificationRepository;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
class OpenCDXRestClassificationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @MockBean
    OpenCDXClassificationRepository openCDXClassificationRepository;

    @MockBean
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .fullName(FullName.newBuilder()
                                        .setLastName("Bobby")
                                        .setFirstName("Bob")
                                        .build())
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                                .setEmail("bob@opencdx.org")
                                                .setType(EmailType.EMAIL_TYPE_PERSONAL)
                                                .build()))
                                        .build())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(Mockito.any(), Mockito.any()))
                .thenReturn(UserQuestionnaireData.getDefaultInstance());
        Mockito.when(this.openCDXClassificationRepository.save(Mockito.any(OpenCDXClassificationModel.class)))
                .thenAnswer(new Answer<OpenCDXClassificationModel>() {
                    @Override
                    public OpenCDXClassificationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXClassificationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
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
    void testSubmitClassification() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/classify")
                        .content(this.objectMapper.writeValueAsString(ClassificationRequest.newBuilder()
                                .setUserAnswer(UserAnswer.newBuilder()
                                        .setPatientId(ObjectId.get().toHexString())
                                        .setUserQuestionnaireId(ObjectId.get().toHexString())
                                        .setGender(Gender.GENDER_MALE)
                                        .setAge(30))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}
