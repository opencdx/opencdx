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
package cdx.opencdx.anf.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.anf.model.OpenCDXANFStatementModel;
import cdx.opencdx.anf.repository.OpenCDXANFStatementRepository;
import cdx.opencdx.anf.service.OpenCDXANFService;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.anf.AnfStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXANFServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXANFService openCDXANFService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXANFStatementRepository openCDXANFStatementRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
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
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                objectMapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
    }

    @Test
    void createANFStatementJson() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXANFService.createANFStatement(anfStatement));
    }

    @Test
    void createANFStatementJson2() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .addAllAuthors(List.of(AnfStatement.Practitioner.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build()))
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXANFService.createANFStatement(anfStatement));
    }

    @Test
    void getANFStatement() throws JsonProcessingException {

        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXANFService.getANFStatement(identifier));
    }

    @Test
    void getANFStatement_2() throws JsonProcessingException {

        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXANFStatementModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .subjectOfRecord(AnfStatement.Participant.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build()));
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXANFService.getANFStatement(identifier));
    }

    @Test
    void updateANFStatement() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXANFService.updateANFStatement(anfStatement));
    }

    @Test
    void updateANFStatement2() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .addAllAuthors(List.of(AnfStatement.Practitioner.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build()))
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> openCDXANFService.updateANFStatement(anfStatement));
    }

    @Test
    void deleteANFStatement() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXANFService.deleteANFStatement(identifier));
    }

    @Test
    void deleteANFStatement_2() throws JsonProcessingException {
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXANFStatementModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .subjectOfRecord(AnfStatement.Participant.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build()));
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenThrow(JsonProcessingException.class);
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                mapper,
                openCDXDocumentValidator,
                openCDXProfileRepository);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXANFService.deleteANFStatement(identifier));
    }
}
