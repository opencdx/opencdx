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
package proto;

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class InventoryTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testAddress() throws JsonProcessingException {
        log.info(
                "Address: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(Address.newBuilder()
                                .setAddress1("1234 Main Street")
                                .setCity("Hometown")
                                .setPostalCode("12345")
                                .setState("Texas")
                                .setCountryId(ObjectId.get().toHexString())
                                .build()));
    }

    @Test
    void testCountry() throws JsonProcessingException {
        log.info(
                "Country: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(Country.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setName("USA")
                                .build()));
    }

    @Test
    void testManufacturer() throws JsonProcessingException {
        log.info(
                "Manufacturer: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(Manufacturer.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setName("Any Manufacturer")
                                .setManufacturerAddress(Address.newBuilder()
                                        .setAddress1("1234 Main Street")
                                        .setCity("Hometown")
                                        .setPostalCode("12345")
                                        .setState("Texas")
                                        .setCountryId(ObjectId.get().toHexString())
                                        .build())
                                .setManufacturerContact("Bob")
                                .setManufacturerEmail("Bob@anyManufacturer")
                                .setManufacturerPhone("123-456-7890")
                                .setManufacturerWebsite("https://anymanufactuerer.com")
                                .setManufacturerDescription("Description of any manufacturer")
                                .addAllManufacturerCertifications(List.of("Cert A", "Cert B", "Cert D"))
                                .build()));
    }

    @Test
    void testVendor() throws JsonProcessingException {
        log.info(
                "Vendor: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(Vendor.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setVendorName("Any Manufacturer")
                                .setVendorAddress(Address.newBuilder()
                                        .setAddress1("1234 Main Street")
                                        .setCity("Hometown")
                                        .setPostalCode("12345")
                                        .setState("Texas")
                                        .setCountryId(ObjectId.get().toHexString())
                                        .build())
                                .setVendorContact("Bob")
                                .setVendorEmail("Bob@anyManufacturer")
                                .setVendorPhone("123-456-7890")
                                .setVendorWebsite("https://anymanufactuerer.com")
                                .setVendorDescription("Description of any manufacturer")
                                .addAllVendorCertifications(List.of("Cert A", "Cert B", "Cert D"))
                                .build()));
    }

    @Test
    void testDevice() throws JsonProcessingException {
        log.info(
                "Device: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(Device.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setType("Medical")
                                .setModel("SR-71")
                                .setManufacturerId(ObjectId.get().toHexString())
                                .setManufacturerCountryId(ObjectId.get().toHexString())
                                .setVendorId(ObjectId.get().toHexString())
                                .setVendorCountryId(ObjectId.get().toHexString())
                                .setManufactureDate(this.getTimeStamp(Instant.now()))
                                .setExpiryDate(this.getTimeStamp(Instant.now()))
                                .setBatchNumber("10")
                                .setSerialNumber(UUID.randomUUID().toString())
                                .setTestTypeId("TEST-ID-001")
                                .setTestSensitivity(10.0)
                                .setTestSpecificity(9.0)
                                .setStorageRequirements("Cool Dry Place")
                                .setTestValidationDate(this.getTimeStamp(Instant.now()))
                                .setApprovalStatus("FDA Approved")
                                .setUrl("https://anymanufacturerer/sr-71")
                                .setNotes("These are the notes on this device.")
                                .setSafety("Safety measures wear a seat belt.")
                                .setUserInstructions("Learn to pilot the fastest plane in the world first.")
                                .setLimitations("Mach 3.2")
                                .setWarrantyInfo("10 minute warranty")
                                .setIntendedUseAge(10)
                                .setIsFdaAuthorized(true)
                                .setDeviceStatus("Super Cool!!!!!")
                                .setAssociatedSoftwareVersion("1.0.0.1")
                                .addAllTestCaseIds(List.of("1", "2", "3"))
                                .build()));
    }

    @Test
    void testCase() throws JsonProcessingException {
        log.info(
                "TestCase: \n{}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(TestCase.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setManufacturerId(ObjectId.get().toHexString())
                                .setVendorId(ObjectId.get().toHexString())
                                .addAllDeviceIds(List.of(
                                        ObjectId.get().toHexString(),
                                        ObjectId.get().toHexString()))
                                .setNumberOfTests(8)
                                .setPackagingDate(this.getTimeStamp(Instant.now()))
                                .setExpiryDate(this.getTimeStamp(Instant.now()))
                                .setBatchNumber("10")
                                .setSerialNumber(UUID.randomUUID().toString())
                                .setStorageRequirements("Cool Dry Place")
                                .setUserInstructions("Swallow with liquid")
                                .setLimitations("Mach 1.0")
                                .setSafety("Safety Glasses")
                                .build()));
    }

    private Timestamp getTimeStamp(Instant time) {
        return Timestamp.newBuilder()
                .setSeconds(time.getEpochSecond())
                .setNanos(time.getNano())
                .build();
    }
}
