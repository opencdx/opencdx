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
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXClassificationMessageService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
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
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor to use the OpenCDXMessageService to send the notificaiton.
     * @param messageService Message service to use to send.
     * @param openCDXDocumentValidator Document validator to validate the document.
     * @param openCDXProfileRepository IAM user repository to use to get the user.
     * @param openCDXCurrentUser Current user to use to get the current user.
     */
    public OpenCDXClassificationMessageServiceImpl(
            OpenCDXMessageService messageService,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.messageService = messageService;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    public void submitQuestionnaire(
            OpenCDXIdentifier patientId, OpenCDXIdentifier questionnaireUserId, OpenCDXIdentifier mediaId) {
        log.info(
                "Submitting questionnaire for user: {}, Questionnaire: {}, Media: {}",
                patientId.toHexString(),
                questionnaireUserId.toHexString(),
                mediaId == null ? "NULL" : mediaId.toHexString());
        this.openCDXDocumentValidator.validateDocumentOrThrow("questionnaire-user", questionnaireUserId);

        UserAnswer.Builder builder = getUserPreparedAnswer(patientId);

        builder.setUserQuestionnaireId(questionnaireUserId.toHexString());
        if (mediaId != null) {
            builder.setMediaId(mediaId.toHexString());
        }
        this.messageService.send(
                OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT,
                ClassificationRequest.newBuilder()
                        .setUserAnswer(builder.build())
                        .build());
    }

    @Override
    public void submitConnectedTest(
            OpenCDXIdentifier patientId, OpenCDXIdentifier connectedTestId, OpenCDXIdentifier mediaId) {
        log.info(
                "Submitting Connected Test for patient: {}, Connected Test: {}, Media: {}",
                patientId.toHexString(),
                connectedTestId.toHexString(),
                mediaId == null ? "NULL" : mediaId.toHexString());
        this.openCDXDocumentValidator.validateDocumentOrThrow("connected-test", connectedTestId);

        UserAnswer.Builder builder = getUserPreparedAnswer(patientId);

        builder.setConnectedTestId(connectedTestId.toHexString());
        if (mediaId != null) {
            builder.setMediaId(mediaId.toHexString());
        }
        this.messageService.send(
                OpenCDXMessageService.CLASSIFICATION_MESSAGE_SUBJECT,
                ClassificationRequest.newBuilder()
                        .setUserAnswer(builder.build())
                        .build());
    }

    private UserAnswer.Builder getUserPreparedAnswer(OpenCDXIdentifier patientId) {
        log.trace("Validating User");
        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(patientId)
                .orElseThrow(() -> new OpenCDXNotFound(
                        "OpenCDXClassificationMessageServiceImpl", 1, "Patient " + patientId + " does not found "));

        UserAnswer.Builder builder = UserAnswer.newBuilder().setPatientId(patientId.toHexString());

        builder.setSubmittingUserId(openCDXCurrentUser.getCurrentUser().getId().toHexString());
        if (patient.getGender() != null) {
            builder.setGender(patient.getGender());
        }

        if (patient.getDateOfBirth() != null) {
            Instant dobInstant = patient.getDateOfBirth();
            ZonedDateTime dobZoned = dobInstant.atZone(ZoneId.systemDefault());
            Period age =
                    Period.between(dobZoned.toLocalDate(), ZonedDateTime.now().toLocalDate());

            builder.setAge(age.getYears());
        }

        return builder;
    }
}
