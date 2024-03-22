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

import cdx.opencdx.health.dto.openfda.Search;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface for the OpenFDA client.  Can be used with an OpenFDA API Key, that can be requested from https://open.fda.gov/apis/authentication/ .
 * The key should be passed in the Authorization header in the form "Basic eW91ckFQSUtleUhlcmU6".
 */
@FeignClient(name = "fdaDrugClient")
public interface OpenCDXOpenFDAClient {

    /**
     * Method to get drugs from OpenFDA
     * @param search search term
     * @param limit limit
     * @param skip skip
     * @return Meta
     */
    @GetMapping("/drugsfda.json")
    ResponseEntity<Search> getDrugs(
            @RequestParam("search") String search, @RequestParam("limit") int limit, @RequestParam("skip") int skip);

    /**
     * Method to get label from OpenFDA
     * @param search search term
     * @param limit limit
     * @param skip skip
     * @return Meta
     */
    @GetMapping("/label.json")
    ResponseEntity<Search> getLabel(
            @RequestParam("search") String search, @RequestParam("limit") int limit, @RequestParam("skip") int skip);

    /**
     * Method to get drugs from OpenFDA
     * @param authorizationToken Token for authorization in the form "Basic eW91ckFQSUtleUhlcmU6"
     * @param search search term
     * @param limit limit
     * @param skip skip
     * @return Meta
     */
    @GetMapping("/drugsfda.json")
    ResponseEntity<Search> getDrugs(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestParam("search") String search,
            @RequestParam("limit") int limit,
            @RequestParam("skip") int skip);

    /**
     * Method to get label from OpenFDA
     * @param authorizationToken Token for authorization in the form "Basic eW91ckFQSUtleUhlcmU6"
     * @param search search term
     * @param limit limit
     * @param skip skip
     * @return Meta
     */
    @GetMapping("/label.json")
    ResponseEntity<Search> getLabel(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestParam("search") String search,
            @RequestParam("limit") int limit,
            @RequestParam("skip") int skip);
}
