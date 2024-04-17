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
import cdx.opencdx.grpc.data.HeartRPM;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Heart RPM in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("heart-rpm")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXHeartRPMModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private Integer measurementTakenOverInSeconds;
    private boolean sittingPositionFiveMinutes;
    private Instant timeOfMeasurement;
    private Instant created;
    private Instant modified;
    private String creator;
    private String modifier;

    /**
     * Constructor from protobuf message heartRPM
     * @param heartRPM Protobuf message to generate from
     */
    public OpenCDXHeartRPMModel(HeartRPM heartRPM) {
        if (heartRPM.hasId()) {
            this.id = new OpenCDXIdentifier(heartRPM.getId());
        }
        this.patientId = new OpenCDXIdentifier(heartRPM.getPatientId());
        this.nationalHealthId = heartRPM.getNationalHealthId();
        this.measurementTakenOverInSeconds = heartRPM.getMeasurementTakenOverInSeconds();
        this.sittingPositionFiveMinutes = heartRPM.getSittingPosition5Minutes();
        if (heartRPM.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    heartRPM.getCreated().getSeconds(), heartRPM.getCreated().getNanos());
        }
        if (heartRPM.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    heartRPM.getCreated().getSeconds(), heartRPM.getCreated().getNanos());
        }
        if (heartRPM.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    heartRPM.getModified().getSeconds(), heartRPM.getModified().getNanos());
        }
        if (heartRPM.hasCreator()) {
            this.creator = heartRPM.getCreator();
        }
        if (heartRPM.hasModifier()) {
            this.modifier = heartRPM.getModifier();
        }
    }

    /**
     * Method to get the protobuf HeartRPM object
     * @return protobuf heartRPM object
     */
    public HeartRPM getProtobufMessage() {
        HeartRPM.Builder builder = HeartRPM.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());

        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setMeasurementTakenOverInSeconds(this.measurementTakenOverInSeconds);
        builder.setSittingPosition5Minutes(this.sittingPositionFiveMinutes);
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
            builder.setCreator(this.creator);
        }
        if (this.modifier != null) {
            builder.setModifier(this.modifier);
        }
        return builder.build();
    }
}
