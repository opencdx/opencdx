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
package cdx.opencdx.predictor.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Applicaiton Configuration
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    /**
     * Default Constructor
     */
    public AppConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Bean indicating the format of the message string
     * @param appProperties properties that contain the message format.
     * @return String providing the format.
     */
    @Bean
    @Description("Demonstration on how to document a bean.")
    public String format(AppProperties appProperties) {
        return appProperties.getFormat();
    }
}