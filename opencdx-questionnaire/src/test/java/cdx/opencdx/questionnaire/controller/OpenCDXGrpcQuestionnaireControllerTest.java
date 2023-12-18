/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.model.Person;
import cdx.opencdx.questionnaire.repository.PersonRepository;
import cdx.opencdx.questionnaire.service.impl.OpenCDXQuestionnaireServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Mock
    PersonRepository personRepository;

    OpenCDXQuestionnaireServiceImpl questionnaireService;

    OpenCDXGrpcQuestionnaireController openCDXGrpcQuestionnaireController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).thenAnswer(new Answer<Person>() {
            @Override
            public Person answer(InvocationOnMock invocation) throws Throwable {
                Person argument = invocation.getArgument(0);
                if (argument.getId() == null) {
                    argument.setId(ObjectId.get());
                }
                return argument;
            }
        });
        this.questionnaireService =
                new OpenCDXQuestionnaireServiceImpl(this.openCDXAuditService, this.objectMapper, openCDXCurrentUser);
        this.openCDXGrpcQuestionnaireController = new OpenCDXGrpcQuestionnaireController(this.questionnaireService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.personRepository);
    }

    @Test
    void submitQuestionnaire() {
        StreamObserver<SubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);

        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(
                        Questionnaire.newBuilder().setResourceType("form").setTitle("Questionnaire"))
                .build();
        SubmissionResponse response = SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Executed")
                .build();

        this.openCDXGrpcQuestionnaireController.submitQuestionnaire(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SubmissionResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
