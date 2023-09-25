package health.safe.api.opencdx.connected.test.model;

import cdx.open_connected_test.v2alpha.ConnectedTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("connected-test")
public class OpenCDXConnectedTest {
    @Id
    private ObjectId id;

    OpenCDXConnectedTestBasicInfo openCDXConnectedTestBasicInfo;


    public OpenCDXConnectedTest(ConnectedTest connectedTest) {
        this.id = new ObjectId(connectedTest.getBasicInfo().getId());
        this.openCDXConnectedTestBasicInfo = new OpenCDXConnectedTestBasicInfo(connectedTest.getBasicInfo());
    }

    public ConnectedTest getProtobufMessage() {
        return ConnectedTest.newBuilder()
                .setBasicInfo(this.openCDXConnectedTestBasicInfo.getProtobufMessage(this.id.toHexString()))
                .build();
    }
}
