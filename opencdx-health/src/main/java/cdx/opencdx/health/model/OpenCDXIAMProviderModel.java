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
package cdx.opencdx.health.model;

import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.AddressPurpose;
import cdx.opencdx.grpc.provider.*;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiResult;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXIAMProviderModel {
    @Id
    private ObjectId id;

    private ObjectId userId;
    private String created_epoch;
    private String enumeration_type;
    private String last_updated_epoch;
    private String npiNumber;
    private List<Address> addresses;
    private List<String> practiceLocations;
    private BasicInfo basic;
    private List<Taxonomy> taxonomies;
    private List<Identifier> identifiers;
    private List<String> endpoints;
    private List<String> otherNames;
    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;
    private ProviderStatus status;

    /**
     * Constructor for Provider Model
     * @param result NPI Result
     * @param openCDXCountryRepository Country Repository
     */
    public OpenCDXIAMProviderModel(OpenCDXDtoNpiResult result, OpenCDXCountryRepository openCDXCountryRepository) {
        this.id = new ObjectId();
        this.created_epoch = result.getCreatedEpoch();
        this.enumeration_type = result.getEnumerationType();
        this.last_updated_epoch = result.getLastUpdatedEpoch();
        this.npiNumber = result.getNumber();

        this.addresses = result.getAddresses().stream()
                .map(address -> {
                    Address.Builder builder = Address.newBuilder();
                    Optional<OpenCDXCountryModel> byName =
                            openCDXCountryRepository.findByName(address.getCountryName());
                    byName.ifPresent(openCDXCountryModel ->
                            builder.setCountryId(openCDXCountryModel.getId().toHexString()));
                    if (address.getAddressPurpose() != null) {
                        builder.setAddressPurpose(AddressPurpose.valueOf(address.getAddressPurpose()));
                    }
                    if (address.getAddress1() != null) {
                        builder.setAddress1(address.getAddress1());
                    }
                    if (address.getCity() != null) {
                        builder.setCity(address.getCity());
                    }
                    if (address.getState() != null) {
                        builder.setState(address.getState());
                    }
                    if (address.getPostalCode() != null) {
                        builder.setPostalCode(address.getPostalCode());
                    }
                    return builder.build();
                })
                .toList();

        this.practiceLocations = result.getPracticeLocations();

        if (result.getBasic() != null) {
            BasicInfo.Builder basicBuilder = BasicInfo.newBuilder();
            if (result.getBasic().getFirstName() != null) {
                basicBuilder.setFirstName(result.getBasic().getFirstName());
            }
            if (result.getBasic().getLastName() != null) {
                basicBuilder.setLastName(result.getBasic().getLastName());
            }
            if (result.getBasic().getCredential() != null) {
                basicBuilder.setCredential(result.getBasic().getCredential());
            }
            if (result.getBasic().getSoleProprietor() != null) {
                basicBuilder.setSoleProprietor(result.getBasic().getSoleProprietor());
            }
            if (result.getBasic().getGender() != null) {
                basicBuilder.setGender(result.getBasic().getGender());
            }
            if (result.getBasic().getEnumerationDate() != null) {
                basicBuilder.setEnumerationDate(result.getBasic().getEnumerationDate());
            }
            basicBuilder.setStatus(ProviderStatus.VALIDATED);
            if (result.getBasic().getNamePrefix() != null) {
                basicBuilder.setNamePrefix(result.getBasic().getNamePrefix());
            }
            if (result.getBasic().getNameSuffix() != null) {
                basicBuilder.setNameSuffix(result.getBasic().getNameSuffix());
            }
            this.basic = basicBuilder.build();
        }
        this.taxonomies = result.getTaxonomies().stream()
                .map(taxonomy -> {
                    Taxonomy.Builder builder = Taxonomy.newBuilder();
                    if (taxonomy.getCode() != null) {
                        builder.setCode(taxonomy.getCode());
                    }
                    if (taxonomy.getTaxonomyGroup() != null) {
                        builder.setTaxonomyGroup(taxonomy.getTaxonomyGroup());
                    }
                    if (taxonomy.getDesc() != null) {
                        builder.setDesc(taxonomy.getDesc());
                    }
                    if (taxonomy.getState() != null) {
                        builder.setState(taxonomy.getState());
                    }
                    if (taxonomy.getLicense() != null) {
                        builder.setLicense(taxonomy.getLicense());
                    }
                    builder.setPrimary(taxonomy.isPrimary());

                    return builder.build();
                })
                .toList();
        this.identifiers = result.getIdentifiers().stream()
                .map(identifiers -> {
                    Identifier.Builder builder = Identifier.newBuilder();
                    if (identifiers.getCode() != null) {
                        builder.setCode(identifiers.getCode());
                    }
                    if (identifiers.getDesc() != null) {
                        builder.setDesc(identifiers.getDesc());
                    }
                    if (null != identifiers.getIssuer()) {
                        builder.setIssuer(identifiers.getIssuer());
                    }
                    if (identifiers.getIdentifier() != null) {
                        builder.setIdentifier(identifiers.getIdentifier());
                    }
                    if (identifiers.getState() != null) {
                        builder.setState(identifiers.getState());
                    }
                    return builder.build();
                })
                .toList();

        this.endpoints = result.getEndpoints().stream().map(Object::toString).toList();
        this.otherNames = result.getOtherNames().stream().map(Object::toString).toList();
        this.status = ProviderStatus.VALIDATED;
    }

    /**
     * Method to get the protobuf provider object
     * @return protobuf provider object
     */
    public Provider getProtobufMessage() {
        Provider.Builder builder = Provider.newBuilder();
        builder.setId(this.id.toHexString());
        if (this.userId != null) {
            builder.setUserId(this.userId.toHexString());
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
        if (this.npiNumber != null) {
            builder.setNumber(this.npiNumber);
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
        if (created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(created.getEpochSecond())
                    .setNanos(created.getNano())
                    .build());
        }
        if (modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(modified.getEpochSecond())
                    .setNanos(modified.getNano())
                    .build());
        }
        if (creator != null) {
            builder.setCreator(creator.toHexString());
        }
        if (modifier != null) {
            builder.setModifier(modifier.toHexString());
        }
        return builder.build();
    }
}
