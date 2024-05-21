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
package cdx.opencdx.commons.dto;

import cdx.opencdx.grpc.types.IamUserStatus;
import cdx.opencdx.grpc.types.IamUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IamUser {

    /**
     * The unique ID of the user.
     */
    private String id;

    /**
     * The name of the system for System Users.
     */
    private String systemName;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * Indicates whether the user's email has been verified.
     */
    private boolean emailVerified;

    /**
     * The current status of the user.
     */
    private IamUserStatus status;

    /**
     * The type of the user.
     */
    private IamUserType type;

    /**
     * The timestamp when the user was created.
     */
    private String created;

    /**
     * The timestamp when the user was last modified.
     */
    private String modified;

    /**
     * The ID of the user who created this user.
     */
    private String creator;

    /**
     * The ID of the user who last modified this user.
     */
    private String modifier;
}
