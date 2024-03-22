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
package cdx.opencdx.logistics.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.model.Indexes;
import java.util.List;

/**
 * Change sets to MongoDB for Connected Test
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class LogisticsChangeSet {

    private static final String USER_ID = "userId";
    private static final String DEVICES = "devices";
    private static final String TESTCASES = "testcases";
    private static final String SYSTEM = "SYSTEM";

    /**
     * Default Constructor
     */
    public LogisticsChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Create an index based on the user id
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup Logistics4 Index", author = "Gaurav Mishra")
    public void setupConnectedTestIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of(USER_ID)));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("manufacturerCountryId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("vendorCountryId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("vendorId")));
        mongockTemplate.getCollection(DEVICES).createIndex(Indexes.ascending(List.of("manufacturerId")));
        mongockTemplate.getCollection("manufacturer").createIndex(Indexes.ascending(List.of("countryId")));
        mongockTemplate.getCollection(TESTCASES).createIndex(Indexes.ascending(List.of("manufacturerId")));
        mongockTemplate.getCollection(TESTCASES).createIndex(Indexes.ascending(List.of("vendorId")));
        mongockTemplate.getCollection("vendor").createIndex(Indexes.ascending(List.of("countryId")));
        mongockTemplate.getCollection("shipping").createIndex(Indexes.ascending(List.of("trackingNumber")));
        mongockTemplate.getCollection("order").createIndex(Indexes.ascending(List.of("patientId")));
    }
}
