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
package cdx.opencdx.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.grpc.questionnaire.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXQuestionnaireClientImplTest {

    @Mock
    QuestionnaireServiceGrpc.QuestionnaireServiceBlockingStub questionnaireServiceBlockingStub;

    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @BeforeEach
    void setUp() {
        this.questionnaireServiceBlockingStub =
                Mockito.mock(QuestionnaireServiceGrpc.QuestionnaireServiceBlockingStub.class);
        this.openCDXQuestionnaireClient = new OpenCDXQuestionnaireClientImpl(this.questionnaireServiceBlockingStub);
        Mockito.when(questionnaireServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.questionnaireServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.questionnaireServiceBlockingStub);
    }

    @Test
    void getRuleSets() {
        Mockito.when(this.questionnaireServiceBlockingStub.getRuleSets(Mockito.any(ClientRulesRequest.class)))
                .thenReturn(RuleSetsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                RuleSetsResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getRuleSets(
                        ClientRulesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getRuleSetsException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getRuleSets(Mockito.any(ClientRulesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClientRulesRequest request = ClientRulesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getRuleSets(request, openCDXCallCredentials));
    }

    @Test
    void createQuestionnaire() {
        Mockito.when(this.questionnaireServiceBlockingStub.createQuestionnaire(Mockito.any(QuestionnaireRequest.class)))
                .thenReturn(Questionnaire.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Questionnaire.getDefaultInstance(),
                this.openCDXQuestionnaireClient.createQuestionnaire(
                        QuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createQuestionnaireException() {
        Mockito.when(this.questionnaireServiceBlockingStub.createQuestionnaire(Mockito.any(QuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        QuestionnaireRequest request = QuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.createQuestionnaire(request, openCDXCallCredentials));
    }

    @Test
    void updateQuestionnaire() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateQuestionnaire(Mockito.any(QuestionnaireRequest.class)))
                .thenReturn(Questionnaire.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Questionnaire.getDefaultInstance(),
                this.openCDXQuestionnaireClient.updateQuestionnaire(
                        QuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateQuestionnaireException() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateQuestionnaire(Mockito.any(QuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        QuestionnaireRequest request = QuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.updateQuestionnaire(request, openCDXCallCredentials));
    }

    @Test
    void getSubmittedQuestionnaire() {
        Mockito.when(this.questionnaireServiceBlockingStub.getSubmittedQuestionnaire(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenReturn(Questionnaire.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Questionnaire.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getSubmittedQuestionnaire(
                        GetQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getSubmittedQuestionnaireException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getSubmittedQuestionnaire(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireRequest request = GetQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getSubmittedQuestionnaire(request, openCDXCallCredentials));
    }

    @Test
    void getSubmittedQuestionnaireList() {
        Mockito.when(this.questionnaireServiceBlockingStub.getSubmittedQuestionnaireList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenReturn(Questionnaires.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Questionnaires.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getSubmittedQuestionnaireList(
                        GetQuestionnaireListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getSubmittedQuestionnaireListException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getSubmittedQuestionnaireList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getSubmittedQuestionnaireList(request, openCDXCallCredentials));
    }

    @Test
    void deleteSubmittedQuestionnaire() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteSubmittedQuestionnaire(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.deleteSubmittedQuestionnaire(
                        DeleteQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteSubmittedQuestionnaireException() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteSubmittedQuestionnaire(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.deleteSubmittedQuestionnaire(request, openCDXCallCredentials));
    }

    @Test
    void createQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.createQuestionnaireData(
                        Mockito.any(QuestionnaireDataRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.createQuestionnaireData(
                        QuestionnaireDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.createQuestionnaireData(
                        Mockito.any(QuestionnaireDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        QuestionnaireDataRequest request = QuestionnaireDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.createQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void updateQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateQuestionnaireData(
                        Mockito.any(QuestionnaireDataRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.updateQuestionnaireData(
                        QuestionnaireDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateQuestionnaireData(
                        Mockito.any(QuestionnaireDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        QuestionnaireDataRequest request = QuestionnaireDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.updateQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void getQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.getQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenReturn(SystemQuestionnaireData.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SystemQuestionnaireData.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getQuestionnaireData(
                        GetQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireRequest request = GetQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void getQuestionnaireDataList() {
        Mockito.when(this.questionnaireServiceBlockingStub.getQuestionnaireDataList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenReturn(SystemQuestionnaireData.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SystemQuestionnaireData.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getQuestionnaireDataList(
                        GetQuestionnaireListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getQuestionnaireDataListException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getQuestionnaireDataList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getQuestionnaireDataList(request, openCDXCallCredentials));
    }

    @Test
    void deleteQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteQuestionnaireData(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.deleteQuestionnaireData(
                        DeleteQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteQuestionnaireData(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.deleteQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void createClientQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.createClientQuestionnaireData(
                        Mockito.any(ClientQuestionnaireDataRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.createClientQuestionnaireData(
                        ClientQuestionnaireDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createClientQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.createClientQuestionnaireData(
                        Mockito.any(ClientQuestionnaireDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.createClientQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void updateClientQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateClientQuestionnaireData(
                        Mockito.any(ClientQuestionnaireDataRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.updateClientQuestionnaireData(
                        ClientQuestionnaireDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateClientQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.updateClientQuestionnaireData(
                        Mockito.any(ClientQuestionnaireDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.updateClientQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void getClientQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.getClientQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenReturn(ClientQuestionnaireData.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ClientQuestionnaireData.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getClientQuestionnaireData(
                        GetQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getClientQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getClientQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireRequest request = GetQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getClientQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void getClientQuestionnaireDataList() {
        Mockito.when(this.questionnaireServiceBlockingStub.getClientQuestionnaireDataList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenReturn(ClientQuestionnaireData.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ClientQuestionnaireData.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getClientQuestionnaireDataList(
                        GetQuestionnaireListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getClientQuestionnaireDataListException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getClientQuestionnaireDataList(
                        Mockito.any(GetQuestionnaireListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getClientQuestionnaireDataList(request, openCDXCallCredentials));
    }

    @Test
    void deleteClientQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteClientQuestionnaireData(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.deleteClientQuestionnaireData(
                        DeleteQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteClientQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.deleteClientQuestionnaireData(
                        Mockito.any(DeleteQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.deleteClientQuestionnaireData(request, openCDXCallCredentials));
    }

    @Test
    void createUserQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.createUserQuestionnaireData(
                        Mockito.any(UserQuestionnaireDataRequest.class)))
                .thenReturn(SubmissionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SubmissionResponse.getDefaultInstance(),
                this.openCDXQuestionnaireClient.createUserQuestionnaireData(
                        UserQuestionnaireDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createUserQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.createUserQuestionnaireData(
                        Mockito.any(UserQuestionnaireDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.createUserQuestionnaireData(request, openCDXCallCredentials));
    }

    //    @Test
    //    void updateUserQuestionnaireData() {
    //        Mockito.when(this.questionnaireServiceBlockingStub.getRuleSets(Mockito.any(ClientRulesRequest.class)))
    //                .thenReturn(RuleSetsResponse.getDefaultInstance());
    //        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
    //        Assertions.assertEquals(
    //                RuleSetsResponse.getDefaultInstance(),
    //                this.openCDXQuestionnaireClient.getRuleSets(
    //                        ClientRulesRequest.getDefaultInstance(), openCDXCallCredentials));
    //    }
    //
    //    @Test
    //    void updateUserQuestionnaireDataException() {
    //    }

    @Test
    void getUserQuestionnaireData() {
        Mockito.when(this.questionnaireServiceBlockingStub.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenReturn(UserQuestionnaireData.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UserQuestionnaireData.getDefaultInstance(),
                this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                        GetQuestionnaireRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getUserQuestionnaireDataException() {
        Mockito.when(this.questionnaireServiceBlockingStub.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetQuestionnaireRequest request = GetQuestionnaireRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXQuestionnaireClient.getUserQuestionnaireData(request, openCDXCallCredentials));
    }

    //    @Test
    //    void getUserQuestionnaireDataList() {
    //        Mockito.when(this.questionnaireServiceBlockingStub.getRuleSets(Mockito.any(ClientRulesRequest.class)))
    //                .thenReturn(RuleSetsResponse.getDefaultInstance());
    //        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
    //        Assertions.assertEquals(
    //                RuleSetsResponse.getDefaultInstance(),
    //                this.openCDXQuestionnaireClient.getRuleSets(
    //                        ClientRulesRequest.getDefaultInstance(), openCDXCallCredentials));
    //    }
    //
    //    @Test
    //    void getUserQuestionnaireDataListException() {
    //    }

    //    @Test
    //    void deleteUserQuestionnaireData() {
    //        Mockito.when(this.questionnaireServiceBlockingStub.getRuleSets(Mockito.any(ClientRulesRequest.class)))
    //                .thenReturn(RuleSetsResponse.getDefaultInstance());
    //        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
    //        Assertions.assertEquals(
    //                RuleSetsResponse.getDefaultInstance(),
    //                this.openCDXQuestionnaireClient.getRuleSets(
    //                        ClientRulesRequest.getDefaultInstance(), openCDXCallCredentials));
    //    }
    //
    //    @Test
    //    void deleteUserQuestionnaireDataException() {
    //    }
}
