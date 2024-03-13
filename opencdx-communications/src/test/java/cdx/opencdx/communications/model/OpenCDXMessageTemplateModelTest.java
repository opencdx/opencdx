package cdx.opencdx.communications.model;

import cdx.opencdx.grpc.communication.Message;
import cdx.opencdx.grpc.communication.MessageTemplate;
import cdx.opencdx.grpc.communication.MessageType;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXMessageTemplateModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXMessageTemplateModel model = new OpenCDXMessageTemplateModel(MessageTemplate.newBuilder()
                .addAllVariables(List.of("var"))
                .setTitle("title")
                .setContent("content")
                .setType(MessageType.INFO)
                .setCreated(Timestamp.newBuilder().getDefaultInstanceForType())
                .setCreator(ObjectId.get().toHexString())
                .setModified(Timestamp.newBuilder())
                .setModifier(ObjectId.get().toHexString()).build());
        Assertions.assertNull(model.getId());
    }

    @Test
    void getProtobufMessage_1() {
        OpenCDXMessageTemplateModel model = new OpenCDXMessageTemplateModel(MessageTemplate.newBuilder()
                .addAllVariables(List.of("var"))
                .setTitle("title")
                .setContent("content")
                .setType(MessageType.INFO)
                .setCreated(Timestamp.newBuilder().getDefaultInstanceForType())
                .setCreator(ObjectId.get().toHexString())
                .setModified(Timestamp.newBuilder())
                .setModifier(ObjectId.get().toHexString()).build());
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }
}