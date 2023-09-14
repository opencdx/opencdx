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
import health.safe.api.opencdx.grpc.communication.*;
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
     * Constructor taking a PersonRepository
     * @param personRepository repository for interacting with the database.
     * @param openCDXAuditService Audit service for tracking FDA requirements
     */
    @Autowired
    public CommunicationServiceImpl(PersonRepository personRepository, OpenCDXAuditService openCDXAuditService) {
        this.personRepository = personRepository;
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public String sayHello(HelloRequest request) {
        Person person = Person.builder().name(request.getName()).build();
        this.personRepository.save(person);
        this.openCDXAuditService.piiCreated(UUID.randomUUID(), AgentType.HUMAN_USER, "purpose", UUID.randomUUID());
        return String.format("Hello %s!", request.getName().trim());
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) {
        return null;
    }

    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) {
        return null;
    }

    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) {
        return null;
    }

    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) {
        return null;
    }

    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) {
        return null;
    }

    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent) {
        return null;
    }

    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) {
        return null;
    }

    @Override
    public SuccessResponse sendNotification(Notification notification) {
        return null;
    }
}
