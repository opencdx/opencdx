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
import cdx.opencdx.grpc.data.BPM;
import cdx.opencdx.grpc.types.ArmUsed;
import cdx.opencdx.grpc.types.BPMUnits;
import cdx.opencdx.grpc.types.CuffSize;
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
 * Model for BPM in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("bpm")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXBPMModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    CuffSize cuffSize;
    ArmUsed armUsed;
    private int systolic;
    private int diastolic;
    BPMUnits bpmUnits;
    private boolean sittingPositionFiveMinutes;
    private boolean urinatedThirtyMinutesPrior;
    private Instant timeOfMeasurement;
    private Instant created;
    private Instant modified;
    private String creator;
    private String modifier;

    /**
     * Constructor from protobuf message bpm
     * @param bpm Protobuf message to generate from
     */
    public OpenCDXBPMModel(BPM bpm) {
        if (bpm.hasId()) {
            this.id = new OpenCDXIdentifier(bpm.getId());
        }
        this.patientId = new OpenCDXIdentifier(bpm.getPatientId());
        this.nationalHealthId = bpm.getNationalHealthId();
        this.cuffSize = bpm.getCuffSize();
        this.armUsed = bpm.getArmUsed();
        this.systolic = bpm.getSystolic();
        this.diastolic = bpm.getDiastolic();
        this.bpmUnits = bpm.getUnit();
        this.sittingPositionFiveMinutes = bpm.getSittingPosition5Minutes();
        this.urinatedThirtyMinutesPrior = bpm.getUrinated30MinutesPrior();
        if (bpm.hasTimeOfMeasurement()) {
            this.timeOfMeasurement = Instant.ofEpochSecond(
                    bpm.getCreated().getSeconds(), bpm.getCreated().getNanos());
        }
        if (bpm.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    bpm.getCreated().getSeconds(), bpm.getCreated().getNanos());
        }
        if (bpm.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    bpm.getModified().getSeconds(), bpm.getModified().getNanos());
        }
        if (bpm.hasCreator()) {
            this.creator = bpm.getCreator();
        }
        if (bpm.hasModifier()) {
            this.modifier = bpm.getModifier();
        }
    }

    /**
     * Method to get the protobuf BPM object
     * @return protobuf bpm object
     */
    public BPM getProtobufMessage() {
        BPM.Builder builder = BPM.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());

        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setCuffSize(this.cuffSize);
        builder.setArmUsed(this.armUsed);
        builder.setSystolic(this.systolic);
        builder.setDiastolic(this.diastolic);
        builder.setUnit(this.bpmUnits);
        builder.setSittingPosition5Minutes(this.sittingPositionFiveMinutes);
        builder.setUrinated30MinutesPrior(this.urinatedThirtyMinutesPrior);
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
