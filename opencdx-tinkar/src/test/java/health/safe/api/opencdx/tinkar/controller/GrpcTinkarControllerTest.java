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
package health.safe.api.opencdx.tinkar.controller;

import health.safe.api.opencdx.grpc.tinkar.TinkarReply;
import health.safe.api.opencdx.grpc.tinkar.TinkarRequest;
import health.safe.api.opencdx.tinkar.model.Person;
import health.safe.api.opencdx.tinkar.repository.PersonRepository;
import health.safe.api.opencdx.tinkar.service.impl.TinkarServiceImpl;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;

class GrpcTinkarControllerTest {

    @Mock
    PersonRepository personRepository;

    TinkarServiceImpl tinkarService;

    health.safe.api.opencdx.tinkar.controller.GrpcTinkarController grpcTinkarController;

    @BeforeEach
    void setUp() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).then(AdditionalAnswers.returnsFirstArg());
        this.tinkarService = new TinkarServiceImpl(this.personRepository);
        this.grpcTinkarController =
                new health.safe.api.opencdx.tinkar.controller.GrpcTinkarController(this.tinkarService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.personRepository);
    }

    @Test
    void sayTinker() {
        StreamObserver<TinkarReply> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarRequest tinkarRequest = TinkarRequest.newBuilder().setName("Bob").build();
        TinkarReply tinkarReply =
                TinkarReply.newBuilder().setMessage("Hello Bob!").build();

        this.grpcTinkarController.sayTinkar(tinkarRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(tinkarReply);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
