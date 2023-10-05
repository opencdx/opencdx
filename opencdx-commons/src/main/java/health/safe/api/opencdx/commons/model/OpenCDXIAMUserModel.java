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
package health.safe.api.opencdx.commons.model;

import cdx.media.v2alpha.IamUser;
import cdx.media.v2alpha.IamUserStatus;
import cdx.media.v2alpha.IamUserType;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Record for IAM
 */
@Data
@AllArgsConstructor
@Builder
@Document("users")
public class OpenCDXIAMUserModel {
    @Id
    private ObjectId id;

    private Instant createdAt;
    private Instant updatedAt;
    private String firstName;
    private String lastName;
    private String email;
    private String systemName;
    private Boolean emailVerified;
    private IamUserStatus status;
    private IamUserType type;
    private String phone;

    public OpenCDXIAMUserModel(IamUser iamUser) {
        if (iamUser.hasId()) {
            this.id = new ObjectId(iamUser.getId());
        }
        this.systemName = iamUser.getSystemName();
        if (iamUser.hasCreatedAt()) {
            this.createdAt = Instant.ofEpochSecond(
                    iamUser.getCreatedAt().getSeconds(), iamUser.getCreatedAt().getNanos());
        } else {
            this.createdAt = Instant.now();
        }
        if (iamUser.hasUpdatedAt()) {
            this.updatedAt = Instant.ofEpochSecond(
                    iamUser.getUpdatedAt().getSeconds(), iamUser.getUpdatedAt().getNanos());
        } else {
            this.updatedAt = Instant.now();
        }

        this.firstName = iamUser.getFirstName();
        this.lastName = iamUser.getLastName();
        this.email = iamUser.getEmail();
        this.emailVerified = iamUser.getEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
        this.phone = iamUser.getPhone();
    }

    public IamUser getProtobufMessage() {
        IamUser.Builder builder = IamUser.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }
        if (this.createdAt != null) {
            builder.setCreatedAt(Timestamp.newBuilder()
                    .setSeconds(this.createdAt.getEpochSecond())
                    .setNanos(this.createdAt.getNano())
                    .build());
        }
        if (this.updatedAt != null) {
            builder.setUpdatedAt(Timestamp.newBuilder()
                    .setSeconds(this.updatedAt.getEpochSecond())
                    .setNanos(this.updatedAt.getNano())
                    .build());
        }

        if (this.firstName != null) {
            builder.setFirstName(this.firstName);
        }
        if (this.lastName != null) {
            builder.setLastName(this.lastName);
        }
        if (this.email != null) {
            builder.setEmail(this.email);
        }
        if (this.systemName != null) {
            builder.setSystemName(this.systemName);
        }
        if (this.emailVerified != null) {
            builder.setEmailVerified(this.emailVerified);
        }
        if (this.status != null) {
            builder.setStatus(this.status);
        }
        if (this.type != null) {
            builder.setType(this.type);
        }
        if (this.phone != null) {
            builder.setPhone(this.phone);
        }
        return builder.build();
    }
}
