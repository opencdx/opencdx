package health.safe.api.opencdx.communications.model;

import cdx.open_connected_test.v2alpha.BasicInfo;
import cdx.open_connected_test.v2alpha.ConnectedTest;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenCDXConnectedTestModelTest {

    @Test
    void getProtobufMessage_1() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new ObjectId().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel();

        Assertions.assertEquals(connectedTest, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new ObjectId().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);
        log.info(model.toString());
        Assertions.assertEquals(connectedTest, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        OpenCDXSMSTemplateModel model = new OpenCDXSMSTemplateModel();
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        ConnectedTest connectedTest =
                ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance()).build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);
        log.info(model.toString());
        Assertions.assertEquals(connectedTest, model.getProtobufMessage());
    }