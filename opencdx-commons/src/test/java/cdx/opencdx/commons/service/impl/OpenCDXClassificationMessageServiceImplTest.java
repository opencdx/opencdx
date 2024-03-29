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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.common.Gender;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpenCDXClassificationMessageServiceImplTest {

    private OpenCDXMessageService messageService = Mockito.mock(OpenCDXMessageService.class);
    private OpenCDXDocumentValidator openCDXDocumentValidator = Mockito.mock(OpenCDXDocumentValidator.class);
    private OpenCDXProfileRepository openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
    private OpenCDXCurrentUser openCDXCurrentUser = Mockito.mock(OpenCDXCurrentUser.class);

    private OpenCDXClassificationMessageServiceImpl service = new OpenCDXClassificationMessageServiceImpl(
            messageService, openCDXDocumentValidator, openCDXProfileRepository, openCDXCurrentUser);

    @Test
    void testSubmitConnectedTest() {

        // Constructs mock objects
        OpenCDXIAMUserRepository openCDXIAMUserRepositoryMock = Mockito.mock(OpenCDXIAMUserRepository.class);
        OpenCDXDocumentValidator openCDXDocumentValidatorMock = Mockito.mock(OpenCDXDocumentValidator.class);
        OpenCDXMessageService messageServiceMock = Mockito.mock(OpenCDXMessageService.class);

        // Input id values
        OpenCDXIdentifier userId = new OpenCDXIdentifier();
        OpenCDXIdentifier connectedTestId = new OpenCDXIdentifier();
        OpenCDXIdentifier mediaId = new OpenCDXIdentifier();

        // Constructs user model
        OpenCDXProfileModel userModel = new OpenCDXProfileModel();
        userModel.setId(userId);
        userModel.setGender(Gender.GENDER_FEMALE);

        // Sets behavior for mock objects
        Mockito.when(openCDXProfileRepository.findById(userId)).thenReturn(Optional.of(userModel));

        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        // Constructs target object
        OpenCDXClassificationMessageServiceImpl target = new OpenCDXClassificationMessageServiceImpl(
                messageServiceMock, openCDXDocumentValidatorMock, openCDXProfileRepository, openCDXCurrentUser);

        // Calls the method to be tested
        target.submitConnectedTest(userId, connectedTestId, mediaId);

        // Verifies behavior
        Mockito.verify(openCDXProfileRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(openCDXDocumentValidatorMock, Mockito.times(1))
                .validateDocumentOrThrow("connected-test", connectedTestId);
        Mockito.verify(messageServiceMock, Mockito.times(1))
                .send(
                        Mockito.eq(OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT),
                        Mockito.any(ClassificationRequest.class));

        Assertions.assertTrue(true);
    }

    @Test
    void submitQuestionnaire_withValidInputs_noExceptionThrown() {

        OpenCDXIdentifier userId = new OpenCDXIdentifier();
        OpenCDXIdentifier questionnaireUserId = new OpenCDXIdentifier();
        OpenCDXIdentifier mediaId = new OpenCDXIdentifier();

        OpenCDXProfileModel userModel = new OpenCDXProfileModel();
        userModel.setId(userId);
        userModel.setGender(Gender.GENDER_FEMALE);
        userModel.setDateOfBirth(Instant.now());

        Mockito.when(openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        when(openCDXProfileRepository.findById(userId)).thenReturn(Optional.of(userModel));

        Assertions.assertDoesNotThrow(() -> service.submitQuestionnaire(userId, questionnaireUserId, mediaId));
    }

    @Test
    void submitQuestionnaire_withInvalidUserId_throwsNotFound() {

        OpenCDXIdentifier userId = new OpenCDXIdentifier();
        OpenCDXIdentifier questionnaireUserId = new OpenCDXIdentifier();
        OpenCDXIdentifier mediaId = new OpenCDXIdentifier();

        when(openCDXProfileRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(OpenCDXNotFound.class, () -> service.submitQuestionnaire(userId, questionnaireUserId, mediaId));
    }
}
