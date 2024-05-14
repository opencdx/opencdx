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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.ContinentEnum;
import cdx.opencdx.grpc.data.Country;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
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
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private String name;
    private String iso2;
    private String iso3;
    private ContinentEnum continent;
    private String fips;
    private Integer isoNumeric;
    private List<String> languageCodes;
    private String phoneCode;
    private String topLevelInternetDomain;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Create this model from this protobuf message
     * @param country Protobuf message to create from
     */
    public OpenCDXCountryModel(Country country) {
        log.trace("Creating OpenCDXCountryModel from protobuf message");
        if (country.hasId()) {
            this.id = new OpenCDXIdentifier(country.getId());
        }
        this.name = country.getName();
        this.iso2 = country.getIso2();
        this.iso3 = country.getIso3();
        this.continent = country.getContinent();
        this.fips = country.getFips();
        this.isoNumeric = country.getIsoNumeric();
        this.languageCodes = country.getLanguageCodesList();
        this.phoneCode = country.getPhoneCode();
        this.topLevelInternetDomain = country.getTopLevelInternetDomain();

        if (country.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    country.getCreated().getSeconds(), country.getCreated().getNanos());
        }
        if (country.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    country.getModified().getSeconds(), country.getModified().getNanos());
        }
        if (country.hasCreator()) {
            this.creator = new OpenCDXIdentifier(country.getCreator());
        }
        if (country.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(country.getModifier());
        }
    }

    /**
     * Create country Object for db
     *
     * @param name Name of country
     * @param digram iso2
     * @param trigram iso3
     * @param topLevelInternetDomain internet domain
     * @param fips fips
     * @param isoNumeric isoNumeric
     * @param phoneCode phone Code
     * @param continent continent
     * @param languageCodes language Code
     */
    public OpenCDXCountryModel(
            final String name,
            final String digram,
            final String trigram,
            final String topLevelInternetDomain,
            final String fips,
            final Integer isoNumeric,
            final String phoneCode,
            final String continent,
            final List<String> languageCodes) {
        this.iso2 = digram;
        this.iso3 = trigram;
        this.continent = ContinentEnum.valueOf(continent);
        this.name = name;
        this.fips = fips;
        this.isoNumeric = isoNumeric;
        this.languageCodes = languageCodes;
        this.phoneCode = phoneCode;
        this.topLevelInternetDomain = topLevelInternetDomain;
    }
    /**
     * Method to get Protobuf Message
     * @return Country protobuf message
     */
    public Country getProtobufMessage() {
        log.trace("Creating protobuf message from OpenCDXCountryModel");
        Country.Builder builder = Country.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.iso2 != null) {
            builder.setIso2(this.iso2);
        }
        if (this.iso3 != null) {
            builder.setIso3(this.iso3);
        }
        if (this.continent != null) {
            builder.setContinent(this.continent);
        }
        if (this.fips != null) {
            builder.setFips(this.fips);
        }
        if (this.isoNumeric != null) {
            builder.setIsoNumeric(this.isoNumeric);
        }
        if (this.languageCodes != null) {
            builder.addAllLanguageCodes(this.languageCodes);
        }
        if (this.phoneCode != null) {
            builder.setPhoneCode(this.phoneCode);
        }
        if (this.topLevelInternetDomain != null) {
            builder.setTopLevelInternetDomain(this.topLevelInternetDomain);
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

    /**
     * Updates the OpenCDXCountryModel with information from the given Country object.
     *
     * @param country the Country object containing the updated information
     * @return the updated OpenCDXCountryModel object
     */
    public OpenCDXCountryModel update(Country country) {
        this.name = country.getName();
        this.iso2 = country.getIso2();
        this.iso3 = country.getIso3();
        this.continent = country.getContinent();
        this.fips = country.getFips();
        this.isoNumeric = country.getIsoNumeric();
        this.languageCodes = country.getLanguageCodesList();
        this.phoneCode = country.getPhoneCode();
        this.topLevelInternetDomain = country.getTopLevelInternetDomain();

        return this;
    }
}
