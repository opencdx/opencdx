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
package cdx.opencdx.commons.utils;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is used to validate if a document exists in a collection.
 */
@Slf4j
@Component
public class MongoDocumentValidator {

    private final MongoTemplate mongoTemplate;

    @Getter
    private static MongoDocumentValidator instance;

    /**
     * Constructor
     *
     * @param mongoTemplate MongoTemplate to use for validation
     */
    @SuppressWarnings("java:S3010")
    public MongoDocumentValidator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        MongoDocumentValidator.instance = this;
    }

    protected boolean isCollectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * Check if a document exists in a collection
     *
     * @param collectionName Name of the collection
     * @param documentId     Id of the document
     * @return true if the document exists, false otherwise
     */
    public boolean documentExists(String collectionName, String documentId) {
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(new ObjectId(documentId))), collectionName);
    }

    /**
     * Check if a document exists in a collection and log an error if it does not
     *
     * @param collectionName Name of the collection
     * @param documentId     Id of the document
     * @return true if the document exists, false otherwise
     */
    public boolean validateDocumentOrLog(String collectionName, String documentId) {
        if (!isCollectionExists(collectionName)) {
            log.error("Collection {} does not exist", collectionName);
            return false;
        }
        if (!documentExists(collectionName, documentId)) {
            log.error("Document {} does not exist in collection {}", documentId, collectionName);
            return false;
        }
        return true;
    }

    /**
     * Check if a document exists in a collection and throw an exception if it does not
     *
     * @param collectionName Name of the collection
     * @param documentId     Id of the document
     */
    public void validateDocumentOrThrow(String collectionName, String documentId) {
        if (!isCollectionExists(collectionName)) {
            throw new OpenCDXNotFound("MongoDocumentValidator", 1, "Collection " + collectionName + " does not exist");
        }
        if (!documentExists(collectionName, documentId)) {
            throw new OpenCDXNotFound(
                    "MongoDocumentValidator",
                    2,
                    "Document " + documentId + " does not exist in collection " + collectionName);
        }
    }

    /**
     * Check if all documents in the given list exist in the specified collection
     *
     * @param collectionName Name of the collection
     * @param documentIds    List of document IDs
     * @return true if all documents exist, false otherwise
     */
    public boolean allDocumentsExist(String collectionName, List<String> documentIds) {
        Criteria criteria = Criteria.where("_id").in(convertToObjectIdList(documentIds));
        Query query = Query.query(criteria);
        return mongoTemplate.exists(query, collectionName);
    }

    /**
     * Check if a document exists in a collection and log an error if it does not
     *
     * @param collectionName Name of the collection
     * @param documentIds    List of document IDs
     * @return true if the document exists, false otherwise
     */
    public boolean validateDocumentsOrLog(String collectionName, List<String> documentIds) {
        if (!isCollectionExists(collectionName)) {
            log.error("Collection {} does not exist", collectionName);
            return false;
        }
        if (!allDocumentsExist(collectionName, documentIds)) {
            log.error("Documents {} does not exist in collection {}", String.join(", ", documentIds), collectionName);
            return false;
        }
        return true;
    }

    /**
     * Check if a document exists in a collection and throw an exception if it does not
     *
     * @param collectionName Name of the collection
     * @param documentIds    List of document IDs
     */
    public void validateDocumentsOrThrow(String collectionName, List<String> documentIds) {
        if (!isCollectionExists(collectionName)) {
            throw new OpenCDXNotFound("MongoDocumentValidator", 3, "Collection " + collectionName + " does not exist");
        }
        if (!allDocumentsExist(collectionName, documentIds)) {
            throw new OpenCDXNotFound(
                    "MongoDocumentValidator",
                    4,
                    "Documents " + String.join(", ", documentIds) + " does not exist in collection " + collectionName);
        }
    }

    // Helper method to convert document IDs to ObjectId list
    private List<ObjectId> convertToObjectIdList(List<String> documentIds) {
        return documentIds.stream()
                .map(ObjectId::new)
                .toList();
    }
}
