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
package cdx.opencdx.classification.service.impl;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.model.RuleResult;
import cdx.opencdx.classification.service.OpenCDXClassifyProcessorService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.TestCaseListRequest;
import cdx.opencdx.grpc.inventory.TestCaseListResponse;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationType;
import cdx.opencdx.grpc.neural.classification.TestKit;
import cdx.opencdx.grpc.questionnaire.GetRuleSetResponse;
import cdx.opencdx.grpc.questionnaire.QuestionnaireItem;
import io.micrometer.observation.annotation.Observed;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * Service implementation for processing Classification Requests. This class is demonstration of types of
 * strategies that could be applied.  This is not a complete implementation. This class should be implemented
 * by any party using the classification service.  Rules engine, AI/ML, or other strategies can be used to
 * process the classification request.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@SuppressWarnings({"java:S1068", "java:S125", "java:S1172"})
public class OpenCDXClassifyProcessorServiceImpl implements OpenCDXClassifyProcessorService {
    private final OpenCDXMediaUpDownClient openCDXMediaUpDownClient;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    private final OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    private final OpenCDXTestCaseClient openCDXTestCaseClient;

    private final Random random;

    /**
     * Constructor for OpenCDXClassifyProcessorServiceImpl
     * @param openCDXMediaUpDownClient service for media upload and download client
     * @param openCDXCurrentUser service for current user
     * @param openCDXQuestionnaireClient service for questionnaire client
     * @param openCDXTestCaseClient service for test case client
     */
    public OpenCDXClassifyProcessorServiceImpl(
            OpenCDXMediaUpDownClient openCDXMediaUpDownClient,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXQuestionnaireClient openCDXQuestionnaireClient,
            OpenCDXTestCaseClient openCDXTestCaseClient) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXQuestionnaireClient = openCDXQuestionnaireClient;
        this.openCDXTestCaseClient = openCDXTestCaseClient;
        this.random = new Random();
    }

    @Override
    @SuppressWarnings("java:S2119")
    public void classify(OpenCDXClassificationModel model) {
        log.trace("Executing classify operation.");
        Resource file = retrieveFile(model.getMedia());
        if (file != null) {
            log.trace("fileName: {}", file.getFilename());
        }
        Resource connectedFile = retrieveFile(model.getTestDetailsMedia());
        if (connectedFile != null) {
            log.trace("fileName: {}", connectedFile.getFilename());
        }
        ClassificationResponse.Builder builder = ClassificationResponse.newBuilder();
        builder.setMessage("Executed classify operation.");

        builder.setConfidence(this.random.nextFloat(100.0f));
        builder.setPositiveProbability(this.random.nextFloat(100.0f));
        builder.setAvailability(this.random.nextBoolean() ? "Not Available" : "Available");
        builder.setCost(this.random.nextFloat(1000.00f));
        builder.setPatientId(model.getUserAnswer().getPatientId());

        if (model.getConnectedTest() != null) {
            log.info(
                    "Connected Test: {}",
                    model.getConnectedTest().getBasicInfo().getId());
            runTestAnalsysis(model, builder);
        } else if (model.getUserQuestionnaireData() != null) {
            log.info("User Questionnaire: {}", model.getUserQuestionnaireData().getId());
            runRules(model, builder);
        } else {
            builder.setType(ClassificationType.UNSPECIFIED_CLASSIFICATION_TYPE);
        }

        runRules(model, builder);

        model.setClassificationResponse(builder.build());
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

    private void runRules(OpenCDXClassificationModel model, ClassificationResponse.Builder builder) {
        if (model.getUserQuestionnaireData() != null
                && model.getUserQuestionnaireData().getQuestionnaireDataCount() > 0
                && !model.getUserQuestionnaireData()
                        .getQuestionnaireData(0)
                        .getRuleId()
                        .isEmpty()
                && !model.getUserQuestionnaireData()
                        .getQuestionnaireData(0)
                        .getRuleId()
                        .isBlank()
                && model.getUserQuestionnaireData().getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            KnowledgeService knowledgeService = new KnowledgeService();
            try {
                Knowledge knowledge = knowledgeService.newKnowledge("JAVA-SOURCE", getRulesClass(model));
                RuleResult ruleResult = new RuleResult();
                knowledge.newStatelessSession().insertAndFire(getResponse(model), ruleResult);
                builder.setNotifyCdc(ruleResult.isNotifyCDC());
                builder.setFurtherActions(ruleResult.getFurtherActions());
                if (ruleResult.getType() != null) {
                    builder.setType(ruleResult.getType());
                }
                if (ruleResult.getTestKit() != null) {
                    builder.setTestKit(ruleResult.getTestKit());
                }
            } catch (IOException e) {
                throw new OpenCDXInternalServerError(
                        OpenCDXClassifyProcessorServiceImpl.log.getName(), 1, e.getMessage());
            }
        } else {
            log.error("No rules to process.");
        }
    }

    private InputStream getRulesClass(OpenCDXClassificationModel model) {
        OpenCDXCallCredentials openCDXCallCredentials =
                new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken());
        GetRuleSetResponse ruleSetResponse = openCDXQuestionnaireClient.getRuleSet(
                model.getUserQuestionnaireData().getQuestionnaireData(0).getRuleId(), openCDXCallCredentials);
        log.info("RuleSet: {}", ruleSetResponse.getRuleSet().getRuleId());
        return new ByteArrayInputStream(ruleSetResponse.getRuleSet().getRule().getBytes());
    }

    private Object getResponse(OpenCDXClassificationModel model) {
        if (model.getUserQuestionnaireData() != null
                && model.getUserQuestionnaireData().getQuestionnaireDataCount() > 0
                && model.getUserQuestionnaireData().getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            String questionId =
                    model.getUserQuestionnaireData().getQuestionnaireData(0).getRuleQuestionId(0);

            if (!questionId.isBlank() && !questionId.isEmpty()) {
                Optional<QuestionnaireItem> question =
                        model.getUserQuestionnaireData().getQuestionnaireData(0).getItemList().stream()
                                .filter(questionItem -> questionId.equals(questionItem.getLinkId()))
                                .findFirst();

                if (question.isPresent()) {
                    switch (question.get().getType()) {
                        case "integer":
                            return question.get().getAnswerInteger();
                        case "boolean":
                            return question.get().getAnswerBoolean();
                        default:
                            return question.get().getAnswerString();
                    }
                }
            }
        }

        return null;
    }

    /**
     * This is an example method for how to process Connected Test Analysis. Ideally this could be a Rules Engine,
     * a Neural Network, or some other form of AI/ML model to process the results of the test.,
     * @param model OpenCDXClassificationModel
     * @param builder ClassificationResponse.Builder
     */
    private void runTestAnalsysis(OpenCDXClassificationModel model, ClassificationResponse.Builder builder) {

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
    }
}
