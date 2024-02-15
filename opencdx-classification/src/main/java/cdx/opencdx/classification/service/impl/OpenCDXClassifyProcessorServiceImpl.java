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
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
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
 * Service implementation for processing Classification Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@SuppressWarnings({"java:S1068", "java:S125", "java:S1172"})
public class OpenCDXClassifyProcessorServiceImpl implements OpenCDXClassifyProcessorService {
    private final OpenCDXMediaUpDownClient openCDXMediaUpDownClient;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for OpenCDXClassifyProcessorServiceImpl
     * @param openCDXMediaUpDownClient service for media upload and download client
     * @param openCDXCurrentUser service for current user
     */
    public OpenCDXClassifyProcessorServiceImpl(
            OpenCDXMediaUpDownClient openCDXMediaUpDownClient, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    @SuppressWarnings("java:S2119")
    public void classify(OpenCDXClassificationModel model) {
        Resource file = retrieveFile(model.getMedia());
        if (file != null) {
            log.info("fileName: {}", file.getFilename());
        }
        Resource connectedFile = retrieveFile(model.getTestDetailsMedia());
        if (connectedFile != null) {
            log.info("fileName: {}", connectedFile.getFilename());
        }
        ClassificationResponse.Builder builder = ClassificationResponse.newBuilder();
        builder.setMessage("Executed classify operation.");

        builder.setConfidence(new Random().nextFloat());
        builder.setPositiveProbability(new Random().nextFloat());
        builder.setAvailability(new Random().nextFloat() < 0.5 ? "Not Available" : "Available");
        builder.setCost(new Random().nextFloat(1000.00f));
        builder.setUserId(model.getUserAnswer().getUserId());

        if (model.getConnectedTest() != null) {
            builder.setFurtherActions(
                    new Random().nextFloat() < 0.5 ? "Follow up with your physician." : "Hospitalization is required.");
        }

        runRules(model, builder);

        model.setClassificationResponse(builder.build());
    }

    private Resource retrieveFile(Media model) {
        if (model != null) {

            try {
                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                MimeType type = allTypes.forName(model.getMimeType());
                String primaryExtension = type.getExtension().replace(".", "");
                log.info("Downloading media for classification: {} as {}", model.getId(), primaryExtension);
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
                && model.getUserQuestionnaireData().getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            KnowledgeService knowledgeService = new KnowledgeService();
            try {
                Knowledge knowledge = knowledgeService.newKnowledge("JAVA-SOURCE", getRulesClass(model));
                RuleResult ruleResult = new RuleResult();
                knowledge.newStatelessSession().insertAndFire(getResponse(model), ruleResult);
                builder.setFurtherActions(ruleResult.getResult());
            } catch (IOException e) {
                throw new OpenCDXInternalServerError(
                        OpenCDXClassifyProcessorServiceImpl.log.getName(), 1, e.getMessage());
            }
        }
    }

    private InputStream getRulesClass(OpenCDXClassificationModel model) {
        // TODO: Get rules string from DB
        String ruleSet =
                """
                package cdx.opencdx.classification.service;
                import cdx.opencdx.classification.model.RuleResult;
                import org.evrete.dsl.annotation.Fact;
                import org.evrete.dsl.annotation.Rule;
                import org.evrete.dsl.annotation.Where;

                public class BloodPressureRules {

                    @Rule
                    @Where("$s < 120")
                    public void normalBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
                        ruleResult.setResult("Normal blood pressure. No further actions needed.");
                    }
                    @Rule
                    @Where("$s >= 120 && $s <= 129")
                    public void elevatedBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
                        ruleResult.setResult("Elevated blood pressure. Please continue monitoring.");
                    }
                    @Rule
                    @Where("$s > 129")
                    public void highBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
                        ruleResult.setResult("High blood pressure. Please seek additional assistance.");
                    }
                }""";

        return new ByteArrayInputStream(ruleSet.getBytes());
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
}
