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
package cdx.opencdx.questionnaire.service;

import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.questionnaire.*;

/**
 * Interface for the QuestionnaireService
 */
public interface OpenCDXQuestionnaireService {

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision request.
     */
    Questionnaire createQuestionnaire(QuestionnaireRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision request.
     */
    Questionnaire updateQuestionnaire(QuestionnaireRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision request.
     */
    Questionnaire getSubmittedQuestionnaire(GetQuestionnaireRequest request);

    /**
     * Refresh the Questionnaire
     * @param request id of the questionnaire to refresh
     * @return Message generated for the data submission request.
     */
    Questionnaire refreshQuestionnaire(GetQuestionnaireRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision request.
     */
    Questionnaires getSubmittedQuestionnaireList(GetQuestionnaireListRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the delete questionnaire request.
     */
    SubmissionResponse deleteSubmittedQuestionnaire(DeleteQuestionnaireRequest request);

    // System Level Questionnaire
    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the create questionnaire request.
     */
    SubmissionResponse createQuestionnaireData(QuestionnaireDataRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the update questionnaire request.
     */
    SubmissionResponse updateQuestionnaireData(QuestionnaireDataRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision request.
     */
    SystemQuestionnaireData getQuestionnaireData(GetQuestionnaireRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the data submision requests.
     */
    SystemQuestionnaireData getQuestionnaireDataList(GetQuestionnaireListRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the delete questionnaire request.
     */
    SubmissionResponse deleteQuestionnaireData(DeleteQuestionnaireRequest request);

    // Client Level Questionnaire
    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the create client questionnaire request.
     */
    SubmissionResponse createClientQuestionnaireData(ClientQuestionnaireDataRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the update client questionnaire request.
     */
    SubmissionResponse updateClientQuestionnaireData(ClientQuestionnaireDataRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the client questionnaire request.
     */
    ClientQuestionnaireData getClientQuestionnaireData(GetQuestionnaireRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the client questionnaire requests.
     */
    ClientQuestionnaireData getClientQuestionnaireDataList(GetQuestionnaireListRequest request);

    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the delete client questionnaire request.
     */
    SubmissionResponse deleteClientQuestionnaireData(DeleteQuestionnaireRequest request);

    // User Level Questionnaire
    /**
     * Process the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the create user questionnaire request.
     */
    SubmissionResponse createUserQuestionnaireData(UserQuestionnaireDataRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the user questionnaire request.
     */
    UserQuestionnaireData getUserQuestionnaireData(GetQuestionnaireRequest request);

    /**
     * Get the QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the user questionnaire requests.
     */
    UserQuestionnaireDataResponse getUserQuestionnaireDataList(GetQuestionnaireListRequest request);
}
