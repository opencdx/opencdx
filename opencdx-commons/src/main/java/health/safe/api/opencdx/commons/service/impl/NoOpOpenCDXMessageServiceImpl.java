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
package health.safe.api.opencdx.commons.service.impl;

import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * No Operation Message service for use in Testing
 */
@Slf4j
@Observed(name = "opencdx")
public class NoOpOpenCDXMessageServiceImpl implements OpenCDXMessageService {

    /**
     * Default Constructor
     */
    public NoOpOpenCDXMessageServiceImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public void subscribe(String subject, OpenCDXMessageHandler handler) {
        log.warn("NoOpOpenCDXMessageServiceImpl::subscribe Called");
    }

    @Override
    public void unSubscribe(String subject) {
        log.warn("NoOpOpenCDXMessageServiceImpl::unSubscribe Called");
    }

    @Override
    public void send(String subject, Object object) {
        log.warn("NoOpOpenCDXMessageServiceImpl::send Called");
    }
}
