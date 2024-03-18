/*
 * Copyright 2024 Safe Health Systems, Inc.
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
import cdx.opencdx.communications.service.OpenCDXMessageService;
import cdx.opencdx.communications.service.OpenCDXNotificationService;
import cdx.opencdx.grpc.common.Pagination;
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
public class OpenCDXGrpcCommunicationsController extends CommunicationServiceGrpc.CommunicationServiceImplBase {

    private final OpenCDXNotificationService openCDXNotificationService;

    private final OpenCDXCommunicationEmailService openCDXCommunicationEmailService;
    private final OpenCDXCommunicationSmsService openCDXCommunicationSmsService;
    private final OpenCDXMessageService openCDXMessageService;

    /**
     * Constructor using the gRPC Communications Controller.
     *  @param openCDXNotificationService       service to process notifications
     * @param openCDXCommunicationEmailService service to process email request
     * @param openCDXCommunicationSmsService   service to process SMS request
     * @param openCDXMessageService           service to process message request
     */
    @Autowired
    public OpenCDXGrpcCommunicationsController(
            OpenCDXNotificationService openCDXNotificationService,
            OpenCDXCommunicationEmailService openCDXCommunicationEmailService,
            OpenCDXCommunicationSmsService openCDXCommunicationSmsService,
            OpenCDXMessageService openCDXMessageService) {
        this.openCDXNotificationService = openCDXNotificationService;
        this.openCDXCommunicationEmailService = openCDXCommunicationEmailService;
        this.openCDXCommunicationSmsService = openCDXCommunicationSmsService;
        this.openCDXMessageService = openCDXMessageService;
        log.info("OpenCDXGrpcCommunicationsController created");
    }

    @Secured({})
    @Override
    public void createEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        log.trace("Received request to create email template ");
        responseObserver.onNext(this.openCDXCommunicationEmailService.createEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getEmailTemplate(TemplateRequest request, StreamObserver<EmailTemplate> responseObserver) {
        log.trace("Received request to get email template ");
        responseObserver.onNext(this.openCDXCommunicationEmailService.getEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateEmailTemplate(EmailTemplate request, StreamObserver<EmailTemplate> responseObserver) {
        log.trace("Received request to update email template ");
        responseObserver.onNext(this.openCDXCommunicationEmailService.updateEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteEmailTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        log.trace("Received request to delete email template ");
        responseObserver.onNext(this.openCDXCommunicationEmailService.deleteEmailTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void createSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        log.trace("Received request to create SMS template ");
        responseObserver.onNext(this.openCDXCommunicationSmsService.createSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getSMSTemplate(TemplateRequest request, StreamObserver<SMSTemplate> responseObserver) {
        log.trace("Received request to get SMS template ");
        responseObserver.onNext(this.openCDXCommunicationSmsService.getSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateSMSTemplate(SMSTemplate request, StreamObserver<SMSTemplate> responseObserver) {
        log.trace("Received request to update SMS template ");
        responseObserver.onNext(this.openCDXCommunicationSmsService.updateSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteSMSTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        log.trace("Received request to delete SMS template ");
        responseObserver.onNext(this.openCDXCommunicationSmsService.deleteSMSTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void createNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        log.trace("Received request to create notification event ");
        responseObserver.onNext(this.openCDXNotificationService.createNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getNotificationEvent(TemplateRequest request, StreamObserver<NotificationEvent> responseObserver) {
        log.trace("Received request to get notification event ");
        responseObserver.onNext(this.openCDXNotificationService.getNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateNotificationEvent(NotificationEvent request, StreamObserver<NotificationEvent> responseObserver) {
        log.trace("Received request to update notification event ");
        responseObserver.onNext(this.openCDXNotificationService.updateNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteNotificationEvent(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        log.trace("Received request to delete notification event ");
        responseObserver.onNext(this.openCDXNotificationService.deleteNotificationEvent(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void sendNotification(Notification request, StreamObserver<SuccessResponse> responseObserver) {
        log.trace("Received request to send notification ");
        responseObserver.onNext(this.openCDXNotificationService.sendNotification(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listSMSTemplates(
            SMSTemplateListRequest request, StreamObserver<SMSTemplateListResponse> responseObserver) {
        log.trace("Received request to list SMS templates ");
        responseObserver.onNext(this.openCDXCommunicationSmsService.listSMSTemplates(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listEmailTemplates(
            EmailTemplateListRequest request, StreamObserver<EmailTemplateListResponse> responseObserver) {
        log.trace("Received request to list email templates ");
        responseObserver.onNext(this.openCDXCommunicationEmailService.listEmailTemplates(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listNotificationEvents(
            NotificationEventListRequest request, StreamObserver<NotificationEventListResponse> responseObserver) {
        log.trace("Received request to list notification events ");
        responseObserver.onNext(this.openCDXNotificationService.listNotificationEvents(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void createMessageTemplate(MessageTemplate request, StreamObserver<MessageTemplate> responseObserver) {
        log.trace("Received request to create message template ");
        responseObserver.onNext(this.openCDXMessageService.createMessageTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getMessageTemplate(TemplateRequest request, StreamObserver<MessageTemplate> responseObserver) {
        log.trace("Received request to get messages ");
        responseObserver.onNext(this.openCDXMessageService.getMessageTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateMessageTemplate(MessageTemplate request, StreamObserver<MessageTemplate> responseObserver) {
        log.trace("Received request to update message template ");
        responseObserver.onNext(this.openCDXMessageService.updateMessageTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteMessageTemplate(TemplateRequest request, StreamObserver<SuccessResponse> responseObserver) {
        log.trace("Received request to delete message template ");
        responseObserver.onNext(this.openCDXMessageService.deleteMessageTemplate(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listMessageTemplates(Pagination request, StreamObserver<MessageTemplateListResponse> responseObserver) {
        log.trace("Received request to list message templates ");
        responseObserver.onNext(this.openCDXMessageService.listMessageTemplates(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void getMessages(GetMessagesRequest request, StreamObserver<GetMessagesResponse> responseObserver) {
        log.trace("Received request to get messages ");
        responseObserver.onNext(this.openCDXMessageService.getMessages(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void markMessageAsRead(
            MarkMessagesAsReadRequest request, StreamObserver<MarkMessagesAsReadResponse> responseObserver) {
        log.trace("Received request to mark message as read ");
        responseObserver.onNext(this.openCDXMessageService.markMessageAsRead(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void markMessageAsUnread(
            MarkMessagesAsUnreadRequest request, StreamObserver<MarkMessagesAsUnreadResponse> responseObserver) {
        log.trace("Received request to mark message as unread ");
        responseObserver.onNext(this.openCDXMessageService.markMessageAsUnread(request));
        responseObserver.onCompleted();
    }
}
