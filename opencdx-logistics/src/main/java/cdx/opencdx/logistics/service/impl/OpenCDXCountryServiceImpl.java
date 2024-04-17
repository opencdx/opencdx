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
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.Country;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.logistics.CountryIdRequest;
import cdx.opencdx.grpc.service.logistics.CountryListRequest;
import cdx.opencdx.grpc.service.logistics.CountryListResponse;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.logistics.repository.OpenCDXVendorRepository;
import cdx.opencdx.logistics.service.OpenCDXCountryService;
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
 * Service for Country Protobuf messages
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCountryServiceImpl implements OpenCDXCountryService {

    private static final String DOMAIN = "OpenCDXCountryServiceImpl";
    private static final String COUNTRY = "COUNTRY: ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_COUNTRY_MODEL = "Failed to convert OpenCDXCountryModel";
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
                .findById(new OpenCDXIdentifier(request.getCountryId()))
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
                    COUNTRY + openCDXCountryModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXCountryModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDX_COUNTRY_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXCountryModel.toString());
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
                    COUNTRY + openCDXCountryModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXCountryModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_COUNTRY_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXCountryModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXCountryModel.getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteCountry(CountryIdRequest request) {
        OpenCDXIdentifier countryId = new OpenCDXIdentifier(request.getCountryId());
        if (Boolean.TRUE.equals(this.openCDXManufacturerRepository.existsByAddress_CountryId(countryId)
                || this.openCDXVendorRepository.existsByAddress_CountryId(countryId)
                || this.openCDXDeviceRepository.existsByManufacturerCountryId(countryId)
                || this.openCDXDeviceRepository.existsByVendorCountryId(countryId))) {
            return DeleteResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Country ID: " + request.getCountryId() + " in use.")
                    .build();
        }

        OpenCDXCountryModel openCDXCountryModel = this.openCDXCountryRepository
                .findById(countryId)
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find country: " + request.getCountryId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting Country",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    COUNTRY + countryId.toHexString(),
                    this.objectMapper.writeValueAsString(openCDXCountryModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_OPEN_CDX_COUNTRY_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXCountryModel.toString());
            throw openCDXNotAcceptable;
        }
        this.openCDXCountryRepository.deleteById(new OpenCDXIdentifier(request.getCountryId()));
        return DeleteResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Country ID: " + request.getCountryId() + " deleted.")
                .build();
    }

    @Override
    public CountryListResponse listCountries(CountryListRequest request) {
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
        log.trace("Searching Database");
        Page<OpenCDXCountryModel> all = this.openCDXCountryRepository.findAll(pageable);
        log.trace("found database results");

        return CountryListResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllCountries(
                        all.get().map(OpenCDXCountryModel::getProtobufMessage).toList())
                .build();
    }
}
