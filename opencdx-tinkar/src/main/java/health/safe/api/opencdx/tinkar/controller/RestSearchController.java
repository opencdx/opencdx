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
package health.safe.api.opencdx.tinkar.controller;

import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import health.safe.api.opencdx.tinkar.service.EntityServiceSearch;
import health.safe.api.opencdx.tinkar.service.SearchService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Observed(name = "opencdx")
@RequestMapping("/search")
public class RestSearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private EntityServiceSearch entityServiceSearch;

    @GetMapping
    public PrimitiveDataSearchResult[] search(
            @RequestParam("query") String query, @RequestParam("maxResults") Integer maxResults) {
        if (maxResults == null) {
            maxResults = 50;
        }

        return searchService.search(query, maxResults);
    }

    @GetMapping("/nid")
    public String getEntity(@RequestParam("nid") Integer nid) {
        return entityServiceSearch.getEntity(nid);
    }
}
