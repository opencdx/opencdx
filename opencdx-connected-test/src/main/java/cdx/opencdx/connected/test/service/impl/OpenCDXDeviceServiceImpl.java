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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXDeviceService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for Device Protobuf Messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDeviceServiceImpl implements OpenCDXDeviceService {
    private final OpenCDXDeviceRepository openCDXDeviceRepository;

    /**
     * Constructor for the Device Service
     * @param openCDXDeviceRepository Repository for persisting Device
     */
    public OpenCDXDeviceServiceImpl(OpenCDXDeviceRepository openCDXDeviceRepository) {
        this.openCDXDeviceRepository = openCDXDeviceRepository;
    }

    @Override
    public Device getDeviceById(DeviceIdRequest request) {
        return this.openCDXDeviceRepository
                .findById(new ObjectId(request.getDeviceId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getDeviceId()))
                .getProtobufMessage();
    }

    @Override
    public Device addDevice(Device request) {
        return this.openCDXDeviceRepository
                .save(new OpenCDXDeviceModel(request))
                .getProtobufMessage();
    }

    @Override
    public Device updateDevice(Device request) {
        return this.openCDXDeviceRepository
                .save(new OpenCDXDeviceModel(request))
                .getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteDevice(DeviceIdRequest request) {
        this.openCDXDeviceRepository.deleteById(new ObjectId(request.getDeviceId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Device: " + request.getDeviceId() + " is deleted.")
                .build();
    }
}
