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
package cdx.opencdx.iam.model;

import cdx.opencdx.grpc.provider.*;
import com.google.protobuf.Timestamp;
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
 * Model for Provider in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("provider")
public class OpenCDXIAMProviderModel {
    @Id
    private ObjectId id;

    private String userId;
    private String created_epoch;
    private String enumeration_type;
    private String last_updated_epoch;
    private String number;
    private List<Address> addresses;
    private List<String> practiceLocations;
    private BasicInfo basic;
    private List<Taxonomy> taxonomies;
    private List<Identifier> identifiers;
    private List<String> endpoints;
    private List<String> otherNames;
    private com.google.protobuf.Timestamp created;
    private com.google.protobuf.Timestamp modified;
    private String creator;
    private String modifier;
    private ProviderStatus status;

    /**
     * Constructor from protobuf message Provider
     * @param provider Protobuf message to generate from
     */
    public OpenCDXIAMProviderModel(Provider provider) {
        this.id = new ObjectId(provider.getId());
        this.userId = provider.getUserId();
        this.created_epoch = provider.getCreatedEpoch();
        this.enumeration_type= provider.getEnumerationType();
        this.last_updated_epoch = provider.getLastUpdatedEpoch();
        this.number = provider.getNumber();
        this.addresses = provider.getAddressesList();
        this.practiceLocations = provider.getPracticeLocationsList();
        this.basic = provider.getBasic();
        this.taxonomies = provider.getTaxonomiesList();
        this.identifiers = provider.getIdentifiersList();
        this.endpoints = provider.getEndpointsList();
        this.otherNames = provider.getOtherNamesList();
        if (provider.hasCreated()) {
            this.created = Timestamp.newBuilder()
                    .setSeconds(provider.getCreated().getSeconds())
                    .setNanos(provider.getCreated().getNanos())
                    .build();
        }
        if (provider.hasModified()) {
            this.modified = Timestamp.newBuilder()
                    .setSeconds(provider.getCreated().getSeconds())
                    .setNanos(provider.getCreated().getNanos())
                    .build();
        }
        this.creator = provider.getCreator();
        this.modifier = provider.getModifier();
        this.status = ProviderStatus.VALIDATED;
    }

    /**
     * Method to get the protobuf provider object
     * @return protobuf provider object
     */
    @SuppressWarnings("java:S3776")
    public Provider getProtobufMessage() {
        Provider.Builder builder = Provider.newBuilder();
        builder.setId(this.id.toHexString());
        if (this.userId != null) {
            builder.setUserId(this.userId);
        }
        if (this.created_epoch != null) {
            builder.setCreatedEpoch(this.created_epoch);
        }
        if (this.enumeration_type != null) {
            builder.setEnumerationType(this.enumeration_type);
        }
        if (this.last_updated_epoch != null) {
            builder.setLastUpdatedEpoch(this.last_updated_epoch);
        }
        if (this.number != null) {
            builder.setNumber(this.number);
        }
        if (this.addresses != null) {
            builder.addAllAddresses(this.addresses);
        }
        if (this.practiceLocations != null) {
            builder.addAllPracticeLocations(this.practiceLocations);
        }
        if (this.basic != null) {
            builder.setBasic(this.basic);
        }
        if (this.taxonomies != null) {
            builder.addAllTaxonomies(this.taxonomies);
        }
        if (this.identifiers != null) {
            builder.addAllIdentifiers(this.identifiers);
        }
        if (this.endpoints != null) {
            builder.addAllEndpoints(this.endpoints);
        }
        if (this.otherNames != null) {
            builder.addAllOtherNames(this.otherNames);
        }
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getSeconds())
                    .setNanos(this.created.getNanos())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getSeconds())
                    .setNanos(this.modified.getNanos())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator);
        }
        if (this.modifier != null) {
            builder.setModifier(this.modifier);
        }
        return builder.build();
    }
}
