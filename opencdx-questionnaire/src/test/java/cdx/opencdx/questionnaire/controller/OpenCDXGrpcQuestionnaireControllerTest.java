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
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.model.OpenCDXQuestionnaireModel;
import cdx.opencdx.questionnaire.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.questionnaire.repository.OpenCDXQuestionnaireRepository;
import cdx.opencdx.questionnaire.repository.OpenCDXUserQuestionnaireRepository;
import cdx.opencdx.questionnaire.service.impl.OpenCDXQuestionnaireServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
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
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
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
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                                .setType(EmailType.EMAIL_TYPE_WORK)
                                                .setEmail("ab@safehealth.me")
                                                .build()))
                                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build()))
                                        .build())
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
                                .list(List.of(QuestionnaireData.getDefaultInstance()))
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
                                .list(List.of(QuestionnaireData.getDefaultInstance()))
                                .build());
                    }
                });
        Mockito.when(openCDXUserQuestionnaireRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);
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
    void getRuleSets_2() throws JsonProcessingException {

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);
        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<RuleSetsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientRulesRequest request = ClientRulesRequest.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getRuleSets(request, responseObserver));
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
                openCDXIAMUserRepository);

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
                openCDXIAMUserRepository);

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
                openCDXIAMUserRepository);

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
                openCDXIAMUserRepository);

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
                openCDXIAMUserRepository);

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
                openCDXIAMUserRepository);

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
    void createQuestionnaireDataInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder()
                .setQuestionnaireData(QuestionnaireData.newBuilder().setId("123"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createQuestionnaireData(request, responseObserver));
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
    void updateQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireDataRequest request = QuestionnaireDataRequest.newBuilder()
                .setQuestionnaireData(QuestionnaireData.newBuilder().setId("123"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.updateQuestionnaireData(request, responseObserver));
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
    void getQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SystemQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireRequest request =
                GetQuestionnaireRequest.newBuilder().setId("123").build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getQuestionnaireData(request, responseObserver));
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
    void getQuestionnaireDataListWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SystemQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().setId("123").build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getQuestionnaireDataList(request, responseObserver));
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

    @Test
    void deleteQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

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
                () -> this.openCDXGrpcQuestionnaireController.deleteQuestionnaireData(request, responseObserver));
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
    void createClientQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.newBuilder()
                .setClientQuestionnaireData(cdata)
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createClientQuestionnaireData(request, responseObserver));
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
    void updateClientQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClientQuestionnaireDataRequest request = ClientQuestionnaireDataRequest.newBuilder()
                .setClientQuestionnaireData(cdata)
                .build();

        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.createClientQuestionnaireData(request, responseObserver));
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
    void getClientQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<ClientQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);
        GetQuestionnaireRequest request = GetQuestionnaireRequest.newBuilder().build();
        ClientQuestionnaireData response = ClientQuestionnaireData.newBuilder()
                .addQuestionnaireData(0, data)
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getClientQuestionnaireData(request, responseObserver));
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
    void getClientQuestionnaireDataListWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<ClientQuestionnaireData> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request =
                GetQuestionnaireListRequest.newBuilder().build();
        ClientQuestionnaireData response = ClientQuestionnaireData.newBuilder()
                .addQuestionnaireData(0, data)
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getClientQuestionnaireDataList(
                        request, responseObserver));
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

    @Test
    void deleteClientQuestionnaireDataWithInvalidRequest() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.questionnaireService = new OpenCDXQuestionnaireServiceImpl(
                this.openCDXAuditService,
                mapper,
                this.openCDXCurrentUser,
                openCDXQuestionnaireRepository,
                openCDXUserQuestionnaireRepository,
                openCDXIAMUserRepository);

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
                () -> this.openCDXGrpcQuestionnaireController.deleteClientQuestionnaireData(request, responseObserver));
    }

    // User Level Questionnaire
    @Test
    void createUserQuestionnaireData() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(data)
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
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder()
                        .addQuestionnaireData(data)
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
                openCDXIAMUserRepository);

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
        StreamObserver<UserQUestionnaireDataResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();

        this.openCDXGrpcQuestionnaireController.getUserQuestionnaireDataList(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserQUestionnaireDataResponse.class));
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
                openCDXIAMUserRepository);

        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);

        StreamObserver<UserQUestionnaireDataResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetQuestionnaireListRequest request = GetQuestionnaireListRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(0).setPageSize(10).build())
                .setId(ObjectId.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXGrpcQuestionnaireController.getUserQuestionnaireDataList(request, responseObserver));
    }
}
