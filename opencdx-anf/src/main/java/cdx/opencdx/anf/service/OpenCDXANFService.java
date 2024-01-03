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
package cdx.opencdx.anf.service;

import cdx.opencdx.grpc.anf.AnfStatement;

/**
 * Interface for the ANF Service
 */
public interface OpenCDXANFService {
    /**
     * Create an ANF Statement
     * @param request the request to create the statement
     * @return the identifier of the statement
     */
    AnfStatement.Identifier createANFStatement(AnfStatement.ANFStatement request);
    /**
     * Get an ANF Statement
     *
     * @param request the request to get the statement
     * @return the statement
     */
    AnfStatement.ANFStatement getANFStatement(AnfStatement.Identifier request);
    /**
     * Update an ANF Statement
     * @param request the request to update the statement
     * @return the identifier of the statement
     */
    AnfStatement.Identifier updateANFStatement(AnfStatement.ANFStatement request);
    /**
     * Delete an ANF Statement
     * @param request the request to delete the statement
     * @return the identifier of the statement
     */
    AnfStatement.Identifier deleteANFStatement(AnfStatement.Identifier request);
}
