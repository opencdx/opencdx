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
package cdx.opencdx.classification.config;

import cdx.opencdx.classification.handlers.OpenCDXClassificationMessageHandler;
import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Application Configuration
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
            "OpenCDXClassificationMessageHandler that is specific for handling Classification messages being received over messaging.")
    OpenCDXClassificationMessageHandler openCDXClassificationMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXClassificationService openCDXClassificationService,
            OpenCDXMessageService openCDXMessageService) {
        log.info("Instantiating OpenCDXClassificationMessageHandler.");
        return new OpenCDXClassificationMessageHandler(
                objectMapper, openCDXClassificationService, openCDXMessageService);
    }
}
