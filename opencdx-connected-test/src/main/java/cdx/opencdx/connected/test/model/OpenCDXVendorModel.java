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
import cdx.opencdx.grpc.inventory.Vendor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document("vendor")
public class OpenCDXVendorModel {

    @Id
    protected ObjectId id;

    protected String name;
    protected Address address;
    protected String contact;
    protected String email;
    protected String phone;
    protected String website;
    protected String description;
    protected List<String> certifications;

    public OpenCDXVendorModel(Vendor vendor) {
        if (vendor.hasId()) {
            this.setId(new ObjectId(vendor.getId()));
        }
        this.setName(vendor.getVendorName());
        if (vendor.hasVendorAddress()) {
            this.setAddress(vendor.getVendorAddress());
        }
        this.setContact(vendor.getVendorContact());
        this.setEmail(vendor.getVendorEmail());
        this.setPhone(vendor.getVendorPhone());
        this.setWebsite(vendor.getVendorWebsite());
        this.setDescription(vendor.getVendorDescription());
        this.setCertifications(vendor.getVendorCertificationsList());
    }

    public Vendor getProtobufMessage() {
        Vendor.Builder builder = Vendor.newBuilder();
        if (this.getId() != null) {
            builder.setId(this.getId().toHexString());
        }
        if (this.getName() != null) {
            builder.setVendorName(this.getName());
        }
        if (this.getAddress() != null) {
            builder.setVendorAddress(this.getAddress());
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

        return builder.build();
    }
}
