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
package cdx.opencdx.helloworld.controller;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.helloworld.model.Person;
import cdx.opencdx.helloworld.repository.PersonRepository;
import cdx.opencdx.helloworld.service.impl.HelloWorldServiceImpl;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
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

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class GrpcHelloWorldControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    PersonRepository personRepository;

    HelloWorldServiceImpl helloWorldService;

    GrpcHelloWorldController grpcHelloWorldController;

    @BeforeEach
    void setUp() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).then(AdditionalAnswers.returnsFirstArg());
        this.helloWorldService = new HelloWorldServiceImpl(this.personRepository, this.openCDXAuditService);
        this.grpcHelloWorldController = new GrpcHelloWorldController(this.helloWorldService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.personRepository);
    }

    @Test
    void sayHello() {
        StreamObserver<HelloReply> responseObserver = Mockito.mock(StreamObserver.class);
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("Bob").build();
        HelloReply helloReply = HelloReply.newBuilder().setMessage("Hello Bob!").build();

        this.grpcHelloWorldController.sayHello(helloRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(helloReply);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}