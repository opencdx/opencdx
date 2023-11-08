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

import cdx.opencdx.connected.test.repository.OpenCDXCountryRepository;
import cdx.opencdx.connected.test.repository.OpenCDXVendorRepository;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXVendorServiceImpl implements OpenCDXVendorService {

    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXCountryRepository openCDXCountryRepository;

    public OpenCDXVendorServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository, OpenCDXCountryRepository openCDXCountryRepository) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXCountryRepository = openCDXCountryRepository;
    }

    @Override
    public Vendor getVendorById(VendorIdRequest request) {
        return Vendor.getDefaultInstance();
    }

    @Override
    public Vendor addVendor(Vendor request) {
        return Vendor.getDefaultInstance();
    }

    @Override
    public Vendor updateVendor(Vendor request) {
        return Vendor.getDefaultInstance();
    }

    @Override
    public DeleteResponse deleteVendor(VendorIdRequest request) {
        return DeleteResponse.getDefaultInstance();
    }
}
