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

import cdx.opencdx.grpc.inventory.Address;
import cdx.opencdx.grpc.inventory.Manufacturer;
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
    private ObjectId id;

    private String name;
    private Address address;
    private String contact;
    private String email;
    private String phone;
    private String website;
    private String description;
    private List<String> certifications;

    /**
     * Create this entity from the Manufacturer protobuf message
     * @param manufacturer Protobuf message to create this entity from.
     */
    public OpenCDXManufacturerModel(Manufacturer manufacturer) {
        if (manufacturer.hasId()) {
            this.setId(new ObjectId(manufacturer.getId()));
        }
        this.setName(manufacturer.getName());
        if (manufacturer.hasManufacturerAddress()) {
            this.setAddress(manufacturer.getManufacturerAddress());
        }
        this.setContact(manufacturer.getManufacturerContact());
        this.setEmail(manufacturer.getManufacturerEmail());
        this.setPhone(manufacturer.getManufacturerPhone());
        this.setWebsite(manufacturer.getManufacturerWebsite());
        this.setDescription(manufacturer.getManufacturerDescription());
        this.setCertifications(manufacturer.getManufacturerCertificationsList());
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
            builder.setManufacturerAddress(this.getAddress());
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

        return builder.build();
    }
}
