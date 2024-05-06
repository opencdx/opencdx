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
import cdx.opencdx.grpc.data.HeightMeasurement;
import cdx.opencdx.grpc.types.HeightUnits;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Height Measurement in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("heights")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXHeightMeasurementModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private double height;
    HeightUnits unitsOfMeasure;
    private Instant timeOfMeasurement;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message HeightMeasurement
     * @param heightMeasurement Protobuf message to generate from
     */
    public OpenCDXHeightMeasurementModel(HeightMeasurement heightMeasurement) {
        if (heightMeasurement.hasId()) {
            this.id = new OpenCDXIdentifier(heightMeasurement.getId());
        }
        this.patientId = new OpenCDXIdentifier(heightMeasurement.getPatientId());
        this.nationalHealthId = heightMeasurement.getNationalHealthId();
        this.height = heightMeasurement.getHeight();
        this.unitsOfMeasure = heightMeasurement.getUnitsOfMeasure();
        if (heightMeasurement.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    heightMeasurement.getCreated().getSeconds(),
                    heightMeasurement.getCreated().getNanos());
        }
        if (heightMeasurement.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    heightMeasurement.getCreated().getSeconds(),
                    heightMeasurement.getCreated().getNanos());
        }
        if (heightMeasurement.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    heightMeasurement.getModified().getSeconds(),
                    heightMeasurement.getModified().getNanos());
        }
        if (heightMeasurement.hasCreator()) {
            this.creator = new OpenCDXIdentifier(heightMeasurement.getCreator());
        }
        if (heightMeasurement.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(heightMeasurement.getModifier());
        }
    }

    /**
     * Method to get the protobuf height measurement object
     * @return protobuf height object
     */
    public HeightMeasurement getProtobufMessage() {
        HeightMeasurement.Builder builder = HeightMeasurement.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());

        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setHeight(this.height);
        if (this.unitsOfMeasure != null) {
            builder.setUnitsOfMeasure(this.unitsOfMeasure);
        }
        if (this.timeOfMeasurement != null) {
            builder.setTimeOfMeasurement(Timestamp.newBuilder()
                    .setSeconds(this.timeOfMeasurement.getEpochSecond())
                    .setNanos(this.timeOfMeasurement.getNano())
                    .build());
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
        if (this.modifier != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }

    public OpenCDXHeightMeasurementModel update(HeightMeasurement heightMeasurement) {
        this.patientId = new OpenCDXIdentifier(heightMeasurement.getPatientId());
        this.nationalHealthId = heightMeasurement.getNationalHealthId();
        this.height = heightMeasurement.getHeight();
        this.unitsOfMeasure = heightMeasurement.getUnitsOfMeasure();
        if (heightMeasurement.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    heightMeasurement.getCreated().getSeconds(),
                    heightMeasurement.getCreated().getNanos());
        }

        return this;
    }
}
