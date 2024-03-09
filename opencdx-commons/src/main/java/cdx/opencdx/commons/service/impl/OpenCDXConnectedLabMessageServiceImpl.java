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

import cdx.opencdx.commons.service.OpenCDXConnectedLabMessageService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.grpc.lab.connected.LabFindings;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Responsible for submitting lab findings to OpenCDXMessageService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXConnectedLabMessageServiceImpl implements OpenCDXConnectedLabMessageService {

    private final OpenCDXMessageService messageService;

    /**
     * Constructor taking the OpenCDXMessageService
     * @param messageService service to use for sending messages
     */
    public OpenCDXConnectedLabMessageServiceImpl(OpenCDXMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void submitLabFindings(LabFindings findings) {
        log.info("Submitting lab findings to OpenCDXMessageService");
        this.messageService.send(OpenCDXMessageService.CONNECTED_LAB_FINDINGS, findings);
    }
}
