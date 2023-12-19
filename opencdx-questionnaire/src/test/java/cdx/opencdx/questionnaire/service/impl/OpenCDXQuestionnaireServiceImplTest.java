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
package cdx.opencdx.questionnaire.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.service.OpenCDXQuestionnaireService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXQuestionnaireServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXQuestionnaireService questionnaireService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, this.objectMapper, openCDXCurrentUser);
    }

    @AfterEach
    void tearDown() {}

    // Unit tests for - User Questionnaire
    @Test
    void testSubmitQuestionnaire() {
        QuestionnaireRequest request = QuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.submitQuestionnaire(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testSubmitQuestionnaireFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> questionnaireService.submitQuestionnaire(request));
    }

    @Test
    void testGetSubmittedQuestionnaire() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaire response = this.questionnaireService.getSubmittedQuestionnaire(request);

        Assertions.assertEquals("User Submitted Questionnaire Description", response.getDescription());
    }

    @Test
    void testGetSubmittedQuestionnaireFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getSubmittedQuestionnaire(request));
    }

    @Test
    void testGetSubmittedQuestionnaireList() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaires response = this.questionnaireService.getSubmittedQuestionnaireList(request);

        Assertions.assertEquals(2, response.getQuestionnairesCount());
    }

    @Test
    void testGetSubmittedQuestionnaireListFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getSubmittedQuestionnaireList(request));
    }

    @Test
    void testDeleteSubmittedQuestionnaire() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteSubmittedQuestionnaire(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testDeleteSubmittedQuestionnaireWithJsonProcessingException() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        // Mocking the necessary dependencies
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        OpenCDXIAMUserModel currentUser =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        Mockito.when(openCDXCurrentUser.getCurrentUser()).thenReturn(currentUser);

        // Creating the service instance
        OpenCDXQuestionnaireService questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(openCDXAuditService, mapper, openCDXCurrentUser);

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
    void testCreateQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.createQuestionnaireData(request));
    }

    @Test
    void testUpdateQuestionnaireData() {
        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.updateQuestionnaireData(request);

        Assertions.assertEquals("updateQuestionnaireData Executed", response.getMessage());
    }

    @Test
    void testUpdateQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.updateQuestionnaireData(request));
    }

    @Test
    void testGetQuestionnaireData() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaire response = this.questionnaireService.getQuestionnaireData(request);

        Assertions.assertEquals("System Level Questionnaire Description", response.getDescription());
    }

    @Test
    void testGetQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> questionnaireService.getQuestionnaireData(request));
    }

    @Test
    void testGetQuestionnaireDataList() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaires response = this.questionnaireService.getQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnairesCount());
    }

    @Test
    void testGetQuestionnaireDataListFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getQuestionnaireDataList(request));
    }

    @Test
    void testDeleteQuestionnaireData() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testDeleteQuestionnaireDataWithJsonProcessingException() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        // Mocking the necessary dependencies
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        OpenCDXIAMUserModel currentUser =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        Mockito.when(openCDXCurrentUser.getCurrentUser()).thenReturn(currentUser);

        // Creating the service instance
        OpenCDXQuestionnaireService questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(openCDXAuditService, mapper, openCDXCurrentUser);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();

        // Verify that the OpenCDXNotAcceptable exception is thrown
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.deleteQuestionnaireData(request));
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
    void testCreateClientQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        ClientQuestionnaireDataRequest request =
                ClientQuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.createClientQuestionnaireData(request));
    }

    @Test
    void testUpdateClientQuestionnaireData() {
        ClientQuestionnaireDataRequest request =
                ClientQuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.updateClientQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testUpdateClientQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        ClientQuestionnaireDataRequest request =
                ClientQuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.updateClientQuestionnaireData(request));
    }

    @Test
    void testGetClientQuestionnaireData() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaire response = this.questionnaireService.getClientQuestionnaireData(request);

        Assertions.assertEquals("Client Level Questionnaire Description", response.getDescription());
    }

    @Test
    void testGetClientQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getClientQuestionnaireData(request));
    }

    @Test
    void testGetClientQuestionnaireDataList() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaires response = this.questionnaireService.getClientQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnairesCount());
    }

    @Test
    void testGetClientQuestionnaireDataListFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getClientQuestionnaireDataList(request));
    }

    void testDeleteClientQuestionnaireData() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteClientQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testDeleteClientQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.deleteClientQuestionnaireData(request));
    }

    @Test
    void testDeleteClientQuestionnaireDataWithJsonProcessingException() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        OpenCDXIAMUserModel currentUser =
                OpenCDXIAMUserModel.builder().id(ObjectId.get()).build();
        Mockito.when(openCDXCurrentUser.getCurrentUser()).thenReturn(currentUser);

        OpenCDXQuestionnaireService questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(openCDXAuditService, mapper, openCDXCurrentUser);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.deleteClientQuestionnaireData(request));
    }

    // Unit tests for - User Questionnaire
    @Test
    void testCreateUserQuestionnaireData() {
        UserQuestionnaireDataRequest request =
                UserQuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.createUserQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testCreateUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        UserQuestionnaireDataRequest request =
                UserQuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.createUserQuestionnaireData(request));
    }

    @Test
    void testUpdateUserQuestionnaireData() {
        UserQuestionnaireDataRequest request =
                UserQuestionnaireDataRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.updateUserQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testUpdateUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);

        UserQuestionnaireDataRequest request =
                UserQuestionnaireDataRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.updateUserQuestionnaireData(request));
    }

    @Test
    void testGetUserQuestionnaireData() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaire response = this.questionnaireService.getUserQuestionnaireData(request);

        Assertions.assertEquals("User Level Questionnaire Description", response.getDescription());
    }

    @Test
    void testGetUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getUserQuestionnaireData(request));
    }

    @Test
    void testGetUserQuestionnaireDataList() {
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Questionnaires response = this.questionnaireService.getUserQuestionnaireDataList(request);

        Assertions.assertEquals(2, response.getQuestionnairesCount());
    }

    @Test
    void testGetUserQuestionnaireDataListFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.getUserQuestionnaireDataList(request));
    }

    @Test
    void testDeleteUserQuestionnaireData() {
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        SubmissionResponse response = this.questionnaireService.deleteUserQuestionnaireData(request);

        Assertions.assertEquals(true, response.getSuccess());
    }

    @Test
    void testDeleteUserQuestionnaireDataFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, mapper, this.openCDXCurrentUser);
        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> questionnaireService.deleteUserQuestionnaireData(request));
    }
}
