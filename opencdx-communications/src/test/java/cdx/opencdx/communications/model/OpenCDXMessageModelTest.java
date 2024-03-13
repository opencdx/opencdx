package cdx.opencdx.communications.model;

import cdx.opencdx.grpc.communication.Message;
import cdx.opencdx.grpc.communication.MessageStatus;
import cdx.opencdx.grpc.communication.MessageType;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXMessageModelTest {

    @Test
    void getProtobufMessage() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setPatientId(ObjectId.get().toHexString())
                .setTitle("title")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ).build());
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_1() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setTitle("title")
                .setMessage("message")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ).build());
        Assertions.assertNull(model.getId());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXMessageModel model = new OpenCDXMessageModel(Message.newBuilder()
                .setPatientId(ObjectId.get().toHexString())
                .setTitle("title")
                .setMessage("message")
                .setType(MessageType.INFO)
                .setStatus(MessageStatus.READ)
                .setCreated(Timestamp.newBuilder().getDefaultInstanceForType())
                .setCreator(ObjectId.get().toHexString())
                .setModified(Timestamp.newBuilder())
                .setModifier(ObjectId.get().toHexString()).build());
        Assertions.assertNull(model.getId());
    }

}