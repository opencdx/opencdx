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
package cdx.opencdx.commons.utils;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 *
 * This class is used to validate if a document exists in a collection with caching.
 */
@Slf4j
@Component
public class MongoDocumentExists {
    private final MongoTemplate mongoTemplate;

    /**
     * Constructor
     *
     * @param mongoTemplate MongoTemplate to use for validation
     */
    @SuppressWarnings("java:S3010")
    public MongoDocumentExists(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Checks if a document exists in a collection.
     *
     * @param collectionName Name of the collection to check
     * @param documentId     OpenCDXIdentifier of the document to check
     * @return true if the document exists, false otherwise
     */
    @Cacheable(value = "documentExists", key = "{#collectionName, #documentId}")
    public boolean documentExists(String collectionName, OpenCDXIdentifier documentId) {
        log.debug("Checking if document {} exists in collection {}", documentId.toHexString(), collectionName);
        boolean exists = mongoTemplate.exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
        if (exists) {
            log.debug("Document {} exists in collection {}", documentId.toHexString(), collectionName);
            return true;
        } else {
            log.debug("Document {} does not exist in collection {}", documentId.toHexString(), collectionName);
            return false;
        }
    }
}
