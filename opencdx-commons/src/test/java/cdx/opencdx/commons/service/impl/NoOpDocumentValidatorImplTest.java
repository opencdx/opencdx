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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoOpDocumentValidatorImplTest {

    @Test
    void documentExists() {
        Assertions.assertTrue(new NoOpDocumentValidatorImpl().documentExists("test", null));
    }

    @Test
    void validateDocumentOrLog() {
        Assertions.assertTrue(new NoOpDocumentValidatorImpl().validateDocumentOrLog("test", null));
    }

    @Test
    void validateDocumentOrThrow() {
        NoOpDocumentValidatorImpl noOpDocumentValidator = new NoOpDocumentValidatorImpl();
        Assertions.assertDoesNotThrow(() -> noOpDocumentValidator.validateDocumentOrThrow("test", null));
    }

    @Test
    void allDocumentsExist() {
        Assertions.assertTrue(new NoOpDocumentValidatorImpl().allDocumentsExist("test", Collections.emptyList()));
    }

    @Test
    void validateDocumentsOrLog() {
        Assertions.assertTrue(new NoOpDocumentValidatorImpl().validateDocumentsOrLog("test", Collections.emptyList()));
    }

    @Test
    void validateDocumentsOrThrow() {
        NoOpDocumentValidatorImpl noOpDocumentValidator = new NoOpDocumentValidatorImpl();
        Assertions.assertDoesNotThrow(
                () -> noOpDocumentValidator.validateDocumentsOrThrow("test", Collections.emptyList()));
    }

    @Test
    void validateOrganizationWorkspaceOrThrow() {
        NoOpDocumentValidatorImpl noOpDocumentValidator = new NoOpDocumentValidatorImpl();
        Assertions.assertDoesNotThrow(
                () -> noOpDocumentValidator.validateOrganizationWorkspaceOrThrow(ObjectId.get(), ObjectId.get()));
    }
}
