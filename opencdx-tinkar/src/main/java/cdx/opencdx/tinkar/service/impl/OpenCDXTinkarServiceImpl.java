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
package cdx.opencdx.tinkar.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.grpc.service.tinkar.*;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import cdx.opencdx.tinkar.service.TinkarPrimitive;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.id.PublicIds;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import io.micrometer.observation.annotation.Observed;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Tinkar Service implementation
 */
@Service
@Observed(name = "opencdx")
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXTinkarServiceImpl implements OpenCDXTinkarService {

    private final String pathParent;

    private final String pathChild;

    private final TinkarPrimitive primitive;

    /**
     * Default Constructor
     * @param pathParent Parent path
     * @param pathChild Child path
     * @param primitive TinkarPrimitive
     */
    public OpenCDXTinkarServiceImpl(
            @Value("${data.path.parent}") String pathParent,
            @Value("${data.path.child}") String pathChild,
            TinkarPrimitive primitive) {
        this.pathParent = pathParent;
        this.pathChild = pathChild;
        this.primitive = primitive;
    }

    /**
     * Initializer
     */
    @ExcludeFromJacocoGeneratedReport
    @Scheduled(initialDelay = 30, fixedDelay = Long.MAX_VALUE)
    public void initialize() {
        log.info("Initializing OpenCDXTinkarServiceImpl");
        primitive.initializePrimitiveData(pathParent, pathChild);
        log.info("OpenCDXTinkarServiceImpl initialized");
    }

    @Cacheable(value = "search")
    @Override
    public TinkarSearchQueryResponse search(TinkarSearchQueryRequest request) {
        try {
            log.info("search query - {}", request.getQuery());
            PrimitiveDataSearchResult[] searchResults = primitive.search(request.getQuery(), request.getMaxResults());
            TinkarSearchQueryResult[] results =
                    Arrays.stream(searchResults).map(this::extract).toArray(TinkarSearchQueryResult[]::new);

            return TinkarSearchQueryResponse.newBuilder()
                    .addAllResults(Arrays.asList(results))
                    .build();
        } catch (Exception e) {
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Search Failed", e);
        }
    }

    @Cacheable(value = "getEntity")
    @Override
    public TinkarGetResult getEntity(TinkarGetRequest request) {
        try {
            List<String> descriptions =
                    primitive.descriptionsOf(List.of(PublicIds.of(UUID.fromString(request.getConceptId()))));
            return TinkarGetResult.newBuilder()
                    .setConceptId(request.getConceptId())
                    .setDescription(descriptions.getFirst())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Entity Get Failed", e);
        }
    }

    @Cacheable(value = "getTinkarChildConcepts")
    @Override
    public TinkarGetResponse getTinkarChildConcepts(TinkarGetRequest request) {
        PublicId parentConceptId = PublicIds.of(UUID.fromString(request.getConceptId()));
        List<PublicId> children = primitive.childrenOf(parentConceptId);
        List<TinkarGetResult> results = children.stream()
                .map(d -> this.getEntity(TinkarGetRequest.newBuilder()
                        .setConceptId(d.asUuidArray()[0].toString())
                        .build()))
                .toList();

        return TinkarGetResponse.newBuilder().addAllResults(results).build();
    }

    @Cacheable(value = "getTinkarDescendantConcepts")
    @Override
    public TinkarGetResponse getTinkarDescendantConcepts(TinkarGetRequest request) {
        PublicId parentConceptId = PublicIds.of(UUID.fromString(request.getConceptId()));
        List<PublicId> children = primitive.descendantsOf(parentConceptId);
        List<TinkarGetResult> results = children.stream()
                .map(d -> this.getEntity(TinkarGetRequest.newBuilder()
                        .setConceptId(d.asUuidArray()[0].toString())
                        .build()))
                .toList();

        return TinkarGetResponse.newBuilder().addAllResults(results).build();
    }

    @Cacheable(value = "getLIDRRecordConceptsFromTestKit")
    @Override
    public TinkarGetResponse getLIDRRecordConceptsFromTestKit(TinkarGetRequest request) {
        PublicId testKitConceptId = PublicIds.of(UUID.fromString(request.getConceptId()));
        List<PublicId> lidrRecords = primitive.getLidrRecordSemanticsFromTestKit(testKitConceptId);
        List<TinkarGetResult> results = lidrRecords.stream()
                .map(d -> this.getEntity(TinkarGetRequest.newBuilder()
                        .setConceptId(d.asUuidArray()[0].toString())
                        .build()))
                .toList();

        return TinkarGetResponse.newBuilder().addAllResults(results).build();
    }

    @Cacheable(value = "getResultConformanceConceptsFromLIDRRecord")
    @Override
    public TinkarGetResponse getResultConformanceConceptsFromLIDRRecord(TinkarGetRequest request) {
        PublicId lidrRecordConceptId = PublicIds.of(UUID.fromString(request.getConceptId()));
        List<PublicId> resultConformances = primitive.getResultConformancesFromLidrRecord(lidrRecordConceptId);
        List<TinkarGetResult> results = resultConformances.stream()
                .map(d -> this.getEntity(TinkarGetRequest.newBuilder()
                        .setConceptId(d.asUuidArray()[0].toString())
                        .build()))
                .toList();

        return TinkarGetResponse.newBuilder().addAllResults(results).build();
    }

    @Cacheable(value = "getAllowedResultConceptsFromResultConformance")
    @Override
    public TinkarGetResponse getAllowedResultConceptsFromResultConformance(TinkarGetRequest request) {
        PublicId resultConformanceConceptId = PublicIds.of(UUID.fromString(request.getConceptId()));
        List<PublicId> allowedResults = primitive.getAllowedResultsFromResultConformance(resultConformanceConceptId);
        List<TinkarGetResult> results = allowedResults.stream()
                .map(d -> this.getEntity(TinkarGetRequest.newBuilder()
                        .setConceptId(d.asUuidArray()[0].toString())
                        .build()))
                .toList();

        return TinkarGetResponse.newBuilder().addAllResults(results).build();
    }

    private TinkarSearchQueryResult extract(PrimitiveDataSearchResult searchResult) {
        return TinkarSearchQueryResult.newBuilder()
                .setNid(searchResult.nid())
                .setRcNid(searchResult.rcNid())
                .setPatternNid(searchResult.patternNid())
                .setFieldIndex(searchResult.fieldIndex())
                .setScore(searchResult.score())
                .setHighlightedString(searchResult.highlightedString())
                .build();
    }
}
