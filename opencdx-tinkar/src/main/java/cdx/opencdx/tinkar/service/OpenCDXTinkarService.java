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

import cdx.opencdx.grpc.service.tinkar.*;

/**
 * Interface to search TINKAR Repository
 */
public interface OpenCDXTinkarService {

    /**
     * Method to search for a term
     *
     * @param request TinkarSearchQueryRequest containing the terms to search
     * @return TinkarSearchQueryResponse
     */
    TinkarSearchQueryResponse search(TinkarSearchQueryRequest request);

    /**
     * Method to search for an NID
     *
     * @param request TinkarGetRequest containing the terms to search
     * @return TinkarGetResult
     */
    TinkarGetResult getEntity(TinkarGetRequest request);

    /**
     * Method to search children for a given public UUID
     *
     * @param request TinkarGetRequest containing the public UUID to search
     * @return TinkarGetResponse
     */
    TinkarGetResponse getTinkarChildConcepts(TinkarGetRequest request);

    /**
     * Method to search descendants for a given public UUID
     *
     * @param request TinkarGetRequest containing the public UUID to search
     * @return TinkarGetResponse
     */
    TinkarGetResponse getTinkarDescendantConcepts(TinkarGetRequest request);

    /**
     * Method to search for LIDR record concepts for a given Test Kit public UUID
     *
     * @param request TinkarGetRequest containing the public UUID of the Test Kit to search
     * @return TinkarGetResponse
     */
    TinkarGetResponse getLIDRRecordConceptsFromTestKit(TinkarGetRequest request);

    /**
     * Method to search for Result Conformance concepts for a given LIDR record public UUID
     *
     * @param request TinkarGetRequest containing the public UUID of the LIDR record to search
     * @return TinkarGetResponse
     */
    TinkarGetResponse getResultConformanceConceptsFromLIDRRecord(TinkarGetRequest request);

    /**
     * Method to search for Allowed Result concepts for a given LIDR record public UUID
     *
     * @param request TinkarGetRequest containing the public UUID of the Result Conformance to search
     * @return TinkarGetResponse
     */
    TinkarGetResponse getAllowedResultConceptsFromResultConformance(TinkarGetRequest request);
}
