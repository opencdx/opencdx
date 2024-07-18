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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAdrMessageService;
import cdx.opencdx.grpc.data.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class OpenCDXANFServiceImplTest {

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXAdrMessageService openCDXAdrMessageService;

    OpenCDXIdentifier openCDXIdentifier;

    @BeforeEach
    void setUp() {
        this.openCDXIdentifier = OpenCDXIdentifier.get();

        openCDXAdrMessageService = Mockito.mock(OpenCDXAdrMessageService.class);

        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
    }

    @Test
    void processQuestionnaires() {
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.getDefaultInstance());
        Assertions.assertDoesNotThrow(() -> openCDXANFService.processQuestionnaires(questionnaires, openCDXIdentifier));
    }

    @Test
    void processQuestionnaires1() {
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.newBuilder()
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .addAllAnfStatementConnector(List.of(AnfStatementConnector.newBuilder()
                                .setAnfStatement(ANFStatement.newBuilder()
                                        .setId("UUID")
                                        .setTime(Measure.newBuilder()
                                                .setUpperBound(1562631151)
                                                .setLowerBound(1562631151)
                                                .setIncludeLowerBound(true)
                                                .setIncludeUpperBound(true)
                                                .setResolution(1)
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Seconds | 1562631151"))
                                                .build())
                                        .setSubjectOfRecord(Participant.newBuilder()
                                                .setId("UUID (PatientId)")
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("UUID (EncounterID)"))
                                                .build())
                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                .setPractitionerValue(Reference.newBuilder()
                                                        .setDisplay("display")
                                                        .setIdentifier("identifier")
                                                        .setReference("reference")
                                                        .setUri("uri")
                                                        .build())
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("expression"))
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .build()))
                                        .setSubjectOfInformation(
                                                LogicalExpression.newBuilder().setExpression("UUID  (PatientId)"))
                                        .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Associated description"))
                                                .build()))
                                        .setTopic(
                                                LogicalExpression.newBuilder()
                                                        .setExpression(
                                                                "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "))
                                        .setType(LogicalExpression.newBuilder().setExpression("PERFORMANCE"))
                                        .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                                                .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                        .setExpression("purpose")
                                                        .build()))
                                                .setStatus(
                                                        LogicalExpression.newBuilder()
                                                                .setExpression(
                                                                        "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}"))
                                                .setHealthRisk(
                                                        LogicalExpression.newBuilder()
                                                                .setExpression(
                                                                        "${{rules.engine.calculated[circumstanceChoice.result]}}"))
                                                .setResult(Measure.newBuilder()
                                                        .setLowerBound(90)
                                                        .setIncludeLowerBound(false)
                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                .setExpression(""))
                                                        .setResolution(1)
                                                        .setUpperBound(120)
                                                        .setIncludeUpperBound(false)
                                                        .build())
                                                .setNormalRange(Measure.getDefaultInstance())
                                                .setTiming(Measure.getDefaultInstance())
                                                .addAllParticipant(List.of(Participant.getDefaultInstance()))
                                                .build())
                                        .build())
                                .build()))
                        .build()))
                .build());
        Assertions.assertDoesNotThrow(
                () -> openCDXANFService.processQuestionnaires(questionnaires, this.openCDXIdentifier));
    }

    @Test
    void processQuestionnaires2() {
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.newBuilder()
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .addAllAnfStatementConnector(List.of(AnfStatementConnector.newBuilder()
                                .setAnfStatement(ANFStatement.newBuilder()
                                        .setId("UUID")
                                        .setTime(Measure.newBuilder()
                                                .setUpperBound(1562631151)
                                                .setLowerBound(1562631151)
                                                .setIncludeLowerBound(true)
                                                .setIncludeUpperBound(true)
                                                .setResolution(1)
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Seconds | 1562631151"))
                                                .build())
                                        .setSubjectOfRecord(Participant.newBuilder()
                                                .setId("UUID (PatientId)")
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("UUID (EncounterID)"))
                                                .build())
                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                .setPractitionerValue(Reference.newBuilder()
                                                        .setDisplay("display")
                                                        .setIdentifier("identifier")
                                                        .setReference("reference")
                                                        .setUri("uri")
                                                        .build())
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("expression"))
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .build()))
                                        .setSubjectOfInformation(
                                                LogicalExpression.newBuilder().setExpression("UUID  (PatientId)"))
                                        .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Associated description"))
                                                .build()))
                                        .setTopic(
                                                LogicalExpression.newBuilder()
                                                        .setExpression(
                                                                "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "))
                                        .setType(LogicalExpression.newBuilder().setExpression("PERFORMANCE"))
                                        .setRequestCircumstance(RequestCircumstance.newBuilder()
                                                .addRequestedParticipant(Reference.newBuilder()
                                                        .setDisplay("display")
                                                        .setIdentifier("identifier")
                                                        .setReference("reference")
                                                        .setUri("uri")
                                                        .build())
                                                .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                        .setExpression("purpose")
                                                        .build()))
                                                .addConditionalTrigger(AssociatedStatement.newBuilder()
                                                        .build())
                                                .setPriority(LogicalExpression.newBuilder()
                                                        .setExpression("high"))
                                                .setRepetition(Repetition.getDefaultInstance())
                                                .build())
                                        .build())
                                .build()))
                        .build()))
                .build());
        Assertions.assertDoesNotThrow(
                () -> openCDXANFService.processQuestionnaires(questionnaires, this.openCDXIdentifier));
    }

    @Test
    void processQuestionnaires3() {
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.newBuilder()
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .addAllAnfStatementConnector(List.of(AnfStatementConnector.newBuilder()
                                .setAnfStatement(ANFStatement.newBuilder()
                                        .setId("UUID")
                                        .setTime(Measure.newBuilder()
                                                .setUpperBound(1562631151)
                                                .setLowerBound(1562631151)
                                                .setIncludeLowerBound(true)
                                                .setIncludeUpperBound(true)
                                                .setResolution(1)
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Seconds | 1562631151"))
                                                .build())
                                        .setSubjectOfRecord(Participant.newBuilder()
                                                .setId("UUID (PatientId)")
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("UUID (EncounterID)"))
                                                .build())
                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                .setPractitionerValue(Reference.newBuilder()
                                                        .setDisplay("display")
                                                        .setIdentifier("identifier")
                                                        .setReference("reference")
                                                        .setUri("uri")
                                                        .build())
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("expression"))
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .build()))
                                        .setSubjectOfInformation(
                                                LogicalExpression.newBuilder().setExpression("UUID  (PatientId)"))
                                        .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Associated description"))
                                                .build()))
                                        .setTopic(
                                                LogicalExpression.newBuilder()
                                                        .setExpression(
                                                                "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "))
                                        .setType(LogicalExpression.newBuilder().setExpression("PERFORMANCE"))
                                        .setNarrativeCircumstance(NarrativeCircumstance.newBuilder()
                                                .addPurpose(LogicalExpression.newBuilder()
                                                        .setExpression("purpose"))
                                                .setText("text")
                                                .setTiming(Measure.newBuilder()
                                                        .setUpperBound(1562631151)
                                                        .setLowerBound(1562631151)
                                                        .setIncludeLowerBound(true)
                                                        .setIncludeUpperBound(true)
                                                        .setResolution(1)
                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                .setExpression("Seconds | 1562631151"))
                                                        .build())
                                                .build())
                                        .build())
                                .build()))
                        .build()))
                .build());
        Assertions.assertDoesNotThrow(
                () -> openCDXANFService.processQuestionnaires(questionnaires, this.openCDXIdentifier));
    }

    @Test
    void processQuestionnaires4() {
        openCDXAdrMessageService = Mockito.mock(OpenCDXAdrMessageService.class);

        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.empty();
                    }
                });
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.newBuilder()
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .addAllAnfStatementConnector(List.of(AnfStatementConnector.newBuilder()
                                .setAnfStatement(ANFStatement.newBuilder()
                                        .setId("UUID")
                                        .setTime(Measure.newBuilder()
                                                .setUpperBound(1562631151)
                                                .setLowerBound(1562631151)
                                                .setIncludeLowerBound(true)
                                                .setIncludeUpperBound(true)
                                                .setResolution(1)
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Seconds | 1562631151"))
                                                .build())
                                        .setSubjectOfRecord(Participant.newBuilder()
                                                .setId("UUID (PatientId)")
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("UUID (EncounterID)"))
                                                .build())
                                        .addAllAuthors(List.of(Practitioner.newBuilder()
                                                .setPractitionerValue(Reference.newBuilder()
                                                        .setDisplay("display")
                                                        .setIdentifier("identifier")
                                                        .setReference("reference")
                                                        .setUri("uri")
                                                        .build())
                                                .setCode(LogicalExpression.newBuilder()
                                                        .setExpression("expression"))
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .build()))
                                        .setSubjectOfInformation(
                                                LogicalExpression.newBuilder().setExpression("UUID  (PatientId)"))
                                        .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                                                .setSemantic(LogicalExpression.newBuilder()
                                                        .setExpression("Associated description"))
                                                .build()))
                                        .setTopic(
                                                LogicalExpression.newBuilder()
                                                        .setExpression(
                                                                "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "))
                                        .setType(LogicalExpression.newBuilder().setExpression("PERFORMANCE"))
                                        .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                                                .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                        .setExpression("purpose")
                                                        .build()))
                                                .setStatus(LogicalExpression.newBuilder()
                                                        .setExpression(
                                                                "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}")
                                                        .build())
                                                .setHealthRisk(
                                                        LogicalExpression.newBuilder()
                                                                .setExpression(
                                                                        "${{rules.engine.calculated[circumstanceChoice.result]}}"))
                                                .setResult(Measure.newBuilder()
                                                        .setLowerBound(90)
                                                        .setIncludeLowerBound(false)
                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                .setExpression(""))
                                                        .setResolution(1)
                                                        .setUpperBound(120)
                                                        .setIncludeUpperBound(false)
                                                        .build())
                                                .setNormalRange(Measure.getDefaultInstance())
                                                .setTiming(Measure.getDefaultInstance())
                                                .addAllParticipant(List.of(Participant.getDefaultInstance()))
                                                .build())
                                        .build())
                                .build()))
                        .build()))
                .build());
        Assertions.assertDoesNotThrow(
                () -> openCDXANFService.processQuestionnaires(questionnaires, this.openCDXIdentifier));
    }

    @Test
    void processQuestionnaires5() {
        OpenCDXANFServiceImpl openCDXANFService =
                new OpenCDXANFServiceImpl(openCDXAdrMessageService, openCDXProfileRepository);
        List<Questionnaire> questionnaires = List.of(Questionnaire.newBuilder()
                .addAllItem(List.of(QuestionnaireItem.newBuilder()
                        .addAllItem(List.of(QuestionnaireItem.newBuilder()
                                .addAllAnfStatementConnector(List.of(AnfStatementConnector.newBuilder()
                                        .setAnfStatement(ANFStatement.newBuilder()
                                                .setId("UUID")
                                                .setTime(Measure.newBuilder()
                                                        .setUpperBound(1562631151)
                                                        .setLowerBound(1562631151)
                                                        .setIncludeLowerBound(true)
                                                        .setIncludeUpperBound(true)
                                                        .setResolution(1)
                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                .setExpression("Seconds | 1562631151"))
                                                        .build())
                                                .setSubjectOfRecord(Participant.newBuilder()
                                                        .setId("UUID (PatientId)")
                                                        .setCode(LogicalExpression.newBuilder()
                                                                .setExpression("UUID (EncounterID)"))
                                                        .build())
                                                .addAllAuthors(List.of(Practitioner.newBuilder()
                                                        .setPractitionerValue(Reference.newBuilder()
                                                                .setDisplay("display")
                                                                .setIdentifier("identifier")
                                                                .setReference("reference")
                                                                .setUri("uri")
                                                                .build())
                                                        .setCode(LogicalExpression.newBuilder()
                                                                .setExpression("expression"))
                                                        .setId(OpenCDXIdentifier.get()
                                                                .toHexString())
                                                        .build()))
                                                .setSubjectOfInformation(LogicalExpression.newBuilder()
                                                        .setExpression("UUID  (PatientId)"))
                                                .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                                                        .setSemantic(LogicalExpression.newBuilder()
                                                                .setExpression("Associated description"))
                                                        .build()))
                                                .setTopic(
                                                        LogicalExpression.newBuilder()
                                                                .setExpression(
                                                                        "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "))
                                                .setType(LogicalExpression.newBuilder()
                                                        .setExpression("PERFORMANCE"))
                                                .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                                                        .addAllPurpose(List.of(LogicalExpression.newBuilder()
                                                                .setExpression("purpose")
                                                                .build()))
                                                        .setStatus(LogicalExpression.newBuilder()
                                                                .setExpression(
                                                                        "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}")
                                                                .build())
                                                        .setHealthRisk(
                                                                LogicalExpression.newBuilder()
                                                                        .setExpression(
                                                                                "${{rules.engine.calculated[circumstanceChoice.result]}}"))
                                                        .setResult(Measure.newBuilder()
                                                                .setLowerBound(90)
                                                                .setIncludeLowerBound(false)
                                                                .setSemantic(LogicalExpression.newBuilder()
                                                                        .setExpression(""))
                                                                .setResolution(1)
                                                                .setUpperBound(120)
                                                                .setIncludeUpperBound(false)
                                                                .build())
                                                        .setNormalRange(Measure.getDefaultInstance())
                                                        .setTiming(Measure.getDefaultInstance())
                                                        .addAllParticipant(List.of(Participant.getDefaultInstance()))
                                                        .build())
                                                .build())
                                        .build()))
                                .build()))
                        .build()))
                .build());
        Assertions.assertDoesNotThrow(
                () -> openCDXANFService.processQuestionnaires(questionnaires, this.openCDXIdentifier));
    }
}
