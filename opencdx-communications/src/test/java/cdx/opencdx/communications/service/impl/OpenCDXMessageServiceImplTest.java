package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.communications.model.OpenCDXMessageTemplateModel;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.communications.repository.OpenCDXMessageRepository;
import cdx.opencdx.communications.repository.OpenCDXMessageTemplateRepository;
import cdx.opencdx.grpc.communication.MessageTemplate;
import cdx.opencdx.grpc.communication.NotificationEvent;
import cdx.opencdx.grpc.communication.TemplateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMessageServiceImplTest {

    @Mock
    OpenCDXMessageServiceImpl openCDXMessageService;
    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;
    @Autowired
    OpenCDXAuditService openCDXAuditService;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    OpenCDXMessageTemplateRepository openCDXMessageTemplateRepository;
    @Mock
    OpenCDXMessageRepository openCDXMessageRepository;
    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.openCDXMessageTemplateRepository =  Mockito.mock(OpenCDXMessageTemplateRepository.class);
        this.openCDXMessageRepository = Mockito.mock(OpenCDXMessageRepository.class);
        this.objectMapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXMessageService = new OpenCDXMessageServiceImpl(
                openCDXDocumentValidator,
                this.openCDXAuditService,
                objectMapper,
                openCDXMessageTemplateRepository,
                openCDXMessageRepository,
                openCDXCurrentUser);

    }

    @AfterEach
    void tearDown() {
        Mockito.reset(
                this.objectMapper
                // this.openCDXEmailTemplateRepository,
//                this.openCDXNotificationEventRepository
                );
        //  this.openCDXSMSTemplateRespository);
    }

    @Test
    void createMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.createMessageTemplate(messageTemplate);
        });
    }

    @Test
    void createMessageTemplateNoException() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        MessageTemplate messageTemplate = MessageTemplate.newBuilder()
                .setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXMessageService.createMessageTemplate(messageTemplate);
        });
    }

    @Test
    void getMessageTemplate() {
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString()).build();
        Mockito.when(openCDXMessageTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXMessageTemplateModel.builder().id(ObjectId.get()).build()));
        Assertions.assertDoesNotThrow(() -> {
            this.openCDXMessageService.getMessageTemplate(messageTemplate);
        });
    }

    @Test
    void getMessageTemplateException() {
        TemplateRequest messageTemplate = TemplateRequest.newBuilder()
                .setTemplateId(ObjectId.get().toHexString()).build();
        Mockito.when(openCDXMessageTemplateRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(OpenCDXNotFound.class, () ->
            this.openCDXMessageService.getMessageTemplate(messageTemplate));
    }

    @Test
    void updateMessageTemplateOpenCDXFailedPrecondition() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.getDefaultInstance();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> {
            this.openCDXMessageService.updateMessageTemplate(messageTemplate);
        });
    }

    @Test
    void updateMessageTemplateException() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        MessageTemplate messageTemplate = MessageTemplate.newBuilder().setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.updateMessageTemplate(messageTemplate);
        });
    }

    @Test
    void updateMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        MessageTemplate messageTemplate = MessageTemplate.newBuilder().setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.updateMessageTemplate(messageTemplate));
    }

    @Test
    void deleteMessageTemplate() throws JsonProcessingException {
        Mockito.when(this.openCDXMessageTemplateRepository.save(Mockito.any(OpenCDXMessageTemplateModel.class)))
                .thenReturn(OpenCDXMessageTemplateModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        TemplateRequest messageTemplate = TemplateRequest.newBuilder().setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.deleteMessageTemplate(messageTemplate));
    }

    @Test
    void deleteMessageTemplateException() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        TemplateRequest messageTemplate = TemplateRequest.newBuilder().setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            this.openCDXMessageService.deleteMessageTemplate(messageTemplate);
        });
    }

    @Test
    void deleteMessageTemplateSuccess () {
        Mockito.when(openCDXMessageTemplateRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        TemplateRequest messageTemplate = TemplateRequest.newBuilder().setTemplateId(ObjectId.get().toHexString()).build();
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.deleteMessageTemplate(messageTemplate));

    }
}