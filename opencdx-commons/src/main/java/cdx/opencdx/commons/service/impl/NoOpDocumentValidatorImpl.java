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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import java.util.List;

/**
 * No-op implementation of OpenCDXDocumentValidator
 */
public class NoOpDocumentValidatorImpl implements OpenCDXDocumentValidator {

    /**
     * Default Constructor
     */
    public NoOpDocumentValidatorImpl() {
        // Explicitly do nothing
    }

    @Override
    public boolean documentExists(String collectionName, OpenCDXIdentifier documentId) {
        return true;
    }

    @Override
    public boolean validateDocumentOrLog(String collectionName, OpenCDXIdentifier documentId) {
        return this.documentExists(collectionName, documentId);
    }

    @Override
    public void validateDocumentOrThrow(String collectionName, OpenCDXIdentifier documentId) {
        // Explicitly do nothing
    }

    @Override
    public boolean allDocumentsExist(String collectionName, List<OpenCDXIdentifier> documentIds) {
        return true;
    }

    @Override
    public boolean validateDocumentsOrLog(String collectionName, List<OpenCDXIdentifier> documentIds) {
        return this.allDocumentsExist(collectionName, documentIds);
    }

    @Override
    public void validateDocumentsOrThrow(String collectionName, List<OpenCDXIdentifier> documentIds) {
        // Explicitly do nothing
    }

    @Override
    public void validateOrganizationWorkspaceOrThrow(OpenCDXIdentifier organization, OpenCDXIdentifier workspace) {
        // Explicitly do nothing
    }

    @Override
    public void validateDocumentsOrThrow(List<OpenCDXIdentifier> documentIds) {
        // Explicitly do nothing
    }
}
