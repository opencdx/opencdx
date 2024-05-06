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
import cdx.opencdx.commons.data.OpenCDXRecord;
import cdx.opencdx.grpc.data.MedicationAdministration;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * Model for Medication Administration in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@OpenCDXRecord(value="medication-administration")
//@Document(value="medication-administration")
public class OpenCDXMedicationAdministrationModel {
    @Id
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier medicationId;
    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private Instant administrationTime;
    private String administratedBy;
    private String administrationNotes;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message MedicationAdministration
     * @param medicationAdministration Protobuf message to generate from
     */
    public OpenCDXMedicationAdministrationModel(MedicationAdministration medicationAdministration) {
        if (medicationAdministration.hasId()) {
            this.id = new OpenCDXIdentifier(medicationAdministration.getId());
        }
        this.medicationId = new OpenCDXIdentifier(medicationAdministration.getMedicationId());
        this.patientId = new OpenCDXIdentifier(medicationAdministration.getPatientId());
        this.nationalHealthId = medicationAdministration.getNationalHealthId();
        this.administrationTime = Instant.ofEpochSecond(
                medicationAdministration.getAdministrationTime().getSeconds(),
                medicationAdministration.getAdministrationTime().getNanos());
        this.administratedBy = medicationAdministration.getAdministratedBy();
        this.administrationNotes = medicationAdministration.getAdministrationNotes();
        if (medicationAdministration.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    medicationAdministration.getCreated().getSeconds(),
                    medicationAdministration.getCreated().getNanos());
        }
        if (medicationAdministration.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    medicationAdministration.getModified().getSeconds(),
                    medicationAdministration.getModified().getNanos());
        }
        if (medicationAdministration.hasCreator()) {
            this.creator = new OpenCDXIdentifier(medicationAdministration.getCreator());
        }
        if (medicationAdministration.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(medicationAdministration.getModifier());
        }
    }

    /**
     * Method to convert to protobuf message Medication Administration
     * @return Protobuf message Medication
     */
    public MedicationAdministration getProtobufMessage() {
        MedicationAdministration.Builder builder = MedicationAdministration.newBuilder();
        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        builder.setMedicationId(this.medicationId.toHexString());
        builder.setPatientId(this.patientId.toHexString());
        builder.setNationalHealthId(this.nationalHealthId);
        if (this.administrationTime != null) {
            builder.setAdministrationTime(Timestamp.newBuilder()
                    .setSeconds(this.administrationTime.getEpochSecond())
                    .setNanos(this.administrationTime.getNano())
                    .build());
        }
        builder.setAdministratedBy(this.administratedBy);
        builder.setAdministrationNotes(this.administrationNotes);
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
