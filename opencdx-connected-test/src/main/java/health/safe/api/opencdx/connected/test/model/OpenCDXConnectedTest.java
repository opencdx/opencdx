/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.connected.test.model;

import cdx.open_connected_test.v2alpha.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Connected in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("connected-test")
public class OpenCDXConnectedTest {
    @Id
    private ObjectId id;

    private BasicInfo basicInfo;
    private OrderInfo orderInfo;
    private TestNotes testNotes;
    private PaymentDetails paymentDetails;
    private ProviderInfo providerInfo;
    private TestDetails testDetails;

    /**
     * Constructor from protobuf message ConnectedTest
     * @param connectedTest Protobuf message to generate from
     */
    public OpenCDXConnectedTest(ConnectedTest connectedTest) {
        this.id = new ObjectId(connectedTest.getBasicInfo().getId());
        this.basicInfo = connectedTest.getBasicInfo();
        this.orderInfo = connectedTest.getOrderInfo();
        this.testNotes = connectedTest.getTestNotes();
        this.paymentDetails = connectedTest.getPaymentDetails();
        this.providerInfo = connectedTest.getProviderInfo();
        this.testDetails = connectedTest.getTestDetails();
    }
    /**
     * Return this model as an ConnectedTest
     * @return ConnectedTest representing this model.
     */
    public ConnectedTest getProtobufMessage() {
        return ConnectedTest.newBuilder()
                .setBasicInfo(this.basicInfo)
                .setOrderInfo(this.orderInfo)
                .setTestNotes(this.testNotes)
                .setPaymentDetails(this.paymentDetails)
                .setProviderInfo(this.providerInfo)
                .setTestDetails(this.getTestDetails())
                .build();
    }
}
