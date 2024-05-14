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
package cdx.opencdx.classification.analyzer.config;

import lombok.extern.slf4j.Slf4j;
import org.evrete.KnowledgeService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration class for opencdx-classification-analyzer.
 */
@Slf4j
@AutoConfiguration
@Configuration
public class AnalyzerConfig {
    /**
     * Default Constructor
     */
    public AnalyzerConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
        log.info("Classification Analyzer Configuration Initializing.");
    }

    /**
     * Knowledge Service Bean
     *
     * @return KnowledgeService
     */
    @Bean
    public KnowledgeService knowledgeService() {
        return new KnowledgeService();
    }
}
