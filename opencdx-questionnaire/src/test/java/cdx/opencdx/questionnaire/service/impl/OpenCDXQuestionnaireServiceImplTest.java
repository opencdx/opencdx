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
package cdx.opencdx.questionnaire.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.commons.service.impl.OpenCDXClassificationMessageServiceImpl;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.model.OpenCDXQuestionnaireModel;
import cdx.opencdx.questionnaire.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.questionnaire.repository.OpenCDXQuestionnaireRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXRuleSetRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXUserQuestionnaireRepository;
import cdx.opencdx.questionnaire.service.OpenCDXQuestionnaireService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXQuestionnaireServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXQuestionnaireService questionnaireService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXQuestionnaireRepository openCDXQuestionnaireRepository;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXUserQuestionnaireRepository openCDXUserQuestionnaireRepository;

    @Mock
    OpenCDXRuleSetRepository openCDXRuleSetRepository;

    @Autowired
    OpenCDXMessageService openCDXMessageService;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    OpenCDXClassificationMessageService openCDXClassificationMessageService;

    @BeforeEach
    void beforeEach() {

        Mockito.when(this.openCDXProfileRepository.save(Mockito.any(OpenCDXProfileModel.class)))
                .thenAnswer(new Answer<OpenCDXProfileModel>() {
                    @Override
                    public OpenCDXProfileModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXProfileModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
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
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });

        Mockito.when(this.openCDXUserQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
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
                                .patientId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build());
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXUserQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(openCDXQuestionnaireRepository.save(Mockito.any(OpenCDXQuestionnaireModel.class)))
                .thenAnswer(new Answer<OpenCDXQuestionnaireModel>() {
                    @Override
                    public OpenCDXQuestionnaireModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXQuestionnaireModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXQuestionnaireModel>>() {
                    @Override
                    public Optional<OpenCDXQuestionnaireModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXQuestionnaireModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        this.openCDXClassificationMessageService = new OpenCDXClassificationMessageServiceImpl(
                openCDXMessageService, openCDXDocumentValidator, openCDXProfileRepository, openCDXCurrentUser);
        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);
    }

    @AfterEach
    void tearDown() {}

    // Unit tests for - User Questionnaire
    @Test
    void testSubmitQuestionnaire() {
        QuestionnaireRequest request = QuestionnaireRequest.newBuilder().build();
        Questionnaire response = this.questionnaireService.createQuestionnaire(request);

        Assertions.assertNotNull(response);
    }

    @Test
    void testSubmitQuestionnaireFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXQuestionnaireModel.class)))
                .thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> questionnaireService.createQuestionnaire(request));
    }

    @Test
    void testGetSubmittedQuestionnaire() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertNotNull(this.questionnaireService.getSubmittedQuestionnaire(request));
    }

    @Test
    void testGetSubmittedQuestionnaireList() {
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageSize(1)
                        .setPageNumber(2)
                        .setSortAscending(true)
                        .build())
                .build();
        Questionnaires response = this.questionnaireService.getSubmittedQuestionnaireList(request);

        Assertions.assertEquals(1, response.getQuestionnairesCount());
    }

    @Test
    void testDeleteSubmittedQuestionnaire() {
        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        SubmissionResponse response = this.questionnaireService.deleteSubmittedQuestionnaire(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testDeleteSubmittedQuestionnaireWithJsonProcessingException() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        // Mocking the necessary dependencies
        Mockito.when(mapper.writeValueAsString(Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenThrow(JsonProcessingException.class);

        OpenCDXIAMUserModel currentUser =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        Mockito.when(openCDXCurrentUser.getCurrentUser()).thenReturn(currentUser);

        // Creating the service instance
        OpenCDXQuestionnaireService questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                openCDXAuditService,
                mapper,
                openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();

        // Verify that the OpenCDXNotAcceptable exception is thrown
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.deleteSubmittedQuestionnaire(request));
    }

    // Unit tests for - System Questionnaire
    @Test
    void testCreateQuestionnaireData() {
        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.createQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testUpdateQuestionnaireData() {
        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.updateQuestionnaireData(request);

        Assertions.assertEquals("updateQuestionnaireData Executed", response.getMessage());
    }

    @Test
    void testGetQuestionnaireData() {
        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        SystemQuestionnaireData response = this.questionnaireService.getQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnaireDataCount());
    }

    @Test
    void testGetQuestionnaireDataList() {
        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        SystemQuestionnaireData response = this.questionnaireService.getQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnaireDataCount());
    }

    @Test
    void testDeleteQuestionnaireData() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    // Unit tests for - Client Questionnaire
    @Test
    void testCreateClientQuestionnaireData() {
        ClientQuestionnaireDataRequest request =
                ClientQuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.createClientQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testUpdateClientQuestionnaireData() {
        ClientQuestionnaireDataRequest request =
                ClientQuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.updateClientQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testGetClientQuestionnaireData() {
        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        ClientQuestionnaireData response = this.questionnaireService.getClientQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnaireDataCount());
    }

    @Test
    void testGetClientQuestionnaireDataList() {
        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        ClientQuestionnaireData response = this.questionnaireService.getClientQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnaireDataCount());
    }

    void testDeleteClientQuestionnaireData() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteClientQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    // Unit tests for - User Questionnaire
    @Test
    void testCreateUserQuestionnaireData() {
        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .addAllQuestionnaireData(List.of(Questionnaire.getDefaultInstance()))
                        .build())
                .build();
        SubmissionResponse response = this.questionnaireService.createUserQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testCreateUserQuestionnaireDataHasId() {
        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .setId("65bb9634ab091e2343ff7ef7")
                        .setPatientId(ObjectId.get().toHexString())
                        .addAllQuestionnaireData(List.of(Questionnaire.getDefaultInstance()))
                        .build())
                .build();
        SubmissionResponse response = this.questionnaireService.createUserQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testCreateUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(Questionnaire.getDefaultInstance())
                        .setPatientId(ObjectId.get().toHexString()))
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.createUserQuestionnaireData(request));
    }

    @Test
    void testGetUserQuestionnaireData() {
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        UserQuestionnaireDataResponse response = this.questionnaireService.getUserQuestionnaireDataList(request);

        Assertions.assertEquals(1, response.getListList().size());
    }

    @Test
    void testGetUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getUserQuestionnaireData(request));
    }

    @Test
    void testGetUserQuestionnaireDataExceptionQuestionnaire() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        Mockito.when(this.openCDXUserQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.questionnaireService.getUserQuestionnaireData(request));
    }

    @Test
    void testGetUserQuestionnaireDataList() {
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        UserQuestionnaireDataResponse response = this.questionnaireService.getUserQuestionnaireDataList(request);

        Assertions.assertEquals(1, response.getListList().size());
    }

    @Test
    void testGetUserQuestionnaireDataListFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                this.openCDXQuestionnaireRepository,
                this.openCDXUserQuestionnaireRepository,
                this.openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getUserQuestionnaireDataList(request));
    }
}
