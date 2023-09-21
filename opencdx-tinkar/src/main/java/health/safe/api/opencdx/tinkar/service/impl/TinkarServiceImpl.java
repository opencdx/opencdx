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
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tinkar Service for providing API access to the gRPC and Rest controllers. Used
 * to centrallize execution of code.
 */
@Service
@Observed(name = "opencdx-tinkar")
public class TinkarServiceImpl implements TinkarService {

    private final PersonRepository personRepository;

    /**
     * Constructor for Tinkar service
     * @param personRepository Repository for accessing the person collection
     */
    @Autowired
    public TinkarServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Method to process TinkarRequest
     * @param request TinkarRequest to process.
     * @return String containing the message.
     */
    @Override
    public String sayTinkar(TinkarRequest request) {
        Person person = Person.builder().name(request.getName()).build();
        this.personRepository.save(person);

        return String.format("Hello %s!", request.getName().trim());
    }
}
