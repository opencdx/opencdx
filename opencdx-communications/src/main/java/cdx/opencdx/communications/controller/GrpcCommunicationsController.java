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

import cdx.open_communication.v2alpha.*;
import cdx.opencdx.communications.service.OpenCDXCommunicationService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Communications service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class GrpcCommunicationsController extends CommunicationServiceGrpc.CommunicationServiceImplBase {

    private final OpenCDXCommunicationService openCDXCommunicationService;

    /**
     * Constructor using the gRPC Communications Controller.
     * @param openCDXCommunicationService service to use for processing
     */
    @Autowired
    public GrpcCommunicationsController(OpenCDXCommunicationService openCDXCommunicationService) {
        this.openCDXCommunicationService = openCDXCommunicationService;
    }

    @Override
    public void createEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.createEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getEmailTemplate(TemplateRequest request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.getEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.updateEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmailTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.deleteEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.createSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getSMSTemplate(TemplateRequest request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.getSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.updateSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSMSTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.deleteSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.createNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getNotificationEvent(TemplateRequest request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.getNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.updateNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteNotificationEvent(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.deleteNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void sendNotification(Notification request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.sendNotification(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listSMSTemplates(
            SMSTemplateListRequest request, StreamObserver<SMSTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.listSMSTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listEmailTemplates(
            EmailTemplateListRequest request, StreamObserver<EmailTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.listEmailTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listNotificationEvents(
            NotificationEventListRequest request, StreamObserver<NotificationEventListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCommunicationService.listNotificationEvents(request));
        responseObserver.onCompleted();
    }
}