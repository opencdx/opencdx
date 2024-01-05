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
package cdx.opencdx.iam.service;

import cdx.opencdx.grpc.profile.*;

/**
 * Interface for OpenCDXIAMProfileService
 */
public interface OpenCDXIAMProfileService {
    /**
     * Method to get a user Profile
     * @param request Request for the User Profile to get.
     * @return Response with the User Profile
     */
    UserProfileResponse getUserProfile(UserProfileRequest request);

    /**
     * Method to update a user Profile
     * @param request Request containing the updated profile.
     * @return Response with updated profile.
     */
    UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request);

    /**
     * Method to delete a profile
     * @param request Request with the profile to delete.
     * @return Response indicating if successful.
     */
    DeleteUserProfileResponse deleteUserProfile(DeleteUserProfileRequest request);
}
