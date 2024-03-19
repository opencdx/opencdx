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

import cdx.opencdx.grpc.health.medication.DosageForm;
import cdx.opencdx.grpc.health.medication.Medication;
import cdx.opencdx.grpc.health.medication.MedicationAdministrationRoute;
import cdx.opencdx.grpc.health.medication.MedicationFrequency;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
@Document("medications")
public class OpenCDXMedicationModel {
    @Id
    private ObjectId id;

    private ObjectId patientId;
    private String nationalHealthId;
    private String medicationName;
    private String dosageStrength;
    private String dosageUnit;
    private DosageForm dosageForm;
    private Double quantity;
    private String instructions;
    private MedicationAdministrationRoute administrationRoute;
    private String otherAdministrationRoute;
    private MedicationFrequency frequency;
    private String otherFrequency;
    private Instant startDate;
    private Instant endDate;
    private String providerNumber;
    private ObjectId pharmacyId;
    private boolean prescription;
    private boolean generic;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Constructor from protobuf message Medication
     * @param medication Protobuf message to generate from
     */
    public OpenCDXMedicationModel(Medication medication) {
        if (medication.hasId()) {
            this.id = new ObjectId(medication.getId());
        }
        this.patientId = new ObjectId(medication.getPatientId());
        this.nationalHealthId = medication.getNationalHealthId();
        this.medicationName = medication.getMedicationName();
        this.dosageStrength = medication.getDosageStrength();
        this.dosageUnit = medication.getDosageUnit();
        this.dosageForm = medication.getDosageForm();
        this.quantity = medication.getQuantity();
        this.instructions = medication.getInstructions();
        this.administrationRoute = medication.getRouteOfAdministration();
        if (medication.hasOtherRouteOfAdministration()) {
            this.otherAdministrationRoute = medication.getOtherRouteOfAdministration();
        }
        this.frequency = medication.getFrequency();
        this.otherFrequency = medication.getOtherFrequency();
        this.startDate = Instant.ofEpochSecond(
                medication.getStartDate().getSeconds(),
                medication.getStartDate().getNanos());
        if (medication.hasEndDate()) {
            this.endDate = Instant.ofEpochSecond(
                    medication.getEndDate().getSeconds(),
                    medication.getEndDate().getNanos());
        }
        this.providerNumber = medication.getProviderNumber();
        this.pharmacyId = new ObjectId(medication.getPharmacyId());
        this.prescription = medication.getIsPrescription();
        this.generic = medication.getIsGeneric();

        if (medication.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    medication.getCreated().getSeconds(),
                    medication.getCreated().getNanos());
        }
        if (medication.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    medication.getModified().getSeconds(),
                    medication.getModified().getNanos());
        }
        if (medication.hasCreator()) {
            this.creator = new ObjectId(medication.getCreator());
        }
        if (medication.hasModifier()) {
            this.modifier = new ObjectId(medication.getModifier());
        }
    }

    /**
     * Method to convert to protobuf message Medication
     * @return Protobuf message Medication
     */
    public Medication getProtobufMessage() {
        Medication.Builder builder = Medication.newBuilder()
                .setPatientId(patientId.toHexString())
                .setNationalHealthId(nationalHealthId)
                .setMedicationName(medicationName)
                .setDosageStrength(dosageStrength)
                .setDosageUnit(dosageUnit)
                .setDosageForm(dosageForm)
                .setQuantity(quantity)
                .setInstructions(instructions)
                .setRouteOfAdministration(administrationRoute);
        if (otherAdministrationRoute != null) {
            builder.setOtherRouteOfAdministration(otherAdministrationRoute);
        }
        builder.setFrequency(frequency);
        if (otherFrequency != null) {
            builder.setOtherFrequency(otherFrequency);
        }
        builder.setStartDate(Timestamp.newBuilder()
                .setSeconds(startDate.getEpochSecond())
                .setNanos(startDate.getNano())
                .build());
        if (endDate != null) {
            builder.setEndDate(Timestamp.newBuilder()
                    .setSeconds(endDate.getEpochSecond())
                    .setNanos(endDate.getNano())
                    .build());
        }
        builder.setProviderNumber(providerNumber)
                .setPharmacyId(pharmacyId.toHexString())
                .setIsPrescription(prescription)
                .setIsGeneric(generic);
        if (id != null) {
            builder.setId(id.toHexString());
        }
        if (created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(created.getEpochSecond())
                    .setNanos(created.getNano())
                    .build());
        }
        if (modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(modified.getEpochSecond())
                    .setNanos(modified.getNano())
                    .build());
        }
        if (creator != null) {
            builder.setCreator(creator.toHexString());
        }
        if (modifier != null) {
            builder.setModifier(modifier.toHexString());
        }
        return builder.build();
    }
}
