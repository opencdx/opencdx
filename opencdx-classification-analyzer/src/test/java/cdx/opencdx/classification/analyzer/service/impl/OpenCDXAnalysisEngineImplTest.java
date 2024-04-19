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
package cdx.opencdx.classification.analyzer.service.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import cdx.opencdx.classification.analyzer.dto.RuleResult;
import cdx.opencdx.classification.analyzer.rules.BloodPressureRules;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.logistics.TestCaseListResponse;
import cdx.opencdx.grpc.types.ClassificationType;
import java.io.IOException;
import java.util.List;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.api.StatelessSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("java:S5976")
class OpenCDXAnalysisEngineImplTest {

    OpenCDXMediaUpDownClient openCDXMediaUpDownClient;
    OpenCDXTestCaseClient openCDXTestCaseClient;
    OpenCDXCurrentUser openCDXCurrentUser;

    @Test
    void getRuleSets() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        Assertions.assertNotNull(engine.getRuleSets(request));
    }

    @Test
    void analyzeConnectedTest() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        ConnectedTest connectedTest = ConnectedTest.newBuilder().build();
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        Assertions.assertNotNull(
                engine.analyzeConnectedTest(openCDXProfileModel, userAnswer, media, connectedTest, media));
    }

    @Test
    void analyzeConnectedTestOpenCDXInternal() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenThrow(OpenCDXInternal.class);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        ConnectedTest connectedTest = ConnectedTest.newBuilder().build();
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> engine.analyzeConnectedTest(openCDXProfileModel, userAnswer, media, connectedTest, media));
    }

    @Test
    void analyzeQuestionnaire() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("")
                        .addAllRuleQuestionId(List.of())
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireIsBlank() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId(" ")
                        .addAllRuleQuestionId(List.of("q1", "q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireIsNull() {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId(" ")
                        .addAllRuleQuestionId(List.of("q1", "q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressureCatch() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllRuleQuestionId(List.of("q1", "q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenThrow(IOException.class);
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressureCatchElseKnowledge() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("9b75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllRuleQuestionId(List.of("q1", "q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenThrow(IOException.class);
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressurePass() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q2")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .addAllRuleQuestionId(List.of("q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressurePassDefault() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("string")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q2")
                        .setType("string")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .addAllRuleQuestionId(List.of("q2"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressurePassIsPresent() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q3")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .addAllRuleQuestionId(List.of("q4"))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressurePassQuesIdBlank() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q3")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .addAllRuleQuestionId(List.of(" "))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressurePassQuesIdEmpty() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q3")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .addAllRuleQuestionId(List.of(""))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
    }

    @Test
    void analyzeQuestionnaireBloodPressureQuesIDCountZero() throws IOException {
        this.openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);
        this.openCDXTestCaseClient = Mockito.mock(OpenCDXTestCaseClient.class);
        this.openCDXMediaUpDownClient = Mockito.mock(OpenCDXMediaUpDownClient.class);
        RuleSetsRequest request = RuleSetsRequest.newBuilder().build();
        OpenCDXAnalysisEngineImpl engine =
                new OpenCDXAnalysisEngineImpl(openCDXMediaUpDownClient, openCDXTestCaseClient, openCDXCurrentUser);
        QuestionnaireItem questionnaireItem = QuestionnaireItem.newBuilder()
                .setLinkId("q1")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q1")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        QuestionnaireItem questionnaireItem2 = QuestionnaireItem.newBuilder()
                .setLinkId("q2")
                .setType("boolean")
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .setLinkId("q3")
                        .setType("boolean")
                        .build()))
                .addAllAnswer(
                        List.of(AnswerValue.newBuilder().setValueBoolean(true).build()))
                .build();
        UserQuestionnaireData userQuestionnaireData = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .addAllQuestionnaireData(List.of(Questionnaire.newBuilder()
                        .setRuleId("8a75ec67-880b-41cd-a526-a12aa9aef2c1")
                        .addAllItem(List.of(questionnaireItem, questionnaireItem2))
                        .build()))
                .build();

        Media media = Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setMimeType("text/plain")
                .build();
        ResponseEntity<Resource> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(Mockito.mock(Resource.class));
        when(openCDXMediaUpDownClient.download(anyString(), anyString(), anyString()))
                .thenReturn(responseEntity);
        when(openCDXCurrentUser.getCurrentUserAccessToken()).thenReturn("token");
        TestCaseListResponse testCaseListResponse =
                TestCaseListResponse.newBuilder().build();
        when(openCDXTestCaseClient.listTestCase(any(), any())).thenReturn(testCaseListResponse);
        OpenCDXProfileModel openCDXProfileModel = mock(OpenCDXProfileModel.class);
        when(openCDXProfileModel.getId()).thenReturn(OpenCDXIdentifier.get());
        UserAnswer userAnswer = UserAnswer.newBuilder().build();
        KnowledgeService knowledgeService = mock(KnowledgeService.class);

        Knowledge knowledge = mock(Knowledge.class);
        when(knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class))
                .thenReturn(knowledge);
        StatelessSession statelessSession = mock(StatelessSession.class);
        RuleResult result = mock(RuleResult.class);
        result.setType(ClassificationType.BACTERIAL);
        result.setTestKit(TestKit.newBuilder().build());
        doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        RuleResult argument = invocation.getArgument(2);
                        argument.setType(ClassificationType.BACTERIAL);
                        argument.setTestKit(TestKit.newBuilder().build());
                        return null;
                    }
                })
                .when(statelessSession)
                .insertAndFire(anyBoolean(), Mockito.any(RuleResult.class));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);

        statelessSession.insertAndFire(anyBoolean(), Mockito.eq(result));
        when(knowledge.newStatelessSession()).thenReturn(statelessSession);
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData));
        UserQuestionnaireData userQuestionnaireData2 = UserQuestionnaireData.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertDoesNotThrow(
                () -> engine.analyzeQuestionnaire(openCDXProfileModel, userAnswer, media, userQuestionnaireData2));
    }
}
