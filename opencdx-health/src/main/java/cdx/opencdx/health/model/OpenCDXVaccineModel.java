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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXAddressModel;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.Vaccine;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Model for Vaccine in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("vaccine")
public class OpenCDXVaccineModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private Instant administrationDate;
    private String fips;
    private OpenCDXAddressModel location;
    private String healthDistrict;
    private String facilityType;
    Medication vaccine;
    private int doseNumber;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message Vaccine
     * @param vaccine Protobuf message to generate from
     */
    public OpenCDXVaccineModel(Vaccine vaccine) {
        if (vaccine.hasId()) {
            this.id = new OpenCDXIdentifier(vaccine.getId());
        }
        this.patientId = new OpenCDXIdentifier(vaccine.getPatientId());
        this.nationalHealthId = vaccine.getNationalHealthId();
        this.administrationDate = Instant.ofEpochSecond(
                vaccine.getAdministrationDate().getSeconds(),
                vaccine.getAdministrationDate().getNanos());
        this.fips = vaccine.getFips();
        if (vaccine.hasLocation()) {
            this.location = new OpenCDXAddressModel(vaccine.getLocation());
        }
        this.healthDistrict = vaccine.getHealthDistrict();
        this.facilityType = vaccine.getFacilityType();
        this.vaccine = vaccine.getVaccine();
        this.doseNumber = vaccine.getDoseNumber();
        if (vaccine.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    vaccine.getCreated().getSeconds(), vaccine.getCreated().getNanos());
        }
        if (vaccine.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    vaccine.getModified().getSeconds(), vaccine.getModified().getNanos());
        }
        if (vaccine.hasCreator()) {
            this.creator = new OpenCDXIdentifier(vaccine.getCreator());
        }
        if (vaccine.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(vaccine.getModifier());
        }
    }

    /**
     * Method to convert to protobuf message vaccine
     * @return Protobuf message Vaccine
     */
    public Vaccine getProtobufMessage() {
        Vaccine.Builder builder = Vaccine.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());
        builder.setNationalHealthId(this.nationalHealthId);
        if (this.administrationDate != null) {
            builder.setAdministrationDate(Timestamp.newBuilder()
                    .setSeconds(this.administrationDate.getEpochSecond())
                    .setNanos(this.administrationDate.getNano())
                    .build());
        }
        builder.setFips(this.fips);
        builder.setHealthDistrict(this.healthDistrict);
        builder.setFacilityType(this.facilityType);
        builder.setVaccine(this.vaccine);
        builder.setDoseNumber(this.doseNumber);
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
        if (this.modifier != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }
}
