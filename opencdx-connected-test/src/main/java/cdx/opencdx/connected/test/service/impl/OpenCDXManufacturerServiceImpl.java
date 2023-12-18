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
import cdx.opencdx.connected.test.model.OpenCDXManufacturerModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerIdRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for Manufacturer Protobuf Messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXManufacturerServiceImpl implements OpenCDXManufacturerService {

    private static final String DOMAIN = "OpenCDXManufacturerServiceImpl";
    private static final String MANUFACTURER = "MANUFACTURER: ";
    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXTestCaseRepository openCDXTestCaseRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for the Manufacturer Service
     *
     * @param openCDXManufacturerRepository Repository for Manufactuer entities
     * @param openCDXDeviceRepository       Repository for Device entities
     * @param openCDXTestCaseRepository     Repository for TestCase entities
     * @param openCDXCurrentUser            Current User Service to access information.
     * @param objectMapper                  ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService           Audit service for tracking FDA requirements
     * @param openCDXDocumentValidator      Document Validator for validating Protobuf messages
     */
    public OpenCDXManufacturerServiceImpl(
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXTestCaseRepository openCDXTestCaseRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXTestCaseRepository = openCDXTestCaseRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public Manufacturer getManufacturerById(ManufacturerIdRequest request) {
        return this.openCDXManufacturerRepository
                .findById(new ObjectId(request.getManufacturerId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 1, "Failed to find manufacturer: " + request.getManufacturerId()))
                .getProtobufMessage();
    }

    @Override
    public Manufacturer addManufacturer(Manufacturer request) {
        if (request.hasManufacturerAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "country", new ObjectId(request.getManufacturerAddress().getCountry()));
        }

        OpenCDXManufacturerModel openCDXManufacturerModel =
                this.openCDXManufacturerRepository.save(new OpenCDXManufacturerModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Manufacturer",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    MANUFACTURER + openCDXManufacturerModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXManufacturerModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, "Failed to convert OpenCDXManufacturerModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXManufacturerModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXManufacturerModel.getProtobufMessage();
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer request) {
        if (request.hasManufacturerAddress()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "country", new ObjectId(request.getManufacturerAddress().getCountry()));
        }
        OpenCDXManufacturerModel openCDXManufacturerModel =
                this.openCDXManufacturerRepository.save(new OpenCDXManufacturerModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Manufacturer",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    MANUFACTURER + openCDXManufacturerModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXManufacturerModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert OpenCDXManufacturerModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXManufacturerModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXManufacturerModel.getProtobufMessage();
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
