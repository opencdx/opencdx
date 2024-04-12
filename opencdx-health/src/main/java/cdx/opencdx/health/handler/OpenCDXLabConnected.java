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

/**
 * Interface to implementation for communicating with OpenCDX Labs.
 */
public interface OpenCDXLabConnected {

    /**
     * Submit lab findings to OpenCDX Labs.
     * @param openCDXConnectedLabModel Lab connection information.
     * @param request the lab findings to submit
     * @return the response from OpenCDX Labs
     */
    LabFindingsResponse submitLabFindings(OpenCDXConnectedLabModel openCDXConnectedLabModel, LabFindings request);
}
