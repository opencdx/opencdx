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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.utils.MongoDocumentExists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SuppressWarnings("java:S6068")
class MongoDocumentValidatorImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private MongoDocumentValidatorImpl documentValidator;

    @Mock
    private OpenCDXIAMUserRepository openCDXIAMUserRepository;

    ;

    @BeforeEach
    void setUp() {
        this.mongoTemplate = Mockito.mock(MongoTemplate.class);
        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.existsById(any())).thenReturn(true);
        this.documentValidator = new MongoDocumentValidatorImpl(
                mongoTemplate, new MongoDocumentExists(mongoTemplate), this.openCDXIAMUserRepository);
    }

    @Test
    void testDocumentExists() {
        String collectionName = "testCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.documentExists(collectionName, documentId));

        // Verify that mongoTemplate.exists was called with the correct parameters
        verify(mongoTemplate).exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
    }

    @Test
    void testValidateDocumentsOrThrow() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertDoesNotThrow(() -> documentValidator.validateDocumentsOrThrow(collectionName, documentIds));

        // Verify that isCollectionExists and mongoTemplate.exists were called with the correct parameters
        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentOrLog() {
        String collectionName = "testCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentOrLog(collectionName, documentId));

        // Verify that isCollectionExists and mongoTemplate.exists were called with the correct parameters
        verify(mongoTemplate).exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
    }

    @Test
    void testValidateDocumentOrThrow() {
        String collectionName = "testCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertDoesNotThrow(() -> documentValidator.validateDocumentOrThrow(collectionName, documentId));

        // Verify that isCollectionExists and mongoTemplate.exists were called with the correct parameters
        verify(mongoTemplate).exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
    }

    @Test
    void testValidateDocumentOrThrowWithDocumentNotFound() {
        String collectionName = "testCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);

        // Mocking mongoTemplate.exists to return false
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(false);

        OpenCDXNotFound exception = assertThrows(
                OpenCDXNotFound.class, () -> documentValidator.validateDocumentOrThrow(collectionName, documentId));

        assertEquals("Document " + documentId + " does not exist in collection testCollection", exception.getMessage());
    }

    @Test
    void testAllDocumentsExist() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.allDocumentsExist(collectionName, documentIds));

        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentsOrLog() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentsOrLog(collectionName, documentIds));

        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentsOrThrow2() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.getCollectionNames()).thenReturn(new HashSet<>(List.of(collectionName)));
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertDoesNotThrow(() -> documentValidator.validateDocumentsOrThrow(documentIds));
    }

    @Test
    void testValidateDocumentsOrThrow3() {
        String collectionName = "testCollection";

        assertDoesNotThrow(() -> documentValidator.validateDocumentsOrThrow(Collections.emptyList()));
    }

    @Test
    void testValidateDocumentsOrThrow4() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.getCollectionNames()).thenReturn(new HashSet<>(List.of(collectionName)));
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(false);

        assertThrows(OpenCDXNotFound.class, () -> documentValidator.validateDocumentsOrThrow(documentIds));
    }

    @Test
    void testValidateDocumentsOrThrowWhenDocumentDoesNotExist() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(false);

        OpenCDXNotFound exception = assertThrows(
                OpenCDXNotFound.class, () -> documentValidator.validateDocumentsOrThrow(collectionName, documentIds));

        assertEquals(
                "Documents " + documentIds.stream().map(ObjectId::toHexString).collect(Collectors.joining(", "))
                        + " does not exist in collection " + collectionName,
                exception.getMessage());

        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentOrLog_DocumentNotExists() {
        String collectionName = "existingCollection";
        ObjectId documentId = ObjectId.get();

        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(false);

        assertFalse(documentValidator.validateDocumentOrLog(collectionName, documentId));
    }

    @Test
    void testValidateDocumentOrLog_Success() {
        String collectionName = "existingCollection";
        ObjectId documentId = ObjectId.get();

        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentOrLog(collectionName, documentId));
    }

    @Test
    void testValidateDocumentsOrLog_Success() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get());

        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testValidateDocumentsOrLog_Fail() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get());

        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(false);

        assertFalse(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testValidateDocumentsOrLog_EmptyDocumentIds() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Collections.emptyList();

        assertTrue(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testAllDocumentsExist_EmptyDocumentIds() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Collections.emptyList();

        assertTrue(documentValidator.allDocumentsExist(collectionName, documentIds));
    }

    @Test
    void testValidateOrganizationWorkspaceOrThrow() {

        ObjectId organization = ObjectId.get();
        ObjectId workspace = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(anyString())).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), anyString())).thenReturn(true);
        when(mongoTemplate.findById(eq(workspace), eq(Document.class), eq("workspace")))
                .thenReturn(Document.parse("{\"_id\": \"" + organization.toHexString() + "\"}"));

        assertDoesNotThrow(() -> documentValidator.validateOrganizationWorkspaceOrThrow(organization, workspace));
    }

    @Test
    void testValidateOrganizationWorkspaceOrThrow_2() {

        ObjectId organization = ObjectId.get();
        ObjectId worksapce = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(anyString())).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(organization))), eq("organization")))
                .thenReturn(false);
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(worksapce))), eq("workspace")))
                .thenReturn(true);

        when(mongoTemplate.findById(eq(organization), eq(Document.class), eq("organization")))
                .thenReturn(Document.parse("{\"_id\": \"" + worksapce.toHexString() + "\"}"));

        assertThrows(
                OpenCDXNotFound.class,
                () -> documentValidator.validateOrganizationWorkspaceOrThrow(organization, worksapce));
    }

    @Test
    void testValidateOrganizationWorkspaceOrThrow_3() {

        ObjectId organization = ObjectId.get();
        ObjectId worksapce = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(anyString())).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(organization))), eq("organization")))
                .thenReturn(true);
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(worksapce))), eq("workspace")))
                .thenReturn(false);

        when(mongoTemplate.findById(eq(organization), eq(Document.class), eq("organization")))
                .thenReturn(Document.parse("{\"_id\": \"" + worksapce.toHexString() + "\"}"));

        assertThrows(
                OpenCDXNotFound.class,
                () -> documentValidator.validateOrganizationWorkspaceOrThrow(organization, worksapce));
    }

    @Test
    void testValidateOrganizationWorkspaceOrThrow_4() {

        ObjectId organization = ObjectId.get();
        ObjectId worksapce = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(anyString())).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(organization))), eq("organization")))
                .thenReturn(true);
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(worksapce))), eq("workspace")))
                .thenReturn(true);

        when(mongoTemplate.findById(eq(organization), eq(Document.class), eq("organization")))
                .thenReturn(Document.parse("{\"_id\": \"bob\"}"));

        assertThrows(
                OpenCDXNotFound.class,
                () -> documentValidator.validateOrganizationWorkspaceOrThrow(organization, worksapce));
    }

    @Test
    void testValidateOrganizationWorkspaceOrThrow_5() {

        ObjectId organization = ObjectId.get();
        ObjectId worksapce = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(anyString())).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(organization))), eq("organization")))
                .thenReturn(true);
        when(mongoTemplate.exists(eq(Query.query(Criteria.where("_id").is(worksapce))), eq("workspace")))
                .thenReturn(true);

        when(mongoTemplate.findById(eq(organization), eq(Document.class), eq("organization")))
                .thenReturn(null);

        assertThrows(
                OpenCDXNotFound.class,
                () -> documentValidator.validateOrganizationWorkspaceOrThrow(organization, worksapce));
    }
}
