package health.safe.api.opencdx.communications.controller;

import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.communications.service.CommunicationService;
import health.safe.api.opencdx.communications.service.impl.CommunicationServiceImpl;
import health.safe.api.opencdx.grpc.communication.EmailTemplate;
import health.safe.api.opencdx.grpc.communication.NotificationEvent;
import health.safe.api.opencdx.grpc.communication.SMSTemplate;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class GrpcCommunicationsControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    CommunicationService communicationService;

    GrpcCommunicationsController grpcCommunicationsController;

    @BeforeEach
    void setUp() {
        this.communicationService = new CommunicationServiceImpl(this.openCDXAuditService);
        this.grpcCommunicationsController = new GrpcCommunicationsController(this.communicationService);
    }

    @Test
    void createEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.grpcCommunicationsController.createEmailTemplate(emailTemplate,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(emailTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static EmailTemplate getTestEmailTemplate() {
        return EmailTemplate.newBuilder()
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getEmailTemplate() {
    }

    @Test
    void updateEmailTemplate() {
        StreamObserver<EmailTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        EmailTemplate emailTemplate = getTestEmailTemplate();
        this.grpcCommunicationsController.updateEmailTemplate(emailTemplate,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(emailTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteEmailTemplate() {
    }

    @Test
    void createSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplate smsTemplate = getTestSMSTemplate();
        this.grpcCommunicationsController.createSMSTemplate(smsTemplate,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(smsTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static SMSTemplate getTestSMSTemplate() {
        return SMSTemplate.newBuilder()
                .setTemplateId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getSMSTemplate() {
    }

    @Test
    void updateSMSTemplate() {
        StreamObserver<SMSTemplate> responseObserver = Mockito.mock(StreamObserver.class);
        SMSTemplate smsTemplate = getTestSMSTemplate();
        this.grpcCommunicationsController.updateSMSTemplate(smsTemplate,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(smsTemplate);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteSMSTemplate() {
    }

    @Test
    void createNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEvent notificationEvent = getTestNotificationEvent();
        this.grpcCommunicationsController.createNotificationEvent(notificationEvent,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(notificationEvent);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private static NotificationEvent getTestNotificationEvent() {
        return NotificationEvent.newBuilder()
                .setEventId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void getNotificationEvent() {
    }

    @Test
    void updateNotificationEvent() {
        StreamObserver<NotificationEvent> responseObserver = Mockito.mock(StreamObserver.class);
        NotificationEvent notificationEvent = getTestNotificationEvent();
        this.grpcCommunicationsController.updateNotificationEvent(notificationEvent,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(notificationEvent);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();

    }

    @Test
    void deleteNotificationEvent() {
    }

    @Test
    void sendNotification() {
    }

    @Test
    void listSMSTemplates() {
    }

    @Test
    void listEmailTemplates() {
    }

    @Test
    void listNotificationEvents() {
    }
}