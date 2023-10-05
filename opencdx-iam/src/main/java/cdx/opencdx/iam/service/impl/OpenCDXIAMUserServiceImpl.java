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
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMUserServiceImpl implements OpenCDXIAMUserService {

    private final OpenCDXAuditService openCDXAuditService;

    /**
     * Constructor taking the a PersonRepository
     * @param openCDXAuditService Audit service for tracking FDA requirements
     */
    @Autowired
    public OpenCDXIAMUserServiceImpl(OpenCDXAuditService openCDXAuditService) {
        this.openCDXAuditService = openCDXAuditService;
    }
}
