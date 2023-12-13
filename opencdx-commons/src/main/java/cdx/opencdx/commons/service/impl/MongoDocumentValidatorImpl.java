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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * This class is used to validate if a document exists in a collection.
 */
@Slf4j
@Component
public class MongoDocumentValidatorImpl implements cdx.opencdx.commons.service.OpenCDXDocumentValidator {

    public static final String DOMAIN = "MongoDocumentValidator";
    private final MongoTemplate mongoTemplate;

    /**
     * Constructor
     *
     * @param mongoTemplate MongoTemplate to use for validation
     */
    @SuppressWarnings("java:S3010")
    public MongoDocumentValidatorImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean isCollectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * Check if a document exists in a collection
     *
     * @param collectionName Name of the collection
     * @param documentId     Id of the document
     * @return true if the document exists, false otherwise
     */
    @Override
    public boolean documentExists(String collectionName, ObjectId documentId) {
        log.debug("Checking if document {} exists in collection {}", documentId.toHexString(), collectionName);
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
    }

    /**
     * Check if a document exists in a collection and log an error if it does not
     *
     * @param collectionName Name of the collection
     * @param documentId     Id of the document
     * @return true if the document exists, false otherwise
     */
    @Override
    public boolean validateDocumentOrLog(String collectionName, ObjectId documentId) {
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
    @Override
    public void validateDocumentOrThrow(String collectionName, ObjectId documentId) {
        if (!isCollectionExists(collectionName)) {
            throw new OpenCDXNotFound(DOMAIN, 1, "Collection " + collectionName + " does not exist");
        }
        if (!documentExists(collectionName, documentId)) {
            throw new OpenCDXNotFound(
                    DOMAIN, 2, "Document " + documentId + " does not exist in collection " + collectionName);
        }
    }

    /**
     * Check if all documents in the given list exist in the specified collection
     *
     * @param collectionName Name of the collection
     * @param documentIds    List of document IDs
     * @return true if all documents exist, false otherwise
     */
    @Override
    public boolean allDocumentsExist(String collectionName, List<ObjectId> documentIds) {
        Criteria criteria = Criteria.where("_id").in(documentIds);
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
    @Override
    public boolean validateDocumentsOrLog(String collectionName, List<ObjectId> documentIds) {
        if (!isCollectionExists(collectionName)) {
            log.error("Collection {} does not exist", collectionName);
            return false;
        }
        if (!allDocumentsExist(collectionName, documentIds)) {
            log.error(
                    "Documents {} does not exist in collection {}",
                    documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")),
                    collectionName);
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
    @Override
    public void validateDocumentsOrThrow(String collectionName, List<ObjectId> documentIds) {
        if (!isCollectionExists(collectionName)) {
            throw new OpenCDXNotFound(DOMAIN, 3, "Collection " + collectionName + " does not exist");
        }
        if (!allDocumentsExist(collectionName, documentIds)) {
            throw new OpenCDXNotFound(
                    DOMAIN,
                    4,
                    "Documents "
                            + documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", "))
                            + " does not exist in collection " + collectionName);
        }
    }
}
