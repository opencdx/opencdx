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
import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXDeviceService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceIdRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service for Device Protobuf Messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDeviceServiceImpl implements OpenCDXDeviceService {
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructor for the Device Service
     *
     * @param openCDXDeviceRepository Repository for persisting Device
     * @param openCDXCurrentUser      Current User Service to access information.
     * @param objectMapper            ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService     Audit service for tracking FDA requirements
     */
    public OpenCDXDeviceServiceImpl(OpenCDXDeviceRepository openCDXDeviceRepository, OpenCDXCurrentUser openCDXCurrentUser, ObjectMapper objectMapper, OpenCDXAuditService openCDXAuditService) {
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public Device getDeviceById(DeviceIdRequest request) {
        return this.openCDXDeviceRepository
                .findById(new ObjectId(request.getDeviceId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXManufacturerServiceImpl", 1, "Failed to find testcase: " + request.getDeviceId()))
                .getProtobufMessage();
    }

    @Override
    public Device addDevice(Device request) {
        OpenCDXDeviceModel openCDXDeviceModel = this.openCDXDeviceRepository.save(new OpenCDXDeviceModel(request));
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "Creating Device",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXDeviceModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXDeviceModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXDeviceServiceImpl", 2, "Failed to convert OpenCDXDeviceModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXDeviceModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXDeviceModel
                .getProtobufMessage();
    }

    @Override
    public Device updateDevice(Device request) {
        OpenCDXDeviceModel openCDXDeviceModel = this.openCDXDeviceRepository.save(new OpenCDXDeviceModel(request));
        try {
            this.openCDXAuditService.config(
                    this.openCDXCurrentUser.getCurrentUser().getId().toHexString(),
                    this.openCDXCurrentUser.getCurrentUserType(),
                    "Updating Device",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXDeviceModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXDeviceModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable("OpenCDXDeviceServiceImpl", 3, "Failed to convert OpenCDXDeviceModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXDeviceModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXDeviceModel
                .getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteDevice(DeviceIdRequest request) {
        this.openCDXDeviceRepository.deleteById(new ObjectId(request.getDeviceId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Device: " + request.getDeviceId() + " is deleted.")
                .build();
    }
}
