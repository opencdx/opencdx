package cdx.opencdx.classification.analyzer.service.impl;

import cdx.opencdx.classification.analyzer.dto.RuleResult;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.service.OpenCDXAnalysisEngine;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.inventory.TestCaseListRequest;
import cdx.opencdx.grpc.inventory.TestCaseListResponse;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationType;
import cdx.opencdx.grpc.neural.classification.TestKit;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.QuestionnaireItem;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


/**
 * Demonstration implementation of the OpenCDXAnalysisEngine class. This class is responsible for analyzing the patient's questionnaire and connected test data,
 * and returning the classification response. This class can be backed by rules engines, machine learning models, or any other classification engine. This is
 * provided to demonstrate the interface and the expected behavior.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineImpl implements OpenCDXAnalysisEngine {

    private final OpenCDXMediaUpDownClient openCDXMediaUpDownClient;
    private final OpenCDXTestCaseClient openCDXTestCaseClient;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    /**
     * Random number generator for demonstration purposes to generate random classification responses.
     */
    private final Random random;

    public OpenCDXAnalysisEngineImpl(OpenCDXMediaUpDownClient openCDXMediaUpDownClient, OpenCDXTestCaseClient openCDXTestCaseClient, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
        this.openCDXTestCaseClient = openCDXTestCaseClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.random = new Random();
    }

    @Override
    public ClassificationResponse analyzeQuestionnaire(OpenCDXProfileModel patient, UserAnswer userAnswer, Media media, UserQuestionnaireData userQuestionnaireData) {
        log.info("Analyzing User Questionnaire: {}", userQuestionnaireData.getId());
        Resource file = retrieveFile(media);
        if (file != null) {
            log.trace("fileName: {}", file.getFilename());
        }

        ClassificationResponse.Builder builder = ClassificationResponse.newBuilder();
        builder.setMessage("Executed classify operation.");

        builder.setConfidence(this.random.nextFloat(100.0f));
        builder.setPositiveProbability(this.random.nextFloat(90.0f));
        builder.setAvailability(this.random.nextBoolean() ? "Not Available" : "Available");
        builder.setCost(this.random.nextFloat(500.00f));
        builder.setPatientId(patient.getId().toHexString());

        return null;
    }


    @Override
    public ClassificationResponse analyzeConnectedTest(OpenCDXProfileModel patient, UserAnswer userAnswer, Media media, ConnectedTest connectedTest, Media testDetailsMedia) {
        log.info(
                "Analyzing Connected Test: {}",
                connectedTest.getBasicInfo().getId());

        Resource file = retrieveFile(media);
        if (file != null) {
            log.trace("fileName: {}", file.getFilename());
        }
        Resource connectedFile = retrieveFile(testDetailsMedia);
        if (connectedFile != null) {
            log.trace("fileName: {}", connectedFile.getFilename());
        }
        ClassificationResponse.Builder builder = ClassificationResponse.newBuilder();
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

        return builder.build();
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

    private void runRules(UserQuestionnaireData userQuestionnaireData, ClassificationResponse.Builder builder) {
        if (userQuestionnaireData != null
                && userQuestionnaireData.getQuestionnaireDataCount() > 0
                && !userQuestionnaireData
                .getQuestionnaireData(0)
                .getRuleId()
                .isEmpty()
                && !userQuestionnaireData
                .getQuestionnaireData(0)
                .getRuleId()
                .isBlank()
                && userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            KnowledgeService knowledgeService = new KnowledgeService();
            //Knowledge knowledge = knowledgeService.newKnowledge("JAVA-SOURCE", getRulesClass(model));
            Knowledge knowledge = null;
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
            log.error("No rules to process.");
        }
    }

    private Object getResponse(UserQuestionnaireData userQuestionnaireData) {
        if (userQuestionnaireData != null
                && userQuestionnaireData.getQuestionnaireDataCount() > 0
                && userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionIdCount() > 0) {
            String questionId =
                    userQuestionnaireData.getQuestionnaireData(0).getRuleQuestionId(0);

            if (!questionId.isBlank() && !questionId.isEmpty()) {
                Optional<QuestionnaireItem> question =
                        userQuestionnaireData.getQuestionnaireData(0).getItemList().stream()
                                .filter(questionItem -> questionId.equals(questionItem.getLinkId()))
                                .findFirst();

                if (question.isPresent()) {
                    switch (question.get().getType()) {
                        case "integer":
                            return question.get().getAnswer(0).getValueInteger();
                        case "boolean":
                            return question.get().getAnswer(0).getValueBoolean();
                        default:
                            return question.get().getAnswer(0).getValueString();
                    }
                }
            }
        }

        return null;
    }
}