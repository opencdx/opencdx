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

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.OpenCDXAnalysisEngine;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Factory bean for OpenCDXClassificationEngine
 */
@Slf4j
@Component
@ConfigurationProperties("opencdx")
public class OpenCDXClassificationEngineFactoryBean {

    @Setter
    private Map<String, String> engines;

    private final ApplicationContext applicationContext;

    /**
     * Constructor using the ApplicationContext
     * @param applicationContext the application context
     */
    public OpenCDXClassificationEngineFactoryBean(ApplicationContext applicationContext) {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
        this.applicationContext = applicationContext;
    }

    /**
     * Get the OpenCDXClassificationEngine by identifier.
     * @param identifier the engine identifier
     * @return the OpenCDXAnalysisEngine
     * @throws Exception if the engine is not found
     */
    public OpenCDXAnalysisEngine getEngine(String identifier) throws Exception {
        String beanName = engines.get(identifier);
        if (beanName == null) {
            beanName = engines.get("default");
        }
        log.warn("Engine: " + beanName);
        if (applicationContext.containsBean(beanName)) {
            return (OpenCDXAnalysisEngine) applicationContext.getBean(beanName);
        } else {
            throw new OpenCDXNotFound("OpenCDXClassificationEngineFactoryBean", 1, "Engine not found: " + identifier);
        }
    }
}
