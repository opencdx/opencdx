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
package cdx.opencdx.media.config;

import cdx.opencdx.media.repository.OpenCDXMediaRepository;
import cdx.opencdx.media.service.OpenCDXFileStorageService;
import cdx.opencdx.media.service.impl.OpenCDXFileStorageLocalFileSystemImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Application Configuration
 */
@Configuration
@EnableConfigurationProperties
public class AppConfig {
    /**
     * Default Constructor
     */
    public AppConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Bean
    OpenCDXFileStorageService openCDXFileStorageService(
            Environment env, OpenCDXMediaRepository openCDXMediaRepository) {
        return new OpenCDXFileStorageLocalFileSystemImpl(env, openCDXMediaRepository);
    }
}
