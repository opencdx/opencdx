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
package cdx.opencdx.media.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.media.*;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXMediaModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXMediaModel model = new OpenCDXMediaModel(Media.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setCreatedAt(Timestamp.getDefaultInstance())
                .setUpdatedAt(Timestamp.getDefaultInstance())
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .setName("name")
                .setShortDescription("This is a short Description")
                .setDescription("This is a description")
                .setType(MediaType.MEDIA_TYPE_IMAGE)
                .addLabels("LABEL_1")
                .addLabels("LABEL_2")
                .setMimeType("application/json")
                .setSize(10L)
                .setLocation("location")
                .setEndpoint("media/downloads/1234")
                .setStatus(MediaStatus.MEDIA_STATUS_ACTIVE)
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build());

        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXMediaModel model = new OpenCDXMediaModel(Media.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .setName("name")
                .setShortDescription("This is a short Description")
                .setDescription("This is a description")
                .setType(MediaType.MEDIA_TYPE_IMAGE)
                .addLabels("LABEL_1")
                .addLabels("LABEL_2")
                .setMimeType("application/json")
                .setSize(10L)
                .setLocation("location")
                .setEndpoint("media/downloads/1234")
                .setStatus(MediaStatus.MEDIA_STATUS_ACTIVE)
                .build());

        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
