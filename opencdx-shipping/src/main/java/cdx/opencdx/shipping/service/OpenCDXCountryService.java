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
package cdx.opencdx.shipping.service;

import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.CountryListRequest;
import cdx.opencdx.grpc.inventory.CountryListResponse;
import cdx.opencdx.grpc.inventory.DeleteResponse;

/**
 * Country service for country identification.
 */
public interface OpenCDXCountryService {
    /**
     * Retrieve a country by an id
     * @param request Request message to get country
     * @return Country requested
     */
    Country getCountryById(CountryIdRequest request);

    /**
     * Method to add a new country to the database
     * @param request Country to add
     * @return The Country with it's assigned ID.
     */
    Country addCountry(Country request);

    /**
     * Update the records of a country.
     * @param request Updated Country data.
     * @return The updated country.
     */
    Country updateCountry(Country request);

    /**
     * Request to delete the country
     * @param request Request of the country to delete
     * @return Response indicating success.
     */
    DeleteResponse deleteCountry(CountryIdRequest request);

    /**
     * Method to get list of countries
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested Countries with page, sorting, and page size
     */
    CountryListResponse listCountries(CountryListRequest request);
}
