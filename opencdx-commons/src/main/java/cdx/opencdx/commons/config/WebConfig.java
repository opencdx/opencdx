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
package cdx.opencdx.commons.config;

import cdx.opencdx.commons.converters.OpenCDXIdentifierReadConverter;
import cdx.opencdx.commons.converters.OpenCDXIdentifierWriteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for Web.
 */
@Slf4j
@AutoConfiguration
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Default Constructor.
     */
    public WebConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Add converters for OpenCDXIdentifier.
     * @param registry The registry to add the converters to.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("Adding converters");
        registry.addConverter(new OpenCDXIdentifierReadConverter());
        registry.addConverter(new OpenCDXIdentifierWriteConverter());
    }
}
