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
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.repository.OpenCDXVendorRepository;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXVendorServiceImpl implements OpenCDXVendorService {

    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;

    public OpenCDXVendorServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository, OpenCDXDeviceRepository openCDXDeviceRepository, OpenCDXTestCaseRepository openCDXTestCaseRepository) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
    }

    @Override
    public Vendor getVendorById(VendorIdRequest request) {
        return this.openCDXVendorRepository.findById(new ObjectId(request.getVendorId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound("OpenCDXManufacturerServiceImpl", 1, "Failed to find vendor: " + request.getVendorId()))
                .getProtobufMessage();
    }

    @Override
    public Vendor addVendor(Vendor request) {
        return this.openCDXVendorRepository.save(new OpenCDXVendorModel(request)).getProtobufMessage();
    }

    @Override
    public Vendor updateVendor(Vendor request) {
        return this.openCDXVendorRepository.save(new OpenCDXVendorModel(request)).getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteVendor(VendorIdRequest request) {
        if(this.openCDXDeviceRepository.existsByVendorId(new ObjectId(request.getVendorId()))
                || this.openCDXTestCaseRepository.existsByVendorId(new ObjectId(request.getVendorId()))) {
            return DeleteResponse.newBuilder().setSuccess(false).setMessage("Vendor: " + request.getVendorId() + " is in use.").build();
        }
        this.openCDXVendorRepository.deleteById(new ObjectId(request.getVendorId()));
        return DeleteResponse.newBuilder().setSuccess(true).setMessage("Vendor: " + request.getVendorId() + " is deleted.").build();
    }
}
