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
package cdx.opencdx.connected.test.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
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
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for Protobuf TestCase messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXTestCaseServiceImpl implements OpenCDXTestCaseService {
    private static final String TESTCASE = "TESTCASE: ";
    private static final String DOMAIN = "OpenCDXTestCaseServiceImpl";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_TEST_CASE_MODEL = "Failed to convert OpenCDXTestCaseModel";
    private static final String OBJECT = "OBJECT";
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for the TestCase Service
     *
     * @param openCDXTestCaseRepository Repository for persiting OpenCDXTestCaseModel
     * @param openCDXCurrentUser        Current User Service to access information.
     * @param objectMapper              ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService       Audit service for tracking FDA requirements
     * @param openCDXDocumentValidator  Document validator for validating the document.
     */
    public OpenCDXTestCaseServiceImpl(
            OpenCDXTestCaseRepository openCDXTestCaseRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
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
        if (request.hasManufacturerId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "manufacturer", new ObjectId(request.getManufacturerId()));
        }
        if (request.hasVendorId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow("vendor", new ObjectId(request.getVendorId()));
        }
        this.openCDXDocumentValidator.validateDocumentsOrThrow(
                "devices",
                request.getDeviceIdsList().stream().map(ObjectId::new).toList());
        OpenCDXTestCaseModel openCDXTestCaseModel =
                this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating TestCase",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    TESTCASE + openCDXTestCaseModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXTestCaseModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDX_TEST_CASE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXTestCaseModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXTestCaseModel.getProtobufMessage();
    }

    @Override
    public TestCase updateTestCase(TestCase request) {
        OpenCDXTestCaseModel openCDXTestCaseModel =
                this.openCDXTestCaseRepository.save(new OpenCDXTestCaseModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating TestCase",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    TESTCASE + openCDXTestCaseModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXTestCaseModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_TEST_CASE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXTestCaseModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXTestCaseModel.getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteTestCase(TestCaseIdRequest request) {
        ObjectId objectId = new ObjectId(request.getTestCaseId());

        OpenCDXTestCaseModel openCDXTestCaseModel = this.openCDXTestCaseRepository
                .findById(objectId)
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getTestCaseId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting TestCase",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    TESTCASE + openCDXTestCaseModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXTestCaseModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_TEST_CASE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXTestCaseModel.toString());
            throw openCDXNotAcceptable;
        }

        this.openCDXTestCaseRepository.deleteById(objectId);
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("TestCase: " + request.getTestCaseId() + " is deleted.")
                .build();
    }
}
