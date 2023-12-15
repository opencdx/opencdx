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
package cdx.opencdx.commons.service;

import java.util.List;
import org.bson.types.ObjectId;

/**
 * Service for validating documents
 */
public interface OpenCDXDocumentValidator {

    /**
     * Check if a document exists
     *
     * @param collectionName Name of the collection to check
     * @param documentId     Id of the document to check
     * @return True if the document exists, false otherwise
     */
    boolean documentExists(String collectionName, ObjectId documentId);

    /**
     * Check if a document exists, if it does not log a warning
     *
     * @param collectionName Name of the collection to check
     * @param documentId     Id of the document to check
     * @return True if the document exists, false otherwise
     */
    boolean validateDocumentOrLog(String collectionName, ObjectId documentId);

    /**
     * Check if a document exists, if it does not throw an exception
     *
     * @param collectionName Name of the collection to check
     * @param documentId     Id of the document to check
     */
    void validateDocumentOrThrow(String collectionName, ObjectId documentId);

    /**
     * Check if a list of documents exists
     *
     * @param collectionName Name of the collection to check
     * @param documentIds    Ids of the documents to check
     * @return True if all documents exist, false otherwise
     */
    boolean allDocumentsExist(String collectionName, List<ObjectId> documentIds);

    /**
     * Check if a list of documents exists, if it does not log a warning
     *
     * @param collectionName Name of the collection to check
     * @param documentIds    Ids of the documents to check
     * @return True if all documents exist, false otherwise
     */
    boolean validateDocumentsOrLog(String collectionName, List<ObjectId> documentIds);

    /**
     * Check if a list of documents exists, if it does not throw an exception
     *
     * @param collectionName Name of the collection to check
     * @param documentIds    Ids of the documents to check
     */
    void validateDocumentsOrThrow(String collectionName, List<ObjectId> documentIds);

    /**
     * Check if a document exists in an organization workspace if it doesn it throws an exception
     *
     * @param organization Organization ID
     * @param workspace    Workspace ID
     */
    void validateOrganizationWorkspaceOrThrow(ObjectId organization, ObjectId workspace);
}
