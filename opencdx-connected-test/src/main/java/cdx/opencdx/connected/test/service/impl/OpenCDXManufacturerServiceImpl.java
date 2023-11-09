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
import cdx.opencdx.connected.test.model.OpenCDXManufacturerModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXManufacturerServiceImpl implements OpenCDXManufacturerService {

    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;

    public OpenCDXManufacturerServiceImpl(
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXTestCaseRepository openCDXTestCaseRepository) {
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
    }

    @Override
    public Manufacturer getManufacturerById(ManufacturerIdRequest request) {
        return this.openCDXManufacturerRepository
                .findById(new ObjectId(request.getManufacturerId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl",
                        1,
                        "Failed to find manufacturer: " + request.getManufacturerId()))
                .getProtobufMessage();
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer request) {
        return this.openCDXManufacturerRepository
                .save(new OpenCDXManufacturerModel(request))
                .getProtobufMessage();
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer request) {
        return this.openCDXManufacturerRepository
                .save(new OpenCDXManufacturerModel(request))
                .getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteManufacturer(ManufacturerIdRequest request) {
        if (this.openCDXDeviceRepository.existsByManufacturerId(new ObjectId(request.getManufacturerId()))
                || this.openCDXTestCaseRepository.existsByManufacturerId(new ObjectId(request.getManufacturerId()))) {
            return DeleteResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Manufacturer: " + request.getManufacturerId() + " is in use.")
                    .build();
        }
        this.openCDXManufacturerRepository.deleteById(new ObjectId(request.getManufacturerId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Manufacturer: " + request.getManufacturerId() + " is deleted.")
                .build();
    }
}
