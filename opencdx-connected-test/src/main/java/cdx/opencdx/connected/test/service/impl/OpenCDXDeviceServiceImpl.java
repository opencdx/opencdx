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

import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXDeviceService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDeviceServiceImpl implements OpenCDXDeviceService {
    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXCountryRepository openCDXCountryRepository;
    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;

    public OpenCDXDeviceServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository,
            OpenCDXCountryRepository openCDXCountryRepository,
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXTestCaseRepository openCDXTestCaseRepository) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXCountryRepository = openCDXCountryRepository;
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
    }

    @Override
    public Device getDeviceById(DeviceIdRequest request) {
        return Device.getDefaultInstance();
    }

    @Override
    public Device addDevice(Device request) {
        return Device.getDefaultInstance();
    }

    @Override
    public Device updateDevice(Device request) {
        return Device.getDefaultInstance();
    }

    @Override
    public DeleteResponse deleteDevice(DeviceIdRequest request) {
        return DeleteResponse.getDefaultInstance();
    }
}
