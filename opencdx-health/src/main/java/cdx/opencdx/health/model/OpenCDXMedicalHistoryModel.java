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
import cdx.opencdx.grpc.data.DiagnosisCode;
import cdx.opencdx.grpc.data.MedicalHistory;
import cdx.opencdx.grpc.types.Relationship;
import cdx.opencdx.grpc.types.RelationshipFamilyLine;
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
 * Model for Vaccine in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("medical-history")
public class OpenCDXMedicalHistoryModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    Relationship relationship;
    RelationshipFamilyLine relationshipFamilyLine;

    private Instant startDate;
    private Instant endDate;

    private DiagnosisCode condition;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message medicalHistory
     * @param medicalHistory Protobuf message to generate from
     */
    public OpenCDXMedicalHistoryModel(MedicalHistory medicalHistory) {
        this.id = new OpenCDXIdentifier(medicalHistory.getId());
        this.patientId = new OpenCDXIdentifier(medicalHistory.getPatientId());
        this.nationalHealthId = medicalHistory.getNationalHealthId();

        this.relationship = medicalHistory.getRelationship();
        this.relationshipFamilyLine = medicalHistory.getRelationshipFamilyLine();
        this.startDate = Instant.ofEpochSecond(
                medicalHistory.getStartDate().getSeconds(),
                medicalHistory.getStartDate().getNanos());
        this.endDate = Instant.ofEpochSecond(
                medicalHistory.getEndDate().getSeconds(),
                medicalHistory.getEndDate().getNanos());

        this.condition = medicalHistory.getCondition();

        if (medicalHistory.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    medicalHistory.getCreated().getSeconds(),
                    medicalHistory.getCreated().getNanos());
        }
        if (medicalHistory.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    medicalHistory.getModified().getSeconds(),
                    medicalHistory.getModified().getNanos());
        }
        if (medicalHistory.hasCreator()) {
            this.creator = new OpenCDXIdentifier(medicalHistory.getCreator());
        }
        if (medicalHistory.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(medicalHistory.getModifier());
        }
    }

    /**
     * Method to convert to protobuf message MedicalHistory
     * @return Protobuf message MedicalHistory
     */
    public MedicalHistory getProtobufMessage() {
        MedicalHistory.Builder builder = MedicalHistory.newBuilder();
        builder.setId(this.id.toHexString());
        builder.setPatientId(this.patientId.toHexString());
        builder.setNationalHealthId(this.nationalHealthId);

        if (this.relationship != null) {
            builder.setRelationship(this.relationship);
        }
        if (this.relationshipFamilyLine != null) {
            builder.setRelationshipFamilyLine(this.relationshipFamilyLine);
        }

        if (this.startDate != null) {
            builder.setStartDate(Timestamp.newBuilder()
                    .setSeconds(this.startDate.getEpochSecond())
                    .setNanos(this.startDate.getNano())
                    .build());
        }

        if (this.endDate != null) {
            builder.setEndDate(Timestamp.newBuilder()
                    .setSeconds(this.endDate.getEpochSecond())
                    .setNanos(this.endDate.getNano())
                    .build());
        }

        if (this.condition != null) {
            builder.setCondition(this.condition);
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
}
