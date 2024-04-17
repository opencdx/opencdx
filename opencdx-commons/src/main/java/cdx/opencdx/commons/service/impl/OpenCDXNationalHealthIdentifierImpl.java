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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.types.IamUserType;
import io.micrometer.observation.annotation.Observed;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The OpenCDXNationalHealthIdentifierImpl class implements the OpenCDXNationalHealthIdentifier interface
 * to generate the ID from a user's email.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXNationalHealthIdentifierImpl implements OpenCDXNationalHealthIdentifier {

    /**
     * The default constructor is declared explicitly to prevent inadvertent instantiation of this class.
     */
    public OpenCDXNationalHealthIdentifierImpl() {
        log.info("OpenCDXNationalHealthIdentifierImpl instantiated");
    }

    /**
     * The generateNationalHealthId method overrides the interface method to generate the National Health ID
     * wrapped in a UUID and converts it to string.
     *
     * @param userModel The user for whom the National Health ID is to be generated.
     * @return Returns the generated National Health ID in string format.
     * @throws OpenCDXNotAcceptable Throws exception if the user type is not IAM_USER_TYPE_REGULAR.
     */
    @Override
    public String generateNationalHealthId(OpenCDXIAMUserModel userModel) {
        if (userModel.getType().equals(IamUserType.IAM_USER_TYPE_REGULAR)) {
            log.info("OpenCDXNationalHealthIdentifierImpl: Generating National Health ID");
            return UUID.nameUUIDFromBytes(userModel.getUsername().getBytes()).toString();
        }

        throw new OpenCDXNotAcceptable(
                "OpenCDXNationalHealthIdentifierImpl", 1, "AgentTypes of: " + userModel.getType());
    }
}
