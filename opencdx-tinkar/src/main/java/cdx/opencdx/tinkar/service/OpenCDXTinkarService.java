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

import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;

/**
 * Interface to search TINKAR Repository
 */
public interface OpenCDXTinkarService {

    /**
     * Method to search for a term
     * @param query String containing the term to search
     * @param maxResultSize Integer with the maximum number of results.
     * @return Array of the found data.
     */
    PrimitiveDataSearchResult[] search(String query, int maxResultSize);

    /**
     * Method to search for an NID
     * @param nid The NID to search for on the entity.
     * @return String representing the entity.
     */
    String getEntity(int nid);
}
