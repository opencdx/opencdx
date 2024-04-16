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

import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXMedicationService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller Medication service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXMedicationGrpcController extends MedicationServiceGrpc.MedicationServiceImplBase {

    private final OpenCDXMedicationService openCDXMedicationService;

    /**
     * Constructor that takes a OpenCDXMedicationService
     * @param openCDXMedicationService service for procesing requests
     */
    public OpenCDXMedicationGrpcController(OpenCDXMedicationService openCDXMedicationService) {
        this.openCDXMedicationService = openCDXMedicationService;
    }

    /**
     * Method to prescribe medication.
     * @param request The medication to prescribe.
     * @param responseObserver The response observer.
     */
    @Override
    public void prescribing(Medication request, StreamObserver<Medication> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationService.prescribing(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to end medication.
     * @param request The medication to end.
     * @param responseObserver The response observer.
     */
    @Override
    public void ending(EndMedicationRequest request, StreamObserver<Medication> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationService.ending(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to list all medications.
     * @param request The request to list all medications.
     * @param responseObserver The response observer.
     */
    @Override
    public void listAllMedications(
            ListMedicationsRequest request, StreamObserver<ListMedicationsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationService.listAllMedications(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to list current medications.
     * @param request The request to list current medications.
     * @param responseObserver The response observer.
     */
    @Override
    public void listCurrentMedications(
            ListMedicationsRequest request, StreamObserver<ListMedicationsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationService.listCurrentMedications(request));
        responseObserver.onCompleted();
    }

    /**
     * Method to search for medications.
     * @param request The request to search for medications.
     * @param responseObserver The response observer.
     */
    @Override
    public void searchMedications(
            SearchMedicationsRequest request, StreamObserver<ListMedicationsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationService.searchMedications(request));
        responseObserver.onCompleted();
    }
}
