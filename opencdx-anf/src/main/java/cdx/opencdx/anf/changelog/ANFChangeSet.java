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
package cdx.opencdx.anf.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;

/**
 * Change set to update ANF.
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class ANFChangeSet {

    private static final String SYSTEM = "SYSTEM";

    /**
     * Create an index based on the user id
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup ANF Index", author = "Gaurav Mishra")
    public void setupANFIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
    }
}
