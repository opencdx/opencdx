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
package cdx.opencdx.commons.model;

import cdx.opencdx.grpc.common.Country;
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
 * Model for Country in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("country")
public class OpenCDXCountryModel {

    @Id
    private ObjectId id;

    private String name;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Create this model from this protobuf message
     * @param country Protobuf message to create from
     */
    public OpenCDXCountryModel(Country country) {
        if (country.hasId()) {
            this.id = new ObjectId(country.getId());
        }
        this.name = country.getName();

        if (country.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    country.getCreated().getSeconds(), country.getCreated().getNanos());
        }
        if (country.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    country.getModified().getSeconds(), country.getModified().getNanos());
        }
        if (country.hasCreator()) {
            this.creator = new ObjectId(country.getCreator());
        }
        if (country.hasModifier()) {
            this.modifier = new ObjectId(country.getModifier());
        }
    }

    /**
     * Method to get Protobuf Message
     * @return Country protobuf message
     */
    public Country getProtobufMessage() {
        Country.Builder builder = Country.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.name != null) {
            builder.setName(this.name);
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
