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

import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.GetVaccineByIdRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesResponse;
import cdx.opencdx.grpc.service.health.VaccineServiceGrpc;
import cdx.opencdx.health.service.OpenCDXVaccineService;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller Vaccine service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXVaccineGrpcController extends VaccineServiceGrpc.VaccineServiceImplBase {

    private final OpenCDXVaccineService openCDXVaccineService;

    /**
     * Constructor that takes a OpenCDXVaccineService
     * @param openCDXVaccineService service for procesing requests
     */
    public OpenCDXVaccineGrpcController(OpenCDXVaccineService openCDXVaccineService) {
        this.openCDXVaccineService = openCDXVaccineService;
    }

    /**
     * Method to track when vaccine is given to a patient.
     * @param request Request for the vaccine administration.
     * @param responseObserver The response observer.
     */
    @Override
    public void trackVaccineAdministration(Vaccine request, StreamObserver<Vaccine> responseObserver) {
        responseObserver.onNext(this.openCDXVaccineService.trackVaccineAdministration(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get vaccine information by ID.
     * @param request Request for the vaccine by ID.
     * @param responseObserver The response observer.
     */
    @Override
    public void getVaccineById(GetVaccineByIdRequest request, StreamObserver<Vaccine> responseObserver) {
        responseObserver.onNext(this.openCDXVaccineService.getVaccineById(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to update vaccine information.
     * @param request Request for the vaccine.
     * @param responseObserver The response observer.
     */
    @Override
    public void updateVaccine(Vaccine request, StreamObserver<Vaccine> responseObserver) {
        responseObserver.onNext(this.openCDXVaccineService.updateVaccine(request));
        responseObserver.onCompleted();
    }
    /**
     * Method to get vaccine information by patient ID within a date range.
     * @param request Request for the vaccine by patient ID
     * @param responseObserver The response observer.
     */
    @Override
    public void listVaccines(ListVaccinesRequest request, StreamObserver<ListVaccinesResponse> responseObserver) {
        responseObserver.onNext(this.openCDXVaccineService.listVaccines(request));
        responseObserver.onCompleted();
    }
}
