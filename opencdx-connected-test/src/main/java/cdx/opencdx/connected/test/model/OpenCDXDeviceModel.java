/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package cdx.opencdx.connected.test.model;

import java.time.Instant;
import java.util.List;

import cdx.opencdx.grpc.inventory.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("devices")
public class OpenCDXDeviceModel {
    @Id
    private ObjectId id;

    private String type;
    private String model;
    private String manufacturerId;
    private String vendorId;
    private String manufacturerCountryId;
    private String vendorCountryId;
    private Instant manufacturerDate;
    private Instant expiryDate;
    private String batchNumber;
    private String serialNumber;
    private String testTypeId;
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
    private List<String> testCaseIds;

    public OpenCDXDeviceModel(Device device) {
        if(device.hasId()) {
            this.id = new ObjectId(device.getId());
        }
        this.type = device.getType();
        this.model = device.getModel();
        this.manufacturerId = device.getManufacturerId();
        this.manufacturerCountryId = device.getManufacturerCountryId();
        this.vendorId = device.getVendorId();
        this.vendorCountryId = device.getVendorCountryId();
        if(device.hasManufactureDate()) {
            this.manufacturerDate = Instant.ofEpochSecond(
                    device.getManufactureDate().getSeconds(), device.getManufactureDate().getNanos());
        }
        if(device.hasExpiryDate()) {
            this.expiryDate = Instant.ofEpochSecond(device.getExpiryDate().getSeconds(), device.getExpiryDate().getNanos());
        }
        this.batchNumber = device.getBatchNumber();
        this.serialNumber = device.getSerialNumber();
        this.testTypeId = device.getTestTypeId();
        this.testSensitivity = device.getTestSensitivity();
        this.testSpecificity = device.getTestSpecificity();
        this.storageRequirements = device.getStorageRequirements();
        if(device.hasTestValidationDate()) {
            this.testValidationDate = Instant.ofEpochSecond(device.getTestValidationDate().getSeconds(), device.getTestValidationDate().getNanos());
        }
        this.approvalStatus = device.getApprovalStatus();
        this.url = device.getUrl();
        this.notes = device.getNotes();
        this.safety = device.getSafety();
        this.userInstructions = device.getUserInstructions();
    }
}
