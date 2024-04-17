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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXCommunicationClient;
import cdx.opencdx.grpc.data.EmailTemplate;
import cdx.opencdx.grpc.data.Notification;
import cdx.opencdx.grpc.data.NotificationEvent;
import cdx.opencdx.grpc.data.SMSTemplate;
import cdx.opencdx.grpc.service.communications.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Open CDX gRPC Communications Client
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXCommunicationClientImpl implements OpenCDXCommunicationClient {

    private static final String DOMAIN = "OpenCDXCommunicationClientImpl";
    private final CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXCommunicationClientImpl(ManagedChannel channel) {
        this.blockingStub = CommunicationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Communication service client implementation.
     * @param blockingStub gRPC Blocking Stub for communications service.
     */
    public OpenCDXCommunicationClientImpl(CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.createEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplate getEmailTemplate(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).getEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).updateEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteEmailTemplate(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).deleteEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).createSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 5, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).getSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 6, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).updateSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 7, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteSMSTemplate(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).deleteSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 8, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent createNotificationEvent(
            NotificationEvent notificationEvent, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).createNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 9, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent getNotificationEvent(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).getNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 10, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent updateNotificationEvent(
            NotificationEvent notificationEvent, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).updateNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 11, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteNotificationEvent(
            TemplateRequest templateRequest, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).deleteNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 12, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse sendNotification(Notification notification, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return blockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .withCallCredentials(openCDXCallCredentials)
                    .sendNotification(notification);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 13, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplateListResponse listSMSTemplates(
            SMSTemplateListRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).listSMSTemplates(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 14, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplateListResponse listEmailTemplates(
            EmailTemplateListRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).listEmailTemplates(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 15, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEventListResponse listNotificationEvents(
            NotificationEventListRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return blockingStub.withCallCredentials(openCDXCallCredentials).listNotificationEvents(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 16, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
