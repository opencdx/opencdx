package health.safe.api.opencdx.communications.model;

import cdx.open_communication.v2alpha.EmailTemplate;
import cdx.open_communication.v2alpha.TemplateType;
import cdx.open_connected_test.v2alpha.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("connected-test")
public class OpenCDXConnectedTestModel {

    @Id
    private ObjectId id;

    private BasicInfo basicInfo;
    private OrderInfo orderInfo;
    private TestNotes testNotes;
    private PaymentDetails paymentDetails;
    private ProviderInfo providerInfo;
    private TestDetails testDetails;

    /**
     * Constructor to create this model based on a Connected test
     * @param connectedTest Connected test to base this model on.
     */
    public void OpenCDXConnectedTestModel(ConnectedTest connectedTest) {

        basicInfo = connectedTest.getBasicInfo();
        orderInfo = connectedTest.getOrderInfo();
        testNotes = connectedTest.getTestNotes();
        paymentDetails = connectedTest.getPaymentDetails();
        providerInfo = connectedTest.getProviderInfo();
        testDetails = connectedTest.getTestDetails();
    }

    /**
     * Return this model as an EmailTemplate
     * @return EmailTemplate representing this model.
     */
    public ConnectedTest getProtobufMessage() {
        ConnectedTest.Builder builder = ConnectedTest.newBuilder();
        builder.setBasicInfo(this.basicInfo);
        builder.setOrderInfo(this.orderInfo);
        builder.setTestNotes(this.testNotes);
        builder.setPaymentDetails(this.paymentDetails);
        builder.setProviderInfo(this.providerInfo);
        builder.setTestDetails(this.testDetails);
        return builder.build();
    }

}
