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
import cdx.opencdx.commons.service.OpenCDXANFService;
import cdx.opencdx.commons.service.OpenCDXAdrMessageService;
import cdx.opencdx.grpc.data.*;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This service is for processing ANF questionnaires.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXANFServiceImpl implements OpenCDXANFService {
    public static final String ASSOCIATED_WITH_363698007_NATIONAL_IDENTIFIER =
            "363704007 |Associated with| = 363698007 |National identifier|";
    public static final String OBSERVATION_FINDING_FINDING = "416541006 |Observation finding (finding)|";
    private final OpenCDXAdrMessageService openCDXAdrMessageService;
    private final OpenCDXProfileRepository openCDXProfileRepository;

    /**
     * Constructor.
     *
     * @param openCDXAdrMessageService The OpenCDX ADR message service.
     * @param openCDXProfileRepository The OpenCDX profile repository.
     */
    public OpenCDXANFServiceImpl(
            OpenCDXAdrMessageService openCDXAdrMessageService, OpenCDXProfileRepository openCDXProfileRepository) {
        this.openCDXAdrMessageService = openCDXAdrMessageService;
        this.openCDXProfileRepository = openCDXProfileRepository;
    }

    @Override
    public void processQuestionnaires(List<Questionnaire> questionnaires, OpenCDXIdentifier patienId) {
        log.info("Processing ANF Statements from Questionnaire");
        List<ANFStatement> anfStatements = new ArrayList<>();

        questionnaires.forEach(questionnaireData -> {
            if (questionnaireData.getItemList() != null) {
                questionnaireData.getItemList().forEach(item -> {
                    anfStatements.addAll(this.processQuestionnaireItem(item));

                    if (item.getItemList() != null) {
                        for (QuestionnaireItem questionnaireItem : item.getItemList()) {
                            anfStatements.addAll(this.processQuestionnaireItem(questionnaireItem));
                        }
                    }
                });
            }
        });
        if (!anfStatements.isEmpty()) {
            this.sendToAdr(anfStatements, patienId);
        }
    }

    @SuppressWarnings("java:S3776")
    private List<ANFStatement> processQuestionnaireItem(QuestionnaireItem item) {
        List<ANFStatement> anfStatements = new ArrayList<>();
        if (item.getAnfStatementConnectorList() != null) {
            item.getAnfStatementConnectorList().forEach(anfStatementConnector -> {
                if (anfStatementConnector.getAnfStatement() != null) {
                    anfStatements.add(anfStatementConnector.getAnfStatement());
                }
            });
        }
        return anfStatements;
    }

    @SuppressWarnings({"java:S3776", "java:S1181"})
    private void sendToAdr(List<ANFStatement> anfStatements, OpenCDXIdentifier patienId) {
        log.info("Sending {} ANF statements to ADR", anfStatements.size());
        Instant now = Instant.now();
        Optional<OpenCDXProfileModel> patient = this.openCDXProfileRepository.findById(patienId);

        if (patient.isPresent()) {
            anfStatements.stream()
                    .map(anfStatement -> ANFStatement.newBuilder(anfStatement)
                            .setSubjectOfRecord(Participant.newBuilder()
                                    .setId(patient.get().getNationalHealthId())
                                    .setCode(LogicalExpression.newBuilder()
                                            .setExpression(ASSOCIATED_WITH_363698007_NATIONAL_IDENTIFIER)
                                            .build())
                                    .build())
                            .setSubjectOfInformation(
                                    LogicalExpression.newBuilder()
                                            .setExpression(
                                                    patient.get().getNationalHealthId()
                                                            + " |Identifier| : 363704007 |Associated with| = 363698007 |National identifier|"))
                            .setTime(Measure.newBuilder()
                                    .setIncludeUpperBound(true)
                                    .setIncludeLowerBound(true)
                                    .setSemantic(
                                            LogicalExpression.newBuilder().setExpression(OBSERVATION_FINDING_FINDING))
                                    .setLowerBound(now.getEpochSecond())
                                    .setUpperBound(now.getEpochSecond())
                                    .build())
                            .build())
                    .map(anfStatement -> {
                        if (anfStatement.hasPerformanceCircumstance()) {
                            return ANFStatement.newBuilder(anfStatement)
                                    .setPerformanceCircumstance(PerformanceCircumstance.newBuilder(
                                                    anfStatement.getPerformanceCircumstance())
                                            .setParticipant(
                                                    0,
                                                    Participant.newBuilder()
                                                            .setId(patient.get().getNationalHealthId())
                                                            .setCode(LogicalExpression.newBuilder()
                                                                    .setExpression(
                                                                            ASSOCIATED_WITH_363698007_NATIONAL_IDENTIFIER)
                                                                    .build())
                                                            .build())
                                            .setTiming(Measure.newBuilder()
                                                    .setIncludeUpperBound(true)
                                                    .setIncludeLowerBound(true)
                                                    .setSemantic(LogicalExpression.newBuilder()
                                                            .setExpression(OBSERVATION_FINDING_FINDING))
                                                    .setLowerBound(now.getEpochSecond())
                                                    .setUpperBound(now.getEpochSecond())
                                                    .build())
                                            .build())
                                    .build();
                        } else {
                            return anfStatement;
                        }
                    })
                    .map(anfStatement -> {
                        if (anfStatement.hasRequestCircumstance()) {
                            return ANFStatement.newBuilder(anfStatement)
                                    .setRequestCircumstance(
                                            RequestCircumstance.newBuilder(anfStatement.getRequestCircumstance())
                                                    .setTiming(Measure.newBuilder()
                                                            .setIncludeUpperBound(true)
                                                            .setIncludeLowerBound(true)
                                                            .setSemantic(LogicalExpression.newBuilder()
                                                                    .setExpression(OBSERVATION_FINDING_FINDING))
                                                            .setLowerBound(now.getEpochSecond())
                                                            .setUpperBound(now.getEpochSecond())
                                                            .build())
                                                    .build())
                                    .build();
                        } else {
                            return anfStatement;
                        }
                    })
                    .map(anfStatement -> {
                        if (anfStatement.hasNarrativeCircumstance()) {
                            return ANFStatement.newBuilder(anfStatement)
                                    .setNarrativeCircumstance(
                                            NarrativeCircumstance.newBuilder(anfStatement.getNarrativeCircumstance())
                                                    .setTiming(Measure.newBuilder()
                                                            .setIncludeUpperBound(true)
                                                            .setIncludeLowerBound(true)
                                                            .setSemantic(LogicalExpression.newBuilder()
                                                                    .setExpression(OBSERVATION_FINDING_FINDING))
                                                            .setLowerBound(now.getEpochSecond())
                                                            .setUpperBound(now.getEpochSecond())
                                                            .build())
                                                    .build())
                                    .build();
                        } else {
                            return anfStatement;
                        }
                    })
                    .forEach(item -> {
                        try {
                            openCDXAdrMessageService.postANFStatement(item);
                        } catch (Throwable throwable) {
                            log.warn("Failed to send ANF statement to ADR", throwable);
                        }
                    });
            log.info("Sent {} ANF statements to ADR", anfStatements.size());
        } else {
            log.error("Patient with id {} not found", patienId);
        }
    }
}
