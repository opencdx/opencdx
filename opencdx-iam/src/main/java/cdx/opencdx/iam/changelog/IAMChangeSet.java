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
package cdx.opencdx.iam.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.types.IamUserStatus;
import cdx.opencdx.grpc.types.IamUserType;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import java.util.List;

/**
 * Change sets to MongoDB for IAM
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class IAMChangeSet {

    private static final String SCRAMBLED_PASSWORD =
            "{bcrypt}$2a$10$FLbaCLMQvIW8u5ceN4BStujPq8dbXeyeIiazOPatFUwcaopXNqlAa";
    private static final String SYSTEM = "SYSTEM";

    /**
     * Default Constructor
     */
    public IAMChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Setup Communications System  for OpenCDX
     * @param openCDXIAMUserRepository User Repository for saving default user.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup Service Users", author = "Jeff Miller")
    public void setServiceUsers(
            OpenCDXIAMUserRepository openCDXIAMUserRepository, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.allowBypassAuthentication(true);
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("iam")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-IAM")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXCurrentUser.allowBypassAuthentication(false);

        openCDXCurrentUser.configureAuthentication(SYSTEM);

        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("audit")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Audit")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("communications")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Communications")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("health")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Health")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("helloworld")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Helloworld")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("media")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Media")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("protector")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Protector")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("routine")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Routine")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("tinkar")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Tinkar")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("classification")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Classification")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("predictor")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Predictor")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("questionnaire")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Questionnaire")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("connected-lab")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Connected-Lab")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .username("logistics")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .systemName("OpenCDX-Logistics")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .password(SCRAMBLED_PASSWORD)
                .emailVerified(true)
                .build());
    }

    /**
     * Setup Index on email for users.
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "002", id = "Setup Users Email Index", author = "Gaurav Mishra")
    public void setupIndexes(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate
                .getCollection("users")
                .createIndex(Indexes.ascending(List.of("username")), new IndexOptions().unique(true));
    }

    /**
     * Setup Default User for OpenCDX
     * @param openCDXIAMUserRepository User Repository for saving default user.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "003", id = "Setup Default User", author = "Jeff Miller")
    public void setupDefaultUser(
            OpenCDXIAMUserRepository openCDXIAMUserRepository, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        openCDXIAMUserRepository.save(OpenCDXIAMUserModel.builder()
                .id(new OpenCDXIdentifier("5f63a53ddcc67c7a1c3d93e8"))
                .username("admin@opencdx.org")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(IamUserType.IAM_USER_TYPE_REGULAR)
                .password("{bcrypt}$2a$10$nHiiVDdMtK3I/gRZYybaOO9dm2KBJ.y2sYmA2IttTB/BvgjnMrQiG")
                .emailVerified(true)
                .build());
    }

    /**
     * Create an index based on the system name
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "004", id = "Setup Users System Index", author = "Jeff Miller")
    public void setupSystemIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
        mongockTemplate
                .getCollection("users")
                .createIndex(Indexes.ascending(List.of("systemName")), new IndexOptions().unique(true));
    }
}
