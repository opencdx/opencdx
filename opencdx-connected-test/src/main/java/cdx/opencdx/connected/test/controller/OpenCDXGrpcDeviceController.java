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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.connected.test.service.OpenCDXDeviceService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import cdx.opencdx.grpc.inventory.DeviceServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * GrPC Device Controller for services
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcDeviceController extends DeviceServiceGrpc.DeviceServiceImplBase {

    private final OpenCDXDeviceService openCDXDeviceService;

    /**
     * Setup the GRPC Device Controller
     * @param openCDXDeviceService Service for processing requests.
     */
    public OpenCDXGrpcDeviceController(OpenCDXDeviceService openCDXDeviceService) {
        this.openCDXDeviceService = openCDXDeviceService;
    }

    @Secured({})
    @Override
    public void getDeviceById(DeviceIdRequest request, StreamObserver<Device> responseObserver) {
        responseObserver.onNext(this.openCDXDeviceService.getDeviceById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void addDevice(Device request, StreamObserver<Device> responseObserver) {
        responseObserver.onNext(this.openCDXDeviceService.addDevice(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateDevice(Device request, StreamObserver<Device> responseObserver) {
        responseObserver.onNext(this.openCDXDeviceService.updateDevice(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteDevice(DeviceIdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        responseObserver.onNext(this.openCDXDeviceService.deleteDevice(request));
        responseObserver.onCompleted();
    }
}
