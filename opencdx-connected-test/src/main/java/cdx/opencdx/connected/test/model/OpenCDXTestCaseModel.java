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

import cdx.opencdx.grpc.inventory.TestCase;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("testcases")
public class OpenCDXTestCaseModel {
    @Id
    private ObjectId id;

    private ObjectId manufacturerId;
    private ObjectId vendorId;
    private List<ObjectId> deviceIds;
    private Integer nunberOftests;
    private Instant packagingDate;
    private Instant expiryDate;
    private String batchNumber;
    private String serialNumber;
    private String storageRequirements;
    private String safety;
    private String userInstructions;
    private String limitations;

    public OpenCDXTestCaseModel(TestCase testCase) {
        if (testCase.hasId()) {
            this.id = new ObjectId(testCase.getId());
        }
        this.manufacturerId = new ObjectId(testCase.getManufacturerId());
        this.vendorId = new ObjectId(testCase.getVendorId());
        if (testCase.hasPackagingDate()) {
            this.packagingDate = Instant.ofEpochSecond(
                    testCase.getPackagingDate().getSeconds(),
                    testCase.getPackagingDate().getNanos());
        }
        if (testCase.hasExpiryDate()) {
            this.expiryDate = Instant.ofEpochSecond(
                    testCase.getExpiryDate().getSeconds(),
                    testCase.getExpiryDate().getNanos());
        }
        this.deviceIds = testCase.getDeviceIdsList().stream().map(ObjectId::new).toList();
        this.nunberOftests = testCase.getNumberOfTests();
        this.batchNumber = testCase.getBatchNumber();
        this.serialNumber = testCase.getSerialNumber();
        this.storageRequirements = testCase.getStorageRequirements();
        this.safety = testCase.getSafety();
        this.userInstructions = testCase.getUserInstructions();
        this.limitations = testCase.getLimitations();
    }

    public TestCase getProtobufMessage() {
        TestCase.Builder builder = TestCase.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }

        if (this.packagingDate != null) {
            builder.setPackagingDate(Timestamp.newBuilder()
                    .setSeconds(this.packagingDate.getEpochSecond())
                    .setNanos(this.packagingDate.getNano())
                    .build());
        }
        if (this.expiryDate != null) {
            builder.setExpiryDate(Timestamp.newBuilder()
                    .setSeconds(this.expiryDate.getEpochSecond())
                    .setNanos(this.expiryDate.getNano())
                    .build());
        }
        if (this.manufacturerId != null) {
            builder.setManufacturerId(this.manufacturerId.toHexString());
        }
        if (this.vendorId != null) {
            builder.setVendorId(this.vendorId.toHexString());
        }
        if (this.batchNumber != null) {
            builder.setBatchNumber(this.batchNumber);
        }
        if (this.serialNumber != null) {
            builder.setSerialNumber(this.serialNumber);
        }
        if (this.storageRequirements != null) {
            builder.setStorageRequirements(this.storageRequirements);
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
        if (this.nunberOftests != null) {
            builder.setNumberOfTests(this.nunberOftests);
        }
        if (this.deviceIds != null && !this.deviceIds.isEmpty()) {
            builder.addAllDeviceIds(
                    this.deviceIds.stream().map(ObjectId::toHexString).toList());
        }

        return builder.build();
    }
}
