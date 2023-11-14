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
import cdx.opencdx.connected.test.model.OpenCDXCountryModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.grpc.inventory.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import io.micrometer.observation.annotation.Observed;
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

    /**
     * Protobuf Country service
     * @param openCDXVendorRepository Repository for Vendors
     * @param openCDXCountryRepository Repository for Country
     * @param openCDXManufacturerRepository Repository for Manufacturer
     * @param openCDXDeviceRepository Repository for Device
     */
    public OpenCDXCountryServiceImpl(
            OpenCDXVendorRepository openCDXVendorRepository,
            OpenCDXCountryRepository openCDXCountryRepository,
            OpenCDXManufacturerRepository openCDXManufacturerRepository,
            OpenCDXDeviceRepository openCDXDeviceRepository) {
        this.openCDXVendorRepository = openCDXVendorRepository;
        this.openCDXCountryRepository = openCDXCountryRepository;
        this.openCDXManufacturerRepository = openCDXManufacturerRepository;
        this.openCDXDeviceRepository = openCDXDeviceRepository;
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
        return this.openCDXCountryRepository
                .save(new OpenCDXCountryModel(request))
                .getProtobufMessage();
    }

    @Override
    public Country updateCountry(Country request) {
        return this.openCDXCountryRepository
                .save(new OpenCDXCountryModel(request))
                .getProtobufMessage();
    }

    @Override
    public DeleteResponse deleteCountry(CountryIdRequest request) {
        if (this.openCDXManufacturerRepository.existsByAddress_Country_(request.getCountryId())
                || this.openCDXVendorRepository.existsByAddress_Country_(request.getCountryId())
                || this.openCDXDeviceRepository.existsByManufacturerCountryId(new ObjectId(request.getCountryId()))
                || this.openCDXDeviceRepository.existsByVendorCountryId(new ObjectId(request.getCountryId()))) {
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
