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
package cdx.opencdx.health.controller;

import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXIAMProfileService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /iam/profile API's
 */
@Slf4j
@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXIAMProfileRestController {

    private final OpenCDXIAMProfileService openCDXIAMProfileService;

    /**
     * Constructor that takes a OpenCDXIAMProfileService
     * @param openCDXIAMProfileService service for procesing requests
     */
    public OpenCDXIAMProfileRestController(OpenCDXIAMProfileService openCDXIAMProfileService) {
        this.openCDXIAMProfileService = openCDXIAMProfileService;
    }

    /**
     * Gets the requested profile.
     * @param id ID of the user to retrieve
     * @return Response with the user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMProfileService.getUserProfile(
                        UserProfileRequest.newBuilder().setPatientId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Gets the requested profile.
     * @param id ID of the user to retrieve
     * @return Response with the user.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUserId(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMProfileService.getUserProfile(
                        UserProfileRequest.newBuilder().setUserId(id).build()),
                HttpStatus.OK);
    }
    /**
     * Method to update information on a profile.
     * @param request The updated information for a user.
     * @return The updated user.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@RequestBody UpdateUserProfileRequest request) {
        return new ResponseEntity<>(this.openCDXIAMProfileService.updateUserProfile(request), HttpStatus.OK);
    }

    /**
     * Method to create user profile.
     * @param request The user profile for a user.
     * @return The created user.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserProfileResponse> createUserProfile(@RequestBody CreateUserProfileRequest request) {
        return new ResponseEntity<>(this.openCDXIAMProfileService.createUserProfile(request), HttpStatus.OK);
    }

    /**
     * Method to delete a user.
     * @param id Id of the user to delete.
     * @return Response with the deleted user.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserProfileResponse> deleteUserProfile(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMProfileService.deleteUserProfile(
                        DeleteUserProfileRequest.newBuilder().setUserId(id).build()),
                HttpStatus.OK);
    }
}
