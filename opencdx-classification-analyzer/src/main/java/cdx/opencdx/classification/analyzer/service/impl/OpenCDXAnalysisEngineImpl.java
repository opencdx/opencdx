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

import cdx.opencdx.classification.analyzer.dto.RuleResult;
import cdx.opencdx.classification.analyzer.rules.BloodPressureRules;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.service.OpenCDXAnalysisEngine;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.classification.RuleSetsResponse;
import cdx.opencdx.grpc.service.logistics.TestCaseListRequest;
import cdx.opencdx.grpc.service.logistics.TestCaseListResponse;
import cdx.opencdx.grpc.types.ClassificationType;
import io.micrometer.observation.annotation.Observed;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Demonstration implementation of the OpenCDXAnalysisEngine class. This class is responsible for analyzing the patient's questionnaire and connected test data,
 * and returning the classification response. This class can be backed by rules engines, machine learning models, or any other classification engine. This is
 * provided to demonstrate the interface and the expected behavior.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineImpl implements OpenCDXAnalysisEngine {

    private static final String FILE_NAME = "fileName: {}";
    private final OpenCDXMediaUpDownClient openCDXMediaUpDownClient;
    private final OpenCDXTestCaseClient openCDXTestCaseClient;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final KnowledgeService knowledgeService;
    /**
     * Random number generator for demonstration purposes to generate random classification responses.
     */
    private final Random random;

    private static final String RULE_BLOOD_PRESSURE = "8a75ec67-880b-41cd-a526-a12aa9aef2c1";

    /**
     * Constructor to initialize the OpenCDXAnalysisEngineImpl with the required clients.
     *
     * @param openCDXMediaUpDownClient the OpenCDXMediaUpDownClient object to download media
     * @param openCDXTestCaseClient the OpenCDXTestCaseClient object to get test cases
     * @param openCDXCurrentUser the OpenCDXCurrentUser object to get the current user
     */
    public OpenCDXAnalysisEngineImpl(
            OpenCDXMediaUpDownClient openCDXMediaUpDownClient,
            OpenCDXTestCaseClient openCDXTestCaseClient,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
        this.openCDXTestCaseClient = openCDXTestCaseClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.random = new Random();
        this.knowledgeService = new KnowledgeService();
    }

    /**
     * Gets the rule sets for the given client rules request.
     *
     * @param request the ClientRulesRequest object containing the request data
     * @return the RuleSetsResponse object representing the rule sets response
     */
    @Override
    public RuleSetsResponse getRuleSets(RuleSetsRequest request) {
        return RuleSetsResponse.newBuilder()
                .addAllRuleSets(List.of(RuleSet.newBuilder()
                        .setRuleId(RULE_BLOOD_PRESSURE)
                        .setCategory("Vitals Checks")
                        .setType("Questionnaire")
                        .setDescription("Checks the results of blood pressure questionnaire.")
                        .setName("Blood Pressure")
                        .build()))
                .build();
    }

    @Override
    public ClassificationResponse analyzeQuestionnaire(
            OpenCDXProfileModel patient,
            UserAnswer userAnswer,
            Media media,
            UserQuestionnaireData userQuestionnaireData) {
        log.info("Analyzing User Questionnaire: {}", userQuestionnaireData.getId());
        Resource file = retrieveFile(media);
        if (file != null) {
            log.trace(FILE_NAME, file.getFilename());
        }

        Classification.Builder builder = Classification.newBuilder();

        builder.setConfidence(this.random.nextFloat(100.0f));
        builder.setPositiveProbability(this.random.nextFloat(90.0f));
        builder.setAvailability(this.random.nextBoolean() ? "Not Available" : "Available");
        builder.setCost(this.random.nextFloat(500.00f));
        builder.setPatientId(patient.getId().toHexString());

        runRules(userQuestionnaireData, builder);

        return ClassificationResponse.newBuilder()
                .setClassification(builder.build())
                .build();
    }

    @Override
    public ClassificationResponse analyzeConnectedTest(
            OpenCDXProfileModel patient,
            UserAnswer userAnswer,
            Media media,
            ConnectedTest connectedTest,
            Media testDetailsMedia) {
        log.info("Analyzing Connected Test: {}", connectedTest.getBasicInfo().getId());

        Resource file = retrieveFile(media);
        if (file != null) {
            log.trace(FILE_NAME, file.getFilename());
        }
        Resource connectedFile = retrieveFile(testDetailsMedia);
        if (connectedFile != null) {
            log.trace(FILE_NAME, connectedFile.getFilename());
        }
        Classification.Builder builder = Classification.newBuilder();
        builder.setMessage("Executed classify operation.");

        builder.setConfidence(this.random.nextFloat(100.0f));
        builder.setPositiveProbability(this.random.nextFloat(100.0f));
        builder.setAvailability(this.random.nextBoolean() ? "Not Available" : "Available");
        builder.setCost(this.random.nextFloat(1000.00f));
        builder.setPatientId(patient.getId().toHexString());

        builder.setFurtherActions(
                random.nextBoolean() ? "Follow up with your physician." : "Hospitalization is required.");

        switch (random.nextInt(3)) {
            case 1:
                builder.setType(ClassificationType.BACTERIAL);
                break;
            case 2:
                builder.setType(ClassificationType.VIRAL);
                break;
            default:
                builder.setType(ClassificationType.INJURY);
                break;
        }

        builder.setNotifyCdc(random.nextBoolean());

        if (random.nextBoolean()) {

            TestCaseListResponse testCaseListResponse = this.openCDXTestCaseClient.listTestCase(
                    TestCaseListRequest.newBuilder()
                            .setPagination(Pagination.newBuilder()
                                    .setPageNumber(0)
                                    .setPageSize(1)
                                    .build())
                            .build(),
                    new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken()));

            if (!testCaseListResponse.getTestCasesList().isEmpty()) {
                builder.setTestKit(TestKit.newBuilder()
                        .setTestCaseId(testCaseListResponse.getTestCases(0).getId())
                        .build());
            }
        }

        return ClassificationResponse.newBuilder()
                .setClassification(builder.build())
                .build();
    }

    private Resource retrieveFile(Media model) {
        if (model != null) {
            log.trace("Retrieving file for classification.");

            try {
                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                MimeType type = allTypes.forName(model.getMimeType());
                String primaryExtension = type.getExtension().replace(".", "");
                log.trace("Downloading media for classification: {} as {}", model.getId(), primaryExtension);
                ResponseEntity<Resource> downloaded = this.openCDXMediaUpDownClient.download(
                        "Bearer " + this.openCDXCurrentUser.getCurrentUserAccessToken(),
                        model.getId(),
                        primaryExtension);
                return downloaded.getBody();
            } catch (OpenCDXInternal e) {
                log.error("Failed to download media for classification: {}", model.getId(), e);
                throw e;
            } catch (MimeTypeException e) {
                throw new OpenCDXDataLoss(
                        "OpenCDXClassifyProcessorServiceImpl",
                        1,
                        "Failed to identify extension: " + model.getMimeType(),
                        e);
            }
        }

        return null;
    }

    private void runRules(UserQuestionnaireData userQuestionnaireData, Classification.Builder builder) {
        if (userQuestionnaireData.getQuestionnaireDataCount() > 0
                && !userQuestionnaireData.getQuestionnaireData(0).getRuleId().isEmpty()
                && !userQuestionnaireData.getQuestionnaireData(0).getRuleId().isBlank()
                && userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {

            Knowledge knowledge = null;

            try {
                if (userQuestionnaireData.getQuestionnaireData(0).getRuleId().equals(RULE_BLOOD_PRESSURE)) {
                    knowledge = knowledgeService.newKnowledge("JAVA-CLASS", BloodPressureRules.class);
                }
            } catch (IOException e) {
                log.error("Unable to create rules knowledge");
            }

            if (knowledge != null) {
                RuleResult ruleResult = new RuleResult();
                knowledge.newStatelessSession().insertAndFire(getResponse(userQuestionnaireData), ruleResult);

                builder.setNotifyCdc(ruleResult.isNotifyCDC());
                builder.setFurtherActions(ruleResult.getFurtherActions());
                if (ruleResult.getType() != null) {
                    builder.setType(ruleResult.getType());
                }
                if (ruleResult.getTestKit() != null) {
                    builder.setTestKit(ruleResult.getTestKit());
                }
            } else {
                log.info("No rules to process.");
            }
        }
    }

    private Object getResponse(UserQuestionnaireData userQuestionnaireData) {
        if (userQuestionnaireData.getQuestionnaireDataCount() > 0
                && userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            String questionId = userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionId(0);

            if (!questionId.isBlank() && !questionId.isEmpty()) {
                Optional<QuestionnaireItem> question =
                        userQuestionnaireData.getQuestionnaireData(0).getItemList().stream()
                                .filter(questionItem -> questionId.equals(questionItem.getLinkId()))
                                .findFirst();

                if (question.isPresent()) {
                    return switch (question.get().getType()) {
                        case "integer" -> question.get().getAnswer(0).getValueInteger();
                        case "boolean" -> question.get().getAnswer(0).getValueBoolean();
                        default -> question.get().getAnswer(0).getValueString();
                    };
                }
            }
        }

        return null;
    }
}
