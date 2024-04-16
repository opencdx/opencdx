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
package cdx.opencdx.logistics.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Device;
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
 * Entity for Device Protobuf message.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("devices")
public class OpenCDXDeviceModel {
    @Id
    private OpenCDXIdentifier id;

    private String type;
    private String model;
    private OpenCDXIdentifier manufacturerId;
    private OpenCDXIdentifier vendorId;
    private OpenCDXIdentifier manufacturerCountryId;
    private OpenCDXIdentifier vendorCountryId;
    private Instant manufacturerDate;
    private Instant expiryDate;
    private String batchNumber;
    private String serialNumber;
    private String testTypeId;
    private String lidrId;
    private Double testSensitivity;
    private Double testSpecificity;
    private String storageRequirements;
    private Instant testValidationDate;
    private String approvalStatus;
    private String url;
    private String notes;
    private String safety;
    private String userInstructions;
    private String limitations;
    private String warrantyInfo;
    private Integer intendedUseAge;
    private Boolean fdaAuthorized;
    private String deviceStatus;
    private String associatedSoftwareVersion;
    private List<OpenCDXIdentifier> testCaseIds;
    private String name;
    private String shortDescription;
    private String description;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Create Device entity from this protobuf message
     * @param device Protobuf message to use for creation.
     */
    public OpenCDXDeviceModel(Device device) {
        if (device.hasId()) {
            this.id = new OpenCDXIdentifier(device.getId());
        }
        this.type = device.getType();
        this.model = device.getModel();
        this.manufacturerId = new OpenCDXIdentifier(device.getManufacturerId());
        this.manufacturerCountryId = new OpenCDXIdentifier(device.getManufacturerCountryId());
        this.vendorId = new OpenCDXIdentifier(device.getVendorId());
        this.vendorCountryId = new OpenCDXIdentifier(device.getVendorCountryId());
        this.lidrId = device.getLidrId();
        if (device.hasManufactureDate()) {
            this.manufacturerDate = Instant.ofEpochSecond(
                    device.getManufactureDate().getSeconds(),
                    device.getManufactureDate().getNanos());
        }
        if (device.hasExpiryDate()) {
            this.expiryDate = Instant.ofEpochSecond(
                    device.getExpiryDate().getSeconds(), device.getExpiryDate().getNanos());
        }
        this.batchNumber = device.getBatchNumber();
        this.serialNumber = device.getSerialNumber();
        this.testTypeId = device.getTestTypeId();
        this.testSensitivity = device.getTestSensitivity();
        this.testSpecificity = device.getTestSpecificity();
        this.storageRequirements = device.getStorageRequirements();
        if (device.hasTestValidationDate()) {
            this.testValidationDate = Instant.ofEpochSecond(
                    device.getTestValidationDate().getSeconds(),
                    device.getTestValidationDate().getNanos());
        }
        this.approvalStatus = device.getApprovalStatus();
        this.url = device.getUrl();
        this.notes = device.getNotes();
        this.safety = device.getSafety();
        this.userInstructions = device.getUserInstructions();
        this.limitations = device.getLimitations();
        this.warrantyInfo = device.getWarrantyInfo();
        this.intendedUseAge = device.getIntendedUseAge();
        this.fdaAuthorized = device.getIsFdaAuthorized();
        this.deviceStatus = device.getDeviceStatus();
        this.associatedSoftwareVersion = device.getAssociatedSoftwareVersion();
        this.testCaseIds =
                device.getTestCaseIdsList().stream().map(OpenCDXIdentifier::new).toList();
        this.name = device.getName();
        this.shortDescription = device.getShortDescription();
        this.description = device.getDescription();

        if (device.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    device.getCreated().getSeconds(), device.getCreated().getNanos());
        }
        if (device.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    device.getModified().getSeconds(), device.getModified().getNanos());
        }
        if (device.hasCreator()) {
            this.creator = new OpenCDXIdentifier(device.getCreator());
        }
        if (device.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(device.getModifier());
        }
    }

    /**
     * Method to get Protobuf Message
     * @return Device protobuf message
     */
    @SuppressWarnings("java:S3776")
    public Device getProtobufMessage() {
        Device.Builder builder = Device.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }

        if (this.manufacturerDate != null) {
            builder.setManufactureDate(Timestamp.newBuilder()
                    .setSeconds(this.manufacturerDate.getEpochSecond())
                    .setNanos(this.manufacturerDate.getNano())
                    .build());
        }
        if (this.expiryDate != null) {
            builder.setExpiryDate(Timestamp.newBuilder()
                    .setSeconds(this.expiryDate.getEpochSecond())
                    .setNanos(this.expiryDate.getNano())
                    .build());
        }
        if (this.testValidationDate != null) {
            builder.setTestValidationDate(Timestamp.newBuilder()
                    .setSeconds(this.testValidationDate.getEpochSecond())
                    .setNanos(this.testValidationDate.getNano())
                    .build());
        }
        if (this.lidrId != null) {
            builder.setLidrId(this.lidrId);
        }
        if (this.type != null) {
            builder.setType(this.type);
        }
        if (this.model != null) {
            builder.setModel(this.model);
        }
        if (this.manufacturerId != null) {
            builder.setManufacturerId(this.manufacturerId.toHexString());
        }
        if (this.manufacturerCountryId != null) {
            builder.setManufacturerCountryId(this.manufacturerCountryId.toHexString());
        }
        if (this.vendorId != null) {
            builder.setVendorId(this.vendorId.toHexString());
        }
        if (this.vendorCountryId != null) {
            builder.setVendorCountryId(this.vendorCountryId.toHexString());
        }
        if (this.batchNumber != null) {
            builder.setBatchNumber(this.batchNumber);
        }
        if (this.serialNumber != null) {
            builder.setSerialNumber(this.serialNumber);
        }
        if (this.testTypeId != null) {
            builder.setTestTypeId(this.testTypeId);
        }
        if (this.testSensitivity != null) {
            builder.setTestSensitivity(this.testSensitivity);
        }
        if (this.testSpecificity != null) {
            builder.setTestSpecificity(this.testSpecificity);
        }
        if (this.storageRequirements != null) {
            builder.setStorageRequirements(this.storageRequirements);
        }
        if (this.approvalStatus != null) {
            builder.setApprovalStatus(this.approvalStatus);
        }
        if (this.url != null) {
            builder.setUrl(this.url);
        }
        if (this.notes != null) {
            builder.setNotes(this.notes);
        }
        if (this.safety != null) {
            builder.setSafety(this.safety);
        }
        if (this.userInstructions != null) {
            builder.setUserInstructions(this.userInstructions);
        }
        if (this.limitations != null) {
            builder.setLimitations(this.limitations);
        }
        if (this.warrantyInfo != null) {
            builder.setWarrantyInfo(this.warrantyInfo);
        }
        if (this.intendedUseAge != null) {
            builder.setIntendedUseAge(this.intendedUseAge);
        }
        if (this.fdaAuthorized != null) {
            builder.setIsFdaAuthorized(this.fdaAuthorized);
        }
        if (this.deviceStatus != null) {
            builder.setDeviceStatus(this.deviceStatus);
        }
        if (this.associatedSoftwareVersion != null) {
            builder.setAssociatedSoftwareVersion(this.associatedSoftwareVersion);
        }
        if (this.testCaseIds != null && !this.testCaseIds.isEmpty()) {
            builder.addAllTestCaseIds(this.testCaseIds.stream()
                    .map(OpenCDXIdentifier::toHexString)
                    .toList());
        }
        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.shortDescription != null) {
            builder.setShortDescription(this.shortDescription);
        }
        if (this.description != null) {
            builder.setDescription(this.description);
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
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }

        return builder.build();
    }
}
