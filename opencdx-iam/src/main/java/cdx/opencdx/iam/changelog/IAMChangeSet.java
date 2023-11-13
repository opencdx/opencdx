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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.iam.IamUserType;
import cdx.opencdx.grpc.profile.FullName;
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

    /**
     * Setup Default User for OpenCDX
     * @param openCDXIAMUserRepository User Repository for saving default user.
     */
    @ChangeSet(order = "002", id = "Setup Default User", author = "Jeff Miller")
    public void setupDefaultUser(OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("admin@opencdx.org")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .fullName(FullName.newBuilder()
                        .setLastName("Admin")
                        .setLastName("OpenCDX")
                        .build())
                .type(IamUserType.IAM_USER_TYPE_REGULAR)
                .password("{bcrypt}$2a$10$nHiiVDdMtK3I/gRZYybaOO9dm2KBJ.y2sYmA2IttTB/BvgjnMrQiG")
                .emailVerified(true)
                .build());
    }

    /**
     * Create an index based on the system name
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     */
    @ChangeSet(order = "003", id = "Setup Users System Index", author = "Jeff Miller")
    public void setupSystemIndex(MongockTemplate mongockTemplate) {
        mongockTemplate.getCollection("users").createIndex(Indexes.ascending(List.of("systemName")));
    }
}
