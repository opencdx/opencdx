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
package health.safe.api.opencdx.communications.service.impl;

import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.model.Person;
import health.safe.api.opencdx.communications.repository.PersonRepository;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.grpc.audit.AgentType;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final PersonRepository personRepository;
    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructor taking the a PersonRepository
     * @param personRepository repository for interacting with the database.
     * @param openCDXAuditService Audit service for tracking FDA requirements
     */
    @Autowired
    public CommunicationServiceImpl(PersonRepository personRepository, OpenCDXAuditService openCDXAuditService) {
        this.personRepository = personRepository;
        this.openCDXAuditService = openCDXAuditService;
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
        this.openCDXAuditService.piiCreated(UUID.randomUUID(), AgentType.HUMAN_USER, "purpose", UUID.randomUUID());
        return String.format("Hello %s!", request.getName().trim());
    }
}
