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

/**
 * Service interface for generating the National Health ID for a
 * user.
 */
public interface OpenCDXNationalHealthIdentifier {

    /**
     * Generate the National Health ID
     * @param userModel User to generate the National Health ID for
     * @return String containing the National Health ID.
     */
    String generateNationalHealthId(OpenCDXIAMUserModel userModel);
}
