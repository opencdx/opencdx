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
package health.safe.api.opencdx.media.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Resource configuration
 */
@Configuration
public class ResourceWebConfig implements WebMvcConfigurer {
    /**
     * URL path for file downloads.
     */
    public static final String MEDIA_DOWNLOADS = "/media/downloads/";

    final Environment environment;

    /**
     * Resource Web Configuraiton for granting access to the resources.
     * @param environment Environment variables
     */
    public ResourceWebConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String location = environment.getProperty("media.upload-dir", "./uploads/files");

        registry.addResourceHandler(MEDIA_DOWNLOADS + "**").addResourceLocations(location);
    }
}
