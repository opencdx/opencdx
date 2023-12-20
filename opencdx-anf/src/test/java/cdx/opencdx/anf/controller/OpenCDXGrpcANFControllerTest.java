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
package cdx.opencdx.anf.controller;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.anf.model.OpenCDXANFStatementModel;
import cdx.opencdx.anf.repository.OpenCDXANFStatementRepository;
import cdx.opencdx.anf.service.OpenCDXANFService;
import cdx.opencdx.anf.service.impl.OpenCDXANFServiceImpl;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.anf.AnfStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXGrpcANFControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXANFStatementRepository openCDXANFStatementRepository;

    OpenCDXGrpcANFController openCDXGrpcANFController;

    OpenCDXANFService openCDXANFService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXANFService = new OpenCDXANFServiceImpl(
                this.openCDXAuditService,
                openCDXCurrentUser,
                this.openCDXANFStatementRepository,
                objectMapper,
                openCDXDocumentValidator);
        this.openCDXGrpcANFController = new OpenCDXGrpcANFController(this.openCDXANFService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createANFStatement() {
        StreamObserver<AnfStatement.Identifier> responseObserver = Mockito.mock(StreamObserver.class);

        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .build();
        this.openCDXGrpcANFController.createANFStatement(anfStatement, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(anfStatement.getId());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getANFStatement() {
        StreamObserver<AnfStatement.ANFStatement> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXANFStatementModel openCDXANFStatementModel =
                OpenCDXANFStatementModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXANFStatementModel));
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(openCDXANFStatementModel.getId().toString())
                .build();
        this.openCDXGrpcANFController.getANFStatement(identifier, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXANFStatementModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateANFStatement() {
        StreamObserver<AnfStatement.Identifier> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .build();

        this.openCDXGrpcANFController.updateANFStatement(anfStatement, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(anfStatement.getId());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteANFStatement() {
        StreamObserver<AnfStatement.Identifier> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXANFStatementModel openCDXANFStatementModel =
                OpenCDXANFStatementModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXANFStatementRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXANFStatementModel));
        AnfStatement.Identifier identifier = AnfStatement.Identifier.newBuilder()
                .setId(openCDXANFStatementModel.getId().toString())
                .build();
        this.openCDXGrpcANFController.deleteANFStatement(identifier, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(openCDXANFStatementModel.getProtobufMessage().getId());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
