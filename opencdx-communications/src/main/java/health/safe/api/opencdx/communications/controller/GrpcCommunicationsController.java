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
package health.safe.api.opencdx.communications.controller;

import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.grpc.communication.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Communications service
 */
@Slf4j
@GRpcService
public class GrpcCommunicationsController extends CommunicationServiceGrpc.CommunicationServiceImplBase {

    private final CommunicationService communicationService;

    /**
     * Constructor using the gRPC Communications Controller.
     * @param communicationService service to use for processing
     */
    @Autowired
    public GrpcCommunicationsController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public void createEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.createEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getEmailTemplate(TemplateRequest request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.getEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.updateEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmailTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.createSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getSMSTemplate(TemplateRequest request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.getSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.updateSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSMSTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.createNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getNotificationEvent(TemplateRequest request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.getNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.updateNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteNotificationEvent(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    public void sendNotification(Notification request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.sendNotification(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listSMSTemplates(
            SMSTemplateListRequest request, StreamObserver<SMSTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listSMSTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listEmailTemplates(
            EmailTemplateListRequest request, StreamObserver<EmailTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listEmailTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listNotificationEvents(
            NotificaitonEventListRequest request, StreamObserver<NotificationEventListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listNotificationEvents(request));
        responseObserver.onCompleted();
    }
}