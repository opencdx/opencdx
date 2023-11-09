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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.iam.IamUserType;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Implementation of OpenCDXNationalHealthIdentifier ID generated from user email.
 */
@Service
public class OpenCDXNationalHealthIdentifierImpl implements OpenCDXNationalHealthIdentifier {
    /**
     * Default Constructor
     */
    public OpenCDXNationalHealthIdentifierImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public String generateNationalHealthId(OpenCDXIAMUserModel userModel) {
        if (userModel.getType().equals(IamUserType.IAM_USER_TYPE_REGULAR)) {
            return UUID.nameUUIDFromBytes(userModel.getEmail().getBytes()).toString();
        }

        throw new OpenCDXNotAcceptable(
                "OpenCDXNationalHealthIdentifierImpl",
                1,
                "AgentTypes of: " + userModel.getType().toString());
    }
}
