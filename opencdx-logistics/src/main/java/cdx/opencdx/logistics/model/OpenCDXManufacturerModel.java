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
package cdx.opencdx.logistics.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXAddressModel;
import cdx.opencdx.grpc.common.ContactInfo;
import cdx.opencdx.grpc.common.EmailAddress;
import cdx.opencdx.grpc.common.PhoneNumber;
import cdx.opencdx.grpc.inventory.Manufacturer;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Manufacturer Protobuf Message
 */
@Slf4j
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document("manufacturer")
public class OpenCDXManufacturerModel {

    @Id
    private OpenCDXIdentifier id;

    private String name;
    private OpenCDXAddressModel address;
    private ContactInfo contact;
    private EmailAddress email;
    private PhoneNumber phone;
    private String website;
    private String description;
    private List<String> certifications;
    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Create this entity from the Manufacturer protobuf message
     * @param manufacturer Protobuf message to create this entity from.
     */
    public OpenCDXManufacturerModel(Manufacturer manufacturer) {
        if (manufacturer.hasId()) {
            this.setId(new OpenCDXIdentifier(manufacturer.getId()));
        }
        this.setName(manufacturer.getName());
        if (manufacturer.hasManufacturerAddress()) {
            this.address = new OpenCDXAddressModel(manufacturer.getManufacturerAddress());
        }
        this.setContact(manufacturer.getManufacturerContact());
        this.setEmail(manufacturer.getManufacturerEmail());
        this.setPhone(manufacturer.getManufacturerPhone());
        this.setWebsite(manufacturer.getManufacturerWebsite());
        this.setDescription(manufacturer.getManufacturerDescription());
        this.setCertifications(manufacturer.getManufacturerCertificationsList());
        if (manufacturer.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    manufacturer.getCreated().getSeconds(),
                    manufacturer.getCreated().getNanos());
        }
        if (manufacturer.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    manufacturer.getModified().getSeconds(),
                    manufacturer.getModified().getNanos());
        }
        if (manufacturer.hasCreator()) {
            this.creator = new OpenCDXIdentifier(manufacturer.getCreator());
        }
        if (manufacturer.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(manufacturer.getModifier());
        }
    }

    /**
     * Method to get Protobuf Message
     * @return Manufactuerer Protobuf message from this entity.
     */
    public Manufacturer getProtobufMessage() {
        Manufacturer.Builder builder = Manufacturer.newBuilder();
        if (this.getId() != null) {
            builder.setId(this.getId().toHexString());
        }
        if (this.getName() != null) {
            builder.setName(this.getName());
        }
        if (this.getAddress() != null) {
            builder.setManufacturerAddress(this.getAddress().getProtobufMessage());
        }
        if (this.getContact() != null) {
            builder.setManufacturerContact(this.getContact());
        }
        if (this.getEmail() != null) {
            builder.setManufacturerEmail(this.getEmail());
        }
        if (this.getPhone() != null) {
            builder.setManufacturerPhone(this.getPhone());
        }
        if (this.getWebsite() != null) {
            builder.setManufacturerWebsite(this.getWebsite());
        }
        if (this.getDescription() != null) {
            builder.setManufacturerDescription(this.getDescription());
        }
        if (this.getCertifications() != null) {
            builder.addAllManufacturerCertifications(this.getCertifications());
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
