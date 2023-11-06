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
package cdx.opencdx.communications.controller;

import cdx.opencdx.communications.service.OpenCDXCommunicationEmailService;
import cdx.opencdx.communications.service.OpenCDXCommunicationSmsService;
import cdx.opencdx.communications.service.OpenCDXNotificationService;
import cdx.opencdx.grpc.communication.*;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * gRPC Controller for Communications service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class GrpcCommunicationsController extends CommunicationServiceGrpc.CommunicationServiceImplBase {

    private final OpenCDXNotificationService openCDXNotificationService;

    private final OpenCDXCommunicationEmailService openCDXCommunicationEmailService;
    private final OpenCDXCommunicationSmsService openCDXCommunicationSmsService;

    /**
     * Constructor using the gRPC Communications Controller.
     *
     * @param openCDXNotificationService       service to process notifications
     * @param openCDXCommunicationEmailService service to process email request
     * @param openCDXCommunicationSmsService   service to process SMS request
     */
    @Autowired
    public GrpcCommunicationsController(
            OpenCDXNotificationService openCDXNotificationService,
            OpenCDXCommunicationEmailService openCDXCommunicationEmailService,
            OpenCDXCommunicationSmsService openCDXCommunicationSmsService) {
        this.openCDXNotificationService = openCDXNotificationService;
        this.openCDXCommunicationEmailService = openCDXCommunicationEmailService;
        this.openCDXCommunicationSmsService = openCDXCommunicationSmsService;
    }

    @Secured({})
    @Override
    public void createEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationEmailService.createEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getEmailTemplate(TemplateRequest request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationEmailService.getEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationEmailService.updateEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteEmailTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationEmailService.deleteEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void createSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationSmsService.createSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getSMSTemplate(TemplateRequest request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationSmsService.getSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationSmsService.updateSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteSMSTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationSmsService.deleteSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void createNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.createNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getNotificationEvent(TemplateRequest request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.getNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.updateNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteNotificationEvent(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.deleteNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void sendNotification(Notification request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.sendNotification(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listSMSTemplates(
            SMSTemplateListRequest request, StreamObserver<SMSTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationSmsService.listSMSTemplates(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listEmailTemplates(
            EmailTemplateListRequest request, StreamObserver<EmailTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationEmailService.listEmailTemplates(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listNotificationEvents(
            NotificationEventListRequest request, StreamObserver<NotificationEventListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXNotificationService.listNotificationEvents(request));
        responseObserver.onCompleted();
    }
}
