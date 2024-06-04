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
import cdx.opencdx.grpc.data.TemperatureMeasurement;
import cdx.opencdx.grpc.types.TemperatureMethod;
import cdx.opencdx.grpc.types.TemperatureScale;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Model for Temperature Measurement in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("temperatures")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXTemperatureMeasurementModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private double temperatureValue;
    private TemperatureScale temperatureScale;
    private TemperatureMethod temperatureMethod;
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
     * Constructor from protobuf message TemperatureMeasurement
     * @param temperatureMeasurement Protobuf message to generate from
     */
    public OpenCDXTemperatureMeasurementModel(TemperatureMeasurement temperatureMeasurement) {
        if (temperatureMeasurement.hasId()) {
            this.id = new OpenCDXIdentifier(temperatureMeasurement.getId());
        }
        this.patientId = new OpenCDXIdentifier(temperatureMeasurement.getPatientId());
        this.nationalHealthId = temperatureMeasurement.getNationalHealthId();
        this.temperatureValue = temperatureMeasurement.getTemperatureValue();
        this.temperatureScale = temperatureMeasurement.getTemperatureScale();
        this.temperatureMethod = temperatureMeasurement.getTemperatureMethod();
        if (temperatureMeasurement.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    temperatureMeasurement.getTimeOfMeasurement().getSeconds(),
                    temperatureMeasurement.getTimeOfMeasurement().getNanos());
        }
        if (temperatureMeasurement.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    temperatureMeasurement.getCreated().getSeconds(),
                    temperatureMeasurement.getCreated().getNanos());
        }
        if (temperatureMeasurement.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    temperatureMeasurement.getModified().getSeconds(),
                    temperatureMeasurement.getModified().getNanos());
        }
        if (temperatureMeasurement.hasCreator()) {
            this.creator = new OpenCDXIdentifier(temperatureMeasurement.getCreator());
        }
        if (temperatureMeasurement.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(temperatureMeasurement.getModifier());
        }
    }

    /**
     * Method to get the protobuf temperature measurement object
     * @return protobuf temperature object
     */
    public TemperatureMeasurement getProtobufMessage() {
        TemperatureMeasurement.Builder builder = TemperatureMeasurement.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());

        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setTemperatureValue(this.temperatureValue);
        if (this.temperatureScale != null) {
            builder.setTemperatureScale(this.temperatureScale);
        }
        if (this.temperatureMethod != null) {
            builder.setTemperatureMethod(this.temperatureMethod);
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

    /**
     * Updates the OpenCDXTemperatureMeasurementModel with the given TemperatureMeasurement object.
     *
     * @param temperatureMeasurement The TemperatureMeasurement object to update the model with.
     * @return The updated OpenCDXTemperatureMeasurementModel instance.
     */
    public OpenCDXTemperatureMeasurementModel update(TemperatureMeasurement temperatureMeasurement) {
        this.patientId = new OpenCDXIdentifier(temperatureMeasurement.getPatientId());
        this.nationalHealthId = temperatureMeasurement.getNationalHealthId();
        this.temperatureValue = temperatureMeasurement.getTemperatureValue();
        this.temperatureScale = temperatureMeasurement.getTemperatureScale();
        this.temperatureMethod = temperatureMeasurement.getTemperatureMethod();
        if (temperatureMeasurement.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    temperatureMeasurement.getTimeOfMeasurement().getSeconds(),
                    temperatureMeasurement.getTimeOfMeasurement().getNanos());
        }

        return this;
    }
}
