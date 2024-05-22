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
import cdx.opencdx.grpc.data.DoctorNotes;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Doctor Notes in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("doctor-notes")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXDoctorNotesModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private String providerNumber;
    private List<String> tags;
    private Instant noteDatetime;
    private String notes;
    private Instant created;
    private Instant modified;
    private String creator;
    private String modifier;

    /**
     * Constructor from protobuf message doctorNotes
     * @param doctorNotes Protobuf message to generate from
     */
    public OpenCDXDoctorNotesModel(DoctorNotes doctorNotes) {
        if (doctorNotes.hasId()) {
            this.id = new OpenCDXIdentifier(doctorNotes.getId());
        }
        this.patientId = new OpenCDXIdentifier(doctorNotes.getPatientId());
        this.nationalHealthId = doctorNotes.getNationalHealthId();
        this.providerNumber = doctorNotes.getProviderNumber();
        this.tags = doctorNotes.getTagsList();
        this.noteDatetime = Instant.ofEpochSecond(
                doctorNotes.getNoteDatetime().getSeconds(),
                doctorNotes.getNoteDatetime().getNanos());
        this.notes = doctorNotes.getNotes();
        if (doctorNotes.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    doctorNotes.getCreated().getSeconds(),
                    doctorNotes.getCreated().getNanos());
        }
        if (doctorNotes.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    doctorNotes.getModified().getSeconds(),
                    doctorNotes.getModified().getNanos());
        }
        if (doctorNotes.hasCreator()) {
            this.creator = doctorNotes.getCreator();
        }
        if (doctorNotes.hasModifier()) {
            this.modifier = doctorNotes.getModifier();
        }
    }

    /**
     * Method to get the protobuf Doctor Notes object
     * @return protobuf doctor notes object
     */
    public DoctorNotes getProtobufMessage() {
        DoctorNotes.Builder builder = DoctorNotes.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setPatientId(this.patientId.toHexString());
        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(this.nationalHealthId);
        }
        builder.setProviderNumber(this.providerNumber);
        builder.addAllTags(this.tags);
        builder.setNoteDatetime(Timestamp.newBuilder()
                .setSeconds(this.noteDatetime.getEpochSecond())
                .setNanos(this.noteDatetime.getNano())
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

    /**
     * Updates the OpenCDXDoctorNotesModel with the provided DoctorNotes data.
     *
     * @param doctorNotes The DoctorNotes object containing the updated data.
     * @return The updated OpenCDXDoctorNotesModel instance.
     */
    public OpenCDXDoctorNotesModel update(DoctorNotes doctorNotes) {

        this.patientId = new OpenCDXIdentifier(doctorNotes.getPatientId());
        this.nationalHealthId = doctorNotes.getNationalHealthId();
        this.providerNumber = doctorNotes.getProviderNumber();
        this.tags = doctorNotes.getTagsList();
        this.noteDatetime = Instant.ofEpochSecond(
                doctorNotes.getNoteDatetime().getSeconds(),
                doctorNotes.getNoteDatetime().getNanos());
        this.notes = doctorNotes.getNotes();

        return this;
    }
}
