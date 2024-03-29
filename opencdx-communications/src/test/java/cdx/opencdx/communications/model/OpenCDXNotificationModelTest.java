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
package cdx.opencdx.communications.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.communication.*;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXNotificationModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXNotificationModel openCDXNotificationModel = new OpenCDXNotificationModel(Notification.newBuilder()
                .setQueueId(OpenCDXIdentifier.get().toHexString())
                .setEventId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setSmsStatus(NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .setEmailStatus(NotificationStatus.NOTIFICATION_STATUS_SENT)
                .setTimestamp(Timestamp.newBuilder().setSeconds(10L).setNanos(5).build())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build());

        Assertions.assertNotNull(openCDXNotificationModel.getProtobufMessage());
    }
}
