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
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC Controller for Doctor Notes
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXDoctorNotesGrpcController extends DoctorNotesServiceGrpc.DoctorNotesServiceImplBase {
    private final OpenCDXDoctorNotesService openCDXDoctorNotesService;

    /**
     * Constructor using the OpenCDXDoctorNotesService
     * @param openCDXDoctorNotesService service to use for processing
     */
    @Autowired
    public OpenCDXDoctorNotesGrpcController(OpenCDXDoctorNotesService openCDXDoctorNotesService) {
        this.openCDXDoctorNotesService = openCDXDoctorNotesService;
    }

    @Override
    public void createDoctorNotes(
            CreateDoctorNotesRequest request, StreamObserver<CreateDoctorNotesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDoctorNotesService.createDoctorNotes(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getDoctorNotes(GetDoctorNotesRequest request, StreamObserver<GetDoctorNotesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDoctorNotesService.getDoctorNotes(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateDoctorNotes(
            UpdateDoctorNotesRequest request, StreamObserver<UpdateDoctorNotesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDoctorNotesService.updateDoctorNotes(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteDoctorNotes(DeleteDoctorNotesRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDoctorNotesService.deleteDoctorNotes(request));
        responseObserver.onCompleted();
    }

    @Override
    public void listAllByPatientId(
            ListDoctorNotesRequest request, StreamObserver<ListDoctorNotesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDoctorNotesService.listAllByPatientId(request));
        responseObserver.onCompleted();
    }
}
