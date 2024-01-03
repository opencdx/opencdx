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
package cdx.opencdx.connected.test.model;

import cdx.opencdx.grpc.connected.*;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Connected in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("connected-test")
public class OpenCDXConnectedTestModel {

    @Id
    private ObjectId id;

    private ObjectId userId;
    private String nationalHealthId;

    private BasicInfo basicInfo;
    private OrderInfo orderInfo;
    private TestNotes testNotes;
    private PaymentDetails paymentDetails;
    private ProviderInfo providerInfo;
    private TestDetails testDetails;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Constructor from protobuf message ConnectedTest
     * @param connectedTest Protobuf message to generate from
     */
    public OpenCDXConnectedTestModel(ConnectedTest connectedTest) {
        if (connectedTest.getBasicInfo().hasId()) {
            this.id = new ObjectId(connectedTest.getBasicInfo().getId());
        }
        this.userId = new ObjectId(connectedTest.getBasicInfo().getUserId());
        this.nationalHealthId = connectedTest.getBasicInfo().getNationalHealthId();
        this.basicInfo = connectedTest.getBasicInfo();
        this.orderInfo = connectedTest.getOrderInfo();
        this.testNotes = connectedTest.getTestNotes();
        this.paymentDetails = connectedTest.getPaymentDetails();
        this.providerInfo = connectedTest.getProviderInfo();
        this.testDetails = connectedTest.getTestDetails();

        if (connectedTest.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    connectedTest.getCreated().getSeconds(),
                    connectedTest.getCreated().getNanos());
        }
        if (connectedTest.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    connectedTest.getModified().getSeconds(),
                    connectedTest.getModified().getNanos());
        }
        if (connectedTest.hasCreator()) {
            this.creator = new ObjectId(connectedTest.getCreator());
        }
        if (connectedTest.hasModifier()) {
            this.modifier = new ObjectId(connectedTest.getModifier());
        }
    }
    /**
     * Return this model as an ConnectedTest
     * @return ConnectedTest representing this model.
     */
    public ConnectedTest getProtobufMessage() {
        ConnectedTest.Builder builder = ConnectedTest.newBuilder();
        if (this.basicInfo != null) {
            builder.setBasicInfo(BasicInfo.newBuilder(this.basicInfo)
                    .setId(this.id.toHexString())
                    .setUserId(this.userId.toHexString())
                    .setNationalHealthId(this.nationalHealthId)
                    .build());
        }
        if (this.orderInfo != null) {
            builder.setOrderInfo(this.orderInfo);
        }
        if (this.paymentDetails != null) {
            builder.setPaymentDetails(this.paymentDetails);
        }
        if (this.providerInfo != null) {
            builder.setProviderInfo(this.providerInfo);
        }
        if (this.testDetails != null) {
            builder.setTestDetails(this.testDetails);
        }
        if (this.testNotes != null) {
            builder.setTestNotes(this.testNotes);
        }
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }
}
