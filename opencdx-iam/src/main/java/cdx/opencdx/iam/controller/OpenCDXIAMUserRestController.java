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
package cdx.opencdx.iam.controller;

import cdx.media.v2alpha.*;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /iam/user  api's
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/iam/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXIAMUserRestController {

    private final OpenCDXIAMUserService openCDXIAMUserService;

    /**
     * Constructor that takes a OpenCDXIAMUserService
     * @param openCDXIAMUserService service for processing requests.
     */
    @Autowired
    public OpenCDXIAMUserRestController(OpenCDXIAMUserService openCDXIAMUserService) {
        this.openCDXIAMUserService = openCDXIAMUserService;
    }

    /**
     * Gets the requested user.
     * @param id ID of the user to retrieve
     * @return Response with the user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetIamUserResponse> getIamUser(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMUserService.getIamUser(
                        GetIamUserRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to delete a user.
     * @param id Id of the user to delete.
     * @return Response with the deleted user.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteIamUserResponse> deleteIamUser(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMUserService.deleteIamUser(
                        DeleteIamUserRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to update information on a user.
     * @param request The updated information for a user.
     * @return The updated user.
     */
    @PutMapping()
    public ResponseEntity<UpdateIamUserResponse> updateIamUser(@RequestBody UpdateIamUserRequest request) {
        return new ResponseEntity<>(this.openCDXIAMUserService.updateIamUser(request), HttpStatus.OK);
    }

    /**
     * Method to signup a new user.
     * @param request New user information for signup
     * @return New user record.
     */
    @PostMapping()
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(this.openCDXIAMUserService.signUp(request), HttpStatus.OK);
    }

    /**
     * List of users
     * @param request The pagination of uses to return.
     * @return The users for the page of users.
     */
    @PostMapping("/list")
    public ResponseEntity<ListIamUsersResponse> listIamUsers(@RequestBody ListIamUsersRequest request) {
        return new ResponseEntity<>(this.openCDXIAMUserService.listIamUsers(request), HttpStatus.OK);
    }

    /**
     * Method to change a user's password
     * @param request Password change information
     * @return Updated user record.
     */
    @PostMapping("/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(this.openCDXIAMUserService.changePassword(request), HttpStatus.OK);
    }

    /**
     * Method to check if a user exists
     * @param request Request with the user to check if exists
     * @return The user record if found.
     */
    @PostMapping("/exists")
    public ResponseEntity<UserExistsResponse> userExists(@RequestBody UserExistsRequest request) {
        return new ResponseEntity<>(this.openCDXIAMUserService.userExists(request), HttpStatus.OK);
    }
}