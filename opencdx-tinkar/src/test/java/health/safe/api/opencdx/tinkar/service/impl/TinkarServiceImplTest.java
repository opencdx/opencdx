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
package health.safe.api.opencdx.tinkar.service.impl;

import health.safe.api.opencdx.grpc.tinkar.TinkarRequest;
import health.safe.api.opencdx.tinkar.model.Person;
import health.safe.api.opencdx.tinkar.repository.PersonRepository;
import health.safe.api.opencdx.tinkar.service.TinkarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class TinkarServiceImplTest {

    @Mock
    PersonRepository personRepository;

    TinkarService tinkarService;

    @BeforeEach
    void beforeEach() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        this.tinkarService = new TinkarServiceImpl(this.personRepository);
    }

    @Test
    void testSayTinkar() {
        Person person = new Person();
        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).thenAnswer(i -> i.getArguments()[0]);
        Assertions.assertEquals(
                "Hello Bob!",
                this.tinkarService.sayTinkar(
                        TinkarRequest.newBuilder().setName(" Bob ").build()));
    }
}
