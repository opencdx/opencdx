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
package cdx.opencdx.iam.service;

import cdx.opencdx.grpc.iam.*;

/**
 * Interface for the OpenCDXIAMUserService
 */
public interface OpenCDXIAMUserService {

    /**
     * Method to sing up a new user
     * @param request SignUpRequest for new user.
     * @return SignUpResponse with the new user created.
     */
    public SignUpResponse signUp(SignUpRequest request);

    /**
     * Method to list users
     * @param request ListIamUserRequest for the users to list.
     * @return ListIamUsersResponse for the users being listed
     */
    public ListIamUsersResponse listIamUsers(ListIamUsersRequest request);

    /**
     * Method to get a particular User
     * @param request Request for the user to get.
     * @return Response with the requested user.
     */
    public GetIamUserResponse getIamUser(GetIamUserRequest request);

    /**
     * Update the User informaiton
     * @param request Request with information to update.
     * @return Response the updated user.
     */
    public UpdateIamUserResponse updateIamUser(UpdateIamUserRequest request);

    /**
     * Method to change a user password
     * @param request Request to change a user password
     * @return Response for changing a users password.
     */
    public ChangePasswordResponse changePassword(ChangePasswordRequest request);

    /**
     * Method to delete a user. User's status is udpated to DELETED. User it not actually removed.
     * @param request Request to delete the specified user.
     * @return Response for deleting a user.
     */
    public DeleteIamUserResponse deleteIamUser(DeleteIamUserRequest request);

    /**
     * Method to check if a user exists.
     * @param request Request to check if a user exists
     * @return Response if the user exists.
     */
    public UserExistsResponse userExists(UserExistsRequest request);

    /**
     * Method to authenticate user login.
     * @param request Request to authenticate user
     * @return Response if the user login is successful.
     */
    public LoginResponse login(LoginRequest request);

    /**
     * Method to fetch current user.
     *
     * @param request Request to fetch current user
     * @return Response is the current user.
     */
    public CurrentUserResponse currentUser(CurrentUserRequest request);
}
