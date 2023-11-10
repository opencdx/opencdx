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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXTestCaseModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXTestCaseService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.inventory.TestCaseIdRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service for Protobuf TestCase messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXTestCaseServiceImpl implements OpenCDXTestCaseService {
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructor for the TestCase Service
     *
     * @param openCDXTestCaseRepository Repository for persiting OpenCDXTestCaseModel
     * @param openCDXCurrentUser        Current User Service to access information.
     * @param objectMapper              ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService       Audit service for tracking FDA requirements
     */
    public OpenCDXTestCaseServiceImpl(OpenCDXTestCaseRepository openCDXTestCaseRepository, OpenCDXCurrentUser openCDXCurrentUser, ObjectMapper objectMapper, OpenCDXAuditService openCDXAuditService) {
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public TestCase getTestCaseById(TestCaseIdRequest request) {
        return this.openCDXTestCaseRepository
                .findById(new ObjectId(request.getTestCaseId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getTestCaseId()))
                .getProtobufMessage();
    }

    @Override
    public TestCase addTestCase(TestCase request) {
        OpenCDXTestCaseModel openCDXTestCaseModel = this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request));
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "Creating TestCase",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXTestCaseModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXTestCaseModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXTestCaseServiceImpl", 2, "Failed to convert OpenCDXTestCaseModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXTestCaseModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXTestCaseModel
                .getProtobufMessage();
    }

    @Override
    public TestCase updateTestCase(TestCase request) {
        OpenCDXTestCaseModel openCDXTestCaseModel = this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request));
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "Updating TestCase",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXTestCaseModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXTestCaseModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXTestCaseServiceImpl", 3, "Failed to convert OpenCDXTestCaseModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXTestCaseModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXTestCaseModel
                .getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteTestCase(TestCaseIdRequest request) {
        this.openCDXTestCaseRepository.deleteById(new ObjectId(request.getTestCaseId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("TestCase: " + request.getTestCaseId() + " is deleted.")
                .build();
    }
}
