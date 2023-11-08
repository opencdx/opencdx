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
import cdx.opencdx.connected.test.model.OpenCDXTestCaseModel;
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXTestCaseService;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.inventory.TestCaseIdRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXTestCaseServiceImpl implements OpenCDXTestCaseService {
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;

    public OpenCDXTestCaseServiceImpl(
            OpenCDXTestCaseRepository openCDXTestCaseRepository) {
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
    }

    @Override
    public TestCase getTestCaseById(TestCaseIdRequest request) {
        return this.openCDXTestCaseRepository.findById(new ObjectId(request.getTestCaseId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound("OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getTestCaseId()))
                .getProtobufMessage();
    }

    @Override
    public TestCase addTestCase(TestCase request) {
        return this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request)).getProtobufMessage();
    }

    @Override
    public TestCase updateTestCase(TestCase request) {
        return this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request)).getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteTestCase(TestCaseIdRequest request) {
        this.openCDXTestCaseRepository.deleteById(new ObjectId(request.getTestCaseId()));
        return DeleteResponse.newBuilder().setSuccess(true).setMessage("TestCase: " + request.getTestCaseId() + " is deleted.").build();
    }
}
