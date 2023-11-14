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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXAddressModel {
    private String street;
    private String city;
    private String postalCode;
    private String region;
    private ObjectId countryId;

    public OpenCDXAddressModel(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.region = address.getRegion();
        this.countryId = new ObjectId(address.getCountry());
    }

    public Address getProtobufMessage() {
        Address.Builder builder = Address.newBuilder();

        if (this.street != null) {
            builder.setStreet(this.street);
        }
        if (this.city != null) {
            builder.setCity(this.city);
        }
        if (this.postalCode != null) {
            builder.setPostalCode(this.postalCode);
        }
        if (this.region != null) {
            builder.setRegion(this.region);
        }
        if (this.countryId != null) {
            builder.setCountry(this.countryId.toHexString());
        }
        return builder.build();
    }
}
