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
import cdx.opencdx.grpc.data.KnownAllergy;
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
 * Model for Allergy in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("allergy")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXAllergyModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private String allergen;
    private String reaction;
    private boolean isSevere;
    private Instant onsetDate;
    private Instant lastOccurrence;
    private String notes;
    private Instant created;
    private Instant modified;
    private String creator;
    private String modifier;

    /**
     * Constructor from protobuf message knownAllergy
     * @param knownAllergy Protobuf message to generate from
     */
    public OpenCDXAllergyModel(KnownAllergy knownAllergy) {
        if (knownAllergy.hasId()) {
            this.id = new OpenCDXIdentifier(knownAllergy.getId());
        }
        this.patientId = new OpenCDXIdentifier(knownAllergy.getPatientId());
        this.nationalHealthId = knownAllergy.getNationalHealthId();
        this.allergen = knownAllergy.getAllergen();
        this.reaction = knownAllergy.getReaction();
        this.isSevere = knownAllergy.getIsSevere();
        this.onsetDate = Instant.ofEpochSecond(
                knownAllergy.getOnsetDate().getSeconds(),
                knownAllergy.getOnsetDate().getNanos());
        this.lastOccurrence = Instant.ofEpochSecond(
                knownAllergy.getLastOccurrence().getSeconds(),
                knownAllergy.getLastOccurrence().getNanos());
        this.notes = knownAllergy.getNotes();
        if (knownAllergy.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    knownAllergy.getCreated().getSeconds(),
                    knownAllergy.getCreated().getNanos());
        }
        if (knownAllergy.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    knownAllergy.getModified().getSeconds(),
                    knownAllergy.getModified().getNanos());
        }
        if (knownAllergy.hasCreator()) {
            this.creator = knownAllergy.getCreator();
        }
        if (knownAllergy.hasModifier()) {
            this.modifier = knownAllergy.getModifier();
        }
    }

    /**
     * Method to get the protobuf Known Allergy object
     * @return protobuf bpm object
     */
    public KnownAllergy getProtobufMessage() {
        KnownAllergy.Builder builder = KnownAllergy.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());
        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setAllergen(this.allergen);
        builder.setReaction(this.reaction);
        builder.setIsSevere(this.isSevere);
        builder.setOnsetDate(Timestamp.newBuilder()
                .setSeconds(this.onsetDate.getEpochSecond())
                .setNanos(this.onsetDate.getNano())
                .build());
        builder.setLastOccurrence(Timestamp.newBuilder()
                .setSeconds(this.lastOccurrence.getEpochSecond())
                .setNanos(this.lastOccurrence.getNano())
                .build());
        builder.setNotes(this.notes);
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
