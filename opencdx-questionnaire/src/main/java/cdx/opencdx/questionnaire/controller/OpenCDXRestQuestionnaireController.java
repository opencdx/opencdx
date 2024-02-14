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
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /questionnaire api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/ruleset/list")
    public ResponseEntity<RuleSetsResponse> getRuleSets(@RequestBody ClientRulesRequest request) {
        RuleSetsResponse ruleSets = openCDXQuestionnaireService.getRuleSets(request);
        return new ResponseEntity<>(ruleSets, HttpStatus.OK);
    }
    /**
     * Create RuleSet Rest API
     * @param request CreateRuleSetRequest indicating ruleSet realted data
     * @return CreateRuleSetResponse with the message.
     */
    @PostMapping("/ruleset")
    public ResponseEntity<CreateRuleSetResponse> createRuleSet(@RequestBody CreateRuleSetRequest request) {
        CreateRuleSetResponse ruleSet = openCDXQuestionnaireService.createRuleSet(request);
        return new ResponseEntity<>(ruleSet, HttpStatus.OK);
    }

    /**
     * Update RuleSet Rest API
     * @param request UpdateRuleSetRequest indicating ruleSet realted data
     * @return UpdateRuleSetResponse with the message.
     */
    @PutMapping("/ruleset")
    public ResponseEntity<UpdateRuleSetResponse> updateRuleSet(@RequestBody UpdateRuleSetRequest request) {
        UpdateRuleSetResponse ruleSet = openCDXQuestionnaireService.updateRuleSet(request);
        return new ResponseEntity<>(ruleSet, HttpStatus.OK);
    }
    /**
     * Get RuleSet Rest API
     * @param ruleSetId Identifier of ruleSet to get.
     * @return GetRuleSetResponse with the message.
     */
    @GetMapping(value = "/ruleset/{Id}")
    public ResponseEntity<GetRuleSetResponse> getRuleSet(@PathVariable(value = "Id") String ruleSetId) {
        GetRuleSetResponse ruleSet = openCDXQuestionnaireService.getRuleSet(
                GetRuleSetRequest.newBuilder().setId(ruleSetId).build());
        return new ResponseEntity<>(ruleSet, HttpStatus.OK);
    }
    /**
     * Delete RuleSet Rest API
     * @param ruleSetId Identifier of ruleSet to get.
     * @return DeleteRuleSetResponse with the message.
     */
    @DeleteMapping(value = "/ruleset/{Id}")
    public ResponseEntity<DeleteRuleSetResponse> deleteRuleSet(@PathVariable(value = "Id") String ruleSetId) {
        DeleteRuleSetResponse ruleSet = openCDXQuestionnaireService.deleteRuleSet(
                DeleteRuleSetRequest.newBuilder().setId(ruleSetId).build());
        return new ResponseEntity<>(ruleSet, HttpStatus.OK);
    }

    /**
     * Post Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PostMapping(value = "/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Questionnaire> createQuestionnaire(@RequestBody QuestionnaireRequest request) {

        return new ResponseEntity<>(openCDXQuestionnaireService.createQuestionnaire(request), HttpStatus.OK);
    }

    /**
     * Update Questionnaire Rest API
     * @param request QuestionnaireRequest indicating questionnaire realted data
     * @return SubmissionResponse with the message.
     */
    @PutMapping(value = "/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Questionnaire> updateQuestionnaire(@RequestBody QuestionnaireRequest request) {

        return new ResponseEntity<>(openCDXQuestionnaireService.updateQuestionnaire(request), HttpStatus.OK);
    }

    /**
     * Get Questionnaire Rest API
     * @param questionnaireId GetQuestionnaireRequest indicating questionnaire realted data
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/questionnaire/{Id}")
    public ResponseEntity<Questionnaire> getQuestionnaire(@PathVariable(value = "Id") String questionnaireId) {
        Questionnaire questionnaire = openCDXQuestionnaireService.getSubmittedQuestionnaire(
                GetQuestionnaireRequest.newBuilder().setId(questionnaireId).build());
        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    /**
     * Get Questionnaires Response Rest API
     * @param request GetSubmittedQuestionnaireList indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @PostMapping(value = "/questionnaire/list")
    public ResponseEntity<Questionnaires> getQuestionnaires(@RequestBody GetQuestionnaireListRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getSubmittedQuestionnaireList(request), HttpStatus.OK);
    }

    /**
     * Delete Questionnaire Rest API
     * @param id Identifier of questionnaire to delete.
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/questionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteQuestionnaireData(DeleteQuestionnaireRequest.newBuilder()
                                        .setId(id)
                                        .build())
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
    @PostMapping(value = "/system/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
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
    @PutMapping(value = "/system/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * @param id  Identifier of questionnaire to get.
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/system/questionnaire/{Id}")
    public ResponseEntity<SystemQuestionnaireData> getSystemQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                openCDXQuestionnaireService.getQuestionnaireData(
                        GetQuestionnaireRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Get Questionnaires Response Rest API
     * @param request GetQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @PostMapping(value = "/system/questionnaire/list")
    public ResponseEntity<SystemQuestionnaireData> getQuestionnaireDataList(
            @RequestBody GetQuestionnaireListRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getQuestionnaireDataList(request), HttpStatus.OK);
    }

    /**
     * Delete Questionnaire Rest API
     * @param id Identifier of questionnaire to delete.
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/system/questionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteSystemQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteQuestionnaireData(DeleteQuestionnaireRequest.newBuilder()
                                        .setId(id)
                                        .build())
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
    @PostMapping(value = "/client/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
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
    @PutMapping(value = "/client/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
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
     *  @param id  Identifier of questionnaire to get.
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/client/questionnaire/{Id}")
    public ResponseEntity<ClientQuestionnaireData> getClientQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                openCDXQuestionnaireService.getClientQuestionnaireData(
                        GetQuestionnaireRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Get Client Questionnaires Response Rest API
     * @param request GetClientQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @PostMapping(value = "/client/questionnaire/list")
    public ResponseEntity<ClientQuestionnaireData> getClientQuestionnaireDataList(
            @RequestBody GetQuestionnaireListRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getClientQuestionnaireDataList(request), HttpStatus.OK);
    }

    /**
     * Delete Client Questionnaire Rest API
     * @param id Identifier of questionnaire to delete.
     * @return SubmissionResponse with the message.
     */
    @DeleteMapping(value = "/client/questionnaire/{Id}")
    public ResponseEntity<SubmissionResponse> deleteClientQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                SubmissionResponse.newBuilder()
                        .setSuccess(openCDXQuestionnaireService
                                .deleteClientQuestionnaireData(DeleteQuestionnaireRequest.newBuilder()
                                        .setId(id)
                                        .build())
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
    @PostMapping(value = "/user/questionnaire", consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * Get User Questionnaire Rest API
     *  @param id  Identifier of questionnaire to get.
     * @return Questionnaire with the message.
     */
    @GetMapping(value = "/user/questionnaire/{Id}")
    public ResponseEntity<UserQuestionnaireData> getUserQuestionnaire(@PathVariable(value = "Id") String id) {
        return new ResponseEntity<>(
                openCDXQuestionnaireService.getUserQuestionnaireData(
                        GetQuestionnaireRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Get User Questionnaires Response Rest API
     * @param request GetUserQuestionnaireListRequest indicating questionnaire realted data
     * @return Questionnaires with the message.
     */
    @PostMapping(value = "/user/questionnaire/list")
    public ResponseEntity<UserQuestionnaireDataResponse> getUserQuestionnaireDataList(
            @RequestBody GetQuestionnaireListRequest request) {
        return new ResponseEntity<>(openCDXQuestionnaireService.getUserQuestionnaireDataList(request), HttpStatus.OK);
    }
}
