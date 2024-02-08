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
package cdx.opencdx.commons.service;

import org.bson.types.ObjectId;

/**
 * OpenCDXClassificationMessageService is an interface that defines methods for submitting a questionnaire and a connected test for classification.
 */
public interface OpenCDXClassificationMessageService {
    /**
     * Submit a questionnaire for classification
     * @param questionnaireUserId the id of the user questionnaire to submit
     * @param userId the id of the user submitting the questionnaire
     * @param mediaId the id of the media to submit, or null if no media is submitted
     */
    void submitQuestionnaire(ObjectId userId, ObjectId questionnaireUserId, ObjectId mediaId);

    /**
     * Submit a connected test for classification
     * @param connectedTestId the id of the connected test to submit
     * @param userId the id of the user submitting the connected test
     * @param mediaId the id of the media to submit, or null if no media is submitted
     */
    void submitConnectedTest(ObjectId userId, ObjectId connectedTestId, ObjectId mediaId);
}
