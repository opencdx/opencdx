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
package cdx.opencdx.shipping.controller;

import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.CountryIdRequest;
import cdx.opencdx.grpc.inventory.CountryListRequest;
import cdx.opencdx.grpc.inventory.CountryListResponse;
import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.shipping.service.OpenCDXCountryService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the Country
 */
@Slf4j
@RestController
@RequestMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestCountryController {

    private final OpenCDXCountryService openCDXCountryService;

    /**
     * Constructor with OpenCDXCountryService for processing
     * @param openCDXCountryService OpenCDXCountryService for processing requests.
     */
    @Autowired
    public OpenCDXRestCountryController(OpenCDXCountryService openCDXCountryService) {
        this.openCDXCountryService = openCDXCountryService;
    }

    /**
     * Method to get a countryById
     *
     * @param id Id of the CountryIdRequest to retrieve.
     * @return The requested Country.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXCountryService.getCountryById(
                        CountryIdRequest.newBuilder().setCountryId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to add a country
     *
     * @param country Country to retrieve.
     * @return The added Country.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        return new ResponseEntity<>(this.openCDXCountryService.addCountry(country), HttpStatus.OK);
    }

    /**
     * Method to update a country
     *
     * @param country Country to update.
     * @return The updated Country.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        return new ResponseEntity<>(this.openCDXCountryService.updateCountry(country), HttpStatus.OK);
    }

    /**
     * Method to delete a country.
     * @param id Id of the country to delete.
     * @return Response with the deleted country.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCountry(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXCountryService.deleteCountry(
                        CountryIdRequest.newBuilder().setCountryId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List Countries
     *
     * @param countryListRequest request for Connected Tests.
     * @return the requested connected tests.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryListResponse> listCountries(@RequestBody CountryListRequest countryListRequest) {
        return new ResponseEntity<>(this.openCDXCountryService.listCountries(countryListRequest), HttpStatus.OK);
    }
}
