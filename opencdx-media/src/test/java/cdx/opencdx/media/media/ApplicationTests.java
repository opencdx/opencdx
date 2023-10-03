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
package cdx.opencdx.media.media;

import cdx.opencdx.media.config.AppConfig;
import cdx.opencdx.media.config.ResourceWebConfig;
import health.safe.api.opencdx.commons.service.impl.NoOpOpenCDXMessageServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.util.UrlPathHelper;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        properties = "spring.cloud.config.enabled=false",
        classes = {AppConfig.class, ResourceWebConfig.class,  NoOpOpenCDXMessageServiceImpl.class})
class ApplicationTests {
    @Autowired
    AppConfig appConfig;

    @Autowired
    ResourceWebConfig resourceWebConfig;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(appConfig);
    }

    @Test
    void addResourceHandlers() {
        GenericWebApplicationContext appContext = new GenericWebApplicationContext();
        ResourceHandlerRegistry registry = new ResourceHandlerRegistry(appContext, new MockServletContext(),
                new ContentNegotiationManager(), new UrlPathHelper());

        Assertions.assertDoesNotThrow(() -> resourceWebConfig.addResourceHandlers(registry));
    }
}
