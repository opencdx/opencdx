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
package cdx.opencdx.helloworld.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.service.helloworld.HelloRequest;
import cdx.opencdx.grpc.service.helloworld.HelloResponse;
import cdx.opencdx.helloworld.model.Person;
import cdx.opencdx.helloworld.repository.PersonRepository;
import cdx.opencdx.helloworld.service.impl.OpenCDXHelloWorldServiceImpl;
import io.grpc.stub.StreamObserver;
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
class OpenCDXGrpcHelloWorldControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    PersonRepository personRepository;

    OpenCDXHelloWorldServiceImpl helloWorldService;

    OpenCDXGrpcHelloWorldController openCDXGrpcHelloWorldController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).thenAnswer(new Answer<Person>() {
            @Override
            public Person answer(InvocationOnMock invocation) throws Throwable {
                Person argument = invocation.getArgument(0);
                if (argument.getId() == null) {
                    argument.setId(OpenCDXIdentifier.get());
                }
                return argument;
            }
        });
        this.helloWorldService =
                new OpenCDXHelloWorldServiceImpl(this.personRepository, this.openCDXAuditService, openCDXCurrentUser);
        this.openCDXGrpcHelloWorldController = new OpenCDXGrpcHelloWorldController(this.helloWorldService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.personRepository);
    }

    @Test
    void sayHello() {
        StreamObserver<HelloResponse> responseObserver = Mockito.mock(StreamObserver.class);
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("Bob").build();
        HelloResponse helloReply =
                HelloResponse.newBuilder().setMessage("Hello Bob!").build();

        this.openCDXGrpcHelloWorldController.sayHello(helloRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(helloReply);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
