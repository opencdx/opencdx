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
package cdx.opencdx.commons.service;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;


/**
 * The OpenCDXNationalHealthIdentifier interface serves as a blueprint for creating National Health ID for users in the OpenCDX system.
 *
 * @implNote The current approach involves generation of a {@link java.util.UUID} based on a user's email and further conversion to a {@link String}.
 * @implSpec The creation of this interface is to facilitate the development of an agnostic mechanism to generate a National Health Identifier since no standard method exists currently.
 *           Being a string, this identifier can be easily shared. Nonetheless, it is important to bear in the mind the risk of collisions, which should be prevented through proper care.
 */
public interface OpenCDXNationalHealthIdentifier {
    /**
     * Method to generate the National Health ID for a given user.
     *
     * @param userModel The user for whom the National Health ID is to be generated.
     * @return Returns a String containing the generated National Health ID.
     */
    String generateNationalHealthId(OpenCDXIAMUserModel userModel);
}
