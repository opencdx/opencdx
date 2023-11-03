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
package cdx.opencdx.helloworld.service.impl;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.helloworld.*;
import cdx.opencdx.helloworld.model.Person;
import cdx.opencdx.helloworld.repository.PersonRepository;
import cdx.opencdx.helloworld.service.HelloWorldService;
import io.micrometer.observation.annotation.Observed;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class HelloWorldServiceImpl implements HelloWorldService {

    private final PersonRepository personRepository;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param personRepository    repository for interacting with the database.
     * @param openCDXAuditService Audit service for tracking FDA requirements
     * @param openCDXCurrentUser
     */
    @Autowired
    public HelloWorldServiceImpl(
            PersonRepository personRepository,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.personRepository = personRepository;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    /**
     * Process the HelloRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public String sayHello(HelloRequest request) {
        Person person = Person.builder().name(request.getName()).build();
        this.personRepository.save(person);
        this.openCDXAuditService.piiCreated(
                this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                this.openCDXCurrentUser.getCurrentUserType(),
                "purpose",
                SensitivityLevel.SENSITIVITY_LEVEL_MEDIUM,
                UUID.randomUUID().toString(),
                "COMMUNICATION: 123",
                "{\"name\":\"John\", \"age\":30, \"car\":null}");
        return String.format("Hello %s!", request.getName().trim());
    }
}
