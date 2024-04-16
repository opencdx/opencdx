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
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.types.DosageForm;
import cdx.opencdx.grpc.types.MedicationAdministrationRoute;
import cdx.opencdx.grpc.types.MedicationFrequency;
import cdx.opencdx.health.dto.openfda.Product;
import cdx.opencdx.health.dto.openfda.Result;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.stream.Collectors;

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
    private OpenCDXIdentifier id;

    private OpenCDXIdentifier patientId;
    private String nationalHealthId;
    private String medicationName;
    private String dosageStrength;
    private String dosageUnit;
    private DosageForm dosageForm;
    private String otherDosageForm;
    private Double quantity;
    private String instructions;
    private MedicationAdministrationRoute administrationRoute;
    private String otherAdministrationRoute;
    private MedicationFrequency frequency;
    private String otherFrequency;
    private Instant startDate;
    private Instant endDate;
    private String providerNumber;
    private OpenCDXIdentifier pharmacyId;
    private boolean prescription;
    private String productNdc;

    @Builder.Default
    private boolean generic = false;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from OpenFDA Result and Product
     * @param medicationName Name of the medication
     * @param result OpenFDA Result
     * @param product OpenFDA Product
     * @param generic Is the medication generic
     */
    public OpenCDXMedicationModel(String medicationName, Result result, Product product, boolean generic) {
        this.medicationName = medicationName;
        this.generic = generic;
        try {
            this.dosageForm = DosageForm.valueOf(product.getDosage_form().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid dosage form: " + product.getDosage_form());
            this.dosageForm = DosageForm.OTHER_DOSAGE_FORM;
            this.otherDosageForm = product.getDosage_form();
        }
        if (product.getActive_ingredients() != null
                && !product.getActive_ingredients().isEmpty()) {
            this.dosageStrength = product.getActive_ingredients().stream()
                    .map(ingredient -> ingredient.getStrength() + " " + ingredient.getName())
                    .collect(Collectors.joining(", "));
        }
        if (result.getDosage_and_administration() != null) {
            this.instructions = result.getDosage_and_administration().getFirst();
        }

        if (result.getOpenfda() != null && result.getOpenfda().getProduct_ndc() != null) {
            this.productNdc = result.getOpenfda().getProduct_ndc().getFirst();
        }

        try {
            this.administrationRoute =
                    MedicationAdministrationRoute.valueOf(product.getRoute().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid route of administration: " + product.getRoute());
            this.administrationRoute = MedicationAdministrationRoute.OTHER_ROUTE;
            this.otherAdministrationRoute = product.getRoute();
        }

        this.prescription = product.getMarketing_status().equalsIgnoreCase("Prescription");
    }

    /**
     * Constructor from protobuf message Medication
     * @param medication Protobuf message to generate from
     */
    public OpenCDXMedicationModel(Medication medication) {
        if (medication.hasId()) {
            this.id = new OpenCDXIdentifier(medication.getId());
        }
        this.patientId = new OpenCDXIdentifier(medication.getPatientId());
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
        this.otherDosageForm = medication.getOtherDosageForm();
        this.otherAdministrationRoute = medication.getOtherRouteOfAdministration();
        this.productNdc = medication.getProductNdc();

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
        if (medication.hasPharmacyId()) {
            this.pharmacyId = new OpenCDXIdentifier(medication.getPharmacyId());
        }
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
            this.creator = new OpenCDXIdentifier(medication.getCreator());
        }
        if (medication.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(medication.getModifier());
        }
    }

    /**
     * Method to convert to protobuf message Medication
     * @return Protobuf message Medication
     */
    @SuppressWarnings("java:S3776")
    public Medication getProtobufMessage() {
        Medication.Builder builder = Medication.newBuilder();
        if (this.medicationName != null) {
            builder.setMedicationName(medicationName);
        }
        if (this.dosageStrength != null) {
            builder.setDosageStrength(dosageStrength);
        }
        if (this.dosageForm != null) {
            builder.setDosageForm(dosageForm);
        }
        if (this.quantity != null) {
            builder.setQuantity(quantity);
        }
        if (this.instructions != null) {
            builder.setInstructions(instructions);
        }
        if (this.administrationRoute != null) {
            builder.setRouteOfAdministration(administrationRoute);
        }
        if (this.productNdc != null) {
            builder.setProductNdc(productNdc);
        }
        if (this.id != null) {
            builder.setId(id.toHexString());
        }
        if (this.patientId != null) {
            builder.setPatientId(patientId.toHexString());
        }
        if (this.nationalHealthId != null) {
            builder.setNationalHealthId(nationalHealthId);
        }

        if (otherAdministrationRoute != null) {
            builder.setOtherRouteOfAdministration(otherAdministrationRoute);
        }
        if (this.frequency != null) {
            builder.setFrequency(frequency);
        }
        if (otherFrequency != null) {
            builder.setOtherFrequency(otherFrequency);
        }
        if (otherDosageForm != null) {
            builder.setOtherDosageForm(otherDosageForm);
        }
        if (this.startDate != null) {
            builder.setStartDate(Timestamp.newBuilder()
                    .setSeconds(startDate.getEpochSecond())
                    .setNanos(startDate.getNano())
                    .build());
        }

        if (endDate != null) {
            builder.setEndDate(Timestamp.newBuilder()
                    .setSeconds(endDate.getEpochSecond())
                    .setNanos(endDate.getNano())
                    .build());
        }
        if (this.providerNumber != null) {
            builder.setProviderNumber(providerNumber);
        }
        if (this.pharmacyId != null) {
            builder.setPharmacyId(pharmacyId.toHexString());
        }
        builder.setIsPrescription(prescription);

        builder.setIsGeneric(generic);

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
