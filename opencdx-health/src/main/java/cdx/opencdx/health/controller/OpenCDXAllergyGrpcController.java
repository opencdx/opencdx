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
import cdx.opencdx.health.service.OpenCDXAllergyService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Known Allergy
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXAllergyGrpcController extends KnownAllergyServiceGrpc.KnownAllergyServiceImplBase {
    private final OpenCDXAllergyService openCDXAllergyService;

    /**
     * Constructor using the OpenCDXAllergyService
     * @param openCDXAllergyService service to use for processing
     */
    @Autowired
    public OpenCDXAllergyGrpcController(OpenCDXAllergyService openCDXAllergyService) {
        this.openCDXAllergyService = openCDXAllergyService;
    }

    @Override
    public void createAllergy(CreateAllergyRequest request, StreamObserver<CreateAllergyResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAllergyService.createAllergy(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllergy(GetAllergyRequest request, StreamObserver<GetAllergyResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAllergyService.getAllergy(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateAllergy(UpdateAllergyRequest request, StreamObserver<UpdateAllergyResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAllergyService.updateAllergy(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllergy(DeleteAllergyRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAllergyService.deleteAllergy(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listAllergies(ListAllergyRequest request, StreamObserver<ListAllergyResponse> responseObserver) {
        responseObserver.onNext(this.openCDXAllergyService.listAllergies(request));
        responseObserver.onCompleted();
    }
}
