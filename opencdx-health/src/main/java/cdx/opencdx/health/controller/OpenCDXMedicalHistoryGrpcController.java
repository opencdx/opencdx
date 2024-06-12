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

import cdx.opencdx.grpc.service.health.CreateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.DeleteMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.GetMedicalHistoryResponse;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesRequest;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesResponse;
import cdx.opencdx.grpc.service.health.MedicalHistoryServiceGrpc;
import cdx.opencdx.grpc.service.health.SuccessResponse;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryResponse;
import cdx.opencdx.health.service.OpenCDXMedicalHistoryService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller MedicalHistory service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXMedicalHistoryGrpcController extends MedicalHistoryServiceGrpc.MedicalHistoryServiceImplBase {
    private final OpenCDXMedicalHistoryService openCDXMedicalHistoryService;

    /**
     * Constructor that takes a OpenCDXMedicalHistoryService
     * @param openCDXMedicalHistoryService service for procesing requests
     */
    public OpenCDXMedicalHistoryGrpcController(OpenCDXMedicalHistoryService openCDXMedicalHistoryService) {
        this.openCDXMedicalHistoryService = openCDXMedicalHistoryService;
    }

    /**
     * Method to track when MedicalHistory is given to a patient.
     * @param request Request for the MedicalHistory administration.
     * @param responseObserver The response observer.
     */
    @Override
    public void createMedicalHistory(
            CreateMedicalHistoryRequest request, StreamObserver<CreateMedicalHistoryResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalHistoryService.createMedicalHistory(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get MedicalHistory information by ID.
     * @param request Request for the MedicalHistory by ID.
     * @param responseObserver The response observer.
     */
    @Override
    public void getMedicalHistory(
            GetMedicalHistoryRequest request, StreamObserver<GetMedicalHistoryResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalHistoryService.getMedicalHistory(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to get MedicalHistory information by ID.
     * @param request Request for the MedicalHistory by ID.
     * @param responseObserver The response observer.
     */
    @Override
    public void updateMedicalHistory(
            UpdateMedicalHistoryRequest request, StreamObserver<UpdateMedicalHistoryResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalHistoryService.updateMedicalHistory(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to get MedicalHistory information by ID.
     * @param request Request for the MedicalHistory by ID.
     * @param responseObserver The response observer.
     */
    @Override
    public void deleteMedicalHistory(
            DeleteMedicalHistoryRequest request, StreamObserver<SuccessResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalHistoryService.deleteMedicalHistory(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get MedicalHistory information by patient ID within a date range.
     * @param request Request for the MedicalHistory by patient ID
     * @param responseObserver The response observer.
     */
    @Override
    public void listMedicalHistories(
            ListMedicalHistoriesRequest request, StreamObserver<ListMedicalHistoriesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicalHistoryService.listMedicalHistories(request));
        responseObserver.onCompleted();
    }
}
