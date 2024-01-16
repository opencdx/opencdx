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
package cdx.opencdx.questionnaire.controller;

import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.service.OpenCDXQuestionnaireService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the /questionnaire api's
 */
@Slf4j
@RestController
@RequestMapping(value = "/questionnaire", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestQuestionnaireController {

    private final OpenCDXQuestionnaireService openCDXQuestionnaireService;

    /**
     * Constructor that takes a QuestionnaireService
     * @param openCDXQuestionnaireService service for processing requests.
     */
    @Autowired
    public OpenCDXRestQuestionnaireController(OpenCDXQuestionnaireService openCDXQuestionnaireService) {
        this.openCDXQuestionnaireService = openCDXQuestionnaireService;
    }

    /**
     * Get RuleSets Rest API
     * @param request ClientRulesRequest indicating organization and workspace
     * @return RuleSetsResponse with the message.
     */
    @PostMapping(value = "/getrulesets")
    public ResponseEntity<RuleSetsResponse> getRuleSets(@RequestBody ClientRulesRequest request) {
        RuleSetsResponse ruleSets = openCDXQuestionnaireService.getRuleSets(request);
        return new ResponseEntity<>(ruleSets, HttpStatus.OK);
    }

    /**
     * Post Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/submitquestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> submitQuestionnaire(@RequestBody QuestionnaireRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .submitQuestionnaire(request)
                                .getSuccess())
                        .setMessage("Executed SubmitQuestionnaire operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Get Questionnaire Rest API
     * @param questionnaireId GetQuestionnaireRequest indicating questionnaire realted data
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/submittedquestionnaire/{Id}")
    public ResponseEntity<Questionnaire> getQuestionnaire(@PathVariable(value = "Id") String questionnaireId) {
        GetQuestionnaireRequest request =
                GetQuestionnaireRequest.newBuilder().setId(questionnaireId).build();

        Questionnaire questionnaire = openCDXQuestionnaireService.getSubmittedQuestionnaire(request);
        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    /**
     * Get Questionnaires Response Rest API
     * @param request GetSubmittedQuestionnaireList indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @GetMapping(value = "/submittedquestionnaires")
    public ResponseEntity<Questionnaires> getQuestionnaires(@RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getSubmittedQuestionnaireList(request), HttpStatus.OK);
    }

    /**
     * Delete Questionnaire Rest API
     * @param request DeleteQuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/deletequestionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteQuestionnaire(@RequestBody DeleteQuestionnaireRequest request) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed DeleteQuestionnaire operation.")
                        .build(),
                HttpStatus.OK);
    }

    // System Questionnaire API's
    /**
     * Post Create Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/system/createquestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> createQuestionnaireData(@RequestBody QuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .createQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed CreateQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post Update Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/system/updatequestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> updateQuestionnaireData(@RequestBody QuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .updateQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed UpdateQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Get Questionnaire Rest API
     * @param request GetQuestionnaireRequest indicating questionnaire realted data
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/system/questionnaire/{Id}")
    public ResponseEntity<SystemQuestionnaireData> getSystemQuestionnaire(
            @RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getQuestionnaireData(request), HttpStatus.OK);
    }

    /**
     * Get Questionnaires Response Rest API
     * @param request GetQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @GetMapping(value = "/system/questionnaires")
    public ResponseEntity<SystemQuestionnaireData> getSystemQuestionnaires(
            @RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getQuestionnaireDataList(request), HttpStatus.OK);
    }

    /**
     * Delete Questionnaire Rest API
     * @param request DeleteQuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/system/deletequestionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteSystemQuestionnaire(
            @RequestBody DeleteQuestionnaireRequest request) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed DeleteQuestionnaire operation.")
                        .build(),
                HttpStatus.OK);
    }

    // Client Questionnaire API's
    /**
     * Post Create Client Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/client/createquestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> createClientQuestionnaireData(
            @RequestBody ClientQuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .createClientQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed CreateClientQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post Update Client Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/client/updatequestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> updateClientQuestionnaireData(
            @RequestBody ClientQuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .updateClientQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed UpdateClientQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Get Client Questionnaire Rest API
     * @param request GetClientQuestionnaireRequest indicating questionnaire realted data
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/client/questionnaire/{Id}")
    public ResponseEntity<ClientQuestionnaireData> getClientQuestionnaire(
            @RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getClientQuestionnaireData(request), HttpStatus.OK);
    }

    /**
     * Get Client Questionnaires Response Rest API
     * @param request GetClientQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @GetMapping(value = "/client/questionnaires")
    public ResponseEntity<ClientQuestionnaireData> getClientQuestionnaires(
            @RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getClientQuestionnaireDataList(request), HttpStatus.OK);
    }

    /**
     * Delete Client Questionnaire Rest API
     * @param request DeleteClientQuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/client/deletequestionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteClientQuestionnaire(
            @RequestBody DeleteQuestionnaireRequest request) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteClientQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed DeleteClientQuestionnaire operation.")
                        .build(),
                HttpStatus.OK);
    }

    // User Questionnaire API's
    /**
     * Post Create User Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/user/createquestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> createUserQuestionnaireData(
            @RequestBody UserQuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .createUserQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed CreateUserQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post Update User Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/user/updatequestionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> updateUserQuestionnaireData(
            @RequestBody UserQuestionnaireDataRequest request) {

        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .updateUserQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed UpdateUserQuestionnaireData operation.")
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Get User Questionnaire Rest API
     * @param request GetUserQuestionnaireRequest indicating questionnaire realted data
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/user/questionnaire/{Id}")
    public ResponseEntity<UserQuestionnaireData> getUserQuestionnaire(@RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getUserQuestionnaireData(request), HttpStatus.OK);
    }

    /**
     * Get User Questionnaires Response Rest API
     * @param request GetUserQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @GetMapping(value = "/user/questionnaires")
    public ResponseEntity<UserQuestionnaireData> getUserQuestionnaires(@RequestBody GetQuestionnaireRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getUserQuestionnaireDataList(request), HttpStatus.OK);
    }

    /**
     * Delete User Questionnaire Rest API
     * @param request DeleteUserQuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/user/deletequestionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteUserQuestionnaire(@RequestBody DeleteQuestionnaireRequest request) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteUserQuestionnaireData(request)
                                .getSuccess())
                        .setMessage("Executed DeleteUserQuestionnaire operation.")
                        .build(),
                HttpStatus.OK);
    }
}
