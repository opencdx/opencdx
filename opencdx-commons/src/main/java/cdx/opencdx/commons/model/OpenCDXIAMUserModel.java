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
package cdx.opencdx.commons.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.iam.IamUser;
import cdx.opencdx.grpc.iam.IamUserStatus;
import cdx.opencdx.grpc.iam.IamUserType;
import cdx.opencdx.grpc.iam.SignUpRequest;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Record for IAM
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("users")
public class OpenCDXIAMUserModel {
    @Id
    private OpenCDXIdentifier id;

    private String username;
    private String systemName;

    @Builder.Default
    private Boolean emailVerified = false;

    private IamUserStatus status;
    private IamUserType type;
    private String password;

    @Builder.Default
    private boolean accountExpired = false;

    @Builder.Default
    private boolean credentialsExpired = false;

    @Builder.Default
    private boolean accountLocked = false;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;
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

    /**
     * Constructor from a SignUpRequest
     * @param request SingUpRequest to create from.
     */
    public OpenCDXIAMUserModel(SignUpRequest request) {
        log.trace("Creating user from sign up request");

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
        log.trace("Creating user from IAM User");
        if (iamUser.hasId()) {
            this.id = new OpenCDXIdentifier(iamUser.getId());
        }
        this.systemName = iamUser.getSystemName();

        this.username = iamUser.getUsername();
        this.emailVerified = iamUser.getEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
        if (iamUser.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    iamUser.getCreated().getSeconds(), iamUser.getCreated().getNanos());
        }
        if (iamUser.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    iamUser.getModified().getSeconds(), iamUser.getModified().getNanos());
        }
        if (iamUser.hasCreator()) {
            this.creator = new OpenCDXIdentifier(iamUser.getCreator());
        }
        if (iamUser.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(iamUser.getModifier());
        }
    }

    /**
     * Method to return a gRPC IamUser Message
     * @return gRPC IamUser Message
     */
    public IamUser getIamUserProtobufMessage() {
        log.trace("Creating IAM User from user");
        IamUser.Builder builder = IamUser.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
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
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }
}
