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
package cdx.opencdx.audit.config;

import cdx.opencdx.audit.handlers.OpenCDXAuditMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Applicaiton Configuration
 */
@Slf4j
@Configuration
public class AppConfig {
    /**
     * Default Constructor
     */
    public AppConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Bean
    @Description(
            "OpenCDXAuditMessageHandler that is specific for handling Audit messages being received over messaging.")
    OpenCDXAuditMessageHandler openCDXAuditMessageHandler(
            ObjectMapper objectMapper, OpenCDXMessageService openCDXMessageService) {
        log.info("Instantiating OpenCDXAuditMessageHandler.");
        return new OpenCDXAuditMessageHandler(objectMapper, openCDXMessageService);
    }
}
