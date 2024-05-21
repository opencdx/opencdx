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
import cdx.opencdx.commons.dto.IamUser;
import cdx.opencdx.commons.dto.SignUpRequest;
import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.IamUserStatus;
import cdx.opencdx.grpc.types.IamUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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
    @Version
    private long version;

    @Id
    private OpenCDXIdentifier id;

    private String username;
    private String systemName;

    @Builder.Default
    private Boolean emailVerified = false;

    @Builder.Default
    private IamUserStatus status = IamUserStatus.IAM_USER_STATUS_ACTIVE;

    private IamUserType type;
    private String password;

    @Builder.Default
    private boolean accountExpired = false;

    @Builder.Default
    private boolean credentialsExpired = false;

    @Builder.Default
    private boolean accountLocked = false;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
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
        if (iamUser.getId() != null) {
            this.id = new OpenCDXIdentifier(iamUser.getId());
        }
        this.systemName = iamUser.getSystemName();

        this.username = iamUser.getUsername();
        this.emailVerified = iamUser.isEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
    }

    /**
     * Method to return a gRPC IamUser Message
     * @return gRPC IamUser Message
     */
    public IamUser getIamUserProtobufMessage() {
        log.trace("Creating IAM User from user");
        IamUser.IamUserBuilder builder = IamUser.builder();

        if (this.id != null) {
            builder.id(this.id.toHexString());
        }

        if (this.username != null) {
            builder.username(this.username);
        }
        if (this.systemName != null) {
            builder.systemName(this.systemName);
        }
        if (this.emailVerified != null) {
            builder.emailVerified(this.emailVerified);
        }
        if (this.status != null) {
            builder.status(this.status);
        }
        if (this.type != null) {
            builder.type(this.type);
        }
        if (this.created != null) {
            builder.created(this.created.toString());
        }
        if (this.modified != null) {
            builder.modified(this.modified.toString());
        }
        if (this.creator != null) {
            builder.creator(this.creator.toHexString());
        }
        if (this.modified != null) {
            builder.modifier(this.modifier.toHexString());
        }
        return builder.build();
    }

    /**
     * Updates the fields of the OpenCDXIAMUserModel object with the values from the given IamUser object.
     *
     * @param iamUser The IamUser object containing the updated field values.
     * @return The updated OpenCDXIAMUserModel object.
     */
    public OpenCDXIAMUserModel update(IamUser iamUser) {
        this.systemName = iamUser.getSystemName();

        this.username = iamUser.getUsername();
        this.emailVerified = iamUser.isEmailVerified();
        this.status = iamUser.getStatus();
        this.type = iamUser.getType();
        return this;
    }
}
