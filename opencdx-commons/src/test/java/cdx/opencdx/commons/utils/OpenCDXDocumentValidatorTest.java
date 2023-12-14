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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.impl.MongoDocumentValidatorImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

class OpenCDXDocumentValidatorTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoDocumentValidatorImpl documentValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void testValidateDocumentOrLog() {
        String collectionName = "testCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking isCollectionExists to return true
        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);

        // Mocking mongoTemplate.exists to return true
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentOrLog(collectionName, documentId));

        // Verify that isCollectionExists and mongoTemplate.exists were called with the correct parameters
        verify(mongoTemplate).collectionExists(collectionName);
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
        verify(mongoTemplate).collectionExists(collectionName);
        verify(mongoTemplate).exists(Query.query(Criteria.where("_id").is(documentId)), collectionName);
    }

    @Test
    void testValidateDocumentOrThrowWithCollectionNotFound() {
        String collectionName = "nonExistentCollection";
        ObjectId documentId = ObjectId.get();

        // Mocking isCollectionExists to return false
        when(mongoTemplate.collectionExists(collectionName)).thenReturn(false);

        OpenCDXNotFound exception = assertThrows(
                OpenCDXNotFound.class, () -> documentValidator.validateDocumentOrThrow(collectionName, documentId));

        assertEquals("Collection nonExistentCollection does not exist", exception.getMessage());
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

        verify(mongoTemplate).collectionExists(collectionName);
        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentsOrThrow() {
        String collectionName = "testCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        when(mongoTemplate.exists(any(Query.class), eq(collectionName))).thenReturn(true);

        assertDoesNotThrow(() -> documentValidator.validateDocumentsOrThrow(collectionName, documentIds));

        verify(mongoTemplate).collectionExists(collectionName);
        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentsOrThrowWhenCollectionDoesNotExist() {
        String collectionName = "nonexistentCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get(), ObjectId.get());

        when(mongoTemplate.collectionExists(collectionName)).thenReturn(false);

        OpenCDXNotFound exception = assertThrows(
                OpenCDXNotFound.class, () -> documentValidator.validateDocumentsOrThrow(collectionName, documentIds));

        assertEquals("Collection " + collectionName + " does not exist", exception.getMessage());

        verify(mongoTemplate).collectionExists(collectionName);
        verify(mongoTemplate, never()).exists(any(Query.class), anyString());
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

        verify(mongoTemplate).collectionExists(collectionName);
        verify(mongoTemplate).exists(any(Query.class), eq(collectionName));
    }

    @Test
    void testValidateDocumentOrLog_CollectionNotExists() {
        String collectionName = "nonexistentCollection";
        ObjectId documentId = ObjectId.get();

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(false);

        assertFalse(documentValidator.validateDocumentOrLog(collectionName, documentId));
    }

    @Test
    void testValidateDocumentOrLog_DocumentNotExists() {
        String collectionName = "existingCollection";
        ObjectId documentId = ObjectId.get();

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(false);

        assertFalse(documentValidator.validateDocumentOrLog(collectionName, documentId));
    }

    @Test
    void testValidateDocumentOrLog_Success() {
        String collectionName = "existingCollection";
        ObjectId documentId = ObjectId.get();

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentOrLog(collectionName, documentId));
    }

    @Test
    void testValidateDocumentsOrLog_CollectionNotExists() {
        String collectionName = "nonexistentCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get());

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(false);

        assertFalse(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testValidateDocumentsOrLog_DocumentNotExists() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get());

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(false);

        assertFalse(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testValidateDocumentsOrLog_Success() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Arrays.asList(ObjectId.get(), ObjectId.get());

        Mockito.when(mongoTemplate.collectionExists(collectionName)).thenReturn(true);
        Mockito.when(mongoTemplate.exists(any(), eq(collectionName))).thenReturn(true);

        assertTrue(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testValidateDocumentsOrLog_EmptyDocumentIds() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Collections.emptyList();

        assertFalse(documentValidator.validateDocumentsOrLog(collectionName, documentIds));
    }

    @Test
    void testAllDocumentsExist_EmptyDocumentIds() {
        String collectionName = "existingCollection";
        List<ObjectId> documentIds = Collections.emptyList();

        assertTrue(documentValidator.allDocumentsExist(collectionName, documentIds));
    }
}
