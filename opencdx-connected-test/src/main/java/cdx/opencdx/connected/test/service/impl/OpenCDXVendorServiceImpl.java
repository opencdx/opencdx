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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.repository.OpenCDXVendorRepository;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorIdRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for vendor activities.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXVendorServiceImpl implements OpenCDXVendorService {

    private static final String VENDOR = "VENDOR: ";
    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * OpenCdx Vendor Service
     *
     * @param openCDXVendorRepository   Repository for vendors
     * @param openCDXDeviceRepository   Repository for Devices
     * @param openCDXTestCaseRepository Repository for TestCases
     * @param openCDXCurrentUser        Current User Service to access information.
     * @param objectMapper              ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService       Audit service for tracking FDA requirements
     * @param openCDXDocumentValidator  Document Validator for validating documents.
     */
    public OpenCDXVendorServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXTestCaseRepository openCDXTestCaseRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public Vendor getVendorById(VendorIdRequest request) {
        return this.openCDXVendorRepository
                .findById(new ObjectId(request.getVendorId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find vendor: " + request.getVendorId()))
                .getProtobufMessage();
    }

    @Override
    public Vendor addVendor(Vendor request) {
        if (request.hasVendorAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "country", new ObjectId(request.getVendorAddress().getCountryId()));
        }
        OpenCDXVendorModel openCDXVendorModel = this.openCDXVendorRepository.save(new OpenCDXVendorModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Vendor",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    VENDOR + openCDXVendorModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXVendorModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXVendorServiceImpl", 2, "Failed to convert OpenCDXVendorModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXVendorModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXVendorModel.getProtobufMessage();
    }

    @Override
    public Vendor updateVendor(Vendor request) {
        if (request.hasVendorAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "country", new ObjectId(request.getVendorAddress().getCountryId()));
        }
        OpenCDXVendorModel openCDXVendorModel = this.openCDXVendorRepository.save(new OpenCDXVendorModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Vendor",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    VENDOR + openCDXVendorModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXVendorModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXVendorServiceImpl", 3, "Failed to convert OpenCDXVendorModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXVendorModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXVendorModel.getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteVendor(VendorIdRequest request) {
        if (this.openCDXDeviceRepository.existsByVendorId(new ObjectId(request.getVendorId()))
                || this.openCDXTestCaseRepository.existsByVendorId(new ObjectId(request.getVendorId()))) {
            return DeleteResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Vendor: " + request.getVendorId() + " is in use.")
                    .build();
        }
        this.openCDXVendorRepository.deleteById(new ObjectId(request.getVendorId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Vendor: " + request.getVendorId() + " is deleted.")
                .build();
    }
}
