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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.utils.MongoDocumentExists;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * This class is used to validate if a document exists in a collection.
 */
@Slf4j
@Component
@Observed(name = "opencdx")
public class MongoDocumentValidatorImpl implements OpenCDXDocumentValidator {

    private static final String DOMAIN = "MongoDocumentValidator";
    private final MongoTemplate mongoTemplate;

    private final MongoDocumentExists mongoDocumentExists;

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructor
     *
     * @param mongoTemplate MongoTemplate to use for validation
     * @param mongoDocumentExists MongoDocumentExists to use for validation with caching
     * @param openCDXIAMUserRepository OpenCDXIAMUserRepository to use for validation with caching
     */
    public MongoDocumentValidatorImpl(
            MongoTemplate mongoTemplate,
            MongoDocumentExists mongoDocumentExists,
            OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.mongoTemplate = mongoTemplate;
        this.mongoDocumentExists = mongoDocumentExists;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    public boolean documentExists(String collectionName, ObjectId documentId) {
        if (collectionName.equals("users")) {
            return this.existsById(documentId);
        }
        return this.mongoDocumentExists.documentExists(collectionName, documentId);
    }

    private boolean existsById(ObjectId objectId) {
        return this.openCDXIAMUserRepository.existsById(objectId);
    }

    @Override
    public boolean validateDocumentOrLog(String collectionName, ObjectId documentId) {
        if (!documentExists(collectionName, documentId)) {
            log.error("Document {} does not exist in collection {}", documentId, collectionName);
            return false;
        }
        return true;
    }

    @Override
    public void validateDocumentOrThrow(String collectionName, ObjectId documentId) {
        if (!documentExists(collectionName, documentId)) {
            throw new OpenCDXNotFound(
                    DOMAIN, 2, "Document " + documentId + " does not exist in collection " + collectionName);
        }
    }

    @Override
    public boolean allDocumentsExist(String collectionName, List<ObjectId> documentIds) {
        if (documentIds.isEmpty()) {
            return true;
        }
        log.debug(
                "Checking if documents {} exist in collection {}",
                documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")),
                collectionName);
        Criteria criteria = Criteria.where("_id").in(documentIds);
        Query query = Query.query(criteria);
        boolean exists = mongoTemplate.exists(query, collectionName);
        if (exists) {
            log.debug(
                    "Documents {} exist in collection {}",
                    documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")),
                    collectionName);
            return true;
        } else {
            log.debug(
                    "Documents {} do not exist in collection {}",
                    documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")),
                    collectionName);
            return false;
        }
    }

    @Override
    public boolean validateDocumentsOrLog(String collectionName, List<ObjectId> documentIds) {
        if (!allDocumentsExist(collectionName, documentIds)) {
            log.error(
                    "Documents {} does not exist in collection {}",
                    documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")),
                    collectionName);
            return false;
        }
        return true;
    }

    @Override
    public void validateDocumentsOrThrow(String collectionName, List<ObjectId> documentIds) {
        if (!allDocumentsExist(collectionName, documentIds)) {
            throw new OpenCDXNotFound(
                    DOMAIN,
                    4,
                    "Documents "
                            + documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", "))
                            + " does not exist in collection " + collectionName);
        }
    }

    @Override
    public void validateOrganizationWorkspaceOrThrow(ObjectId organization, ObjectId workspace) {
        if (!documentExists("organization", organization)) {
            throw new OpenCDXNotFound(DOMAIN, 4, "Organization " + organization + " does not exist");
        }
        if (!documentExists("workspace", workspace)) {
            throw new OpenCDXNotFound(DOMAIN, 5, "Workspace " + workspace + " does not exist");
        }

        Document document = this.findWorkspace(workspace);

        if (document != null && (document.toJson().contains(organization.toHexString()))) {
            return;
        }

        throw new OpenCDXNotFound(
                DOMAIN, 7, "Organization  " + organization + " does not have a workspace " + workspace);
    }

    @Override
    public void validateDocumentsOrThrow(List<ObjectId> documentIds) {
        if (documentIds.isEmpty()) {
            return;
        }
        log.debug(
                "Checking if documents {} exist",
                documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", ")));

        Set<String> collectionNames = mongoTemplate.getCollectionNames();

        for (ObjectId id : documentIds) {
            boolean found = false;
            for (String collectionName : collectionNames) {
                if (documentExists(collectionName, id)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new OpenCDXNotFound(
                        DOMAIN, 6, "Document " + id.toHexString() + " does not exist in any collection");
            }
        }
    }

    /**
     * Find an organization by id
     *
     * @param workspace Workspace id
     * @return Organization document
     */
    @Cacheable(value = "findWorkspace", key = "{#workspace}")
    public Document findWorkspace(ObjectId workspace) {
        return mongoTemplate.findById(workspace, Document.class, "workspace");
    }
}
