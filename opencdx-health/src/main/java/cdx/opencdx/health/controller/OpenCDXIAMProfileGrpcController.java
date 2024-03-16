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

import cdx.opencdx.grpc.profile.*;
import cdx.opencdx.health.service.OpenCDXIAMProfileService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller IAM Profile service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXIAMProfileGrpcController extends UserProfileServiceGrpc.UserProfileServiceImplBase {

    private final OpenCDXIAMProfileService openCDXIAMProfileService;

    /**
     * Constructor for the gRPC Profile Controller
     * @param openCDXIAMProfileService Reference to Profile service
     */
    public OpenCDXIAMProfileGrpcController(OpenCDXIAMProfileService openCDXIAMProfileService) {
        this.openCDXIAMProfileService = openCDXIAMProfileService;
    }

    @Override
    public void getUserProfile(UserProfileRequest request, StreamObserver<UserProfileResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProfileService.getUserProfile(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateUserProfile(
            UpdateUserProfileRequest request, StreamObserver<UpdateUserProfileResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProfileService.updateUserProfile(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createUserProfile(
            CreateUserProfileRequest request, StreamObserver<CreateUserProfileResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProfileService.createUserProfile(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUserProfile(
            DeleteUserProfileRequest request, StreamObserver<DeleteUserProfileResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMProfileService.deleteUserProfile(request));
        responseObserver.onCompleted();
    }
}
