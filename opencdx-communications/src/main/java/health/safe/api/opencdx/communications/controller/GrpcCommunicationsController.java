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

import cdx.open_communication.v2alpha.*;
import health.safe.api.opencdx.communications.service.CommunicationService;
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
    @Observed(name = "opencdx-communications.grpc.createEmailTemplate")
    public void createEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.createEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.getEmailTemplate")
    public void getEmailTemplate(TemplateRequest request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.getEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.updateEmailTemplate")
    public void updateEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.updateEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.deleteEmailTemplate")
    public void deleteEmailTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.createSMSTemplate")
    public void createSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.createSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.getSMSTemplate")
    public void getSMSTemplate(TemplateRequest request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.getSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.updateSMSTemplate")
    public void updateSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        responseObserver.onNext(this.communicationService.updateSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.deleteSMSTemplate")
    public void deleteSMSTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.createNotificationEvent")
    public void createNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.createNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.getNotificationEvent")
    public void getNotificationEvent(TemplateRequest request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.getNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.updateNotificationEvent")
    public void updateNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        responseObserver.onNext(this.communicationService.updateNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.deleteNotificationEvent")
    public void deleteNotificationEvent(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.deleteNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.sendNotification")
    public void sendNotification(Notification request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.sendNotification(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.listSMSTemplates")
    public void listSMSTemplates(
            SMSTemplateListRequest request, StreamObserver<SMSTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listSMSTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.listEmailTemplates")
    public void listEmailTemplates(
            EmailTemplateListRequest request, StreamObserver<EmailTemplateListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listEmailTemplates(request));
        responseObserver.onCompleted();
    }

    @Override
    @Observed(name = "opencdx-communications.grpc.listNotificationEvents")
    public void listNotificationEvents(
            NotificationEventListRequest request, StreamObserver<NotificationEventListResponse> responseObserver) {
        responseObserver.onNext(this.communicationService.listNotificationEvents(request));
        responseObserver.onCompleted();
    }
}
