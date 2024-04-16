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
package cdx.opencdx.logistics.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Device;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.service.logistics.DeviceIdRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListResponse;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.logistics.model.OpenCDXDeviceModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.service.OpenCDXDeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for Device Protobuf Messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDeviceServiceImpl implements OpenCDXDeviceService {
    private static final String COUNTRY = "country";
    private static final String DEVICE = "DEVICE: ";
    private static final String DOMAIN = "OpenCDXDeviceServiceImpl";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_DEVICE_MODEL = "Failed to convert OpenCDXDeviceModel";
    private static final String OBJECT = "OBJECT";
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for the Device Service
     *
     * @param openCDXDeviceRepository Repository for persisting Device
     * @param openCDXCurrentUser      Current User Service to access information.
     * @param objectMapper            ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService     Audit service for tracking FDA requirements
     * @param openCDXDocumentValidator Document Validator for validating the Device
     */
    public OpenCDXDeviceServiceImpl(
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public Device getDeviceById(DeviceIdRequest request) {
        return this.openCDXDeviceRepository
                .findById(new OpenCDXIdentifier(request.getDeviceId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getDeviceId()))
                .getProtobufMessage();
    }

    @Override
    public Device addDevice(Device request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                COUNTRY, new OpenCDXIdentifier(request.getManufacturerCountryId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                COUNTRY, new OpenCDXIdentifier(request.getVendorCountryId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "manufacturer", new OpenCDXIdentifier(request.getManufacturerId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow("vendor", new OpenCDXIdentifier(request.getVendorId()));

        this.openCDXDocumentValidator.validateDocumentsOrThrow(
                "testcases",
                request.getTestCaseIdsList().stream()
                        .map(OpenCDXIdentifier::new)
                        .toList());

        OpenCDXDeviceModel openCDXDeviceModel = this.openCDXDeviceRepository.save(new OpenCDXDeviceModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Device",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    DEVICE + openCDXDeviceModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXDeviceModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDX_DEVICE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXDeviceModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXDeviceModel.getProtobufMessage();
    }

    @Override
    public Device updateDevice(Device request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                COUNTRY, new OpenCDXIdentifier(request.getManufacturerCountryId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                COUNTRY, new OpenCDXIdentifier(request.getVendorCountryId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "manufacturer", new OpenCDXIdentifier(request.getManufacturerId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow("vendor", new OpenCDXIdentifier(request.getVendorId()));
        this.openCDXDocumentValidator.validateDocumentsOrThrow(
                "testcases",
                request.getTestCaseIdsList().stream()
                        .map(OpenCDXIdentifier::new)
                        .toList());

        OpenCDXDeviceModel openCDXDeviceModel = this.openCDXDeviceRepository.save(new OpenCDXDeviceModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Device",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    DEVICE + openCDXDeviceModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXDeviceModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_DEVICE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXDeviceModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXDeviceModel.getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteDevice(DeviceIdRequest request) {
        OpenCDXIdentifier openCDXIdentifier = new OpenCDXIdentifier(request.getDeviceId());

        OpenCDXDeviceModel openCDXDeviceModel = this.openCDXDeviceRepository
                .findById(openCDXIdentifier)
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getDeviceId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting Device",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    DEVICE + openCDXDeviceModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXDeviceModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_DEVICE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXDeviceModel.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXDeviceRepository.deleteById(openCDXIdentifier);
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Device: " + request.getDeviceId() + " is deleted.")
                .build();
    }

    /**
     * Method to get list of test cases
     *
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested test case with page, sorting, and page size
     */
    @Override
    public DeviceListResponse listDevices(DeviceListRequest request) {
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }
        log.info("Searching Database");
        Page<OpenCDXDeviceModel> all;

        if (request.hasManufacturerId()) {
            all = this.openCDXDeviceRepository.findAllByManufacturerId(
                    new OpenCDXIdentifier(request.getManufacturerId()), pageable);
        } else if (request.hasVendorId()) {
            all = this.openCDXDeviceRepository.findAllByVendorId(
                    new OpenCDXIdentifier(request.getVendorId()), pageable);
        } else {
            all = this.openCDXDeviceRepository.findAll(pageable);
        }

        return DeviceListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllDevice(
                        all.get().map(OpenCDXDeviceModel::getProtobufMessage).toList())
                .build();
    }
}
