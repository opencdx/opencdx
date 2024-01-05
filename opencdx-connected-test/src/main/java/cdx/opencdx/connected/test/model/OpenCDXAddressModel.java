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

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.AddressPurpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Model for protobuf address.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXAddressModel {
    private AddressPurpose addressPurpose;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String postalCode;
    private String state;
    private ObjectId countryId;

    /**
     * Constructor from a protobuf address
     * @param address protobuf address
     */
    public OpenCDXAddressModel(Address address) {
        this.addressPurpose = address.getAddressPurpose();

        this.address1 = address.getAddress1();
        this.address2 = address.getAddress2();
        this.address3 = address.getAddress3();

        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.state = address.getState();
        this.countryId = new ObjectId(address.getCountryId());
    }

    /**
     * Method to get the protobuf address object
     * @return protobuf address object
     */
    public Address getProtobufMessage() {
        Address.Builder builder = Address.newBuilder();
        if (this.addressPurpose != null) {
            builder.setAddressPurpose(this.addressPurpose);
        }
        if (this.address1 != null) {
            builder.setAddress1(this.address1);
        }
        if (this.address2 != null) {
            builder.setAddress2(this.address2);
        }
        if (this.address3 != null) {
            builder.setAddress3(this.address3);
        }
        if (this.city != null) {
            builder.setCity(this.city);
        }
        if (this.postalCode != null) {
            builder.setPostalCode(this.postalCode);
        }
        if (this.state != null) {
            builder.setState(this.state);
        }
        if (this.countryId != null) {
            builder.setCountryId(this.countryId.toHexString());
        }
        return builder.build();
    }
}
