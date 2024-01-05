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
package cdx.opencdx.tinkar.controller;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API for access TINKAR resources
 */
@Slf4j
@RestController
@Observed(name = "opencdx")
@RequestMapping
@ExcludeFromJacocoGeneratedReport
public class OpenCDXRestSearchController {

    private final OpenCDXTinkarService openCDXTinkarService;

    /**
     * Constructor taking the OpenCDXTinkarService
     * @param openCDXTinkarService OpenCDXTInkarServer to use
     */
    public OpenCDXRestSearchController(OpenCDXTinkarService openCDXTinkarService) {
        this.openCDXTinkarService = openCDXTinkarService;
    }

    /**
     * Search for a data result based on a query
     * @param query String containing the query of the message
     * @param maxResults Maximum number of results to return.
     * @return Array of the results.
     */
    @GetMapping
    public PrimitiveDataSearchResult[] search(
            @RequestParam("query") String query, @RequestParam("maxResults") Integer maxResults) {
        return openCDXTinkarService.search(query, maxResults);
    }

    /**
     * Method to look up an entity by the NID
     * @param nid Integer representing an entity.
     * @return String representing hte entity.
     */
    @GetMapping("/nid")
    public String getEntity(@RequestParam("nid") Integer nid) {
        return openCDXTinkarService.getEntity(nid);
    }
}
