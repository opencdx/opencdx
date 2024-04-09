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

import cdx.opencdx.grpc.health.medication.*;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller Medication Administration service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXMedicationAdministrationGrpcController
        extends MedicationAdministrationServiceGrpc.MedicationAdministrationServiceImplBase {

    private final OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;

    /**
     * Constructor that takes a OpenCDXMedicationAdministrationService
     * @param openCDXMedicationAdministrationService service for procesing requests
     */
    public OpenCDXMedicationAdministrationGrpcController(
            OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService) {
        this.openCDXMedicationAdministrationService = openCDXMedicationAdministrationService;
    }

    /**
     * Method to track when medication is given to a patient.
     * @param request Request for the medication administration.
     * @param responseObserver The response observer.
     */
    @Override
    public void trackMedicationAdministration(
            MedicationAdministration request, StreamObserver<MedicationAdministration> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationAdministrationService.trackMedicationAdministration(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get medication information by ID.
     * @param request Request for the medication by ID.
     * @param responseObserver The response observer.
     */
    @Override
    public void getMedicationById(GetMedicationByIdRequest request, StreamObserver<Medication> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationAdministrationService.getMedicationById(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get medication information by patient ID within a date range.
     * @param request Request for the medication by patient ID
     * @param responseObserver The response observer.
     */
    @Override
    public void listMedications(
            ListMedicationsRequest request, StreamObserver<ListMedicationsResponse> responseObserver) {
        responseObserver.onNext(this.openCDXMedicationAdministrationService.listMedications(request));
        responseObserver.onCompleted();
    }
}
