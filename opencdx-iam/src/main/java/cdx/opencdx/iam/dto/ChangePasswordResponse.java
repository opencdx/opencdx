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
package cdx.opencdx.iam.dto;

import cdx.opencdx.commons.dto.IamUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Response to change password for a user.
 */
@Data
@AllArgsConstructor
@Builder
public class ChangePasswordResponse {
    private IamUser iamUser;

    /**
     * Default constructor.
     */
    public ChangePasswordResponse() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
