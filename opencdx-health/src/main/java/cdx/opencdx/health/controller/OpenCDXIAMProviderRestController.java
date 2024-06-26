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
package cdx.opencdx.health.controller;

import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /iam/provider  api's
 */
@Slf4j
@RestController
@RequestMapping(value = "/provider", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXIAMProviderRestController {
    private final OpenCDXIAMProviderService openCDXIAMProviderService;

    /**
     * Constructor for the Provider Rest Controller
     * @param openCDXIAMProviderService Service Interface
     */
    public OpenCDXIAMProviderRestController(OpenCDXIAMProviderService openCDXIAMProviderService) {
        this.openCDXIAMProviderService = openCDXIAMProviderService;
    }

    /**
     * Get a Provider by number.
     * @param id for the provider.
     * @return Response with the organization.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetProviderResponse> getProviderByNumber(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMProviderService.getProviderByNumber(
                        GetProviderRequest.newBuilder().setProviderNumber(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to delete a provider.
     * @param id Number of the provider to delete.
     * @return Response with the deleted provider.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProviderResponse> deleteProvider(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXIAMProviderService.deleteProvider(
                        DeleteProviderRequest.newBuilder().setProviderId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List of providers
     * @param request List of providers.
     * @return All the providers.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListProvidersResponse> listProviders(@RequestBody ListProvidersRequest request) {
        return new ResponseEntity<>(this.openCDXIAMProviderService.listProviders(request), HttpStatus.OK);
    }

    /**
     * List of providers
     * @param request List of providers.
     * @return All the providers.
     */
    @PostMapping("/load")
    public ResponseEntity<LoadProviderResponse> loadProvider(@RequestBody LoadProviderRequest request) {
        return new ResponseEntity<>(this.openCDXIAMProviderService.loadProvider(request), HttpStatus.OK);
    }
}
