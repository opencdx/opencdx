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
package health.safe.api.opencdx.helloworld.service.impl;

import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import health.safe.api.opencdx.helloworld.model.Person;
import health.safe.api.opencdx.helloworld.repository.PersonRepository;
import health.safe.api.opencdx.helloworld.service.HelloWorldService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class HelloWorldServiceImplTest {

    @Mock
    PersonRepository personRepository;

    HelloWorldService helloWorldService;

    @BeforeEach
    void beforeEach() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        this.helloWorldService = new HelloWorldServiceImpl(this.personRepository);
    }

    @Test
    void testSayHello() {
        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).thenAnswer(i -> i.getArguments()[0]);
        Assertions.assertEquals(
                "Hello Bob!",
                this.helloWorldService.sayHello(
                        HelloRequest.newBuilder().setName(" Bob ").build()));
    }
}
