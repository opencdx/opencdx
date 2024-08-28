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
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.tinkar.service.TinkarPrimitive;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.*;
import dev.ikm.tinkar.provider.search.Searcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * The TinkarPrimitiveImpl class provides methods for performing various operations
 * on Tinkar primitive data.
 */
@Service
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class TinkarPrimitiveImpl implements TinkarPrimitive {

    /**
     * The name of the array store that should be opened.
     * This variable is a constant of type String and it is private and static.
     */
    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    /**
     * Default constructor.
     */
    public TinkarPrimitiveImpl() {
        // Default constructor
    }

    /**
     * Performs a search based on the given string and integer.
     *
     * @param str The string to search for
     * @param i The integer to search for
     * @return An array of PrimitiveDataSearchResult objects representing the search results
     * @throws OpenCDXInternal if an internal server error occurs during the search operation
     */
    @Override
    public PrimitiveDataSearchResult[] search(String str, Integer i) throws OpenCDXInternal {
        try {
            return PrimitiveData.get().search(str, i);
        } catch (Exception e) {
            log.error("Error searching for primitive data", e);
            throw new OpenCDXInternal("TinkarPrimitiveImpl", 1, "Error searching for primitive data", e);
        }
    }

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     * @throws OpenCDXInternal If there is an internal server error.
     */
    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        return Searcher.descendantsOf(parentConceptId);
    }

    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId The parent PublicId.
     * @return A list of child PublicIds.
     * @throws OpenCDXInternal If an internal server error occurs.
     */
    @Override
    public List<PublicId> childrenOf(PublicId parentConceptId) {
        return Searcher.childrenOf(parentConceptId);
    }

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId the concept Id of the test kit
     * @return a list of PublicIds representing the Lidr record semantics
     * @throws OpenCDXInternal if there is an internal server error
     */
    @Override
    public List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId) {
        return Searcher.getLidrRecordSemanticsFromTestKit(testKitConceptId);
    }

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    @Override
    public List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId) {
        return Searcher.getResultConformancesFromLidrRecord(lidrRecordConceptId);
    }

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     * @throws OpenCDXInternal If an internal server error occurs.
     */
    @Override
    public List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId) {
        return Searcher.getAllowedResultsFromResultConformance(resultConformanceConceptId);
    }

    /**
     * Initializes the primitive data by providing the parent and child paths.
     *
     * @param pathParent the path of the parent directory
     * @param pathChild the path of the child directory
     */
    @Override
    public void initializePrimitiveData(String pathParent, String pathChild) {
        if (!PrimitiveData.running()) {
            CachingService.clearAll();
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            PrimitiveData.start();
        }
    }

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    @Override
    public List<String> descriptionsOf(List<PublicId> conceptIds) {
        return Searcher.descriptionsOf(conceptIds);
    }
}
