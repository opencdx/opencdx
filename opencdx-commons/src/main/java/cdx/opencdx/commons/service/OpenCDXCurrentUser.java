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
package cdx.opencdx.commons.service;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.grpc.audit.AgentType;

/**
 * Service to get information on the current user
 */
public interface OpenCDXCurrentUser {
    /**
     * Method to get the current authenticated user.
     * @return Model for hte current user.
     */
    OpenCDXIAMUserModel getCurrentUser();

    /**
     * Metho to get the current authenticated user, or return the default user.  User
     * primarly for non-authenticated calls.
     * @param defaultUser Default user to reutrn if no currently authenticated users.
     * @return Model for the current user.
     */
    OpenCDXIAMUserModel getCurrentUser(OpenCDXIAMUserModel defaultUser);

    /**
     * Method to get current user type
     * @return AgentType for the current user.
     */
    AgentType getCurrentUserType();
}