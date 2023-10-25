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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXCommunicationClient;
import cdx.opencdx.grpc.communication.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Open CDX gRPC Communications Client
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@ConditionalOnProperty(prefix = "opencdx.client", name = "communication", havingValue = "true")
public class OpenCDXCommunicationClientImpl implements OpenCDXCommunicationClient {

    private static final String DOMAIN = "OpenCDXCommunicationClientImpl";
    private final CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub;

    /**
     * Default Constructor used for normal operation.
     */
    public OpenCDXCommunicationClientImpl() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("communications", 9090)
                .usePlaintext()
                .build();

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
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.createEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 1, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplate getEmailTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 2, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.updateEmailTemplate(emailTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 3, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteEmailTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteEmailTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 4, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate createSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.createSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 5, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate getSMSTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 6, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplate updateSMSTemplate(SMSTemplate smsTemplate) throws OpenCDXClientException {
        try {
            return blockingStub.updateSMSTemplate(smsTemplate);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 7, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteSMSTemplate(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteSMSTemplate(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 8, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent createNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXClientException {
        try {
            return blockingStub.createNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 9, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent getNotificationEvent(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.getNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 10, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXClientException {
        try {
            return blockingStub.updateNotificationEvent(notificationEvent);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 11, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) throws OpenCDXClientException {
        try {
            return blockingStub.deleteNotificationEvent(templateRequest);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 12, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SuccessResponse sendNotification(Notification notification) throws OpenCDXClientException {
        try {
            return blockingStub.sendNotification(notification);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 13, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public SMSTemplateListResponse listSMSTemplates(SMSTemplateListRequest request) {
        try {
            return blockingStub.listSMSTemplates(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 14, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public EmailTemplateListResponse listEmailTemplates(EmailTemplateListRequest request) {
        try {
            return blockingStub.listEmailTemplates(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 15, status.getMessage(), status.getDetailsList(), e);
        }
    }

    @Override
    public NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request) {
        try {
            return blockingStub.listNotificationEvents(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()), DOMAIN, 16, status.getMessage(), status.getDetailsList(), e);
        }
    }
}
