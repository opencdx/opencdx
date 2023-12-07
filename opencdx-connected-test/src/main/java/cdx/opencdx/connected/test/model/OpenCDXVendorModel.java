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
package cdx.opencdx.connected.test.model;

import cdx.opencdx.grpc.inventory.Vendor;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Vendor Protobuf message.
 */
@Slf4j
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document("vendor")
public class OpenCDXVendorModel {

    @Id
    private ObjectId id;

    private String name;
    private OpenCDXAddressModel address;
    private String contact;
    private String email;
    private String phone;
    private String website;
    private String description;
    private List<String> certifications;
    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Constructor to create model from protobuf message
     * @param vendor Vendor Protobuf message to create from.
     */
    public OpenCDXVendorModel(Vendor vendor) {
        if (vendor.hasId()) {
            this.setId(new ObjectId(vendor.getId()));
        }
        this.setName(vendor.getVendorName());
        if (vendor.hasVendorAddress()) {
            this.address = new OpenCDXAddressModel(vendor.getVendorAddress());
        }
        this.setContact(vendor.getVendorContact());
        this.setEmail(vendor.getVendorEmail());
        this.setPhone(vendor.getVendorPhone());
        this.setWebsite(vendor.getVendorWebsite());
        this.setDescription(vendor.getVendorDescription());
        this.setCertifications(vendor.getVendorCertificationsList());
        if (vendor.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    vendor.getCreated().getSeconds(), vendor.getCreated().getNanos());
        }
        if (vendor.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    vendor.getModified().getSeconds(), vendor.getModified().getNanos());
        }
        if (vendor.hasCreator()) {
            this.creator = new ObjectId(vendor.getCreator());
        }
        if (vendor.hasModifier()) {
            this.modifier = new ObjectId(vendor.getModifier());
        }
    }

    /**
     * Method to get the Protobuf message for this enitity
     * @return Vendor protobuf message
     */
    public Vendor getProtobufMessage() {
        Vendor.Builder builder = Vendor.newBuilder();
        if (this.getId() != null) {
            builder.setId(this.getId().toHexString());
        }
        if (this.getName() != null) {
            builder.setVendorName(this.getName());
        }
        if (this.getAddress() != null) {
            builder.setVendorAddress(this.getAddress().getProtobufMessage());
        }
        if (this.getContact() != null) {
            builder.setVendorContact(this.getContact());
        }
        if (this.getEmail() != null) {
            builder.setVendorEmail(this.getEmail());
        }
        if (this.getPhone() != null) {
            builder.setVendorPhone(this.getPhone());
        }
        if (this.getWebsite() != null) {
            builder.setVendorWebsite(this.getWebsite());
        }
        if (this.getDescription() != null) {
            builder.setVendorDescription(this.getDescription());
        }
        if (this.getCertifications() != null) {
            builder.addAllVendorCertifications(this.getCertifications());
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
