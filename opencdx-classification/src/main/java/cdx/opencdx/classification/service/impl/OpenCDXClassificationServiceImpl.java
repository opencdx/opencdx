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

import cdx.opencdx.classification.config.OpenCDXClassificationEngineFactoryBean;
import cdx.opencdx.classification.service.OpenCDXCDCPayloadService;
import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXMediaClient;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.*;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.classification.ClassificationRequest;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.classification.RuleSetsResponse;
import cdx.opencdx.grpc.service.health.TestIdRequest;
import cdx.opencdx.grpc.service.media.GetMediaRequest;
import cdx.opencdx.grpc.service.media.GetMediaResponse;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.AddressPurpose;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.PhoneType;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Classification Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXClassificationServiceImpl implements OpenCDXClassificationService {

    private static final String OBJECT = "OBJECT";
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXMediaClient openCDXMediaClient;
    private final OpenCDXConnectedTestClient openCDXConnectedTestClient;
    private final OpenCDXQuestionnaireClient openCDXQuestionnaireClient;
    private final OpenCDXClassificationRepository openCDXClassificationRepository;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXOrderMessageService openCDXOrderMessageService;
    private final OpenCDXCommunicationService openCDXCommunicationService;
    private final OpenCDXANFService openCDXANFService;

    private final OpenCDXClassificationEngineFactoryBean openCDXClassificationEngineFactoryBean;
    private final OpenCDXCDCPayloadService openCDXCDCPayloadService;
    private final OpenCDXConnectedLabMessageService openCDXConnectedLabMessageService;

    /**
     * Constructor for OpenCDXClassificationServiceImpl
     * @param openCDXAuditService service for auditing
     * @param objectMapper object mapper for converting objects
     * @param openCDXCurrentUser service for getting current user
     * @param openCDXDocumentValidator service for validating documents
     * @param openCDXMediaClient service for media client
     * @param openCDXConnectedTestClient service for connected test client
     * @param openCDXQuestionnaireClient service for questionnaire client
     * @param openCDXClassificationRepository repository for classification
     * @param openCDXProfileRepository repository for profile
     * @param openCDXOrderMessageService service for order message
     * @param openCDXCommunicationService service for communication
     * @param openCDXCDCPayloadService service for CDC payload
     * @param openCDXConnectedLabMessageService service for connected lab message
     * @param openCDXANFService service for ANF
     * @param openCDXClassificationEngineFactoryBean factory bean for classification engine
     */
    @Autowired
    public OpenCDXClassificationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXMediaClient openCDXMediaClient,
            OpenCDXConnectedTestClient openCDXConnectedTestClient,
            OpenCDXQuestionnaireClient openCDXQuestionnaireClient,
            OpenCDXClassificationRepository openCDXClassificationRepository,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXOrderMessageService openCDXOrderMessageService,
            OpenCDXCommunicationService openCDXCommunicationService,
            OpenCDXANFService openCDXANFService,
            OpenCDXClassificationEngineFactoryBean openCDXClassificationEngineFactoryBean,
            OpenCDXCDCPayloadService openCDXCDCPayloadService,
            OpenCDXConnectedLabMessageService openCDXConnectedLabMessageService) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXMediaClient = openCDXMediaClient;
        this.openCDXConnectedTestClient = openCDXConnectedTestClient;
        this.openCDXQuestionnaireClient = openCDXQuestionnaireClient;
        this.openCDXClassificationRepository = openCDXClassificationRepository;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXOrderMessageService = openCDXOrderMessageService;
        this.openCDXCommunicationService = openCDXCommunicationService;
        this.openCDXClassificationEngineFactoryBean = openCDXClassificationEngineFactoryBean;
        this.openCDXANFService = openCDXANFService;
        this.openCDXCDCPayloadService = openCDXCDCPayloadService;
        this.openCDXConnectedLabMessageService = openCDXConnectedLabMessageService;
    }

    /**
     * Operation to get rulesets
     *
     * @param request the request to retrieve rules at the client level
     * @return Response containing a list of rulesets
     */
    @Override
    public RuleSetsResponse getRuleSets(RuleSetsRequest request) {
        try {
            OpenCDXAnalysisEngine defaultEngine = this.openCDXClassificationEngineFactoryBean.getEngine("default");
            return defaultEngine.getRuleSets(request);
        } catch (Exception e) {
            throw new OpenCDXServiceUnavailable(this.getClass().getName(), 1, "Failed to get rule sets", e);
        }
    }

    /**
     * Process the ClassificationRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @SuppressWarnings("java:S3776")
    @Override
    public ClassificationResponse classify(ClassificationRequest request) {
        log.trace("Processing ClassificationRequest");

        OpenCDXClassificationModel model = creeateOpenCDXClassificationModel(request);
        try {
            OpenCDXAnalysisEngine defaultEngine = this.openCDXClassificationEngineFactoryBean.getEngine("default");

            if (model.getConnectedTest() != null) {
                model.setClassificationResponse(
                        new OpenCDXClassificationResponseModel(defaultEngine.analyzeConnectedTest(
                                model.getPatient(),
                                model.getUserAnswer(),
                                model.getMedia(),
                                model.getConnectedTest(),
                                model.getTestDetailsMedia())));
            } else if (model.getUserQuestionnaireData() != null) {
                log.info("Checking for ANF Statements");
                if (model.getUserQuestionnaireData().getList() != null) {
                    log.info("Processing ANF Statements");
                    this.openCDXANFService.processQuestionnaires(
                            model.getUserQuestionnaireData().getList(),
                            model.getPatient().getId());
                }
                model.setClassificationResponse(
                        new OpenCDXClassificationResponseModel(defaultEngine.analyzeQuestionnaire(
                                model.getPatient(),
                                model.getUserAnswer(),
                                model.getMedia(),
                                model.getUserQuestionnaireData())));
            } else {
                throw new OpenCDXNotAcceptable(
                        this.getClass().getName(),
                        1,
                        "Failed to classify: No connected test or questionnaire data found");
            }

            model.setClassificationResponse(
                    this.openCDXClassificationRepository.save(model.getClassificationResponse()));

            this.processClassification(model);
        } catch (Exception e) {
            throw new OpenCDXServiceUnavailable(this.getClass().getName(), 1, "Failed to classify", e);
        }

        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {

            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Classification Record and Results",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatient().getId().toHexString())
                            .setNationalHealthId(model.getPatient().getNationalHealthId())
                            .build(),
                    "CLASSIFICATION: "
                            + model.getClassificationResponse().getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 1, "Failed to convert OpenCDXClassificationModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        log.info("Processed ClassificationRequest");
        return model.getClassificationResponse().getProtobuf();
    }

    private OpenCDXClassificationModel creeateOpenCDXClassificationModel(ClassificationRequest request) {
        log.trace("Validating ClassificationRequest");
        OpenCDXClassificationModel model = new OpenCDXClassificationModel();

        model.setPatient(this.openCDXProfileRepository
                .findById(new OpenCDXIdentifier(request.getUserAnswer().getPatientId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        this.getClass().getName(),
                        1,
                        "Failed to find patient: " + request.getUserAnswer().getPatientId())));

        if (request.hasUserAnswer()) {
            model.setUserAnswer(new OpenCDXUserAnswerModel(request.getUserAnswer()));
        }
        OpenCDXCallCredentials openCDXCallCredentials =
                new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken());

        log.trace("Validating User");
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getUserAnswer().getPatientId()));

        if (request.getUserAnswer().hasMediaId()) {
            log.trace("Validating Media");
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "media", new OpenCDXIdentifier(request.getUserAnswer().getMediaId()));

            GetMediaResponse response = this.openCDXMediaClient.getMedia(
                    GetMediaRequest.newBuilder()
                            .setId(request.getUserAnswer().getMediaId())
                            .build(),
                    openCDXCallCredentials);
            if (response.hasMedia()) {
                model.setMedia(new OpenCDXMediaModel(response.getMedia()));
            }
        }

        log.trace("Validated ClassificationRequest");

        if (request.getUserAnswer().hasConnectedTestId()) {
            log.trace("Validating ConnectedTest");
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "connected-test",
                    new OpenCDXIdentifier(request.getUserAnswer().getConnectedTestId()));

            retrieveConnectedTest(request, openCDXCallCredentials, model);
        }

        if (request.getUserAnswer().hasUserQuestionnaireId()) {
            log.trace("Validating UserQuestionnaire");
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "questionnaire-user",
                    new OpenCDXIdentifier(request.getUserAnswer().getUserQuestionnaireId()));
            retrieveQuestionnaire(request, openCDXCallCredentials, model);
        }
        log.trace("Validated ClassificationRequest");
        return model;
    }

    private void retrieveConnectedTest(
            ClassificationRequest request,
            OpenCDXCallCredentials openCDXCallCredentials,
            OpenCDXClassificationModel model) {
        log.trace("Retrieving ConnectedTest: {}", request.getUserAnswer().getConnectedTestId());
        ConnectedTest testDetailsById = this.openCDXConnectedTestClient.getTestDetailsById(
                TestIdRequest.newBuilder()
                        .setTestId(request.getUserAnswer().getConnectedTestId())
                        .build(),
                openCDXCallCredentials);

        if (testDetailsById != null) {
            model.setConnectedTest(new OpenCDXConnectedTestModel(testDetailsById));
            if (testDetailsById.hasTestDetails()
                    && StringUtils.isNotEmpty(testDetailsById.getTestDetails().getMediaId())) {
                GetMediaResponse response = this.openCDXMediaClient.getMedia(
                        GetMediaRequest.newBuilder()
                                .setId(testDetailsById.getTestDetails().getMediaId())
                                .build(),
                        openCDXCallCredentials);
                if (response.hasMedia()) {
                    log.info(
                            "Retrieved TestDetailsMedia: {}",
                            response.getMedia().getId());
                    model.setTestDetailsMedia(new OpenCDXMediaModel(response.getMedia()));
                }
            }
        }
    }

    private void retrieveQuestionnaire(
            ClassificationRequest request,
            OpenCDXCallCredentials openCDXCallCredentials,
            OpenCDXClassificationModel model) {
        log.trace(
                "Retrieving UserQuestionnaireData: {}", request.getUserAnswer().getUserQuestionnaireId());
        UserQuestionnaireData userQuestionnaireData = this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                GetQuestionnaireRequest.newBuilder()
                        .setId(request.getUserAnswer().getUserQuestionnaireId())
                        .build(),
                openCDXCallCredentials);

        if (userQuestionnaireData != null) {
            model.setUserQuestionnaireData(new OpenCDXUserQuestionnaireModel(userQuestionnaireData));
        }
    }

    private void processClassification(OpenCDXClassificationModel model) {

        sendTestResults(model);

        if (model.getClassificationResponse().getClassification().hasTestKit()) {
            orderTestCase(model);
        }

        if (model.getClassificationResponse().getClassification().getNotifyCdc()) {
            this.openCDXCDCPayloadService.sendCDCPayloadMessage(model);
        }
        if (model.getConnectedTest() != null) {
            submitConnectedLabMessage(model);
        }
    }

    private void submitConnectedLabMessage(OpenCDXClassificationModel model) {

        LabFindings.Builder builder = LabFindings.newBuilder();

        builder.setBasicInfo(model.getConnectedTest().getBasicInfo());
        builder.setProviderInfo(model.getConnectedTest().getProviderInfo());
        builder.setMetadata(model.getConnectedTest().getTestDetails().getMetadata());
        builder.setClassification(model.getClassificationResponse().getClassification());

        this.openCDXConnectedLabMessageService.submitLabFindings(builder.build());
    }

    private void orderTestCase(OpenCDXClassificationModel model) {
        log.info(
                "Ordering Test Case: {}",
                model.getClassificationResponse()
                        .getClassification()
                        .getTestKit()
                        .getTestCaseId());

        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "testcases",
                new OpenCDXIdentifier(model.getClassificationResponse()
                        .getClassification()
                        .getTestKit()
                        .getTestCaseId()));
        Address shippingAddress = null;

        Optional<Address> addressOptional = model.getPatient().getAddresses().stream()
                .filter(address -> address.getAddressPurposeValue() == AddressPurpose.SHIPPING_VALUE)
                .findFirst();
        if (addressOptional.isPresent()) {
            shippingAddress = addressOptional.get();
        }

        if (shippingAddress == null) {
            addressOptional = model.getPatient().getAddresses().stream()
                    .filter(address -> address.getAddressPurposeValue() == AddressPurpose.PRIMARY_VALUE)
                    .findFirst();
            if (addressOptional.isPresent()) {
                shippingAddress = addressOptional.get();
            }
        }

        if (shippingAddress == null) {
            addressOptional = model.getPatient().getAddresses().stream().findFirst();
            if (addressOptional.isPresent()) {
                shippingAddress = addressOptional.get();
            }
        }
        if (shippingAddress != null) {
            Order.Builder builder = Order.newBuilder();
            builder.setShippingName(model.getPatient().getFullName());
            builder.setPatientId(model.getPatient().getId().toHexString());
            builder.setShippingAddress(shippingAddress);
            builder.setTestCaseId(model.getClassificationResponse()
                    .getClassification()
                    .getTestKit()
                    .getTestCaseId());

            this.openCDXOrderMessageService.submitOrder(builder.build());
        }
    }

    private void sendTestResults(OpenCDXClassificationModel model) {
        log.info("Send test Results with response {}", model.getClassificationResponse());
        String testName = null;
        ClassificationResponse classificationResponse =
                model.getClassificationResponse().getProtobuf();
        if (model.getConnectedTest() != null) {
            ConnectedTest connectedTest = model.getConnectedTest().getProtobufMessage();
            if (null != connectedTest && connectedTest.hasTestDetails()) {
                TestDetails testDetails = connectedTest.getTestDetails();
                testName = testDetails.getTestName();
            }
        }

        if (model.getUserQuestionnaireData() != null) {
            UserQuestionnaireData userQuestionnaireData =
                    model.getUserQuestionnaireData().getProtobufMessage();
            if (null != userQuestionnaireData
                    && !userQuestionnaireData.getQuestionnaireDataList().isEmpty()) {
                testName = userQuestionnaireData
                        .getQuestionnaireDataList()
                        .getFirst()
                        .getTitle();
            }
        }

        if (testName == null) {
            testName = "Latest Test";
        }
        OpenCDXProfileModel patient = model.getPatient();

        Map<String, String> map = new HashMap<>();
        map.put("firstName", patient.getFullName().getFirstName());
        map.put("lastName", patient.getFullName().getLastName());
        map.put("testName", testName);
        map.put("message", classificationResponse.getClassification().getFurtherActions());

        Notification.Builder builder = Notification.newBuilder()
                .setEventId(OpenCDXCommunicationService.TEST_RESULT)
                .putAllVariables(map);
        builder.setPatientId(patient.getId().toHexString());

        EmailAddress emailAddress = null;
        if (!patient.getPrimaryContactInfo().getEmailsList().isEmpty()) {
            emailAddress = patient.getPrimaryContactInfo().getEmailsList().stream()
                    .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_PERSONAL))
                    .findFirst()
                    .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                            .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_WORK))
                            .findFirst()
                            .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                                    .findFirst()
                                    .orElse(null)));
        }
        if (emailAddress != null) {
            builder.addAllToEmail(List.of(emailAddress.getEmail()));
        }
        List<String> mobileList = Collections.emptyList();

        if (!patient.getPrimaryContactInfo().getPhoneNumbersList().isEmpty()) {
            mobileList = patient.getPrimaryContactInfo().getPhoneNumbersList().stream()
                    .filter(phoneNumber -> phoneNumber.getType().equals(PhoneType.PHONE_TYPE_MOBILE))
                    .map(PhoneNumber::getNumber)
                    .toList();
        }
        if (!mobileList.isEmpty()) {
            builder.addAllToPhoneNumber(mobileList);
        }
        this.openCDXCommunicationService.sendNotification(builder.build());
    }
}
