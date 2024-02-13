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

import static org.mockito.Mockito.when;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.repository.OpenCDXClassificationRepository;
import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.classification.service.OpenCDXClassifyProcessorService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXMediaClient;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Duration;
import cdx.opencdx.grpc.common.DurationType;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.connected.TestIdRequest;
import cdx.opencdx.grpc.media.GetMediaRequest;
import cdx.opencdx.grpc.media.GetMediaResponse;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.SeverityLevel;
import cdx.opencdx.grpc.neural.classification.Symptom;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.grpc.questionnaire.GetQuestionnaireRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXClassificationServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXClassificationService classificationService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXMediaClient openCDXMediaClient;

    @MockBean
    OpenCDXMediaUpDownClient openCDXMediaUpDownClient;

    @MockBean
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXConnectedTestClient openCDXConnectedTestClient;

    @Mock
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @Autowired
    OpenCDXClassifyProcessorService openCDXClassifyProcessorService;

    @Mock
    OpenCDXClassificationRepository openCDXClassificationRepository;

    @BeforeEach
    void beforeEach() {
        when(this.openCDXIAMUserRepository.findByUsername(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(ObjectId.get())
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });

        Mockito.when(this.openCDXClassificationRepository.save(Mockito.any(OpenCDXClassificationModel.class)))
                .thenAnswer(new Answer<OpenCDXClassificationModel>() {
                    @Override
                    public OpenCDXClassificationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXClassificationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMediaClient.getMedia(
                        Mockito.any(GetMediaRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<GetMediaResponse>() {
                    @Override
                    public GetMediaResponse answer(InvocationOnMock invocation) throws Throwable {
                        GetMediaRequest argument = invocation.getArgument(0);
                        return GetMediaResponse.newBuilder()
                                .setMedia(Media.newBuilder()
                                        .setId(argument.getId())
                                        .setMimeType("image/jpeg")
                                        .build())
                                .build();
                    }
                });

        ResponseEntity<Resource> resource = ResponseEntity.ok()
                .contentLength(2)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(new ByteArrayResource("{}".getBytes()));
        Mockito.when(this.openCDXMediaUpDownClient.download(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(resource);
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.classificationService = new OpenCDXClassificationServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                openCDXCurrentUser,
                openCDXDocumentValidator,
                openCDXMediaClient,
                this.openCDXConnectedTestClient,
                this.openCDXQuestionnaireClient,
                this.openCDXClassifyProcessorService,
                openCDXClassificationRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository);
        Mockito.reset(this.openCDXClassificationRepository);
        Mockito.reset(this.openCDXMediaClient);
    }

    @Test
    void testSubmitClassification() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .setConnectedTestId(ObjectId.get().toHexString())
                        .build())
                .build();
        ClassificationResponse response = this.classificationService.classify(request);

        Assertions.assertEquals(
                "Executed classify operation.", response.getMessage().toString());
    }

    @Test
    void testSubmitClassificationException() {
        Mockito.when(this.openCDXMediaClient.getMedia(
                        Mockito.any(GetMediaRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<GetMediaResponse>() {
                    @Override
                    public GetMediaResponse answer(InvocationOnMock invocation) throws Throwable {
                        GetMediaRequest argument = invocation.getArgument(0);
                        return GetMediaResponse.newBuilder()
                                .setMedia(Media.newBuilder()
                                        .setId(argument.getId())
                                        .setMimeType("imabc")
                                        .build())
                                .build();
                    }
                });
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .setConnectedTestId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXDataLoss.class, () -> this.classificationService.classify(request));
    }

    @Test
    void testSubmitClassificationNoQuestionnaireID() {
        Mockito.when(this.openCDXMediaClient.getMedia(
                        Mockito.any(GetMediaRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<GetMediaResponse>() {
                    @Override
                    public GetMediaResponse answer(InvocationOnMock invocation) throws Throwable {
                        GetMediaRequest argument = invocation.getArgument(0);
                        return GetMediaResponse.newBuilder()
                                .setMedia(Media.newBuilder()
                                        .setId(argument.getId())
                                        .setMimeType("imabc")
                                        .build())
                                .build();
                    }
                });
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        // .setConnectedTestId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXDataLoss.class, () -> this.classificationService.classify(request));
    }

    @Test
    void testSubmitClassificationRetrieveQuestionnaireNull() {
        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(null);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .setUserQuestionnaireId(ObjectId.get().toHexString())
                        .build())
                .build();

        ClassificationResponse response = this.classificationService.classify(request);

        Assertions.assertEquals(
                "Executed classify operation.", response.getMessage().toString());
    }

    @Test
    void testSubmitClassificationMediaNull() {
        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .setUserQuestionnaireId(ObjectId.get().toHexString())
                        .build())
                .build();

        ClassificationResponse response = this.classificationService.classify(request);

        Assertions.assertEquals(
                "Executed classify operation.", response.getMessage().toString());
    }

    @Test
    void testSubmitClassificationConnectedTestIdNull() {
        Mockito.when(this.openCDXMediaClient.getMedia(
                        Mockito.any(GetMediaRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<GetMediaResponse>() {
                    @Override
                    public GetMediaResponse answer(InvocationOnMock invocation) throws Throwable {
                        GetMediaRequest argument = invocation.getArgument(0);
                        return GetMediaResponse.newBuilder()
                                .setMedia(Media.newBuilder()
                                        .setId(argument.getId())
                                        .setMimeType("imabc")
                                        .build())
                                .build();
                    }
                });
        Mockito.when(this.openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<ConnectedTest>() {
                    @Override
                    public ConnectedTest answer(InvocationOnMock invocation) throws Throwable {
                        TestIdRequest argument = invocation.getArgument(0);
                        return ConnectedTest.newBuilder().getDefaultInstanceForType();
                    }
                });
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .setConnectedTestId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXDataLoss.class, () -> this.classificationService.classify(request));
    }

    @Test
    void testSubmitClassificationFail() throws JsonProcessingException {
        // Mock the ObjectMapper
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        // Mock the ObjectMapper's behavior to throw JsonProcessingException
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXClassificationModel.class)))
                .thenThrow(JsonProcessingException.class);

        // Create an instance of the OpenCDXClassificationServiceImpl with the mocked dependencies
        this.classificationService = new OpenCDXClassificationServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXDocumentValidator,
                openCDXMediaClient,
                this.openCDXConnectedTestClient,
                this.openCDXQuestionnaireClient,
                this.openCDXClassifyProcessorService,
                openCDXClassificationRepository);

        // Build a ClassificationRequest with invalid data (e.g., null symptom name)
        ClassificationRequest classificationRequest = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setConnectedTestId(ObjectId.get().toHexString())
                        .addSymptoms(
                                Symptom.newBuilder()
                                        .setName("John Smith") // Simulating an invalid case with null symptom name
                                        .setSeverity(SeverityLevel.LOW) // Set severity level for the symptom
                                        .setOnsetDate(Timestamp.newBuilder()
                                                .setSeconds(1641196800)
                                                .setNanos(0)) // Set onset date to a specific timestamp
                                        .setDuration(Duration.newBuilder()
                                                .setDuration(5)
                                                .setType(DurationType.DURATION_TYPE_HOURS)
                                                .build()) // Set duration of the symptom
                                        .setAdditionalDetails(
                                                "Additional details about the symptom") // Set additional details
                                )
                        .build())
                .build();

        // Verify that submitting the classification with the ObjectMapper throwing JsonProcessingException results in
        // OpenCDXNotAcceptable exception
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> classificationService.classify(classificationRequest));
    }

    @Test
    void testSubmitClassificationBloodPressure() throws JsonProcessingException {
        String ruleQuestionId = ObjectId.get().toHexString();
        int bloodPressure = 120;

        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(Questionnaire.newBuilder()
                                .addRuleQuestionId(ruleQuestionId)
                                .addItem(QuestionnaireItem.newBuilder()
                                        .setLinkId(ruleQuestionId)
                                        .setType("integer")
                                        .setAnswerInteger(bloodPressure))
                                .build())
                        .build());

        ClassificationRequest classificationRequest = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setUserQuestionnaireId(ObjectId.get().toHexString())
                        .build())
                .build();

        // Verify that the rules executed and set the correct further actions
        Assertions.assertEquals(
                "Elevated blood pressure. Please continue monitoring.",
                classificationService.classify(classificationRequest).getFurtherActions());
    }
}
