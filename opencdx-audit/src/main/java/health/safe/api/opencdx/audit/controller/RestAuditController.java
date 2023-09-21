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
package health.safe.api.opencdx.audit.controller;

import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditStatus;
import health.safe.api.opencdx.audit.handlers.OpenCDXAuditMessageHandler;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for mapping /audit/
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/audit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx-audit")
public class RestAuditController {
    private final OpenCDXAuditMessageHandler openCDXAuditMessageHandler;

    /**
     * Constructor to handle processing by using the OpenCDXAuditMessageHandler.
     * @param openCDXAuditMessageHandler Handler for processing AuditEvents
     */
    public RestAuditController(OpenCDXAuditMessageHandler openCDXAuditMessageHandler) {
        this.openCDXAuditMessageHandler = openCDXAuditMessageHandler;
    }

    /**
     * Post Hello Rest API
     * @param request HelloRequest indicating who to say hello to.
     * @return HelloReply with the hello message.
     */
    @PostMapping(value = "/event")
    public ResponseEntity<AuditStatus> event(@RequestBody AuditEvent request) {

        this.openCDXAuditMessageHandler.processAuditEvent(request);

        return new ResponseEntity<>(AuditStatus.newBuilder().setSuccess(true).build(), HttpStatus.OK);
    }
}
