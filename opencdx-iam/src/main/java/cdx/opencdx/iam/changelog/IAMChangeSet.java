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
package cdx.opencdx.iam.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.model.Indexes;
import java.util.List;

/**
 * Change sets to MongoDB for IAM
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class IAMChangeSet {
    /**
     * Default Constructor
     */
    public IAMChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Setup Index on email for users.
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     */
    @ChangeSet(order = "001", id = "Setup Users Email Index", author = "Gaurav Mishra")
    public void setupIndexes(MongockTemplate mongockTemplate) {
        mongockTemplate.getCollection("users").createIndex(Indexes.ascending(List.of("email")));
    }
}
