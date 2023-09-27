package health.safe.api.opencdx.communications.model;

import cdx.open_communication.v2alpha.Notification;
import cdx.open_communication.v2alpha.NotificationStatus;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXNotificationModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXNotificationModel openCDXNotificationModel = new OpenCDXNotificationModel(Notification.newBuilder()
                .setQueueId(ObjectId.get().toHexString())
                .setEventId(ObjectId.get().toHexString())
                .setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                .setTimestamp(Timestamp.newBuilder().setSeconds(10L).setNanos(5).build())
                .build());

        Assertions.assertNotNull(openCDXNotificationModel.getProtobufMessage());
    }
}