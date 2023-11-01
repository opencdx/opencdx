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

import cdx.opencdx.grpc.iam.*;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Hello World
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXIAMUserGrpcController extends IamUserServiceGrpc.IamUserServiceImplBase {

    private final OpenCDXIAMUserService openCDXIAMUserService;

    /**
     * Constructor using the HelloworldService
     * @param openCDXIAMUserService service to use for processing
     */
    @Autowired
    public OpenCDXIAMUserGrpcController(OpenCDXIAMUserService openCDXIAMUserService) {
        this.openCDXIAMUserService = openCDXIAMUserService;
    }

    @Override
    public void signUp(SignUpRequest request, StreamObserver<SignUpResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.signUp(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listIamUsers(ListIamUsersRequest request, StreamObserver<ListIamUsersResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.listIamUsers(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getIamUser(GetIamUserRequest request, StreamObserver<GetIamUserResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.getIamUser(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateIamUser(UpdateIamUserRequest request, StreamObserver<UpdateIamUserResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.updateIamUser(request));
        responseObserver.onCompleted();
    }

    @Override
    public void changePassword(ChangePasswordRequest request, StreamObserver<ChangePasswordResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.changePassword(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteIamUser(DeleteIamUserRequest request, StreamObserver<DeleteIamUserResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.deleteIamUser(request));
        responseObserver.onCompleted();
    }

    @Override
    public void userExists(UserExistsRequest request, StreamObserver<UserExistsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.userExists(request));
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        responseObserver.onNext(this.openCDXIAMUserService.login(request));
        responseObserver.onCompleted();
    }
}
