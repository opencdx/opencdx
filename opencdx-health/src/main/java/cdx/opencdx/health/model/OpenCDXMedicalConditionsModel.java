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
import cdx.opencdx.commons.model.OpenCDXDiagnosisCodeModel;
import cdx.opencdx.grpc.data.Diagnosis;
import cdx.opencdx.grpc.types.DiagnosisStatus;
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
 * Model for Medication in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("medical-conditions")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXMedicalConditionsModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;

    private OpenCDXDiagnosisCodeModel diagnosisCode;
    private Instant diagnosisDatetime;
    private String matchedValueSet;
    private List<String> relatedEntities;
    private DiagnosisStatus diagnosisStatus;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message Diagnosis
     * @param diagnosis Protobuf message to generate from
     */
    public OpenCDXMedicalConditionsModel(Diagnosis diagnosis) {
        if (diagnosis.hasDiagnosisId()) {
            this.id = new OpenCDXIdentifier(diagnosis.getDiagnosisId());
        }
        if (diagnosis.getPatientId() != null) {
            this.patientId = new OpenCDXIdentifier(diagnosis.getPatientId());
        }
        this.nationalHealthId = diagnosis.getNationalHealthId();
        this.diagnosisStatus = diagnosis.getDiagnosisStatus();
        this.relatedEntities = diagnosis.getRelatedEntitiesList();
        this.matchedValueSet = diagnosis.getMatchedValueSet();
        this.diagnosisDatetime = Instant.ofEpochSecond(
                diagnosis.getDiagnosisDatetime().getSeconds(),
                diagnosis.getDiagnosisDatetime().getNanos());

        if (diagnosis.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    diagnosis.getCreated().getSeconds(), diagnosis.getCreated().getNanos());
        }
        if (diagnosis.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    diagnosis.getModified().getSeconds(),
                    diagnosis.getModified().getNanos());
        }
        if (diagnosis.hasCreator()) {
            this.creator = new OpenCDXIdentifier(diagnosis.getCreator());
        }
        if (diagnosis.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(diagnosis.getModifier());
        }
    }

    /**
     * Updates the OpenCDXMedicalConditionsModel with the provided Diagnosis.
     *
     * @param diagnosis The Diagnosis object containing the data to update.
     * @return The updated OpenCDXMedicalConditionsModel.
     */
    public OpenCDXMedicalConditionsModel update(Diagnosis diagnosis) {

        this.diagnosisStatus = diagnosis.getDiagnosisStatus();
        this.relatedEntities = diagnosis.getRelatedEntitiesList();
        this.matchedValueSet = diagnosis.getMatchedValueSet();
        this.diagnosisDatetime = Instant.ofEpochSecond(
                diagnosis.getDiagnosisDatetime().getSeconds(),
                diagnosis.getDiagnosisDatetime().getNanos());

        return this;
    }

    /**
     * Method to convert to protobuf message Diagnosis
     * @return Protobuf message Diagnosis
     */
    public Diagnosis getProtobufMessage() {
        Diagnosis.Builder builder = Diagnosis.newBuilder();
        builder.setDiagnosisId(this.id.toHexString());
        builder.setPatientId(this.patientId.toHexString());
        builder.setNationalHealthId(this.nationalHealthId);
        if (this.diagnosisCode != null) {
            builder.setDiagnosisCode(this.diagnosisCode.getProtobufMessage());
        }
        builder.setMatchedValueSet(this.matchedValueSet);
        builder.setDiagnosisStatus(this.diagnosisStatus);
        builder.addAllRelatedEntities(this.relatedEntities);

        if (this.diagnosisDatetime != null) {
            builder.setDiagnosisDatetime(Timestamp.newBuilder()
                    .setSeconds(this.diagnosisDatetime.getEpochSecond())
                    .setNanos(this.diagnosisDatetime.getNano())
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
}
