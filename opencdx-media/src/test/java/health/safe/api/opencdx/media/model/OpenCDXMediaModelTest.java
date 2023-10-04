package health.safe.api.opencdx.media.model;

import cdx.media.v2alpha.Media;
import cdx.media.v2alpha.MediaStatus;
import cdx.media.v2alpha.MediaType;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXMediaModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXMediaModel model = new OpenCDXMediaModel(Media.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setCreatedAt(Timestamp.getDefaultInstance())
                .setUpdatedAt(Timestamp.getDefaultInstance())
                .setOrganizationSlug("organization")
                .setWorkspaceSlug("workspace")
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

    @Test
    void getProtobufMessage_2() {
        OpenCDXMediaModel model = new OpenCDXMediaModel(Media.newBuilder()
                .setOrganizationSlug("organization")
                .setWorkspaceSlug("workspace")
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