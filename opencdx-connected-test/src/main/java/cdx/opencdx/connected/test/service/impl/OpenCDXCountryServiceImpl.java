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

import cdx.opencdx.connected.test.repository.OpenCDXCountryRepository;
import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.grpc.inventory.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXCountryServiceImpl implements OpenCDXCountryService {

    private final OpenCDXCountryRepository openCDXCountryRepository;

    public OpenCDXCountryServiceImpl(OpenCDXCountryRepository openCDXCountryRepository) {
        this.openCDXCountryRepository = openCDXCountryRepository;
    }

    @Override
    public Country getCountryById(CountryIdRequest request) {
        return Country.getDefaultInstance();
    }

    @Override
    public Country addCountry(Country request) {
        return Country.getDefaultInstance();
    }

    @Override
    public Country updateCountry(Country request) {
        return Country.getDefaultInstance();
    }

    @Override
    public DeleteResponse deleteCountry(CountryIdRequest request) {
        return DeleteResponse.getDefaultInstance();
    }
}
