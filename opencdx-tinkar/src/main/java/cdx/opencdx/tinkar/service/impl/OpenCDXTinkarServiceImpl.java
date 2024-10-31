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
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Tinkar Service implementation
 */
@Service
@Observed(name = "opencdx")
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXTinkarServiceImpl implements OpenCDXTinkarService {

    private final TinkarPrimitive primitive;

    /**
     * Default Constructor
     * @param primitive TinkarPrimitive
     */
    public OpenCDXTinkarServiceImpl(TinkarPrimitive primitive) {
        this.primitive = primitive;
    }

    @Cacheable(value = "search")
    @Override
    public TinkarSearchQueryResponse search(TinkarSearchQueryRequest request) {
        try {
            log.info("search query - {}", request.getQuery());
            List<PublicId> searchResults = primitive.search(request.getQuery(), request.getMaxResults());
            TinkarGetResult[] results =
                    searchResults.stream().map(this::extract).toArray(TinkarGetResult[]::new);

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

    private TinkarGetResult extract(PublicId conceptId) {
        return TinkarGetResult.newBuilder()
                .setConceptId(conceptId.idString())
                .setDescription(primitive.descriptionsOf(Arrays.asList(conceptId)).getFirst())
                .build();
    }

}
