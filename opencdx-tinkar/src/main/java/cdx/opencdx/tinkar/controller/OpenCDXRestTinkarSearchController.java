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

import cdx.opencdx.grpc.service.tinkar.*;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
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
public class OpenCDXRestTinkarSearchController {

    private final OpenCDXTinkarService openCDXTinkarService;

    /**
     * Constructor taking the OpenCDXTinkarService
     * @param openCDXTinkarService OpenCDXTInkarServer to use
     */
    public OpenCDXRestTinkarSearchController(OpenCDXTinkarService openCDXTinkarService) {
        this.openCDXTinkarService = openCDXTinkarService;
    }

    /**
     * Search for a data result based on a query
     * @param query String containing the query of the message
     * @param maxResults Maximum number of results to return.
     * @return TinkarSearchQueryResponse that contains the results.
     */
    @GetMapping("/search")
    public TinkarSearchQueryResponse search(
            @RequestParam("query") String query, @RequestParam("maxResults") Integer maxResults) {
        return openCDXTinkarService.search(TinkarSearchQueryRequest.newBuilder()
                .setQuery(query)
                .setMaxResults(maxResults)
                .build());
    }

    /**
     * Search for concepts based off a search term
     * @param query String containing the term to search
     * @param maxResults Maximum number of results to return.
     * @return TinkarSearchQueryResponse that contains the results.
     */
    @GetMapping("/conceptSearch")
    public TinkarGetResponse conceptSearch(
            @RequestParam("query") String query,
            @RequestParam(name = "maxResults", required = false) Integer maxResults) {
        return openCDXTinkarService.conceptSearch(TinkarSearchQueryRequest.newBuilder()
                .setQuery(query)
                .setMaxResults(maxResults != null ? maxResults : 30)
                .build());
    }

    /**
     * Method to look up an entity by the tinkar concept public ID
     * @param conceptId String representing an entity concept ID.
     * @return TinkarGetResult representing the entity.
     */
    @GetMapping("/conceptId")
    public TinkarGetResult getEntity(@RequestParam("conceptId") String conceptId) {
        return openCDXTinkarService.getEntity(
                TinkarGetRequest.newBuilder().setConceptId(conceptId).build());
    }

    /**
     * Method to look up child concepts for the tinkar concept public ID
     * @param conceptId String representing an entity concept ID.
     * @return TinkarGetResponse representing the entity.
     */
    @GetMapping("/children/conceptId")
    public TinkarGetResponse getTinkarChildConcepts(@RequestParam("conceptId") String conceptId) {
        return openCDXTinkarService.getTinkarChildConcepts(
                TinkarGetRequest.newBuilder().setConceptId(conceptId).build());
    }

    /**
     * Method to look up dependent concepts for the tinkar concept public ID
     * @param conceptId String representing an entity concept ID.
     * @return TinkarGetResponse representing the entity.
     */
    @GetMapping("/descendants/conceptId")
    public TinkarGetResponse getTinkarDescendantConcepts(@RequestParam("conceptId") String conceptId) {
        return openCDXTinkarService.getTinkarDescendantConcepts(
                TinkarGetRequest.newBuilder().setConceptId(conceptId).build());
    }

    /**
     * Method to look up lidr record concepts for a test kit concept public ID
     * @param testKitConceptId String representing a test kit concept ID.
     * @return TinkarGetResponse representing the entity.
     */
    @GetMapping("/lidr-records/testKitConceptId")
    public TinkarGetResponse getLIDRRecordConceptsFromTestKit(
            @RequestParam("testKitConceptId") String testKitConceptId) {
        return openCDXTinkarService.getLIDRRecordConceptsFromTestKit(
                TinkarGetRequest.newBuilder().setConceptId(testKitConceptId).build());
    }

    /**
     * Method to look up result conformances for a LIDR record concept public ID
     * @param lidrRecordConceptId String representing a LIDR record concept ID.
     * @return TinkarGetResponse representing the entity.
     */
    @GetMapping("/result-conformances/lidrRecordConceptId")
    public TinkarGetResponse getResultConformanceConceptsFromLIDRRecord(
            @RequestParam("lidrRecordConceptId") String lidrRecordConceptId) {
        return openCDXTinkarService.getResultConformanceConceptsFromLIDRRecord(
                TinkarGetRequest.newBuilder().setConceptId(lidrRecordConceptId).build());
    }

    /**
     * Method to look up allowed results for a result conformance concept public ID
     * @param resultConformanceConceptId String representing a result conformance concept ID.
     * @return TinkarGetResponse representing the entity.
     */
    @GetMapping("/allowed-results/resultConformanceConceptId")
    public TinkarGetResponse getAllowedResultConceptsFromResultConformance(
            @RequestParam("resultConformanceConceptId") String resultConformanceConceptId) {
        return openCDXTinkarService.getAllowedResultConceptsFromResultConformance(TinkarGetRequest.newBuilder()
                .setConceptId(resultConformanceConceptId)
                .build());
    }
}
