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
import cdx.opencdx.connected.test.model.OpenCDXCountryModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.inventory.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Service for Country Protobuf messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCountryServiceImpl implements OpenCDXCountryService {

    private static final String DOMAIN = "OpenCDXCountryServiceImpl";
    private final OpenCDXVendorRepository openCDXVendorRepository;
    private final OpenCDXCountryRepository openCDXCountryRepository;
    private final OpenCDXManufacturerRepository openCDXManufacturerRepository;
    private final OpenCDXDeviceRepository openCDXDeviceRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Protobuf Country service
     *
     * @param openCDXVendorRepository       Repository for Vendors
     * @param openCDXCountryRepository      Repository for Country
     * @param openCDXManufacturerRepository Repository for Manufacturer
     * @param openCDXDeviceRepository       Repository for Device
     * @param openCDXCurrentUser            Current User Service to access information.
     * @param objectMapper                  ObjectMapper used for converting messages for the audit system.
     * @param openCDXAuditService                Audit service for tracking FDA requirements
     */
    public OpenCDXCountryServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository,
            OpenCDXCountryRepository openCDXCountryRepository,
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXCountryRepository = openCDXCountryRepository;
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
    }

    @Override
    public Country getCountryById(CountryIdRequest request) {
        return this.openCDXCountryRepository
                .findById(new ObjectId(request.getCountryId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find country: " + request.getCountryId()))
                .getProtobufMessage();
    }

    @Override
    public Country addCountry(Country request) {
        OpenCDXCountryModel openCDXCountryModel = this.openCDXCountryRepository.save(new OpenCDXCountryModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Country",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXCountryModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXCountryModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert OpenCDXCountryModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXCountryModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXCountryModel.getProtobufMessage();
    }

    @Override
    public Country updateCountry(Country request) {
        OpenCDXCountryModel openCDXCountryModel = this.openCDXCountryRepository.save(new OpenCDXCountryModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Country",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    openCDXCountryModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXCountryModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, "Failed to convert OpenCDXCountryModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("OBJECT", openCDXCountryModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXCountryModel.getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteCountry(CountryIdRequest request) {
        ObjectId countryId = new ObjectId(request.getCountryId());
        if (this.openCDXManufacturerRepository.existsByAddress_CountryId(countryId)
                || this.openCDXVendorRepository.existsByAddress_CountryId(countryId)
                || this.openCDXDeviceRepository.existsByManufacturerCountryId(countryId)
                || this.openCDXDeviceRepository.existsByVendorCountryId(countryId)) {
            return DeleteResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Country ID: " + request.getCountryId() + " in use.")
                    .build();
        }

        this.openCDXCountryRepository.deleteById(new ObjectId(request.getCountryId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Country ID: " + request.getCountryId() + " deleted.")
                .build();
    }
}
