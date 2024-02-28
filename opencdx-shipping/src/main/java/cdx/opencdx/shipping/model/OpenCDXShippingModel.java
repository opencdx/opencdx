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
import cdx.opencdx.grpc.common.PaymentDetails;
import cdx.opencdx.grpc.shipping.AdditionalService;
import cdx.opencdx.grpc.shipping.Order;
import cdx.opencdx.grpc.shipping.ServiceLevel;
import cdx.opencdx.grpc.shipping.Shipping;
import cdx.opencdx.shipping.dto.OpenCDXShippingRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Shipping in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@Document("shipping")
public class OpenCDXShippingModel {

    @Id
    private ObjectId id;

    private Address senderAddress;
    private Address recipientAddress;
    private Order packageDetails;
    private ServiceLevel serviceLevel;
    private List<AdditionalService> additionalServices;
    private boolean requireSignature;
    private Double declaredValue;
    private Double shippingCost;
    private String shippingVendorId;
    private PaymentDetails paymentDetails;

    /**
     * Default constructor
     */
    public OpenCDXShippingModel() {}

    /**
     * Create this model from this protobuf message
     * @param request Protobuf message to create from
     */
    public OpenCDXShippingModel(OpenCDXShippingRequest request) {
        this.senderAddress = request.getSenderAddress();
        this.recipientAddress = request.getRecipientAddress();
        this.packageDetails = request.getPackageDetails();
        this.declaredValue = request.getDeclaredValue();
        this.requireSignature = request.isRequireSignature();
    }

    /**
     * Create this model from this protobuf message
     * @param shipping Protobuf message to create from
     */
    public OpenCDXShippingModel(Shipping shipping) {
        if (shipping.hasId()) {
            this.id = new ObjectId(shipping.getId());
        }
        this.senderAddress = shipping.getSenderAddress();
        this.recipientAddress = shipping.getRecipientAddress();
        this.packageDetails = shipping.getPackageDetails();
        this.serviceLevel = shipping.getServiceLevel();
        this.additionalServices = shipping.getAdditionalServicesList();
        this.requireSignature = shipping.getRequireSignature();
        this.declaredValue = shipping.getDeclaredValue();
        this.shippingVendorId = shipping.getShippingVendorId();
        this.paymentDetails = shipping.getPaymentDetails();
        this.shippingCost = shipping.getShippingCost();
    }

    /**
     * Create a protobuf message from this model
     * @return Protobuf message created from this model
     */
    public Shipping toProtobuf() {
        Shipping.Builder builder = Shipping.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.senderAddress != null) {
            builder.setSenderAddress(this.senderAddress);
        }

        if (this.recipientAddress != null) {
            builder.setRecipientAddress(this.recipientAddress);
        }

        if (this.packageDetails != null) {
            builder.setPackageDetails(this.packageDetails);
        }

        if (this.serviceLevel != null) {
            builder.setServiceLevel(this.serviceLevel);
        }

        if (this.additionalServices != null) {
            builder.addAllAdditionalServices(this.additionalServices);
        }

        builder.setRequireSignature(this.requireSignature);

        if (this.declaredValue != null) {
            builder.setDeclaredValue(this.declaredValue);
        }

        if (this.shippingVendorId != null) {
            builder.setShippingVendorId(this.shippingVendorId);
        }

        if (this.paymentDetails != null) {
            builder.setPaymentDetails(this.paymentDetails);
        }

        if (this.shippingCost != null) {
            builder.setShippingCost(this.shippingCost);
        }

        return builder.build();
    }

    /**
     * Copy constructor
     * @param other Model to copy from
     */
    public OpenCDXShippingModel(OpenCDXShippingModel other) {
        this.id = other.id;
        this.senderAddress = other.senderAddress;
        this.recipientAddress = other.recipientAddress;
        this.packageDetails = other.packageDetails;
        this.serviceLevel = other.serviceLevel;
        this.additionalServices = other.additionalServices;
        this.requireSignature = other.requireSignature;
        this.declaredValue = other.declaredValue;
        this.shippingCost = other.shippingCost;
        this.shippingVendorId = other.shippingVendorId;
        this.paymentDetails = other.paymentDetails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OpenCDXShippingModel{").append("\n");
        sb.append("id=").append(id).append("\n");
        sb.append("senderAddress = ").append(senderAddress).append("\n");
        sb.append("recipientAddress = ").append(recipientAddress).append("\n");
        sb.append("packageDetails = ").append(packageDetails).append("\n");
        sb.append("serviceLevel = ").append(serviceLevel).append("\n");
        sb.append("additionalServices = ").append(additionalServices).append("\n");
        sb.append("requireSignature = ").append(requireSignature).append("\n");
        sb.append("declaredValue = ").append(declaredValue).append("\n");
        sb.append("shippingCost= ").append(shippingCost).append("\n");
        sb.append("shippingVendorId = ").append(shippingVendorId).append("\n");
        sb.append("paymentDetails = ").append(paymentDetails).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
