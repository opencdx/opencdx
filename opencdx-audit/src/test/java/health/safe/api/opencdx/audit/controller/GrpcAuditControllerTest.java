package health.safe.api.opencdx.audit.controller;

import com.google.protobuf.Timestamp;
import health.safe.api.opencdx.audit.handlers.OpenCDXAuditMessageHandler;
import health.safe.api.opencdx.grpc.audit.AuditEvent;
import health.safe.api.opencdx.grpc.audit.AuditEventType;
import health.safe.api.opencdx.grpc.audit.AuditSource;
import health.safe.api.opencdx.grpc.audit.AuditStatus;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
public class GrpcAuditControllerTest {

    GrpcAuditController grpcAuditController;

    @Autowired
    OpenCDXAuditMessageHandler openCDXAuditMessageHandler;


    @BeforeEach
    void setup() {
        this.grpcAuditController = new GrpcAuditController(this.openCDXAuditMessageHandler);
    }

    @Test
    void event() {
        StreamObserver<AuditStatus> responseObserver = Mockito.mock(StreamObserver.class);
        AuditEvent event = AuditEvent.newBuilder()
                .setEventType(AuditEventType.PHI_CREATED)
                .setCreated(Timestamp.getDefaultInstance())
                .setAuditSource(AuditSource.getDefaultInstance())
                .build();

        this.grpcAuditController.event(event,responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(AuditStatus.newBuilder().setSuccess(true).build());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
