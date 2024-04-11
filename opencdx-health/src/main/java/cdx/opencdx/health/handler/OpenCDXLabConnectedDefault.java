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
package cdx.opencdx.health.handler;

import cdx.opencdx.grpc.lab.connected.LabFindings;
import cdx.opencdx.grpc.lab.connected.LabFindingsResponse;
import cdx.opencdx.health.model.OpenCDXConnectedLabModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of OpenCDXLabConnected
 */
@Slf4j
public class OpenCDXLabConnectedDefault implements OpenCDXLabConnected {

    /**
     * Constructor for OpenCDXLabConnectedDefault
     */
    public OpenCDXLabConnectedDefault() {
        log.info("OpenCDXLabConnectedDefault initialized");
    }

    @Override
    public LabFindingsResponse submitLabFindings(
            OpenCDXConnectedLabModel openCDXConnectedLabModel, LabFindings request) {
        log.info(
                "Received request to submit lab findings for patient: {}",
                request.getBasicInfo().getPatientId());
        log.info("Lab Findings: {}", request);

        return LabFindingsResponse.newBuilder()
                .setLabFindingsId(request.getId())
                .build();
    }
}
