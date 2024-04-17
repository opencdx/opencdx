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

import cdx.opencdx.grpc.data.LabFindings;

/**
 * The OpenCDXConnectedLabMessageService interface provides methods to send LabFindings to the opencdx-connected-lab system.
 *
 * @implSpec This implementation is based on {@link cdx.opencdx.commons.service.OpenCDXMessageService}.
 */
public interface OpenCDXConnectedLabMessageService {
    /**
     * This method is responsible for submitting lab findings.
     *
     * @param findings The request object containing the lab findings.
     */
    void submitLabFindings(LabFindings findings);
}
