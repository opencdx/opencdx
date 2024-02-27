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
package cdx.opencdx.shipping.model;

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.ShippingStatus;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.shipping.Order;
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
 * Model for Order in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("order")
public class OpenCDXOrderModel {

    @Id
    private ObjectId id;

    private ObjectId patientId;
    Address shippingAddress;
    TestCase testCase;
    ShippingStatus status;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Create this model from this protobuf message
     * @param order Protobuf message to create from
     */
    public OpenCDXOrderModel(Order order) {
        if (order.hasStatus()) {
            this.status = order.getStatus();
        }

        if (order.hasId()) {
            this.id = new ObjectId(order.getId());
        } else {
            this.status = ShippingStatus.PENDING;
        }

        this.patientId = new ObjectId(order.getPatientId());
        this.shippingAddress = order.getShippingAddress();
        this.testCase = order.getTestCase();

        if (order.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    order.getCreated().getSeconds(), order.getCreated().getNanos());
        }
        if (order.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    order.getModified().getSeconds(), order.getModified().getNanos());
        }
        if (order.hasCreator()) {
            this.creator = new ObjectId(order.getCreator());
        }
        if (order.hasModifier()) {
            this.modifier = new ObjectId(order.getModifier());
        }
    }

    /**
     * Create a protobuf message from this model
     * @return Protobuf message created from this model
     */
    public Order getProtobuf() {
        Order.Builder builder = Order.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.patientId != null) {
            builder.setPatientId(this.patientId.toHexString());
        }

        if (this.shippingAddress != null) {
            builder.setShippingAddress(this.shippingAddress);
        }
        if (this.testCase != null) {
            builder.setTestCase(this.testCase);
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
