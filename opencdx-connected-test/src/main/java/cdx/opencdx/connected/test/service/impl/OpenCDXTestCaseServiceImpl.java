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
import cdx.opencdx.connected.test.service.OpenCDXTestCaseService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.inventory.TestCaseIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXTestCaseServiceImpl implements OpenCDXTestCaseService {
    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXCountryRepository openCDXCountryRepository;
    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;

    public OpenCDXTestCaseServiceImpl(
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
    public TestCase getTestCaseById(TestCaseIdRequest request) {
        return TestCase.getDefaultInstance();
    }

    @Override
    public TestCase addTestCase(TestCase request) {
        return TestCase.getDefaultInstance();
    }

    @Override
    public TestCase updateTestCase(TestCase request) {
        return TestCase.getDefaultInstance();
    }

    @Override
    public DeleteResponse deleteTestCase(TestCaseIdRequest request) {
        return DeleteResponse.getDefaultInstance();
    }
}
