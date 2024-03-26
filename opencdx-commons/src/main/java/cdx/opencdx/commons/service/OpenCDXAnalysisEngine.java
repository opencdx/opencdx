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

import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.RuleSetsRequest;
import cdx.opencdx.grpc.neural.classification.RuleSetsResponse;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;

/**
 * Interface for the ClassificationProcessorService.  Performs classification of Questionnaire and Connected Test
 * data.
 */
public interface OpenCDXAnalysisEngine {

    /**
     * Analyzes a questionnaire and returns the classification response.
     *
     * @param patient               the OpenCDXProfileModel representing the patient
     * @param userAnswer            the UserAnswer object containing the user's answer
     * @param media                 the Media object containing media related to the test
     * @param userQuestionnaireData the UserQuestionnaireData object containing the user's questionnaire data
     * @return the ClassificationResponse object representing the classification response
     */
    ClassificationResponse analyzeQuestionnaire(
            OpenCDXProfileModel patient,
            UserAnswer userAnswer,
            Media media,
            UserQuestionnaireData userQuestionnaireData);

    /**
     * Analyzes a connected test and returns the classification response.
     *
     * @param patient           the OpenCDXProfileModel representing the patient
     * @param userAnswer        the UserAnswer object containing the user's answer
     * @param media             the Media object containing media related to the test
     * @param connectedTest     the ConnectedTest object representing the connected test
     * @param testDetailsMedia  the Media object containing test details
     * @return the ClassificationResponse object representing the classification response
     */
    ClassificationResponse analyzeConnectedTest(
            OpenCDXProfileModel patient,
            UserAnswer userAnswer,
            Media media,
            ConnectedTest connectedTest,
            Media testDetailsMedia);

    /**
     * Gets the rule sets for the given client rules request.
     *
     * @param request the ClientRulesRequest object containing the request data
     * @return the RuleSetsResponse object representing the rule sets response
     */
    RuleSetsResponse getRuleSets(RuleSetsRequest request);
}
