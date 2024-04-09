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

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.grpc.tinkar.TinkarGetRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryResponse;
import cdx.opencdx.grpc.tinkar.TinkarQueryResult;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import dev.ikm.tinkar.common.service.*;
import io.micrometer.observation.annotation.Observed;
import java.io.File;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Tinkar Service implementation
 */
@Service
@Observed(name = "opencdx")
@Slf4j
public class OpenCDXTinkarServiceImpl implements OpenCDXTinkarService {

    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    private final String pathParent;

    private final String pathChild;

    /**
     * Default Constructor
     * @param pathParent Parent path
     * @param pathChild Child path
     */
    public OpenCDXTinkarServiceImpl(
            @Value("${data.path.parent}") String pathParent, @Value("${data.path.child}") String pathChild) {
        this.pathParent = pathParent;
        this.pathChild = pathChild;
    }

    /**
     * Initializer
     */
    @Scheduled(initialDelay = 120000, fixedDelay = Long.MAX_VALUE)
    public void initialize() {
        log.info("Initializing OpenCDXTinkarServiceImpl");
        initializePrimitiveData(pathParent, pathChild);
        log.info("OpenCDXTinkarServiceImpl initialized");
    }

    @Override
    public TinkarQueryResponse search(TinkarQueryRequest request) {
        try {
            initializePrimitiveData(pathParent, pathChild);
            PrimitiveDataSearchResult[] searchResults =
                    PrimitiveData.get().search(request.getQuery(), request.getMaxResults());
            TinkarQueryResult[] results =
                    Arrays.stream(searchResults).map(this::extract).toArray(TinkarQueryResult[]::new);

            return TinkarQueryResponse.newBuilder()
                    .addAllResults(Arrays.asList(results))
                    .build();
        } catch (Exception e) {
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Search Failed", e);
        }
    }

    @Override
    public TinkarQueryResult getEntity(TinkarGetRequest request) {
        try {
            initializePrimitiveData(pathParent, pathChild);
            PrimitiveDataSearchResult[] searchResult = PrimitiveData.get().search("nid=" + request.getNid(), 1);
            return (searchResult.length > 0) ? extract(searchResult[0]) : null;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Entity Get Failed", e);
        }
    }

    private void initializePrimitiveData(String pathParent, String pathChild) {
        if (!PrimitiveData.running()) {
            CachingService.clearAll();
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            PrimitiveData.start();
        }
    }

    private TinkarQueryResult extract(PrimitiveDataSearchResult searchResult) {
        return TinkarQueryResult.newBuilder()
                .setNid(searchResult.nid())
                .setRcNid(searchResult.rcNid())
                .setPatternNid(searchResult.patternNid())
                .setFieldIndex(searchResult.fieldIndex())
                .setScore(searchResult.score())
                .setHighlightedString(searchResult.highlightedString())
                .build();
    }
}
