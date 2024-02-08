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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXClassificationMessageService;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * Implementation for OpenCDXClassificationMessageService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXClassificationMessageServiceImpl implements OpenCDXClassificationMessageService {

    private final OpenCDXMessageService messageService;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructor to use the OpenCDXMessageService to send the notificaiton.
     * @param messageService Message service to use to send.
     * @param openCDXDocumentValidator Document validator to validate the document.
     * @param openCDXIAMUserRepository IAM user repository to use to get the user.
     */
    public OpenCDXClassificationMessageServiceImpl(
            OpenCDXMessageService messageService,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.messageService = messageService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    public void submitQuestionnaire(ObjectId userId, ObjectId questionnaireUserId, ObjectId mediaId) {
        this.openCDXDocumentValidator.validateDocumentOrThrow("questionnaire-user", questionnaireUserId);

        UserAnswer.Builder builder = getUserPreparedAnswer(userId);

        builder.setUserQuestionnaireId(questionnaireUserId.toHexString());
        builder.setMediaId(mediaId.toHexString());

        this.messageService.send(
                OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT,
                ClassificationRequest.newBuilder()
                        .setUserAnswer(builder.build())
                        .build());
    }

    @Override
    public void submitConnectedTest(ObjectId userId, ObjectId connectedTestId, ObjectId mediaId) {
        this.openCDXDocumentValidator.validateDocumentOrThrow("connected-test", connectedTestId);

        UserAnswer.Builder builder = getUserPreparedAnswer(userId);

        builder.setConnectedTestId(connectedTestId.toHexString());
        builder.setMediaId(mediaId.toHexString());

        this.messageService.send(
                OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT,
                ClassificationRequest.newBuilder()
                        .setUserAnswer(builder.build())
                        .build());
    }

    private UserAnswer.Builder getUserPreparedAnswer(ObjectId userId) {
        OpenCDXIAMUserModel user = this.openCDXIAMUserRepository
                .findById(userId)
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXClassificationMessageServiceImpl", 1, "User " + userId + " does not found "));

        UserAnswer.Builder builder =
                UserAnswer.newBuilder().setGender(user.getGender()).setUserId(userId.toHexString());

        if (user.getDateOfBirth() != null) {
            Instant dobInstant = user.getDateOfBirth();
            ZonedDateTime dobZoned = dobInstant.atZone(ZoneId.systemDefault());
            Period age =
                    Period.between(dobZoned.toLocalDate(), ZonedDateTime.now().toLocalDate());

            builder.setAge(age.getYears());
        }

        return builder;
    }
}
