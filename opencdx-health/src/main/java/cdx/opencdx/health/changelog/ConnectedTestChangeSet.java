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
package cdx.opencdx.health.changelog;

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
public class ConnectedTestChangeSet {

    private static final String USER_ID = "userId";
    private static final String SYSTEM = "SYSTEM";

    /**
     * Default Constructor
     */
    public ConnectedTestChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Create an index based on the user id
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup Connected Test Index", author = "Gaurav Mishra")
    public void setupConnectedTestIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate.getCollection("connected-test").createIndex(Indexes.ascending(List.of(USER_ID)));
        mongockTemplate.getCollection("connected-test").createIndex(Indexes.ascending(List.of("nationalHealthId")));
        mongockTemplate.getCollection("medications").createIndex(Indexes.ascending(List.of("nationalHealthId")));
        mongockTemplate.getCollection("medications").createIndex(Indexes.ascending(List.of("patientId")));
        mongockTemplate.getCollection("provider").createIndex(Indexes.ascending(List.of("npiNumber")));
    }

    /**
     * Create an index based on the height
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "002", id = "Setup Height Index", author = "Gaurav Mishra")
    public void setupHeightIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate.getCollection("heights").createIndex(Indexes.ascending(List.of("patientId")));
    }
}
