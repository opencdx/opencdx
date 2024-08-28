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
package cdx.opencdx.tinkar.service;

import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import java.util.List;

/**
 * The TinkarPrimitive interface provides methods for performing various operations
 * on Tinkar primitive data.
 */
public interface TinkarPrimitive {

    /**
     * Performs a search based on the given string and integer.
     *
     * @param str The string to search for
     * @param i The integer to search for
     * @return An array of PrimitiveDataSearchResult objects representing the search results
     * @throws OpenCDXInternal if an internal server error occurs during the search operation
     */
    PrimitiveDataSearchResult[] search(String str, Integer i) throws OpenCDXInternal;

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     * @throws OpenCDXInternal If there is an internal server error.
     */
    List<PublicId> descendantsOf(PublicId parentConceptId);

    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId the parent PublicId
     * @return a list of child PublicIds
     * @throws OpenCDXInternal if an internal server error occurs
     */
    List<PublicId> childrenOf(PublicId parentConceptId);

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId the concept Id of the test kit
     * @return a list of PublicIds representing the Lidr record semantics
     * @throws OpenCDXInternal if there is an internal server error
     */
    List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId);

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId);

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     * @throws OpenCDXInternal If an internal server error occurs.
     */
    List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId);

    /**
     * Initializes the primitive data by providing the parent and child paths.
     *
     * @param pathParent the path of the parent directory
     * @param pathChild the path of the child directory
     */
    void initializePrimitiveData(String pathParent, String pathChild);

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    List<String> descriptionsOf(List<PublicId> conceptIds);
}
