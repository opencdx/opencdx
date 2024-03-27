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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.health.profile.*;

/**
 * Interface for communicating with the IAM Organization microservice.
 */
public interface OpenCDXIAMProfileClient {
    /**
     * Method to gRPC Call IAM Profile Service getUserProfile() api.
     * @param request UserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UserProfileResponse getUserProfile(UserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call IAM Profile Service updateUserProfile() api.
     * @param request UpdateUserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UpdateUserProfileResponse updateUserProfile(
            UpdateUserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call IAM Profile Service deleteUserProfile() api.
     * @param request DeleteUserProfileRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    DeleteUserProfileResponse deleteUserProfile(
            DeleteUserProfileRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
