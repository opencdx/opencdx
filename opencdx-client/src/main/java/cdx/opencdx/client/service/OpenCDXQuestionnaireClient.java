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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.questionnaire.*;

/**
 * Interface for communicating with the Questionnaire microservice.
 */
public interface OpenCDXQuestionnaireClient {
    /**
     * Method to gRPC Call Questionnaire Service createQuestionnaire() api.
     * @param request Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Questionnaire createQuestionnaire(QuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service updateQuestionnaire() api.
     * @param request Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Questionnaire updateQuestionnaire(QuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getSubmittedQuestionnaire() api.
     * @param request Get Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Questionnaire getSubmittedQuestionnaire(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getSubmittedQuestionnaireList() api.
     * @param request Get Questionnaire List request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Questionnaires getSubmittedQuestionnaireList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service deleteSubmittedQuestionnaire() api.
     * @param request Delete Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse deleteSubmittedQuestionnaire(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service createQuestionnaireData() api.
     * @param request Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse createQuestionnaireData(
            QuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service updateQuestionnaireData() api.
     * @param request Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse updateQuestionnaireData(
            QuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getQuestionnaireData() api.
     * @param request Get Questionnaire Request Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SystemQuestionnaireData getQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getQuestionnaireDataList() api.
     * @param request Get Questionnaire List request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SystemQuestionnaireData getQuestionnaireDataList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service deleteQuestionnaireRequest() api.
     * @param request Delete Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse deleteQuestionnaireData(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service createClientQuestionnaireData() api.
     * @param request Client Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse createClientQuestionnaireData(
            ClientQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service updateClientQuestionnaireData() api.
     * @param request Client Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse updateClientQuestionnaireData(
            ClientQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getClientQuestionnaireData() api.
     * @param request Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ClientQuestionnaireData getClientQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getClientQuestionnaireDataList() api.
     * @param request Get Questionnaire List Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ClientQuestionnaireData getClientQuestionnaireDataList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service deleteClientQuestionnaireData() api.
     * @param request Delete Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse deleteClientQuestionnaireData(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service createUserQuestionnaireData() api.
     * @param request User Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    SubmissionResponse createUserQuestionnaireData(
            UserQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Questionnaire Service getUserQuestionnaireData() api.
     * @param request Get Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    UserQuestionnaireData getUserQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
