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
package cdx.opencdx.commons.model;

import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.iam.IamUser;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.iam.IamUserType;
import cdx.opencdx.grpc.iam.SignUpRequest;
import cdx.opencdx.grpc.profile.FullName;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Record for IAM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("users")
public class OpenCDXIAMUserModel {
    @Id
    private ObjectId id;

    private Instant createdAt;
    private Instant updatedAt;
    private FullName fullName;
    private String username;
    private String systemName;

    @Builder.Default
    private Boolean emailVerified = false;

    private IamUserStatus status;
    private IamUserType type;
    private String password;
    private String nationalHealthId;

    @Builder.Default
    private boolean accountExpired = false;

    @Builder.Default
    private boolean credentialsExpired = false;

    @Builder.Default
    private boolean accountLocked = false;

    /**
     * Method to identify AgentType for Audit
     * @return AgentType corresponding to IamUser.
     */
    public AgentType getAgentType() {
        if (this.type == null) {
            return AgentType.AGENT_TYPE_UNSPECIFIED;
        }
        return switch (type) {
            case IAM_USER_TYPE_TRIAL, IAM_USER_TYPE_REGULAR -> AgentType.AGENT_TYPE_HUMAN_USER;
            case IAM_USER_TYPE_SYSTEM -> AgentType.AGENT_TYPE_SYSTEM;
            default -> AgentType.AGENT_TYPE_UNSPECIFIED;
        };
    }

    public OpenCDXIAMUserModel(SignUpRequest request) {

        this.fullName = FullName.newBuilder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .build();
        this.systemName = request.getSystemName();
        this.username = request.getUsername();
        this.status = IamUserStatus.IAM_USER_STATUS_ACTIVE;
        this.type = request.getType();
    }
    /**
     * Constructor to convert in an IamUser
     * @param iamUser IamUser to read in.
     */
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

        this.username = iamUser.getUsername();
        this.emailVerified = iamUser.getEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
    }

    /**
     * Method to return a gRPC IamUser Message
     * @return gRPC IamUser Message
     */
    public IamUser getIamUserProtobufMessage() {
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

        if (this.username != null) {
            builder.setUsername(this.username);
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
        return builder.build();
    }
}
