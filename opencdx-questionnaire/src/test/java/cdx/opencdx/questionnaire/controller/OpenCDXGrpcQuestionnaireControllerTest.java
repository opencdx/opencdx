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
package cdx.opencdx.questionnaire.controller;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.commons.service.impl.OpenCDXClassificationMessageServiceImpl;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.model.OpenCDXQuestionnaireModel;
import cdx.opencdx.questionnaire.model.OpenCDXRuleSet;
import cdx.opencdx.questionnaire.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.questionnaire.repository.OpenCDXQuestionnaireRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXRuleSetRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXUserQuestionnaireRepository;
import cdx.opencdx.questionnaire.service.impl.OpenCDXQuestionnaireServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcQuestionnaireControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXQuestionnaireServiceImpl questionnaireService;

    OpenCDXGrpcQuestionnaireController openCDXGrpcQuestionnaireController;

    @Mock
    OpenCDXQuestionnaireRepository openCDXQuestionnaireRepository;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Mock
    OpenCDXUserQuestionnaireRepository openCDXUserQuestionnaireRepository;

    @Mock
    OpenCDXRuleSetRepository openCDXRuleSetRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Autowired
    OpenCDXMessageService openCDXMessageService;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    OpenCDXClassificationMessageService openCDXClassificationMessageService;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXProfileRepository.save(Mockito.any(OpenCDXProfileModel.class)))
                .thenAnswer(new Answer<OpenCDXProfileModel>() {
                    @Override
                    public OpenCDXProfileModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXProfileModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(openCDXQuestionnaireRepository.save(Mockito.any(OpenCDXQuestionnaireModel.class)))
                .thenAnswer(new Answer<OpenCDXQuestionnaireModel>() {
                    @Override
                    public OpenCDXQuestionnaireModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXQuestionnaireModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXQuestionnaireModel>>() {
                    @Override
                    public Optional<OpenCDXQuestionnaireModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXQuestionnaireModel.builder().id(argument).build());
                    }
                });
        Mockito.when(openCDXQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXUserQuestionnaireRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(ObjectId.get())
                                .userId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(openCDXUserQuestionnaireRepository.save(Mockito.any(OpenCDXUserQuestionnaireModel.class)))
                .thenAnswer(new Answer<OpenCDXUserQuestionnaireModel>() {
                    @Override
                    public OpenCDXUserQuestionnaireModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXUserQuestionnaireModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXUserQuestionnaireModel>>() {
                    @Override
                    public Optional<OpenCDXUserQuestionnaireModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXUserQuestionnaireModel.builder()
                                .id(argument)
                                .userId(ObjectId.get())
                                .list(List.of(Questionnaire.getDefaultInstance()))
                                .build());
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);

        this.openCDXClassificationMessageService = new OpenCDXClassificationMessageServiceImpl(
                openCDXMessageService, openCDXDocumentValidator, openCDXProfileRepository);

        Mockito.when(openCDXRuleSetRepository.save(Mockito.any(OpenCDXRuleSet.class)))
                .thenAnswer(new Answer<OpenCDXRuleSet>() {
                    @Override
                    public OpenCDXRuleSet answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXRuleSet argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXRuleSetRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXRuleSet>>() {
                    @Override
                    public Optional<OpenCDXRuleSet> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXRuleSet.builder()
                                .id(argument)
                                .category("category")
                                .type("type")
                                .description("description")
                                .rule("rule")
                                .status(QuestionnaireStatus.draft)
                                .build());
                    }
                });

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);
        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);
    }

    QuestionnaireData data =
            QuestionnaireData.newBuilder().setId("1").setState("Active").build();

    ClientQuestionnaireData cdata = ClientQuestionnaireData.newBuilder()
            .setOrganizationId("org-opencdx")
            .addQuestionnaireData(data)
            .build();

    SystemQuestionnaireData sdata =
            SystemQuestionnaireData.newBuilder().addQuestionnaireData(data).build();

    @Test
    void getRuleSets() {
        StreamObserver<RuleSetsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientRulesRequest request = ClientRulesRequest.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build();

        this.openCDXGrpcQuestionnaireController.getRuleSets(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(RuleSetsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void submitQuestionnaire() {
        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(Questionnaire.newBuilder()
                        .setRuleId(ObjectId.get().toHexString())
                        .setCreated(Timestamp.newBuilder().setSeconds(50000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(600000).build())
                        .setCreator(ObjectId.get().toHexString())
                        .setModifier(ObjectId.get().toHexString())
                        .setResourceType("form")
                        .addAllItem(List.of(QuestionnaireItem.getDefaultInstance()))
                        .setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.createQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaire.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateQuestionnaire() {
        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(Questionnaire.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setResourceType("form")
                        .setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.updateQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaire.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void submitQuestionnaireWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(
                        Questionnaire.newBuilder().setResourceType("form").setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createQuestionnaire(request, responseObserver));
    }

    @Test
    void updateQuestionnaireWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(Questionnaire.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setResourceType("form")
                        .setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.updateQuestionnaire(request, responseObserver));
    }

    @Test
    void updateQuestionnaireWithInvalidRequest2() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        this.openCDXQuestionnaireRepository = Mockito.mock(OpenCDXQuestionnaireRepository.class);

        Mockito.when(this.openCDXQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(false);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(Questionnaire.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setResourceType("form")
                        .setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXGrpcQuestionnaireController.updateQuestionnaire(request, responseObserver));
    }

    @Test
    void getSubmittedQuestionnaire() {
        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Questionnaire response = Questionnaire.newBuilder()
                .setDescription("response getSubmittedQuestionnaire")
                .build();

        this.openCDXGrpcQuestionnaireController.getSubmittedQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaire.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getSubmittedQuestionnaireWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXQuestionnaireRepository = Mockito.mock(OpenCDXQuestionnaireRepository.class);
        Mockito.when(this.openCDXQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<Questionnaire> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Questionnaire response = Questionnaire.newBuilder().setId("123").build();

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXGrpcQuestionnaireController.getSubmittedQuestionnaire(request, responseObserver));
    }

    @Test
    void getSubmittedQuestionnaireList() {
        StreamObserver<Questionnaires> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setId("123")
                .setPagination(Pagination.newBuilder()
                        .setPageSize(1)
                        .setPageNumber(2)
                        .setSortAscending(true)
                        .build())
                .build();
        Questionnaires response = Questionnaires.newBuilder()
                .addQuestionnaires(
                        Questionnaire.newBuilder().setResourceType("form").setTitle("Questionnaire"))
                .build();

        this.openCDXGrpcQuestionnaireController.getSubmittedQuestionnaireList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaires.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getSubmittedQuestionnaireListWithSort() {
        StreamObserver<Questionnaires> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setId("123")
                .setPagination(Pagination.newBuilder()
                        .setPageSize(1)
                        .setPageNumber(2)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();
        Questionnaires response = Questionnaires.newBuilder()
                .addQuestionnaires(
                        Questionnaire.newBuilder().setResourceType("form").setTitle("Questionnaire"))
                .build();

        this.openCDXGrpcQuestionnaireController.getSubmittedQuestionnaireList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaires.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getSubmittedQuestionnaireListWithSortDesc() {
        StreamObserver<Questionnaires> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setId("123")
                .setPagination(Pagination.newBuilder()
                        .setPageSize(1)
                        .setPageNumber(2)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();
        Questionnaires response = Questionnaires.newBuilder()
                .addQuestionnaires(
                        Questionnaire.newBuilder().setResourceType("form").setTitle("Questionnaire"))
                .build();

        this.openCDXGrpcQuestionnaireController.getSubmittedQuestionnaireList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(Questionnaires.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteSubmittedQuestionnaire() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.deleteSubmittedQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteSubmittedQuestionnaireWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().setId("123").build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.deleteSubmittedQuestionnaire(request, responseObserver));
    }

    @Test
    void deleteSubmittedQuestionnaireWithOutFinding() throws JsonProcessingException {

        this.openCDXQuestionnaireRepository = Mockito.mock(OpenCDXQuestionnaireRepository.class);
        Mockito.when(this.openCDXQuestionnaireRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteQuestionnaireRequest request = DeleteQuestionnaireRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.deleteSubmittedQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    // System Level Questionnaire
    @Test
    void createQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder()
                .setQuestionnaireData(QuestionnaireData.newBuilder().setId("123"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.createQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder()
                .setQuestionnaireData(QuestionnaireData.newBuilder().setId("123"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.updateQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getQuestionnaireData() {
        StreamObserver<SystemQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request =
                GetQuestionnaireRequest.newBuilder().setId("123").build();

        this.openCDXGrpcQuestionnaireController.getQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SystemQuestionnaireData.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getQuestionnaireDataList() {
        StreamObserver<SystemQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().setId("123").build();

        this.openCDXGrpcQuestionnaireController.getQuestionnaireDataList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SystemQuestionnaireData.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().setId("123").build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.deleteQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    // Client Level Questionnaire
    @Test
    void createClientQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.newBuilder()
                .setClientQuestionnaireData(cdata)
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.createClientQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateClientQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.newBuilder()
                .setClientQuestionnaireData(cdata)
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.updateClientQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getClientQuestionnaireData() {
        StreamObserver<ClientQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        ClientQuestionnaireData response = cdata;

        this.openCDXGrpcQuestionnaireController.getClientQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ClientQuestionnaireData.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getClientQuestionnaireDataList() {
        StreamObserver<ClientQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        ClientQuestionnaireData response = ClientQuestionnaireData.newBuilder()
                .addQuestionnaireData(0, data)
                .build();

        this.openCDXGrpcQuestionnaireController.getClientQuestionnaireDataList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ClientQuestionnaireData.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteClientQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteQuestionnaireRequest request =
                DeleteQuestionnaireRequest.newBuilder().setId("123").build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.deleteClientQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    // User Level Questionnaire
    @Test
    void createUserQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(Questionnaire.getDefaultInstance())
                        .setUserId(ObjectId.get().toHexString()))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.createUserQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createUserQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(Questionnaire.getDefaultInstance())
                        .setUserId(ObjectId.get().toHexString()))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createUserQuestionnaireData(request, responseObserver));
    }

    @Test
    void getUserQuestionnaireData() {
        StreamObserver<UserQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        UserQuestionnaireData response = UserQuestionnaireData.newBuilder().build();

        this.openCDXGrpcQuestionnaireController.getUserQuestionnaireData(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserQuestionnaireData.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getUserQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<UserQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();
        UserQuestionnaireData response = UserQuestionnaireData.newBuilder().build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getUserQuestionnaireData(request, responseObserver));
    }

    @Test
    void getUserQuestionnaireDataList() {
        StreamObserver<UserQuestionnaireDataResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();

        this.openCDXGrpcQuestionnaireController.getUserQuestionnaireDataList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserQuestionnaireDataResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getUserQuestionnaireDataListWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<UserQuestionnaireDataResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getUserQuestionnaireDataList(request, responseObserver));
    }

    @Test
    void createRuleSetTest() {
        StreamObserver<CreateRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        CreateRuleSetRequest request = CreateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setType("type")
                        .setCategory("category")
                        .setRule("rule")
                        .build())
                .build();
        this.openCDXGrpcQuestionnaireController.createRuleSet(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateRuleSetResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateRuleSetTest() {
        StreamObserver<UpdateRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateRuleSetRequest request = UpdateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setRuleId(ObjectId.get().toHexString())
                        .setType("type")
                        .setCategory("category")
                        .setRule("rule")
                        .build())
                .build();
        this.openCDXGrpcQuestionnaireController.updateRuleSet(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateRuleSetResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getRuleSetTest() {
        StreamObserver<GetRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetRuleSetRequest request = GetRuleSetRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcQuestionnaireController.getRuleSet(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetRuleSetResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteRuleSetTest() {
        StreamObserver<DeleteRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteRuleSetRequest request = DeleteRuleSetRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcQuestionnaireController.deleteRuleSet(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteRuleSetResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createRuleSetTestFail() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<CreateRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        CreateRuleSetRequest request = CreateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setType("type")
                        .setCategory("category")
                        .setRule("rule")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createRuleSet(request, responseObserver));
    }

    @Test
    void updateRuleSetTestFail() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<UpdateRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateRuleSetRequest request = UpdateRuleSetRequest.newBuilder()
                .setRuleSet(RuleSet.newBuilder()
                        .setRuleId(ObjectId.get().toHexString())
                        .setType("type")
                        .setCategory("category")
                        .setRule("rule")
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.updateRuleSet(request, responseObserver));
    }

    @Test
    void deleteRuleSetTestFail() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<DeleteRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteRuleSetRequest request = DeleteRuleSetRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.deleteRuleSet(request, responseObserver));
    }

    @Test
    void deleteRuleSetTestFail2() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXRuleSetRepository = Mockito.mock(OpenCDXRuleSetRepository.class);
        Mockito.when(this.openCDXRuleSetRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXRuleSet>>() {
                    @Override
                    public Optional<OpenCDXRuleSet> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.empty();
                    }
                });

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<DeleteRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteRuleSetRequest request = DeleteRuleSetRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXGrpcQuestionnaireController.deleteRuleSet(request, responseObserver));
    }

    @Test
    void getRuleSetTestFail2() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXRuleSetRepository = Mockito.mock(OpenCDXRuleSetRepository.class);
        Mockito.when(this.openCDXRuleSetRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXRuleSet>>() {
                    @Override
                    public Optional<OpenCDXRuleSet> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.empty();
                    }
                });

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXClassificationMessageService,
                this.openCDXRuleSetRepository,
                this.openCDXProfileRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<GetRuleSetResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetRuleSetRequest request = GetRuleSetRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXGrpcQuestionnaireController.getRuleSet(request, responseObserver));
    }
}
