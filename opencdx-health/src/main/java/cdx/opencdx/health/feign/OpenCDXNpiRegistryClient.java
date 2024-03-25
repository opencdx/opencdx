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
package cdx.opencdx.health.feign;

import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiJsonResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface for the NPI Registry client.
 */
@FeignClient(name = "npiRegistryClient")
public interface OpenCDXNpiRegistryClient {

    public static final String NPI_VERSION = "2.1";

    /**
     * Method to get provider info from NPI Registry
     * @param version Version of the API, currently this should always be 2.1
     * @param providerNumber provider number to load info.
     * @return OpenCDXDtoNpiJsonResponse DTO of the JSON response
     */
    @Cacheable("npiProviderInfo")
    @GetMapping
    ResponseEntity<OpenCDXDtoNpiJsonResponse> getProviderInfo(
            @RequestParam("version") String version, @RequestParam("number") String providerNumber);
}
